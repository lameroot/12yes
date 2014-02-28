package ru.twelveyes.util;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * User: Krainov
 * Date: 28.02.14
 * Time: 17:12
 * http://svn.opengeo.org/CAPRA/GeoNode/trunk/src/geoserver-geonode-ext/src/test/java/org/geonode/geojson/GeoJSONParserTest.java
 */
public class GeoUtil {

    private static WKTReader wktReader = new WKTReader();

    public static Geometry parseWellKnowText(String wellKnownText) throws ParseException {
        return wktReader.read(wellKnownText);
    }

    public static <T extends Geometry> T parseWellKnowText(Class<T> clazz, String wellKnownText) throws ParseException {
        return (T)wktReader.read(wellKnownText);
    }
}
