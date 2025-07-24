package com.etlions.ms_cv_identity_doc.business.usecase;


import java.math.BigInteger;

public interface GenerateDNIUseCase {
    String generateDNISampleDataSet(BigInteger amount);
}
