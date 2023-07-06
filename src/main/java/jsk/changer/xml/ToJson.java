package jsk.changer.xml;

import jsk.changer.exception.XmlParserException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class ToJson {
    private String xml = "";
    private final List<String> SKIP_WORD = Arrays.asList("#text");
    private final Map<String, Object> result = new LinkedHashMap<>();

    public ToJson(String xml) {
        this.xml = xml;
    }

    private void parser() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputStream in = new ByteArrayInputStream(this.xml.getBytes(StandardCharsets.UTF_8));
        Document document = builder.parse(in);

        Element root = document.getDocumentElement();
        String rootName = root.getNodeName();
        setNode(result, rootName, new LinkedHashMap<>());

        NodeList list = root.getChildNodes();

        for (int i=0; i<list.getLength(); i++) {
            Node node = list.item(i);

            if (SKIP_WORD.contains(node.getNodeName())) {
                continue;
            }

            String tag = node.getNodeName();
            String value = node.getTextContent();

            if (isMap(result.get(rootName))) {
                setNode((Map<String, Object>) result.get(rootName), tag, value);
            } else {
                throw new XmlParserException("Parsing failed with invalid data. Error node : " +rootName);
            }
        }
    }

    protected String convert() {
        try {
            parser();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        return this.result.toString();
    }

    /**
     * XML 의 기본 노드를 JSON 의 Key-Value 형태로 변환
     * @param key XML의 태그명
     * @param value 태그에 해당하는 값
     * @return 변환된 Key-Value 형태의 Map
     */
    private Map<String, Object> setNode(Map<String, Object> map, String key, Object value) {
        map.put(key, value);
        return map;
    }

    /**
     * 변수가 Map 인지 확인
     * @param obj 확인할 변수
     * @return Map인 경우 true, 아닌 경우 false;
     */
    private boolean isMap(Object obj) {
        return obj instanceof Map;
    }
}
