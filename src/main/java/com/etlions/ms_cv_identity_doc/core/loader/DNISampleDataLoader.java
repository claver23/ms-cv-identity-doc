package com.etlions.ms_cv_identity_doc.core.loader;

import com.etlions.ms_cv_identity_doc.core.model.DNIDataModel;

import java.time.LocalDate;
import java.util.Random;

public class DNISampleDataLoader {

    public DNISampleDataLoader() {
    }

    public DNIDataModel loadSingleSampleData() {
        return DNIDataModel.builder()
                .isDuplicate(true)
                .dni(String.format("%08d", new Random().nextInt(100000000)))
                .verifierCode("2")
                .firstLastName("Pérez")
                .secondLastName("Guzmán")
                .firstName("Juan")
                .secondName("Carlos")
                .birthPlace("140117")
                .sex("M")
                .maritalStatus("S")
                .registrationDate(LocalDate.of(2008, 10, 10))
                .birthDate(LocalDate.of(1996, 12, 10))
                .emissionDate(LocalDate.of(2025, 1, 1))
                .expirationDate(LocalDate.of(2030, 1, 1))
                .build();
    }
}
