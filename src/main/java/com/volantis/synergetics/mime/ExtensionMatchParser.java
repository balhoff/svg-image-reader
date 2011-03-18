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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Class responsible for parsing mapping file of extension->mime type pairs.
 *
 * @volantis-api-include-in InternalAPI
 */
public class ExtensionMatchParser {

    /**
     * The Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ExtensionMatchParser.class);

    /**
     * The name of the resource that contains extensions mapping.
     */
    private static final String EXTENSIONS_MAP_RESOURCE = "extensions.properties";

    /**
     * The map with extension -> mime type mapping.
     */
    private Properties map = new Properties();

    /**
     * Constructor.
     */
    public ExtensionMatchParser() {
        try {
            InputStream is = getExtensionMapStream();
            map.load(is);
        } catch (IllegalStateException ise) {
            LOGGER.error(ise);            
        } catch (IOException ie) {
            LOGGER.error(ie);            
        }
    }

    /**
     * Returns stream of extension mapping file.
     * 
     * @return a non-null BufferedReader
     * @throws IllegalStateException if the resource isn't on the classpath
     */
    private InputStream getExtensionMapStream() {
        InputStream in = ExtensionMatchParser.class
                .getResourceAsStream(EXTENSIONS_MAP_RESOURCE);
        if (in == null) {
            throw new IllegalStateException(
                "Unable to load extension->mime map file: " + EXTENSIONS_MAP_RESOURCE);
        }

        return in;
    }

    /**
     * Get a list of the matchers that have been created from parsing the map file.
     *
     * @return a non-null Map of Matchers
     */
    public Map<Object,Object> getMatchers() {
        return map;
    }
}