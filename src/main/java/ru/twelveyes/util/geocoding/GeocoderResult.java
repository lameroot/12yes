package ru.twelveyes.util.geocoding;

import java.util.ArrayList;

/**
 * User: Krainov
 * Date: 08.05.13
 * Time: 14:55
 */
public class GeocoderResult extends ArrayList<GeocodingAddress> {

    private int totalResults = -1;

    public void setTotalResults(int total) {
        totalResults = total;
    }

    public int getTotalResults() {
        return totalResults < 0 ? size() : totalResults;
    }
}
