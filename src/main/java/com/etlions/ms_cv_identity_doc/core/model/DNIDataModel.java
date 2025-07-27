package com.etlions.ms_cv_identity_doc.core.model;

import com.etlions.ms_cv_identity_doc.core.exception.IncompleteOACIDataException;
import com.etlions.ms_cv_identity_doc.core.loader.OACIDataLoader;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Setter
@Getter
@Builder(builderClassName = "DNIDataModelBuilder", buildMethodName = "buildInternal")
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



    public static class DNIDataModelBuilder {
        private static final List<Predicate<DNIDataModel>> oaciConditions = List.of(
                model -> model.getDni() != null,
                model -> model.getFirstLastName() != null,
                model -> model.getFirstName() != null,
                model -> model.getBirthDate() != null,
                model -> model.getExpirationDate() != null,
                model -> model.getSex() != null
        );

        public DNIDataModel build() {
            DNIDataModel model = this.buildInternal();

            if (!isOACIEligible(model)) {
                throw new IncompleteOACIDataException(String.format(
                        "❌ No se pudo generar el modelo OACI: Faltan campos obligatorios. dni=%s, firstLastName=%s, firstName=%s, birthDate=%s, expirationDate=%s, sex=%s",
                        model.getDni(),
                        model.getFirstLastName(),
                        model.getFirstName(),
                        model.getBirthDate(),
                        model.getExpirationDate(),
                        model.getSex()
                ));
            }

            model.setOaciDataModel(OACIDataLoader.generateOACI(model));
            return model;
        }

        private boolean isOACIEligible(DNIDataModel model) {
            return oaciConditions.stream().allMatch(predicate -> predicate.test(model));
        }
    }
}
