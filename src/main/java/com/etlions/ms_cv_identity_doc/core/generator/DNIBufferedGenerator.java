package com.etlions.ms_cv_identity_doc.core.generator;
import com.etlions.ms_cv_identity_doc.core.loader.DNISampleDataLoader;
import com.etlions.ms_cv_identity_doc.core.model.DNIDataModel;
import com.etlions.ms_cv_identity_doc.core.util.TemplateConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.etlions.ms_cv_identity_doc.core.util.Constant.*;

@Component
@RequiredArgsConstructor
public class DNIBufferedGenerator implements CommandLineRunner {

    private static final Random RANDOM = new Random();
    private static final String OUTPUT_DIR = "dataset/";
    private final DNISampleDataLoader dniSampleDataLoader;

    private void generateDniImages(String dniType, int amount) throws Exception {

        DNIDataModel dniDataModel = dniSampleDataLoader.loadSingleSampleData();

        String templateFile;
        switch (dniType.toUpperCase()) {
            case "AZUL":
                templateFile = "doc-template/dni/dni-azul-clean.png";
                break;
            case "AMARILLO":
                templateFile = "doc-template/dni/dni-amarillo-clean.png";
                break;
            case "ELECTRONICO":
                templateFile = "doc-template/dni/dni-electronico.png";
                break;
            default:
                throw new IllegalArgumentException("Invalid DNI type: " + dniType);
        }

        BufferedImage plantilla;
        try (InputStream is = DNIBufferedGenerator.class.getClassLoader().getResourceAsStream(templateFile)) {
            if (is == null) throw new FileNotFoundException("No se encontró la plantilla en resources");
            plantilla = ImageIO.read(is);
        }
        int width = plantilla.getWidth();
        int height = plantilla.getHeight();

        // Crear carpeta para el tipo de DNI
        File outputDir = new File(OUTPUT_DIR + "dni_" + dniType.toLowerCase());
        outputDir.mkdirs();

        // Generar 'amount' imágenes
        for (int i = 0; i < amount; i++) {
            BufferedImage imagen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = imagen.createGraphics();
            g2d.drawImage(plantilla, 0, 0, null);

            /* ABOVE SECTION */
            g2d.setColor(new Color(0x512D2F));

            Optional.ofNullable(dniDataModel.getIsDuplicate())
                    .filter(Boolean::booleanValue)
                    .ifPresent(isDup -> {
                        g2d.setFont(new Font("Arial", Font.BOLD, 18));
                        g2d.drawString("DUPLICADO", 50, 45);
                    });
            g2d.setFont(new Font("Arial", Font.BOLD, 23));
            g2d.drawString(dniDataModel.getDni(), 646, 47);

            /* MID LEFT SECTION */

            g2d.drawImage(TemplateConstants.bytesToImage(dniDataModel.getPhoto1()) , 16, 70, null);

            g2d.setFont(new Font("Arial", Font.BOLD, 22));
            AffineTransform originalTransform = g2d.getTransform();
            g2d.rotate(Math.toRadians(90), 9, 80);
            g2d.drawString(dniDataModel.getDni().chars()
                    .mapToObj(c -> String.valueOf((char) c))
                    .collect(Collectors.joining("   ")), 9, 80);
            g2d.setTransform(originalTransform);

            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            g2d.drawString(dniDataModel.getFirstLastName(), 75, 325);   // TODO: Dibujar texto en posiciones específicas (ajusta según la plantilla)

            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.setColor(Color.BLACK);
            g2d.drawString(dniDataModel.getFirstLastName(), 225, 110);
            g2d.drawString(dniDataModel.getSecondLastName(), 225, 170);
            g2d.drawString(dniDataModel.getFirstName() + " " + dniDataModel.getSecondName(), 225, 225);
            g2d.drawString(dniDataModel.getBirthDate().format(DNI_BIRTH_DATE_FORMATTER), 225, 275);
            g2d.drawString(dniDataModel.getBirthPlace(), 365, 275);
            g2d.drawString(dniDataModel.getSex(), 225, 330);
            g2d.drawString(dniDataModel.getMaritalStatus(), 335, 330);

            /* MID RIGHT SECTION */
            g2d.drawString(dniDataModel.getRegistrationDate().format(DNI_DETAIL_DATE_FORMATTER), 650, 120);
            g2d.drawString(dniDataModel.getEmissionDate().format(DNI_DETAIL_DATE_FORMATTER), 650, 167);
            g2d.setColor(new Color(0x512D2F));
            g2d.drawString(dniDataModel.getExpirationDate().format(DNI_DETAIL_DATE_FORMATTER), 650, 210);

            g2d.drawImage(TemplateConstants.bytesToImage(dniDataModel.getPhoto2()) , 700, 240, null);


            /* OACI SECTION */
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 22));
            g2d.drawString(String.join(" ", dniDataModel.getOaciDataModel().getFirstLine()), 15, 375);
            g2d.drawString(String.join(" ", dniDataModel.getOaciDataModel().getSecondLine()), 15, 400);
            g2d.drawString(String.join(" ", dniDataModel.getOaciDataModel().getThirdLine()), 15, 425);



            // Aplicar transformaciones (rotación y traslación)
            double angulo = RANDOM.nextDouble() * 20 - 10; // Rotación entre -10 y +10 grados
            g2d.rotate(Math.toRadians(angulo), width / 2.0, height / 2.0);

            // Guardar la imagen
            ImageIO.write(imagen, "png", new File(outputDir + "/dni_" + i + ".png"));
            g2d.dispose();
        }
    }

//    public static void main(String[] args) throws Exception {
//        // Ejemplo: generar 1000 imágenes de DNI AZUL
//        generateDniImages("AMARILLO", 1);
//    }


    @Override
    public void run(String... args) throws Exception {
        try {
            generateDniImages("AZUL", 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}