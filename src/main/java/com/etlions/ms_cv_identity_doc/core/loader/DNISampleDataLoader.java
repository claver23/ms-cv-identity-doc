package com.etlions.ms_cv_identity_doc.core.loader;

import com.etlions.ms_cv_identity_doc.core.model.DNIDataModel;
import com.etlions.ms_cv_identity_doc.core.util.TemplateConstants;
import com.etlions.ms_cv_identity_doc.infrastructure.openfeign.clients.PersonImageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DNISampleDataLoader {

    private final PersonImageClient personImageClient;

    public DNIDataModel loadSingleSampleData() {

        long startTime = System.currentTimeMillis();
        byte[] personImage = personImageClient.getRandomPersonImage();
        long endTime = System.currentTimeMillis();
        log.info("Tiempo de respuesta de la API de imágenes: {} ms", (endTime - startTime) / 1000.0);

        BufferedImage originalImage = TemplateConstants.bytesToImage(personImage);

        BufferedImage resizedImage = resizeImage(originalImage, TemplateConstants.DNI_PHOTO_WIDTH, TemplateConstants.DNI_PHOTO_HEIGHT);

        byte[] resizedImageBytes = TemplateConstants.imageToBytes(resizedImage);

        return DNIDataModel.builder()
                .isDuplicate(true)
                .dni(String.format("%08d", new Random().nextInt(100000000)))
                .verifierCode("2")
                .photo1(resizedImageBytes)
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

    private BufferedImage resizeImage(BufferedImage original, int targetWidth, int targetHeight) {
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();

        // Configuración de calidad
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Dibuja la imagen redimensionada
        g2d.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return resized;
    }
}
