package compilador_proyecto;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        
        //Se declara la Entrada y Salida con su ruta
        String rutaArchivoEntrada = "app/input/compile.txt";
        String rutaArchivoSalida = "app/output/result.txt";

        //Un mapa para guardar las variables
        Map<String, Integer> mapaVariables = new HashMap<>();

        //inicializa los archivosd E/S
        try (BufferedReader lectorArchivo = new BufferedReader(new FileReader(rutaArchivoEntrada));
             BufferedWriter escritorArchivo = new BufferedWriter(new FileWriter(rutaArchivoSalida))) {


            //sirve para leer las lineas de el archivo 1 por 1
            String linea;
            int indicadorLinea = 1;

            //Para cada linea en el readline diferente de nada (verifica que haya algo)
            while ((linea = lectorArchivo.readLine()) != null) { // salta los espacios vacios al inicio o final de la linea
                linea = linea.trim(); //salta os espacios vacios en medio de la linea
                if (linea.isEmpty()) continue; //verifica si esta vacio o no

                //Cabezera impresa en el archivo S
                escritorArchivo.write("Tokens para la línea: " + linea + "\n");
                List<String> tokens = Tokenizer.tokenize(linea); // se tokeniza la linea actual con tokenizer (separar token)

                int indicadorColumna = 0; //Se inicia en 0 (buena practica)

                for (String token : tokens) { // Para cada token en tokens genera un formato de salida tipo string string numero numero
                    escritorArchivo.write(String.format("LexToken(%s,%s,%d,%d)\n",
                        Tokenizer.identificarTipo(token), token, indicadorLinea, indicadorColumna));

                    if (Tokenizer.identificarTipo(token).equals("DESCONOCIDO")) { // se relaciona a tokenizer
                        escritorArchivo.write("Error léxico: token inválido '" + token + "' en la columna " + indicadorColumna + "\n\n");
                        break;  // no seguimos si hay error léxico
                    }
 
                    indicadorColumna += token.length(); // el indicar de la columna es en base a la longitud del token
                }

                try { // llama a la clase parser del parser para darle la estructura
                    String result = Parser.parse(tokens, mapaVariables);
                    escritorArchivo.write("Resultado del análisis:\n" + result + "\n\n"); // encabezado contextual
                } catch (Exception e) {
                    escritorArchivo.write("Error: " + e.getMessage() + "\n\n");
                }

                indicadorLinea++;
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Object getGreeting() {
        throw new UnsupportedOperationException("Unimplemented method 'getGreeting'");
    }
}