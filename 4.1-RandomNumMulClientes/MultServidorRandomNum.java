import java.io.*;
import java.net.*;

public class MultServidorRandomNum
{
    public static void main(String[] args)
    {
        int puerto = 2000;
        
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor escuchando en el puerto " + puerto);
            
            //La modificacion ha sido meter en un bucle el servidor.accept y delegar cada conexion a un hilo
            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado desde: " + cliente.getInetAddress());
                Hilo hilo = new Hilo(cliente);
                hilo.start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
 
//He creado esta clase para programar la lógica de cada cliente
class Hilo extends Thread {
    private Socket socket;

    public Hilo(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        int numeroSecreto = (int) (Math.random() * 101);
        //Genero número secreto por hilo/cliente
        System.out.println("Hilo " + this.getName() + ": Número secreto generado: " + numeroSecreto);

        try (DataInputStream entrada = new DataInputStream(socket.getInputStream());
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {

            boolean acertado = false;
            //Bucle hasta acertar el número
            while (!acertado) {
                int intento = entrada.readInt();
                System.out.println("Hilo " + this.getName() + " recibió: " + intento);

                if (intento < numeroSecreto) {
                    salida.writeUTF("El número secreto es MAYOR");
                } else if (intento > numeroSecreto) {
                    salida.writeUTF("El número secreto es MENOR");
                } else {
                    salida.writeUTF("¡CORRECTO! Has adivinado el número");
                    acertado = true;
                }
                salida.flush();
            }
        } catch (IOException e) {
            System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}