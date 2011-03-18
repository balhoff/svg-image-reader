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
package com.volantis.map.ics.io;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.stream.ImageInputStream;

/**
 * This class is responsible for wrapping <code> javax.imageio.stream.ImageInputStream
 * </code> with an <code> InputStream </code> thereby making it compatible with
 * APIs that expect images to be read from a standard <code> InputStream
 * </code>.
 */
public class ImageInputStreamAdaptor extends InputStream {

    /**
     * The stream to be wrapped
     */
    private ImageInputStream imageInputStream;

    /**
     * Creates a wrapper compatable with <code> InputStream </code> for the
     * supplied ImageInputStream.
     *
     * @param theImageInputStream the stream to be wrapped for compatability
     *                            with <code> InputStream </code>
     */
    public ImageInputStreamAdaptor(ImageInputStream theImageInputStream) {
        imageInputStream = theImageInputStream;
    }

    // Javadoc inherited
    @Override
    public int available() throws IOException {
        return super.available();
    }

    // Javadoc inherited
    @Override
    public int read() throws IOException {
        return imageInputStream.read();
    }

    // Javadoc inherited
    @Override
    public void close() throws IOException {

        // This class was originally created to enable an ImageInputStream
        // to be used as input to a <code>TranscoderInput</code>
        // defined in the Batik API.

        // During the transcoding process, which is contained in the ImageIO
        // reader plugin (<code> SVGImageReader </code>) the input stream
        // is closed.  This causes an IOException to be thrown when the
        // SVGImageReader is invoked via a call to ImageIO.read as the last
        // stage of the read operation is to close the input stream
        // (which has already been closed by the transcoding process).

        // Hence this method is not implemented to avoid the IOException being
        // thrown.
    }

    // Javadoc inherited
    @Override
    public synchronized void reset() throws IOException {
        imageInputStream.reset();
    }

    // Javadoc inherited
    @Override
    public boolean markSupported() {
        return false;
    }

    // Javadoc inherited
    @Override
    public synchronized void mark(int readlimit) {
        // No need to implement as mark is not supported by this class.
    }

    // Javadoc inherited
    @Override
    public long skip(long n) throws IOException {
        return imageInputStream.skipBytes(n);
    }

    // Javadoc inherited
    @Override
    public int read(byte b[]) throws IOException {
        return imageInputStream.read(b);
    }

    // Javadoc inherited
    @Override
    public int read(byte b[], int off, int len) throws IOException {
        return imageInputStream.read(b, off, len);
    }

}

/*
 ===========================================================================
 Change History
 ===========================================================================
 $Log$

 16-Mar-05  311/1   rgreenall   VBM:2005012701 Calling writeImage in concrete implementations of AbstractImageWriter no longer results in the image being converted twice.

 21-Feb-05  311/1   rgreenall   VBM:2005012701 Resolved conflicts

 ===========================================================================
*/