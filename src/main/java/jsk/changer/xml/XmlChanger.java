package jsk.changer.xml;

import jsk.changer.common.Changer;
import jsk.changer.common.FileReader;
import jsk.changer.common.ReaderType;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class XmlChanger {

    public XmlChanger() {

    }

    public static Changer fromXml(Path p) {
        return new ChangerImpl(p);
    }

    public static Changer fromXml(File f) {
        return new ChangerImpl(f);
    }

    public static Changer fromXml(String x) {
        return new ChangerImpl(x);
    }

    private static class ChangerImpl implements Changer {
        private Path path;
        private String str;
        private File file;
        private final ReaderType readerType;
        private final FileReader fileReader = new FileReader();

        public ChangerImpl(Path path) {
            this.path = path;
            readerType = ReaderType.PATH;
        }

        public ChangerImpl(File file) {
            this.file = file;
            readerType = ReaderType.FILE;
        }

        public ChangerImpl(String str) {
            this.str = str;
            readerType = ReaderType.STRING;
        }

        public String toJson() throws IOException, ParserConfigurationException, SAXException {
            return this.toJson(false);
        }

        public String toJson(boolean beautify) throws IOException, ParserConfigurationException, SAXException {
            return switch (readerType.getSignal()) {
                case 0 -> new ToJson(fileReader.read(this.path)).convert(beautify);
                case 1 -> new ToJson(fileReader.read(this.file)).convert(beautify);
                case 2 -> new ToJson(this.str).convert(beautify);
                default -> throw new UnsupportedOperationException();
            };
        }
    }
}
