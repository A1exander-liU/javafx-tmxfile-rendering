package com.example.tiledmaptest;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Crops images.
 */
public final class ImageCropper {
    private ImageCropper() { }
    /**
     * Creates a sub-image from the given original image.
     * @param imagePath the path to the original image
     * @param x the x position of the desired sub-image
     * @param y the y position of the desired sub-image
     * @param width the desired width of the sub-image
     * @param height the desired height of the sub-image
     * @param relativePath true if the path is relative
     * @return the sub-image
     * @throws IOException when an illegal IO operation happens
     */
    public static javafx.scene.image.Image crop(final String imagePath, final int x, final int y, final int width, final int height, final boolean relativePath) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(ResourcePathMapper.getPathToResource(imagePath, relativePath)));
        BufferedImage subImage = originalImage.getSubimage(x, y, width, height);
        return createImage(subImage);
    }


    /**
     * Copies a java.awt.Image to a javafx.scene/image.Image.
     * Method used from https://community.oracle.com/message/9655930#9655930.
     * @param image the image to copy
     * @return the image as a javafx.scene.image.Image
     * @throws IOException when an illegal IO operation happens
     */
    public static javafx.scene.image.Image createImage(java.awt.Image image) throws IOException {
        if (!(image instanceof RenderedImage)) {
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            image = bufferedImage;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image, "png", out);
        out.flush();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        return new javafx.scene.image.Image(in);
    }
}
