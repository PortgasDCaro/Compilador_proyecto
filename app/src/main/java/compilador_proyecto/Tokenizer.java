package compilador_proyecto;

import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    public static List<String> tokenize(String linea) { //creacion de lista para guardas los tokens
        return Arrays.asList(linea.trim().split("\\s+")); //Separa cada token de el otro y los guarda
    }

    //Declaracion de nuestros tokens SET ADD PRINT
    public static String identificarTipo(String token) {
        switch (token.toUpperCase()) { // De nuevo por si antes no funciono pone el imput en mayusculas 
            case "SET": return "SET";
            case "ADD": return "ADD";
            case "PRINT": return "PRINT";
            default:

                //Control de errorees en capa 8 haciendo que no sea KeySensitive
                if (token.matches("[a-zA-Z][a-zA-Z0-9]*")) return "ID"; // si concuerda con los parametros
                // Números válidos (positivos o negativos)
                if (token.matches("-?\\d+")) return "NUM"; // Si el token concuerda con un numero negativo o no, retorna NUM
                return "DESCONOCIDO";
        }
    }
}