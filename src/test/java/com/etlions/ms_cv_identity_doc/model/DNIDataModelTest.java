package com.etlions.ms_cv_identity_doc.model;

import com.etlions.ms_cv_identity_doc.core.exception.IncompleteOACIDataException;
import com.etlions.ms_cv_identity_doc.core.loader.OACIDataLoader;
import com.etlions.ms_cv_identity_doc.core.model.DNIDataModel;
import com.etlions.ms_cv_identity_doc.core.model.OACIDataModel;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DNIDataModelTest {

    @Test
    void build_shouldCreateModelWithValidFields() {
        try (MockedStatic<OACIDataLoader> oaciMock = org.mockito.Mockito.mockStatic(OACIDataLoader.class)) {
            oaciMock.when(() -> OACIDataLoader.generateOACI(org.mockito.Mockito.any()))
                    .thenReturn(new OACIDataModel());

            DNIDataModel model = DNIDataModel.builder()
                    .dni("12345678")
                    .firstLastName("Perez")
                    .firstName("Juan")
                    .birthDate(LocalDate.of(1990, 1, 1))
                    .expirationDate(LocalDate.of(2030, 1, 1))
                    .sex("M")
                    .build();

            assertNotNull(model);
            assertNotNull(model.getOaciDataModel());
            assertEquals("12345678", model.getDni());
        }
    }

    @Test
    void build_shouldThrowExceptionWhenMissingRequiredFields() {
        DNIDataModel.DNIDataModelBuilder builder = DNIDataModel.builder()
                .dni("12345678")
                .firstLastName("Perez")
                // Falta firstName
                .birthDate(LocalDate.of(1990, 1, 1))
                .expirationDate(LocalDate.of(2030, 1, 1))
                .sex("M");

        IncompleteOACIDataException ex = assertThrows(
                IncompleteOACIDataException.class,
                builder::build
        );
        assertTrue(ex.getMessage().contains("firstName=null"));
    }
}