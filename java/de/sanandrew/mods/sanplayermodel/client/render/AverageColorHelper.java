/**
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.sanplayermodel.client.render;
import net.darkhax.bookshelf.lib.ColorObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public final class AverageColorHelper
{
    /**
     * Gets the average color from an image.
     *
     * @param is    The image as InputStream
     * @return the average color
     * @throws java.io.IOException when the InputStream cannot be read as an image
     */
    public static ColorObject getAverageColor(InputStream is) throws IOException {
        return getAverageColor(is, null);
    }

    public static ColorObject getAverageColor(InputStream is, ColorObject maskClr) throws IOException {
        // read texture as BufferedImage from InputStream
        BufferedImage bi = ImageIO.read(is);

        // holds the added RGB values of the whole texture and pixel counter
        double red = 0.0D;
        double green = 0.0D;
        double blue = 0.0D;
        double count = 0;
        for( int x = 0; x < bi.getWidth(); x++ ) {          // loop through the pixels
            for( int y = 0; y < bi.getHeight(); y++ ) {
                ColorObject color = new ColorObject(bi.getRGB(x, y));
                if( color.getAlpha() < 0x01 ) {            // check if it isn't fully transparent, if it is, then ignore this color, since it will darken it,
                    continue;                               // because those pixels are usually black and you don't see them anyway
                }

                if( maskClr != null ) {
                    if( isFloatSimilar(color.getRed(), maskClr.getRed()) && isFloatSimilar(color.getGreen(), maskClr.getGreen()) && isFloatSimilar(color.getBlue(), maskClr.getBlue()) ) {
                        continue;
                    }
                }

                red += color.getRed();              // add RGB from the pixel to the RGB storage variables, increase pixel counter
                green += color.getGreen();
                blue += color.getBlue();
                count += 1.0D;
            }
        }

        int avgRed = (int) (red / count);       // calculating the average of each channel
        int avgGreen = (int) (green / count);
        int avgBlue = (int) (blue / count);

        return new ColorObject(avgRed, avgGreen, avgBlue, 255); // return combined RGB channels
    }

    private static boolean isFloatSimilar(float f1, float f2) {
        return f1 - 0.001 < f2 && f1 + 0.001 > f2;
    }
}
