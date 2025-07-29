package com.etlions.ms_cv_identity_doc.core.util;

import java.time.format.DateTimeFormatter;

public class Constant {

    public static final DateTimeFormatter DNI_DETAIL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter DNI_BIRTH_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy");

    private Constant() {
        throw new AssertionError("Clase de constantes no debe ser instanciada");
    }
}


