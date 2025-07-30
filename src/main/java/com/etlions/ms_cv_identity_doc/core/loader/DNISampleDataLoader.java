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

        BufferedImage resizedImage = resizeImage(originalImage, TemplateConstants.DNI_FIRST_PHOTO_WIDTH, TemplateConstants.DNI_FIRST_PHOTO_HEIGHT);
        byte[] resizedImageBytes = TemplateConstants.imageToBytes(resizedImage);

        BufferedImage resizedSecondImage = resizeImage(originalImage, TemplateConstants.DNI_SECOND_PHOTO_WIDTH, TemplateConstants.DNI_SECOND_PHOTO_HEIGHT);
        BufferedImage transparentSecondImage = applyTransparency(resizedSecondImage, 0.7f);
        BufferedImage coloredSecondImage = applyColorLayer(transparentSecondImage, new Color(0x3CB8D2), 0.5f);

        byte[] resizedColoredImage = TemplateConstants.imageToBytes(coloredSecondImage);

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
                .photo2(resizedColoredImage)
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

    private BufferedImage applyTransparency(BufferedImage image, float transparencyFactor) {
        BufferedImage transparentImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = transparentImage.createGraphics();

        // Aplica transparencia global
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparencyFactor));
        g2d.drawImage(image, 0, 0, null);

        g2d.dispose();
        return transparentImage;
    }

    public BufferedImage applyColorLayer(BufferedImage originalImage, Color layerColor, float opacity) {
        // 1. Crear una copia de la imagen original con canal alpha (transparencia)
        BufferedImage coloredImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        // 2. Dibujar la imagen original
        Graphics2D g2d = coloredImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);

        // 3. Aplicar capa de color con transparencia
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.setColor(layerColor);
        g2d.fillRect(0, 0, originalImage.getWidth(), originalImage.getHeight());

        g2d.dispose();
        return coloredImage;
    }
}
