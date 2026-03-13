import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteFicheros {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 1600;
        Scanner teclado = new Scanner(System.in);

        try (Socket socket = new Socket(host, puerto);
             DataInputStream entrada = new DataInputStream(socket.getInputStream());
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream()))
        {
            
            //Autenticación
            boolean acceso = false;
            while (!acceso) {
                //Solicito usuario y contraseña
                System.out.println("Usuario : ");
                String user = teclado.nextLine();
                System.out.println("Contraseña : ");
                String pass = teclado.nextLine();
                salida.writeUTF(user);
                salida.writeUTF(pass);
                //Si la entrada es correcta imprimimos login correcto
                if (entrada.readUTF().equals("OK")) {
                    acceso = true;
                    System.out.println("Login correcto.");
                } else {
                    System.out.println("Login incorrecto.");
                }
            }
            
            //Menu
            int opcion = 0;
            while (opcion != 3) {
                System.out.println("\n1. Listar | 2. Ver Archivo | 3. Salir");
                
                try {
                opcion = Integer.parseInt(teclado.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Por favor, introduce un número válido (1, 2 o 3).");
                    opcion = 0;
                    continue;   // Saltamos al inicio del bucle while
                }
                if (opcion == 1) {
                    salida.writeUTF("LS");
                    int total = entrada.readInt();
                    for (int i = 0; i < total; i++)
                        System.out.println("- " + entrada.readUTF());
                } else if (opcion == 2) {
                    salida.writeUTF("GET");
                    System.out.print("Nombre archivo: ");
                    salida.writeUTF(teclado.nextLine());
                    if (entrada.readUTF().equals("OK")) {
                        String linea;
                        while (!(linea = entrada.readUTF()).equals("FIN_FICHERO"))
                            System.out.println(linea);
                    } else {
                        System.out.println("Archivo no encontrado.");
                    }
                } else if (opcion == 3) {
                    salida.writeUTF("EXIT");
                } else {
                    System.out.println("Introduce una opción válida");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }           
    }
}