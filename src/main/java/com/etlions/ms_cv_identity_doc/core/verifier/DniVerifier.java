package com.etlions.ms_cv_identity_doc.core.verifier;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class DniVerifier {

    private static final int[] MULTIPLICADORES = {3, 2, 7, 6, 5, 4, 3, 2};
    private static final char[] SERIE_NUMERICA   = {'6', '7', '8', '9', '0', '1', '1', '2', '3', '4', '5'};
    private static final char[] SERIE_ALFABETICA = {'K', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

    private static final Predicate<String> VALID_DNI_FORMAT =
            dni -> dni != null && dni.matches("\\d{8}") && !dni.matches("0{8}");

    public static String calculateVerifierNumber(String dni, boolean esMayorDe60) {
        if (!VALID_DNI_FORMAT.test(dni)) {
            throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos numéricos.");
        }

        int suma = IntStream.range(0, 8)
                .map(i -> Character.getNumericValue(dni.charAt(i)) * MULTIPLICADORES[i])
                .sum();

        int position = calculatePosition(suma);
        char[] serial = esMayorDe60 ? SERIE_ALFABETICA : SERIE_NUMERICA;

        if (position >= 0 && position < serial.length) {
            return String.valueOf(serial[position]);
        }

        throw new IllegalStateException("La posición del verificador está fuera del rango permitido.");
    }

    private static int calculatePosition(int suma) {
        int result = 11 - (suma % 11);
        if (result == 10) return 0;
        if (result == 11) return 1;
        return result;
    }
}
