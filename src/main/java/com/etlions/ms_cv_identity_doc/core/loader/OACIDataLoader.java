package com.etlions.ms_cv_identity_doc.core.loader;

import com.etlions.ms_cv_identity_doc.core.model.DNIDataModel;
import com.etlions.ms_cv_identity_doc.core.model.OACIDataModel;
import com.etlions.ms_cv_identity_doc.core.util.OACIConstants;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Random;

public class OACIDataLoader {
    private static final Random RANDOM = new Random();

    public static OACIDataModel generateOACI(DNIDataModel dniDataModel) {

        validateRequiredFields(dniDataModel);
        String[] checkDigits = generateCheckDigits(OACIConstants.CHECK_DIGIT_COUNT);

        String fullSurname = normalizeOACI(dniDataModel.getFirstLastName());
        String fullName = buildFullName(dniDataModel);

        String line1 = buildLine1(dniDataModel, checkDigits[0]);
        String line2 = buildLine2(dniDataModel, checkDigits[1], checkDigits[2], checkDigits[3]);
        String line3 = buildLine3(fullSurname, fullName);

        OACIDataModel oaci = new OACIDataModel();
        oaci.setFirstLine(line1.split(""));
        oaci.setSecondLine(line2.split(""));
        oaci.setThirdLine(line3.split(""));
        return oaci;
    }

    private static String[] generateCheckDigits(int count) {
        String[] digits = new String[count];
        for (int i = 0; i < count; i++) {
            digits[i] = String.valueOf(RANDOM.nextInt(10));
        }
        return digits;
    }

    private static void validateRequiredFields(DNIDataModel model) {
        if (model.getDni() == null || model.getBirthDate() == null || model.getSex() == null)
            throw new IllegalArgumentException("DNI, birthDate, and sex are required to generate OACI.");
    }

    private static String buildLine1(DNIDataModel dniDataModel, String check1) {
        String documentType = OACIConstants.DOCUMENT_TYPE_DNI + OACIConstants.FILLER_CHAR;
        String countryCode = formatCode(OACIConstants.DEFAULT_COUNTRY_CODE_DNI);
        String dni = padRight(dniDataModel.getDni(), OACIConstants.DNI_MAX_LENGTH, OACIConstants.FILLER_CHAR);

        return padRight(
                documentType + countryCode + dni + check1,
                OACIConstants.MRZ_LINE_LENGTH,
                OACIConstants.FILLER_CHAR);
    }

    private static String buildLine2(DNIDataModel dniDataModel, String check2, String check3, String check4) {
        String birthDate = OACIConstants.MRZ_DATE_FORMATTER.format(dniDataModel.getBirthDate());
        String expiryDate = isOlderThan60(dniDataModel.getBirthDate())
                ? OACIConstants.EXPIRY_DATE_OLDER_THAN_60
                : OACIConstants.MRZ_DATE_FORMATTER.format(dniDataModel.getExpirationDate());

        String countryCode = formatCode(OACIConstants.DEFAULT_COUNTRY_CODE_DNI);
        String baseLine2 = birthDate + check2 + dniDataModel.getSex().toUpperCase().charAt(0) + expiryDate + check3 + countryCode;

        return padRight(baseLine2, OACIConstants.MRZ_LINE_LENGTH - 1, OACIConstants.FILLER_CHAR) + check4;
    }

    private static String buildLine3(String fullSurname, String fullName) {
        char[] fillers = {OACIConstants.FILLER_CHAR, OACIConstants.FILLER_CHAR};
        return padRight(
                fullSurname + String.valueOf(fillers) + fullName,
                OACIConstants.MRZ_LINE_LENGTH,
                OACIConstants.FILLER_CHAR);
    }

    private static String buildFullName(DNIDataModel dniDataModel) {
        String firstName = normalizeOACI(dniDataModel.getFirstName());
        String secondName = dniDataModel.getSecondName() != null && !dniDataModel.getSecondName().isBlank()
                ? OACIConstants.FILLER_CHAR + normalizeOACI(dniDataModel.getSecondName())
                : "";
        return firstName + secondName;
    }

    private static boolean isOlderThan60(LocalDate birthDate) {
        return birthDate != null && birthDate.plusYears(OACIConstants.AGE_THRESHOLD_YEARS).isBefore(LocalDate.now());
    }

    private static String normalizeOACI(String input) {
        if (input == null) return "";
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^A-Z<]", "")
                .replaceAll(" ",  String.valueOf(OACIConstants.FILLER_CHAR))
                .toUpperCase();
    }

    private static String formatCode(String code) {
        return (code == null || code.isBlank()) ? OACIConstants.DEFAULT_COUNTRY_CODE_DNI : code.toUpperCase();
    }

    private static String padRight(String text, int length, char filler) {
        return text.length() >= length ? text.substring(0, length)
                : text + String.valueOf(filler).repeat(length - text.length());
    }
}