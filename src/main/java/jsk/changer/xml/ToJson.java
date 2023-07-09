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

        if (beautify) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.result);
        } else {
            return mapper.writeValueAsString(this.result);
        }
    }

    /**
     * XML 의 기본 노드를 JSON 의 Key-Value 형태로 변환
     * @param key XML의 태그명
     * @param value 태그에 해당하는 값
     */
    private void setNode(Map<String, Object> map, String key, Object value) {
        map.put(key, value);
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
