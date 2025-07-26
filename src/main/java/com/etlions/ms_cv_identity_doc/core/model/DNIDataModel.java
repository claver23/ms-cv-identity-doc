package com.etlions.ms_cv_identity_doc.core.model;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.time.LocalDate;

@Setter
@Getter
public class DNIDataModel {
    /* Detalle Parte superior */
    private String isDuplicate;
    private String dni;
    private String verifierCode;

    /* Detalle Parte media izquierda */

    private InputStream photo1;
    private String firstLastName;
    private String secondLastName;
    private String firstName;
    private String secondName;
    private LocalDate birthDate;
    private String birthPlace;
    private String sex;
    private String maritalStatus;

    /* Detalle Parte media izquierda */
    private InputStream signature;
    private LocalDate registrationDate;
    private LocalDate emissionDate;
    private LocalDate expirationDate;
    private InputStream photo2;

    /* Detalle Parte inferior */
    private OACIDataModel oaciDataModel;

}
