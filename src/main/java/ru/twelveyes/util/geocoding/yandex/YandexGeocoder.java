package ru.twelveyes.util.geocoding.yandex;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.twelveyes.util.geocoding.Geocoder;
import ru.twelveyes.util.geocoding.GeocoderResult;
import ru.twelveyes.util.geocoding.PagedGeocoder;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * User: Krainov
 * Date: 08.05.13
 * Time: 14:48
 */
public class YandexGeocoder implements Geocoder, PagedGeocoder {

    private YMMLParser parser;
    private HttpClient client;
    private String apiKey;
    private int responseSkip = 0;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public YandexGeocoder() {
        parser = new YMMLParser();
        client = new DefaultHttpClient();
        apiKey = "";
    }

    public GeocoderResult getFromLocationName(String locationName, int maxResults) throws IOException {

        GeocoderResult result = new GeocoderResult();
        Document resultDoc = geocoderResult(locationName, maxResults);
        result.addAll(parser.getAddressList(resultDoc));
        result.setTotalResults(parser.getTotalResultsCount(resultDoc));
        return result;
    }


    private Document geocoderResult(String locationName, int maxResults) throws IOException {

        String url = buildUrl(locationName, maxResults);
        System.out.println("url = " + url);
        InputStream responseStream = client.execute(
                new HttpGet(
                        url
                )).getEntity().getContent();

        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return docBuilder.parse(new InputSource(new InputStreamReader(responseStream)));
        } catch (ParserConfigurationException e) {
            throw new IOException(e.getMessage());
        } catch (SAXException e) {
            throw new IOException(e.getMessage());
        }
    }

    private String buildUrl(String locationName, int maxResults) throws UnsupportedEncodingException {
        return  String.format("http://geocode-maps.yandex.ru/1.x/?geocode=%s&results=%d&key=%s&skip=%d",
                URLEncoder.encode(locationName, "UTF-8"), maxResults, apiKey, responseSkip);
    }

    public void setResponseBegin(int responseBegin) {
        responseSkip = responseBegin;
    }


    public static void main(String[] args) throws Exception {
        YandexGeocoder geocoder = new YandexGeocoder();
        GeocoderResult result = geocoder.getFromLocationName("иваново нормандия-неман 83",10);
        System.out.println(result);
    }

}
