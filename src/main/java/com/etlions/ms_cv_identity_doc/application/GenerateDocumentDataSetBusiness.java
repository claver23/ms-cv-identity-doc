package com.etlions.ms_cv_identity_doc.application;

import com.etlions.ms_cv_identity_doc.application.usecase.GenerateCEUseCase;
import com.etlions.ms_cv_identity_doc.application.usecase.GenerateDNIUseCase;
import com.etlions.ms_cv_identity_doc.application.usecase.GeneratePassportUseCase;

public interface GenerateDocumentDataSetBusiness extends
        GenerateDNIUseCase,
        GeneratePassportUseCase,
        GenerateCEUseCase {
}
