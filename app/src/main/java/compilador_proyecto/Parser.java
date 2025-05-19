package compilador_proyecto;

import java.util.*;

public class Parser {
    public static String parse(List<String> listaTokens, Map<String, Integer> memory) throws Exception {
        if (listaTokens.size() < 2)
            throw new Exception("Error sintáctico: instrucción incompleta");

        String comando = listaTokens.get(0).toUpperCase();

        switch (comando) {
            case "SET":
                if (listaTokens.size() != 3)
                    throw new Exception("Error sintáctico: SET requiere 2 argumentos");

                String varSet = listaTokens.get(1);
                String valorSet = listaTokens.get(2);

                if (!esIdentificadorValido(varSet)) {
                    // 1a) y valorSet sí es ID, es intercambio de parámetros → sintáctico
                    if (esIdentificadorValido(valorSet)) {
                        throw new Exception(
                            "Error sintáctico: parámetros intercambiados, se esperaba <ID> <NUM> y se encontró <NUM> <ID>"
                        );
                    }
                    // 1b) en cualquier otro caso varSet no puede ser ID → léxico
                    throw new Exception("Error léxico: identificador inválido '" + varSet + "'");
                }


                if (!esNumeroValido(valorSet))
                    throw new Exception("Error léxico: valor no numérico '" + valorSet + "'");

                memory.put(varSet, Integer.parseInt(valorSet));
                return "OK";

            case "ADD":
                if (listaTokens.size() != 3)
                    throw new Exception("Error sintáctico: ADD requiere 2 argumentos");

                String varAdd = listaTokens.get(1);
                String valorAdd = listaTokens.get(2);

                if (!memory.containsKey(varAdd))
                    throw new Exception("Error semántico: variable no definida: '" + varAdd + "'");

                if (!esNumeroValido(valorAdd))
                    throw new Exception("Error léxico: valor no numérico '" + varAdd + "'");

                memory.put(varAdd, memory.get(varAdd) + Integer.parseInt(valorAdd));
                return "OK";

            case "PRINT":
                String varPrint = listaTokens.get(1);

                if (listaTokens.size() != 2)
                    throw new Exception("Error sintáctico: PRINT requiere 1 argumento");

                if (!memory.containsKey(varPrint))
                    throw new Exception("Error sémantico: variable no definida: '" + varPrint + "'");

                return String.valueOf(memory.get(varPrint));

            default:
                throw new Exception("Error léxico: comando no reconocido: " + comando);
        }
    }

    private static boolean esIdentificadorValido(String token) {
        return token.matches("[a-zA-Z]+");
    }

    private static boolean esNumeroValido(String token) {
        return token.matches("-?\\d+"); // permite negativos también
    }
}
