package com.etlions.ms_cv_identity_doc.core.util;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import javax.imageio.ImageIO;

public final class TemplateConstants {

    // Standard dimensions (public constants)
    public static final int DNI_PHOTO_WIDTH = 200;  // 35mm at 300 DPI
    public static final int DNI_PHOTO_HEIGHT = 250;

    // Image storage
    private static final byte[] TEMPLATE_BYTES;
    private static final BufferedImage TEMPLATE_IMAGE;
    private static final String TEMPLATE_FORMAT = "PNG";

    static {
        try {
            // Load and validate template during class initialization
            TEMPLATE_BYTES = loadResourceAsBytes("/doc-template/mask/dni-mask.png");
            TEMPLATE_IMAGE = convertToBufferedImage(TEMPLATE_BYTES);
            validateTemplateDimensions(TEMPLATE_IMAGE);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load DNI template", e);
        }
    }

    // PUBLIC API ===============================================

    /**
     * Gets a copy of the template image for manipulation
     */
    public static BufferedImage getTemplateImage() {
        return copyImage(TEMPLATE_IMAGE);
    }

    /**
     * Gets a copy of the template as byte array
     */
    public static byte[] getTemplateBytes() {
        return TEMPLATE_BYTES.clone();
    }

    // PRIVATE HELPERS ==========================================

    private static byte[] loadResourceAsBytes(String resourcePath) throws IOException {
        try (InputStream is = TemplateConstants.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Template resource not found: " + resourcePath);
            }

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[8192];
            int bytesRead;

            while ((bytesRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }

            return buffer.toByteArray();
        }
    }

    private static BufferedImage convertToBufferedImage(byte[] imageData) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(imageData)) {
            BufferedImage image = ImageIO.read(bais);
            if (image == null) {
                throw new IOException("Unsupported image format");
            }
            return image;
        }
    }

    private static void validateTemplateDimensions(BufferedImage image) {
        if (image.getWidth() < DNI_PHOTO_WIDTH || image.getHeight() < DNI_PHOTO_HEIGHT) {
            throw new IllegalStateException(String.format(
                    "Template must be at least %dx%d pixels",
                    DNI_PHOTO_WIDTH, DNI_PHOTO_HEIGHT
            ));
        }
    }

    private static BufferedImage copyImage(BufferedImage source) {
        // Optimized copy for common image types
        if (source.getType() == BufferedImage.TYPE_INT_ARGB ||
                source.getType() == BufferedImage.TYPE_INT_RGB) {
            return new BufferedImage(
                    source.getColorModel(),
                    source.copyData(null),
                    source.isAlphaPremultiplied(),
                    null
            );
        }

        // Fallback for other image types
        BufferedImage copy = new BufferedImage(
                source.getWidth(),
                source.getHeight(),
                source.getType()
        );
        copy.getGraphics().drawImage(source, 0, 0, null);
        return copy;
    }

    // CONVERSION UTILITIES =====================================

    /**
     * Converts byte array to BufferedImage (unchecked exception)
     */
    public static BufferedImage bytesToImage(byte[] imageData) {
        try {
            return convertToBufferedImage(imageData);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Converts BufferedImage to byte array (unchecked exception)
     */
    public static byte[] imageToBytes(BufferedImage image) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, TEMPLATE_FORMAT, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // PREVENT INSTANTIATION ====================================

    private TemplateConstants() {
        throw new AssertionError("TemplateConstants is a utility class and cannot be instantiated");
    }
}