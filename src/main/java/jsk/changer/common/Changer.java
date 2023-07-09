package jsk.changer.common;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface Changer {

    String toJson() throws IOException, ParserConfigurationException, SAXException;
}
