package ru.twelveyes.util.geocoding.yandex;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.twelveyes.util.geocoding.GeocodingAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * User: Krainov
 * Date: 08.05.13
 * Time: 14:49
 */
public class YMMLParser {


    public List<GeocodingAddress> getAddressList(Document ymmlDocument) {

        ArrayList<GeocodingAddress> retval = new ArrayList<GeocodingAddress>();

        NodeList foundNodes = ymmlDocument.getElementsByTagName("GeoObject");
        if (foundNodes != null) {
            for (int i = 0, l = foundNodes.getLength(); i < l; i++) {
                retval.add(nodeToAddress(foundNodes.item(i)));
            }
        }

        return retval;
    }

    public int getTotalResultsCount(Document ymmlDocument) {
        NodeList foundNode = ymmlDocument.getElementsByTagName("found");
        return (foundNode != null && foundNode.getLength() > 0) ?
                Integer.parseInt(foundNode.item(0).getFirstChild().getNodeValue(), 10) : 0;
    }

    private GeocodingAddress nodeToAddress(Node node) {
        GeocodingAddress retval;

        String addressLine = getAddressLine(node); //String) address.evaluate(node, XPathConstants.STRING);
        String coordsText = getCoords(node); //String) coords.evaluate(node, XPathConstants.STRING);
        String countryNode = getCountry(node); //String) country.evaluate(node, XPathConstants.STRING);
        String localityName = getLocality(node); //String) locality.evaluate(node, XPathConstants.STRING);
        String thoroughfareName = getThoroughfare(node); //String) thoroughfare.evaluate(node, XPathConstants.STRING);
        String adminAreaName = getAdminArea(node); //String) admin.evaluate(node, XPathConstants.STRING);
        String subAdminName = getSubAdminArea(node); //String) subAdmin.evaluate(node, XPathConstants.STRING);
        String featureName = getFeature(node); //String) premise.evaluate(node, XPathConstants.STRING);
        String premises = getPremises(node);

        retval = new GeocodingAddress(Locale.getDefault());
        if (addressLine != null)
            retval.setAddressLine(0, addressLine);
        if (countryNode != null)
            retval.setCountryName(countryNode);
        if (localityName != null)
            retval.setLocality(localityName);
        if (thoroughfareName != null)
            retval.setThoroughfare(thoroughfareName);
        if (adminAreaName != null)
            retval.setAdminArea(adminAreaName);
        if (subAdminName != null)
            retval.setSubAdminArea(subAdminName);
        if (featureName != null)
            retval.setFeatureName(featureName);
        if (coordsText != null) {
            int space = coordsText.indexOf(' ');
            retval.setLatitude(Double.valueOf(coordsText.substring(space + 1)));
            retval.setLongitude(Double.valueOf(coordsText.substring(0, space - 1)));
        }
        if ( null != premises ) {
            retval.setPremises(premises);
            String[] sa = premises.split("/");
            if ( 2 == sa.length ) {
                retval.setNumber(sa[0]);
                retval.setPart(sa[1]);
            }
            else retval.setNumber(sa[0]);
        }

        return retval;
    }


    private String getAddressLine(Node node) {
        String result = null;
        try {
            result = getChild(node, "text").getFirstChild().getNodeValue();
        } catch (Exception e) {
            // ignore
        }
        return result;
    }

    private String getCoords(Node node) {
        String result = null;
        try {
            result = getImmediateChild(getImmediateChild(node, "Point"), "pos").getFirstChild().getNodeValue();
        } catch (Exception e) {
            // ignore
        }
        return result;
    }

    private String getCountry(Node node) {
        String result = null;
        try {
            result = getChild(node, "CountryName").getFirstChild().getNodeValue();
        } catch (Exception e) {
            // ignore
        }
        return result;
    }

    private String getLocality(Node node) {
        String result = null;
        try {
            result = getChild(node, "LocalityName").getFirstChild().getNodeValue();
        } catch (Exception e) {
            // ignore
        }
        return result;
    }

    private String getThoroughfare(Node node) {
        String result = null;
        try {
            result = getChild(node, "ThoroughfareName").getFirstChild().getNodeValue();
        } catch (Exception e) {
            // ignore
        }
        return result;
    }

    private String getPremises(Node node) {
        String result = null;
        try {
            result = getChild(node,"PremiseNumber").getFirstChild().getNodeValue();
        }catch (Exception e) {

        }
        return result;
    }

    private String getAdminArea(Node node) {
        String result = null;
        try {
            result = getChild(node, "AdministrativeAreaName").getFirstChild().getNodeValue();
        } catch (Exception e) {
            // ignore
        }
        return result;
    }

    private String getSubAdminArea(Node node) {
        String result = null;
        try {
            result = getChild(node, "SubAdministrativeAreaName").getFirstChild().getNodeValue();
        } catch (Exception e) {
            // ignore
        }
        return result;
    }

    private String getFeature(Node node) {
        String result = null;
        try {
            result = getImmediateChild(node, "name").getFirstChild().getNodeValue();
        } catch (Exception e) {
            // ignore
        }
        return result;
    }

    private Node getImmediateChild(Node parent, String name) throws Exception {
        NodeList children = parent.getChildNodes();
        for (int i = 0, l = children.getLength(); i < l; i++) {
            if (children.item(i).getNodeName().equals(name))
                return children.item(i);
        }
        throw new Exception("No such node");
    }

    private Node getChild(Node parent, String nodeName) throws Exception {
        Element pElement = (Element)parent;
        NodeList nodes = pElement.getElementsByTagName(nodeName);
        if(nodes != null && nodes.getLength() > 0)
            return nodes.item(0);
        throw new Exception("No such node");
    }


}
