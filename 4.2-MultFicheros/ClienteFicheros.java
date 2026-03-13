import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteFicheros {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 1500;
        Scanner teclado = new Scanner(System.in);

        try (Socket socket = new Socket(host, puerto);
             DataInputStream entrada = new DataInputStream(socket.getInputStream());
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream()))
        {
            System.out.print("Introduce el nombre del fichero que deseas descargar: ");
            String nombreFichero = teclado.nextLine();
            salida.writeUTF(nombreFichero);
            salida.flush();
            String respuesta = entrada.readUTF();
            if (respuesta.equals("OK"))
            {
                System.out.println("--- Contenido del fichero ---");
                String contenido;
                while (!(contenido = entrada.readUTF()).equals("FIN_FICHERO")) {
                    System.out.println(contenido);
                }
                System.out.println("-----------------------------");
            } else {
                System.out.println(respuesta);
            }

        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        } finally {
            teclado.close();
        }
    }
}