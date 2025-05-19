
package compilador_proyecto;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String rutaArchivoEntrada = "app/input/compile.txt";
        String rutaArchivoSalida = "app/output/result.txt";

        Map<String, Integer> mapaVariables = new HashMap<>();

        try (BufferedReader lectorArchivo = new BufferedReader(new FileReader(rutaArchivoEntrada));
             BufferedWriter escritorArchivo = new BufferedWriter(new FileWriter(rutaArchivoSalida))) {

            String linea;
            int indicadorLinea = 1;

            while ((linea = lectorArchivo.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                escritorArchivo.write("Tokens para la línea: " + linea + "\n");
                List<String> tokens = Tokenizer.tokenize(linea);

                int indicadorColumna = 0;

                for (String token : tokens) {
                    escritorArchivo.write(String.format("LexToken(%s,%s,%d,%d)\n",
                        Tokenizer.identificarTipo(token), token, indicadorLinea, indicadorColumna));

                    if (Tokenizer.identificarTipo(token).equals("DESCONOCIDO")) {
                        escritorArchivo.write("Error léxico: token inválido '" + token + "' en la columna " + indicadorColumna + "\n\n");
                        break;  // no seguimos si hay error léxico
                    }
 
                    indicadorColumna += token.length();
                }

                try {
                    String result = Parser.parse(tokens, mapaVariables);
                    escritorArchivo.write("Resultado del análisis:\n" + result + "\n\n");
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