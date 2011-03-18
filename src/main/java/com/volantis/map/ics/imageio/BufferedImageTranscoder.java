/*
This file is part of Volantis Mobility Server. 

Volantis Mobility Server is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Volantis Mobility Server is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Volantis Mobility Server.  If not, see <http://www.gnu.org/licenses/>. 
*/
/* ----------------------------------------------------------------------------
 * (c) Volantis Systems Ltd 2005. 
 * ----------------------------------------------------------------------------
 */
package com.volantis.map.ics.imageio;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;


/**
 * This class is an <tt>ImageTranscoder</tt> that produces a BufferedImage
 * image.
 */
public class BufferedImageTranscoder extends ImageTranscoder {

    /**
     * Initializes a BufferedImageTranscoder which converts an SVG to a
     * BufferedImage in which transparent pixels are replaced with the colour
     * white.
     */
    public BufferedImageTranscoder() {
        // ensure that transparent pixels are converted to white ones.
        // The default is black.
        hints.put(ImageTranscoder.KEY_BACKGROUND_COLOR, Color.white);
    }

    /**
     * Writes the specified image to the specified output.
     *
     * @param img                 the image to write
     * @param output              the output where to store the image
     * @param TranscoderException if an error occured while storing the image
     */
    @Override
    public void writeImage(BufferedImage img,
                           TranscoderOutput output)
        throws TranscoderException {

        // We want the SVG in the form of a BufferedImage so write the
        // supplied image to the output servlet

        // Need to check that the cast is legal.
        if (output instanceof BufferedImageTranscoderOutput) {
            BufferedImageTranscoderOutput transcoderOutput =
                (BufferedImageTranscoderOutput) output;
            transcoderOutput.setBufferedImage(img);
        } else {

            throw new IllegalArgumentException(
                "Expected a BufferedImageTranscoderOutput");
        }
    }


    /**
     * Creates a new RGB image with the specified dimension.
     *
     * @param width  the image width in pixels
     * @param height the image height in pixels
     */
    @Override
    public BufferedImage createImage(int width, int height) {
        final BufferedImage image =
            new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        return image;
    }
}

/*
 ===========================================================================
 Change History
 ===========================================================================
 $Log$

 06-May-05  450/3   rgreenall   VBM:2005042907 Writers now only change to a different Writer if max image size is exceeded.

 05-May-05  450/1   rgreenall   VBM:2005042907 Conversion of a transparent SVG now results in white background rather than black.

 16-Mar-05  311/5   rgreenall   VBM:2005012701 General improvements post review.

 16-Mar-05  311/3   rgreenall   VBM:2005012701 Calling writeImage in concrete implementations of AbstractImageWriter no longer results in the image being converted twice.

 21-Feb-05  311/1   rgreenall   VBM:2005012701 Resolved conflicts

 ===========================================================================
*/