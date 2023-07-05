package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class FileReader {

    public String read(Path path) throws IOException {
        return this.read(path.toFile());
    }

    public String read(File file) throws IOException {
        if (!file.isFile()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }

        FileInputStream in = new FileInputStream(file);
        byte[] xmlByte = in.readAllBytes();

        return new String(xmlByte, StandardCharsets.UTF_8);
    }
}
