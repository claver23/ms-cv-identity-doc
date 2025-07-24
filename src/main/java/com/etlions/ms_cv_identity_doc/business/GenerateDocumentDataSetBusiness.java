package com.etlions.ms_cv_identity_doc.business;

import com.etlions.ms_cv_identity_doc.business.usecase.GenerateCEUseCase;
import com.etlions.ms_cv_identity_doc.business.usecase.GenerateDNIUseCase;
import com.etlions.ms_cv_identity_doc.business.usecase.GeneratePassportUseCase;

public interface GenerateDocumentDataSetBusiness extends
        GenerateDNIUseCase,
        GeneratePassportUseCase,
        GenerateCEUseCase {
}
