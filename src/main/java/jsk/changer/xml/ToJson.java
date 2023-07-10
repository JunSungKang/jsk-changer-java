package jsk.changer.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsk.changer.exception.XmlParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class ToJson {
    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
    private final DocumentBuilder builder = factory.newDocumentBuilder();
    private final InputStream inputStream;

    private final ObjectMapper mapper = new ObjectMapper();

    private final List<String> SKIP_WORD = Arrays.asList("#text");
    private final Map<String, Object> result = new LinkedHashMap<>();

    public ToJson(String xml) throws ParserConfigurationException {
        this.inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
    }

    protected String convert(boolean beautify) throws IOException, SAXException {
        Document document = builder.parse(this.inputStream);

        Element root = document.getDocumentElement();
        String rootTag = root.getNodeName();
        result.put(rootTag, new LinkedHashMap<>());

        NodeList list = root.getChildNodes();

        for (int i=0; i<list.getLength(); i++) {
            Node node = list.item(i);

            if (SKIP_WORD.contains(node.getNodeName())) {
                continue;
            }

            if (hasChildNodes(node)) {
                nextChildNode(this.result, node, rootTag);
            } else {
                setNode(this.result, node, rootTag);
            }
        }

        if (beautify) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.result);
        } else {
            return mapper.writeValueAsString(this.result);
        }
    }

    /**
     * Convert the basic node of XML to Key-Value format of JSON
     * @param tResult Map converted to JSON
     * @param node Special node
     * @param parentNodeName Parent node name
     */
    private void setNode(Map<String, Object> tResult, Node node, String parentNodeName) throws XmlParserException {
        String tag = node.getNodeName();
        String value = node.getTextContent();

        if (isMap(tResult.get(parentNodeName))) {
            ((Map<String, Object>) tResult.get(parentNodeName)).put(tag, value);
        } else {
            throw new XmlParserException("Parsing failed with invalid data. Error node : " + parentNodeName);
        }
    }

    /**
     * A method that is executed when there is a child node and extracts the child node
     * @param tResult Map converted to JSON
     * @param nodes Special nodes
     * @param parentNodeName The name of the parent node of the current node
     */
    private void nextChildNode(Map<String, Object> tResult, Node nodes, String parentNodeName) throws XmlParserException {
        NodeList list = nodes.getChildNodes();

        for (int i=0; i<list.getLength(); i++) {
            Node node = list.item(i);

            if (SKIP_WORD.contains(node.getNodeName())) {
                continue;
            }

            String nodeName = node.getNodeName();
            if (hasChildNodes(node)) {
                tResult.put(parentNodeName, new LinkedHashMap<>());
                nextChildNode(tResult, nodes, nodeName);
            } else {
                setNode(tResult, node, parentNodeName);
            }
        }
    }

    /**
     * Check if the variable is a Map
     * @param node variable to check
     * @return true if next node, otherwise false;
     */
    private boolean hasChildNodes(Node node) {
        return node.getChildNodes().getLength() > 1;
    }

    /**
     * Check if the variable is a Map
     * @param obj variable to check
     * @return true if Map, otherwise false;
     */
    private boolean isMap(Object obj) {
        return obj instanceof Map;
    }
}
