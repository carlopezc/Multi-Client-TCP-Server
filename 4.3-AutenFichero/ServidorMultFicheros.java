import java.io.*;
import java.net.*;

public class ServidorMultFicheros {
    public static void main(String[] args) {
        int puerto = 1600;

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor de control iniciado en el puerto " + puerto);

            while (true) 
            {
                Socket cliente = servidor.accept();
                new HiloControl(cliente).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class HiloControl extends Thread {
    private Socket socket;
    //Definimos el usuario y contraseña
    private final String USER_VALIDO = "carlota";
    private final String PASS_VALIDO = "hola_pswd";

    public HiloControl(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream entrada = new DataInputStream(socket.getInputStream());
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {

            //Proceso de autenticación
            boolean autenticado = false;
            while (!autenticado) {
                String user = entrada.readUTF();
                String pass = entrada.readUTF();
                
                if (user.equals(USER_VALIDO) && pass.equals(PASS_VALIDO)) {
                    autenticado = true;
                    salida.writeUTF("OK");
                } else {
                    salida.writeUTF("ERROR");
                }
            }
            
            //Comandos
            boolean salir = false;
            while (!salir) {
                String comando = entrada.readUTF();
                
                switch (comando) {
                    //Comando listar, recupero archivos e imprimo nombres
                    case "LS":
                        File dir = new File(".");
                        String[] archivos = dir.list();
                        salida.writeInt(archivos.length);
                        for (String f : archivos)
                            salida.writeUTF(f);
                        break;
                    
                    //Comando get, lista info del archivo solicitado
                    case "GET":
                        String nombre = entrada.readUTF();
                        File f = new File(nombre);
                        if (f.exists() && f.isFile()) {
                            salida.writeUTF("OK");
                        BufferedReader br = new BufferedReader(new FileReader(f));
                        String linea;
                        while ((linea = br.readLine()) != null)
                            salida.writeUTF(linea);
                        salida.writeUTF("FIN_FICHERO");
                        br.close();
                        } else {
                            salida.writeUTF("ERROR");
                        }
                        break;
                    
                    //Comando exit, pone flag salir en true para salir al hacer la comprobación del bucle
                    case "EXIT":
                        salir = true;
                        break;
                        
                }
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado.");
        }
    }
}
           