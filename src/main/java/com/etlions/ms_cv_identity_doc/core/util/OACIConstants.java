package com.etlions.ms_cv_identity_doc.core.util;


import java.time.format.DateTimeFormatter;

public class OACIConstants {
    // MRZ (Machine Readable Zone) constants
    public static final int MRZ_LINE_LENGTH = 30;
    public static final DateTimeFormatter MRZ_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    // Document type constants
    public static final String DEFAULT_COUNTRY_CODE_DNI = "PER";
    public static final String DOCUMENT_TYPE_DNI = "I";

    // Formatting constants
    public static final char FILLER_CHAR = '<';
    public static final int DNI_MAX_LENGTH = 9;
    public static final int CHECK_DIGIT_COUNT = 4;

    // Special case values
    public static final String EXPIRY_DATE_OLDER_THAN_60 = "000101";
    public static final int AGE_THRESHOLD_YEARS = 60;

    // MRZ line structure
    public static final int LINE1_DOC_TYPE_POSITION = 0;
    public static final int LINE1_COUNTRY_CODE_POSITION = 2;
    public static final int LINE1_DNI_POSITION = 5;

    private OACIConstants() {
        // Private constructor to prevent instantiation
    }
}