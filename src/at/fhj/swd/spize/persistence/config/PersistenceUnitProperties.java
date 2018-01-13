package at.fhj.swd.spize.persistence.config;

import java.util.MissingResourceException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.InputStream;
import java.io.IOException;

public class PersistenceUnitProperties {

protected static DocumentBuilder dBuilder;
    protected static Document document = null;

    private static final String propertyPrefix = "javax.persistence.jdbc.";

    public static final String USER = propertyPrefix + "user";
    public static final String PASSWORD = propertyPrefix + "password";
    public static final String URL = propertyPrefix + "url";
    public static final String DRIVER = propertyPrefix + "driver";

    private static final String resourceDir = "META-INF";
    private static final String resourceName = "persistence.xml";


    private static void read() throws MissingResourceException {
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader()
                    .getResource(resourceDir + "/" + resourceName)
                    .openStream();
        } catch (IOException exc) {
            throw new MissingResourceException(exc.toString(), resourceDir, resourceName);
        }

        try {
            document = dBuilder.parse(is);
        } catch (IOException exc) {
            throw new MissingResourceException(exc.toString(), resourceDir, resourceName);
        } catch (SAXException exc) {
            throw new MissingResourceException(exc.toString(), resourceDir, resourceName);
        }

    }

    public static String getUrl() throws MissingResourceException {
        return getPropertyValue(URL);
    }

    public static String getDriver() throws MissingResourceException {
        return getPropertyValue(DRIVER);
    }

    public static String getUser() throws MissingResourceException {
        return getPropertyValue(USER);
    }

    public static String getPassword() throws MissingResourceException {
        return getPropertyValue(PASSWORD);
    }

    private static String getPropertyValue(String propertyName) throws MissingResourceException {
        String tag = "property";

        if (document == null) {
            setup();
            read();
        }


        NodeList nodeList = document.getElementsByTagName(tag);

        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);

            Element element = (Element) node;

            if (element.getAttribute("name").equals(propertyName)) {
                return element.getAttribute("value");
            }
        }
        return null;
    }

    private static void setup() {
        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();

        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException exc) {
            throw new MissingResourceException(exc.toString(), resourceDir, resourceName);
        }
    }

}
