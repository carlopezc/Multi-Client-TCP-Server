import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteRandomNum
{
    public static void main(String[] args)
    {
        String host = "localhost";
        int puerto = 2000;
        Scanner teclado = new Scanner(System.in);

        try (Socket socket = new Socket(host, puerto);
             DataInputStream entrada = new DataInputStream(socket.getInputStream());
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream()))
        {
            System.out.println("Conectado al servidor de adivinanza en " + host + ":" + puerto);
            String respuestaServidor = "";

            while (!respuestaServidor.contains("CORRECTO")) {
                System.out.print("Introduce un número (0-100): ");
                int numero = teclado.nextInt();

                salida.writeInt(numero);
                salida.flush();
 
                respuestaServidor = entrada.readUTF();
                System.out.println("Servidor dice: " + respuestaServidor);
            }
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        } finally {
            teclado.close();
        }
    }
}