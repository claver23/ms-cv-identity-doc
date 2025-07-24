package com.etlions.ms_cv_identity_doc;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsCvIdentityDocApplication {

	public static void main(String[] args) {
		OpenCV.loadShared();
		SpringApplication.run(MsCvIdentityDocApplication.class, args);
	}

}
