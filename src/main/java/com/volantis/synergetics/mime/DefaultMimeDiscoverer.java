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
 * (c) Volantis Systems Ltd 2006. 
 * ----------------------------------------------------------------------------
 */
package com.volantis.synergetics.mime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;


/**
 * Default implementation of MimeDiscoverer
 */
public class DefaultMimeDiscoverer implements MimeDiscoverer {

    /**
     * The Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DefaultMimeDiscoverer.class);

    /**
     * Mime type matcher based on magic numbers.
     */
    private static MatchParser parser = new MatchParser();
    /**
     * Mime type matcher based on extension.
     */
    private static ExtensionMatchParser extensionParser = new ExtensionMatchParser();

    /**
     * Defualt binary mime type.
     */
    private static final String DEFAULT_BINARY_MIME_TYPE = "application/octet-stream";

    /**
     * Defaykt text/plain mime type.
     */
    private static final String DEFAULT_TEXT_MIME_TYPE = "text/plain";

    /**
     * Constructor
     */
    public DefaultMimeDiscoverer() {
    }

    // javadoc inherited
    @Override
    public String discoverMimeType(byte[] data) {
        return discoverMimeType(data, null);
    }
    
    // javadoc inherited
    @Override
    public String discoverMimeType(byte[] data, String fileExtension) {

        Map<Object,Object> extensions = extensionParser.getMatchers();
        
        if (fileExtension != null && !"".equals(fileExtension)) {
            String mime = (String) extensions.get(fileExtension);
            if (mime != null && !"".equals(mime)) {
                return mime; 
            }
        }

        for (Iterator<Matcher> iter = parser.getMatchers().iterator(); iter.hasNext();) {
            Matcher matcher = iter.next();
            if (matcher.test(data)) {
                return matcher.getMimeType();
            }
        }

        return guessDefaultMimeType(data);
    }

    // javadoc inherited
    @Override
    public String discoverMimeType(ImageInputStream data) throws IOException {
        return discoverMimeType(data, null);
    }
    
    // javadoc inherited
    @Override
    public String discoverMimeType(ImageInputStream data, String fileExtension) throws IOException {
        long origPos = data.getStreamPosition();
        try {
            byte[] b = new byte[parser.getMaxDataLength()];
            
            ByteBuffer buffer = ByteBuffer.wrap(b);
            int count = 0;
            if ((count = data.read(b)) != -1) {
                buffer.put(b, 0, count);
            }
            return discoverMimeType(b, fileExtension);
        } finally{
            data.seek(origPos);
        }
    }

    // javadoc inherited
    @Override
    public String discoverMimeType(InputStream stream) throws IOException {
        return discoverMimeType(stream, null); 
    }
    
    // javadoc inherited
    @Override
    public String discoverMimeType(InputStream stream, String fileExtension) throws IOException {

        try {
            byte[] b = new byte[parser.getMaxDataLength()];
            if (stream.markSupported()) {
                stream.mark(parser.getMaxDataLength());
            }
            ByteBuffer buffer = ByteBuffer.wrap(b);
            int count = 0;
            if ((count = stream.read(b)) != -1) {
                buffer.put(b, 0, count);
            }
            return discoverMimeType(b, fileExtension);

        } finally {
            if (stream.markSupported()) {
                stream.reset();
            } else {
                stream.close();
            }
        }
    }

    // javadoc inherited
    @Override
    public String discoverMimeType(File file) throws IOException {
        return discoverMimeType(new FileInputStream(file), getExtension(file));
    }

    /**
     * Returns extension of given file.
     * 
     * @param file
     * @return extension of given file, or null if none found.
     */
    private String getExtension(File file) {
        String name = file.getName();
        int pos = name.lastIndexOf('.');
        String ext = null;
        if (pos > 0) {
            ext = name.substring(pos + 1);
        }
        return ext;
    }
    
    /**
     * Mime type resolver of last resort. Returns binary or plain text mime type.
     * 
     * @param data
     * @return default mime type, one of binary and text/plain. 
     */
    private String guessDefaultMimeType(byte[] data) {
        LOGGER.warn("Matched mime type not found ... use default");

        for (int i = 0; i < data.length; ++i) {
            if ((data[i] > 255
                || data[i] < 32)
                && data[i] != '\n'
                && data[i] != '\t'
                && data[i] != '\r'
                && data[i] != 0) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Guessing it's a "
                                 + DEFAULT_BINARY_MIME_TYPE);
                }
                return DEFAULT_BINARY_MIME_TYPE;
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Guessing it's a " + DEFAULT_TEXT_MIME_TYPE);
        }
        return DEFAULT_TEXT_MIME_TYPE;
    }

}