package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class ToJson {
    protected ToJson() {

    }

    protected String convert(Path xml) throws IOException {
        return this.convert(xml.toFile());
    }

    protected String convert(File xml) throws IOException {
        if (!xml.isFile()) {
            throw new FileNotFoundException(xml.getAbsolutePath());
        }

        FileInputStream in = new FileInputStream(xml);
        byte[] xmlByte = in.readAllBytes();

        return this.convert(new String(xmlByte));
    }

    protected String convert(String xml) {
        return "";
    }
}
