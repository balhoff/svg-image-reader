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

import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

import com.volantis.synergetics.mime.DefaultMimeDiscoverer;
import com.volantis.synergetics.mime.MimeDiscoverer;

/**
 * This class is responsible for defining the configuration properties of
 * <code> SVGImageReader</code>.
 */
public class SVGImageReaderSpi extends ImageReaderSpi {

    static final String VENDOR_NAME = "Volantis";

    static final String VERSION = "1.0";

    static final String READER_CLASS_NAME = SVGImageReader.class.getName();

    static final String[] NAMES = {"svg", "SVG"};

    static final String[] SUFFIXES = {"svg", "SVG"};

    private static final String XML_MIME_TYPE = "text/xml";

    private static final String SVG_MIME_TYPE = "image/svg+xml";

    static final String[] MIME_TYPES = { SVG_MIME_TYPE };

    static final String[] WRITER_SPI_NAMES = {""};

    // Metadata formats, more information below
    static final boolean SUPPORTS_STANDARD_STREAM_METADATA_FORMAT = false;

    static final String NATIVE_STREAM_METADATA_FORMAT_NAME = null;

    static final String NATIVE_STREAM_METADATA_FORMAT_CLASS_NAME = null;

    static final String[] EXTRA_STREAM_METADATA_FORMAT_NAMES = null;

    static final String[] EXTRA_STREAM_METADATA_FORMAT_CLASS_NAMES = null;

    static final boolean SUPPORTS_STANDARD_IMAGE_METADATA_FORMAT = false;

    static final String NATIVE_IMAGE_METADATA_FORMAT_NAME = null;

    static final String NATIVE_IMAGE_METADATA_FORMAT_CLASS_NAME = null;

    static final String[] EXTRA_IMAGE_METADATA_FORMAT_NAMES = null;

    static final String[] EXTRA_IMAGE_METADATA_FORMAT_CLASS_NAMES = null;

    // Javadoc inherited
    public SVGImageReaderSpi() {
        super(VENDOR_NAME,
              VERSION,
              NAMES,
              SUFFIXES,
              MIME_TYPES,
              READER_CLASS_NAME,
              // The standard input type for this class is ImageInputStream
              STANDARD_INPUT_TYPE,
              WRITER_SPI_NAMES,
              SUPPORTS_STANDARD_STREAM_METADATA_FORMAT,
              NATIVE_STREAM_METADATA_FORMAT_NAME,
              NATIVE_STREAM_METADATA_FORMAT_CLASS_NAME,
              EXTRA_STREAM_METADATA_FORMAT_NAMES,
              EXTRA_STREAM_METADATA_FORMAT_CLASS_NAMES,
              SUPPORTS_STANDARD_IMAGE_METADATA_FORMAT,
              NATIVE_IMAGE_METADATA_FORMAT_NAME,
              NATIVE_IMAGE_METADATA_FORMAT_CLASS_NAME,
              EXTRA_IMAGE_METADATA_FORMAT_NAMES,
              EXTRA_IMAGE_METADATA_FORMAT_CLASS_NAMES);
    }

    // Javadoc inherited
    @Override
    public String getDescription(Locale locale) {
        // @todo later return a localized description
        // Localize as appropriate
        return "Volantis SVG Reader";
    }

    // Javadoc inherited
    @Override
    public boolean canDecodeInput(Object input)
        throws IOException {

        boolean canDecode = false;
        if (input instanceof ImageInputStream) {

            ImageInputStream inputStream = (ImageInputStream) input;

            MimeDiscoverer mimeDiscoverer = new DefaultMimeDiscoverer();
            String mimeType = mimeDiscoverer.discoverMimeType(inputStream);
            if (SVG_MIME_TYPE.equals(mimeType))
            {
                canDecode = true;
            }
            else if (XML_MIME_TYPE.equals(mimeType))
            {
                // check if the file is SVG without a doctype
                try {
                    inputStream.mark();
                    String line = inputStream.readLine();
                    while (line != null && line.indexOf("<svg") == -1)
                    {
                        line = inputStream.readLine();
                    }
                    canDecode = line != null &&
                                line.indexOf("<svg") > -1;
                } finally {
                    inputStream.reset();
                }
            }
        }
        return canDecode;
    }

    // Javadoc inherited
    @Override
    public ImageReader createReaderInstance(Object extension) {
        return new SVGImageReader(this);
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