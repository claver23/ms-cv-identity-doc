package com.etlions.ms_cv_identity_doc.application.impl;

import com.etlions.ms_cv_identity_doc.application.GenerateDocumentDataSetBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Slf4j
@AllArgsConstructor
@Service
public class GenerateDocumentDataSetBusinessImpl implements GenerateDocumentDataSetBusiness {


    @Override
    public String generateDNISampleDataSet(BigInteger amount) {
        return "";
    }

    @Override
    public String generatePassportSampleDataSet(BigInteger amount) {
        return "";
    }

    @Override
    public String generateCESampleDataSet(BigInteger amount) {
        return "";
    }
}
