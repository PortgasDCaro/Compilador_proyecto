package compilador_proyecto;

import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    public static List<String> tokenize(String linea) {
        return Arrays.asList(linea.trim().split("\\s+"));
    }

    public static String identificarTipo(String token) {
        switch (token.toUpperCase()) {
            case "SET": return "SET";
            case "ADD": return "ADD";
            case "PRINT": return "PRINT";
            default:
                if (token.matches("[a-zA-Z][a-zA-Z0-9]*")) return "ID";
                // Números válidos (positivos o negativos)
                if (token.matches("-?\\d+")) return "NUM";
                return "DESCONOCIDO";
        }
    }
}