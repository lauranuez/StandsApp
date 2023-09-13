import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class fileRoute {
    public static void SetFileRoute(){
        String filePath = "/files/ruta.txt"; // Nombre del archivo de texto que contiene la ruta
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Supongamos que la línea contiene la ruta del archivo a abrir
                // Puedes realizar la operación deseada con la ruta, por ejemplo:
                // Abre el archivo, procesa su contenido, etc.
                System.out.println("Ruta del archivo: " + linea);

                // Aquí puedes realizar otras operaciones con la ruta, como abrir el archivo
                // o procesar su contenido según tus necesidades.
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al leer el archivo.");
        }
    }

}
