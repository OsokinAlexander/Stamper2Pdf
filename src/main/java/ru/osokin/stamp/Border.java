package ru.osokin.stamp;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


/** Stamp border.
 * @author Osokin Alexander
 * @since 1.0
 */
public class Border {
    /** Portrait image of border. */
    private byte[] portraitImage;
    /** Landscape image of border. */
    private byte[] landscapeImage;
    /** Width of portrait image. */
    private float portraitWidth;
    /** Height of portrait image. */
    private float portraitHeight;
    /** Width of landscape image. */
    private float landscapeWidth;
    /** Height of landscape image. */
    private float landscapeHeight;

    /** Constructor of border.
      * @param portraitBorderImage portrait image
     * @param landscapeBorderImage landscape image
     * @throws IOException exception if could not read any images
     */
    public Border(final InputStream portraitBorderImage, final InputStream landscapeBorderImage) throws IOException {
        this.portraitImage = IOUtils.toByteArray(portraitBorderImage);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(portraitImage));
        portraitWidth = image.getWidth();
        portraitHeight = image.getHeight();
        this.landscapeImage = IOUtils.toByteArray(landscapeBorderImage);
        image = ImageIO.read(new ByteArrayInputStream(landscapeImage));
        landscapeWidth = image.getWidth();
        landscapeHeight = image.getHeight();
    }

    public Border(final Border border) {
        this.portraitImage = border.portraitImage;
        this.landscapeImage = border.landscapeImage;
        this.portraitWidth = border.portraitWidth;
        this.portraitHeight = border.portraitHeight;
        this.landscapeWidth = border.landscapeWidth;
        this.landscapeHeight = border.landscapeHeight;
    }

    public byte[] getPortraitImage() {
        return portraitImage;
    }

    public Border setPortraitImage(final byte[] image) {
        this.portraitImage = image;
        return this;
    }

    public byte[] getLandscapeImage() {
        return landscapeImage;
    }

    public Border setLandscapeImage(final byte[] image) {
        this.landscapeImage = image;
        return this;
    }

    /** Get portrait or landscape border image.
     * @param isPortrait flat that need portrait or landscape image
     * @return image bytes
     */
    public byte[] getImage(final boolean isPortrait) {
        return isPortrait ? portraitImage : landscapeImage;
    }

    public Border setPortraitSize(final float width, final float height) {
        this.portraitWidth = width;
        this.portraitHeight = height;
        return this;
    }

    public Border setLandscapeSize(final float width, final float height) {
        this.landscapeWidth = width;
        this.landscapeHeight = height;
        return this;
    }

    /** Get width of border.
     * @param isPortrait flat that need portrait or landscape image
     * @return pixels
     */
    public float getWidth(final boolean isPortrait) {
        return isPortrait ? portraitWidth : landscapeWidth;
    }

    /** Get height of border.
     * @param isPortrait flat that need portrait or landscape image
     * @return pixels
     */
    public float getHeight(final boolean isPortrait) {
        return isPortrait ? portraitHeight : landscapeHeight;
    }
}
