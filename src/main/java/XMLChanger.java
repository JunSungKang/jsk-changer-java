import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XMLChanger {

    public XMLChanger() {

    }

    public String toJson(Path xml) throws IOException {
        return this.toJson(xml.toFile());
    }
    
    public String toJson(File xml) throws IOException {
        if (!xml.isFile()) {
            throw new FileNotFoundException(xml.getAbsolutePath());
        }

        FileInputStream in = new FileInputStream(xml);
        byte[] xmlByte = in.readAllBytes();

        return this.toJson(new String(xmlByte));
    }

    public String toJson(String xml) {
        return "";
    }
}
