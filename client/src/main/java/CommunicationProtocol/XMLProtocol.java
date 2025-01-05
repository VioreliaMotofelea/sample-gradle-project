package CommunicationProtocol;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class XMLProtocol implements Protocol {

    @Override
    public String input(String input) {
        return "<message>" + input + "</message>";
    }

    @Override
    public String output(String output) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(output)));

        NodeList nodeList = doc.getElementsByTagName("message");
        if (nodeList.getLength() == 0) {
            throw new IOException("No <message> element found in XML");
        }

        return nodeList.item(0).getTextContent();
    }
}
