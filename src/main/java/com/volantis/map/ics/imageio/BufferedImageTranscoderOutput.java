/*
This file is part of Volantis Mobility Server. 

Volantis Mobility Server is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Volantis Mobility Server is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.Â  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Volantis Mobility Server.Â  If not, see <http://www.gnu.org/licenses/>. 
*/
/* ----------------------------------------------------------------------------
 * (c) Volantis Systems Ltd 2005. 
 * ----------------------------------------------------------------------------
 */

package com.volantis.map.ics.imageio;

import java.awt.image.BufferedImage;

import org.apache.batik.transcoder.TranscoderOutput;

/**
 * This class is responsible for defining a <code>TranscoderOuput</code> class
 * that is capable of storing a buffered image that is the result of
 * transcoding an SVG file.
 */
public class BufferedImageTranscoderOutput extends TranscoderOutput {

    /**
     * The resultant image of transcoding the SVG.
     */
    private BufferedImage image;

    /**
     * Initializes the new instance.
     */
    public BufferedImageTranscoderOutput() {
        super();
    }

    /**
     * Sets the BufferedImage.
     *
     * @param image the image to be stored.
     */
    public void setBufferedImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Returns the BufferedImage currently stored.
     *
     * @return the resultant image of the transcoding process.
     */
    public BufferedImage getBufferedImage() {
        return image;
    }
}

/*
 ===========================================================================
 Change History
 ===========================================================================
 $Log$

 16-Mar-05  311/5   rgreenall   VBM:2005012701 General improvements post review.

 16-Mar-05  311/3   rgreenall   VBM:2005012701 Calling writeImage in concrete implementations of AbstractImageWriter no longer results in the image being converted twice.

 21-Feb-05  311/1   rgreenall   VBM:2005012701 Resolved conflicts

 ===========================================================================
*/