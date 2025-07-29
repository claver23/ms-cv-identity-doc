package com.etlions.ms_cv_identity_doc.application.usecase;

import java.math.BigInteger;

public interface GeneratePassportUseCase {
    String generatePassportSampleDataSet(BigInteger amount);
}
