package com.etlions.ms_cv_identity_doc.core.generator;
import com.etlions.ms_cv_identity_doc.core.loader.DNISampleDataLoader;
import com.etlions.ms_cv_identity_doc.core.model.DNIDataModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import static com.etlions.ms_cv_identity_doc.core.util.Constant.*;

public class DNIBufferedGenerator {

    private static final Random RANDOM = new Random();
    private static final String OUTPUT_DIR = "dataset/";
    private static final DNISampleDataLoader dniSampleDataLoader = new DNISampleDataLoader();

    public static void generateDniImages(String dniType, int amount) throws Exception {

        DNIDataModel dniDataModel = dniSampleDataLoader.loadSingleSampleData();


        // Cargar la plantilla según el tipo de DNI
        String templateFile;
        switch (dniType.toUpperCase()) {
            case "AZUL":
                templateFile = "doc-template/dni/dni-azul.png";
                break;
            case "AMARILLO":
                templateFile = "doc-template/dni/dni-amarillo.png";
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

            // Configurar fuente y color
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.setColor(Color.BLACK);

            // Dibujar texto en posiciones específicas (ajusta según la plantilla)

            /* ABOVE SECTION */

                //TODO: Implementar la sección de arriba con los datos del DNI

            /* MID LEFT SECTION */
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
            g2d.drawString(dniDataModel.getExpirationDate().format(DNI_DETAIL_DATE_FORMATTER), 650, 210);

            /* OACI SECTION */

            //TODO: Implementar la sección OACI con los datos del DNI


            // Aplicar transformaciones (rotación y traslación)
            double angulo = RANDOM.nextDouble() * 20 - 10; // Rotación entre -10 y +10 grados
            g2d.rotate(Math.toRadians(angulo), width / 2.0, height / 2.0);

            // Guardar la imagen
            ImageIO.write(imagen, "png", new File(outputDir + "/dni_" + i + ".png"));
            g2d.dispose();
        }
    }

    public static void main(String[] args) throws Exception {
        // Ejemplo: generar 1000 imágenes de DNI AZUL
        generateDniImages("AZUL", 1);
    }
}