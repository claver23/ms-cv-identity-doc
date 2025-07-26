package com.etlions.ms_cv_identity_doc.core.loader;

import com.etlions.ms_cv_identity_doc.core.model.OACIDataModel;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OACIDataLoader {

    private static final int MRZ_LINE_LENGTH = 30;
    private static final DateTimeFormatter MRZ_DATE = DateTimeFormatter.ofPattern("yyMMdd");
    private static final Random RANDOM = new Random();

    public static OACIDataModel generateOACI(
            String documentNumber,
            String nationality,
            String firstLastName,
            String firstName,
            String secondName,
            LocalDate birthDate,
            LocalDate expirationDate,
            String sex
    ) {
        String check1 = String.valueOf(RANDOM.nextInt(10));
        String check2 = String.valueOf(RANDOM.nextInt(10));
        String check3 = String.valueOf(RANDOM.nextInt(10));
        String check4 = String.valueOf(RANDOM.nextInt(10));

        String fullSurname = normalizeOACI(firstLastName);
        String fullName = normalizeOACI(firstName) + (secondName != null && !secondName.isBlank() ? "<" + normalizeOACI(secondName) : "");

        String line1 = padRight(
                "I<" + formatCode(nationality) + padRight(documentNumber, 9, '<') + check1, // 4 = dummy check digit
                MRZ_LINE_LENGTH, '<');

        String birth = MRZ_DATE.format(birthDate);
        String expiry = isOlderThan60(birthDate)
                ? "000101"
                : MRZ_DATE.format(expirationDate);

        String baseLine2 = birth + check2 + sex.toUpperCase().charAt(0) + expiry + check3 + formatCode(nationality);
        String line2 = padRight(baseLine2, MRZ_LINE_LENGTH - 1, '<') + check4;

        String line3 = padRight(
                fullSurname + "<<" + fullName,
                MRZ_LINE_LENGTH, '<');

        OACIDataModel oaci = new OACIDataModel();
        oaci.setFirstLine(line1.split(""));
        oaci.setSecondLine(line2.split(""));
        oaci.setThirdLine(line3.split(""));
        return oaci;
    }

    private static boolean isOlderThan60(LocalDate birthDate) {
        return birthDate != null && birthDate.plusYears(60).isBefore(LocalDate.now());
    }

    private static String normalizeOACI(String input) {
        if (input == null) return "";
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^A-Z<]", "")
                .replaceAll(" ", "<")
                .toUpperCase();
    }

    private static String formatCode(String code) {
        return (code == null || code.isBlank()) ? "PER" : code.toUpperCase();
    }

    private static String padRight(String text, int length, char filler) {
        return text.length() >= length ? text.substring(0, length)
                : text + String.valueOf(filler).repeat(length - text.length());
    }

    public static void main(String[] args) {
        OACIDataModel oaci = generateOACI(
                "72604199",
                "PER",
                "GARCIA",
                "MARIA",
                "GUADALUPE",
                LocalDate.of(1960, 5, 23),
                LocalDate.of(2025, 1, 1),
                "F"
        );

        System.out.println("Línea 1: " + String.join("", oaci.getFirstLine()));
        System.out.println("Línea 2: " + String.join("", oaci.getSecondLine()));
        System.out.println("Línea 3: " + String.join("", oaci.getThirdLine()));
    }
}
