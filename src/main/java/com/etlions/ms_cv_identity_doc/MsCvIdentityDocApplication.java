package com.etlions.ms_cv_identity_doc;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.etlions.ms_cv_identity_doc.infrastructure.openfeign")
public class MsCvIdentityDocApplication {

	public static void main(String[] args) {
		OpenCV.loadShared();
		SpringApplication.run(MsCvIdentityDocApplication.class, args);
	}

}
