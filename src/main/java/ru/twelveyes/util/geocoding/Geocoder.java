package ru.twelveyes.util.geocoding;

import java.io.IOException;

/**
 * User: Krainov
 * Date: 08.05.13
 * Time: 15:18
 * @see <a href="https://code.google.com/p/city-router/">THX</a>
 */
public interface Geocoder {

    public final static String DELIMITER_COORDS = ",";

    public GeocoderResult getFromLocationName(String locationName, int maxResults) throws IOException;

}