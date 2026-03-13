import java.io.*;
import java.net.*;

public class ServidorMultFicheros {
    public static void main(String[] args) {
        int puerto = 1500;

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor de ficheros concurrente iniciado en el puerto " + puerto);

            while (true) 
            {
                Socket cliente = servidor.accept();
                System.out.println("Nuevo cliente conectado para descarga.");

                new HiloFichero(cliente).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}

class HiloFichero extends Thread {
    private Socket socket;

    public HiloFichero(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream entrada = new DataInputStream(socket.getInputStream());
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {

            String nombreFichero = entrada.readUTF();
            System.out.println("Hilo " + this.getName() + " procesando: " + nombreFichero);

            File fichero = new File(nombreFichero);
            if (fichero.exists() && fichero.isFile()) {
                salida.writeUTF("OK");
                
                // Leo el fichero línea a línea y lo envio
                try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        salida.writeUTF(linea);
                    }
                    //Envío señal para que el cliente sepa cuando dejar de escuchar el flujo de entrada
                    salida.writeUTF("FIN_FICHERO");
                }
            } else {
                salida.writeUTF("ERROR: El fichero no existe en el servidor.");
            }
            salida.flush();

        } catch (IOException e) {
            System.err.println("Error al gestionar la descarga: " + e.getMessage());
        } finally {
            //Uso bloque finally para asegurarme que siempre se va a cerrar el socket
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}