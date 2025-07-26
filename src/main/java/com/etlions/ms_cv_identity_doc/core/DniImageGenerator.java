package com.etlions.ms_cv_identity_doc.core;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class DniImageGenerator {
    private static final String[] NOMBRES = {"Ana Gómez", "Juan Pérez", "María López", "Carlos Ruiz"};
    private static final Random RANDOM = new Random();
    private static final String OUTPUT_DIR = "dataset/";

    public static void generateDniImages(String dniType, int amount) throws Exception {
        // Cargar la plantilla según el tipo de DNI
        String templateFile;
        switch (dniType.toUpperCase()) {
            case "AZUL":
                templateFile = "templates/dni_azul_base.png";
                break;
            case "AMARILLO":
                templateFile = "templates/dni_amarillo_base.png";
                break;
            case "ELECTRONICO":
                templateFile = "templates/dni_electronico_base.png";
                break;
            default:
                throw new IllegalArgumentException("Invalid DNI type: " + dniType);
        }

        BufferedImage plantilla = ImageIO.read(new File(templateFile));
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
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            g2d.setColor(Color.BLACK);

            // Insertar datos ficticios
            String nombre = NOMBRES[RANDOM.nextInt(NOMBRES.length)];
            String numero = String.format("%08d", RANDOM.nextInt(100000000));
            String fecha = String.format("%02d/%02d/%04d", RANDOM.nextInt(28) + 1, RANDOM.nextInt(12) + 1, RANDOM.nextInt(50) + 1970);

            // Dibujar texto en posiciones específicas (ajusta según la plantilla)
            g2d.drawString(nombre, 50, 100);
            g2d.drawString(numero, 50, 150);
            g2d.drawString(fecha, 50, 200);

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
        generateDniImages("AZUL", 2);
    }
}