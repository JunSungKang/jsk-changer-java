package xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class JskXmlChanger {

    private Path path = null;
    private File file = null;
    private StringBuilder xml = new StringBuilder();

    public JskXmlChanger() {

    }

    public static class builder {
        private Path path = null;
        private File file = null;
        private StringBuilder xml = new StringBuilder();
        private final ToJson toJson = new ToJson();

        public builder(Path path) {
            this.path = path;
        }

        public builder(File file) {
            this.file = file;
        }

        public builder(String xml) {
            this.xml.append(xml);
        }

        public String build() throws IOException {
            if (this.path != null) {
                return this.toJson.convert(this.path.toFile());
            } else if (this.file != null) {
                return this.toJson.convert(this.file);
            } else if (!this.xml.isEmpty()) {
                return "";
            } else {
                throw new NullPointerException(null);
            }
        }
    }
}
