import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import xml.JskXmlChanger;

class JskXmlChangerTest {

    final JskXmlChanger jskXmlChanger = new JskXmlChanger();

    @Test
    void testCase1() throws IOException {
        // Origin xml value
        File xmlFile = Paths.get("sample", "case1.xml").toFile();
        if (!xmlFile.isFile()) {
            throw new FileNotFoundException(xmlFile.getAbsolutePath());
        }

        // Expected json value
        File expectedFile = Paths.get("sample", "case1.json").toFile();
        if (!expectedFile.isFile()) {
            throw new FileNotFoundException(expectedFile.getAbsolutePath());
        }

        FileInputStream expectedIn = new FileInputStream(expectedFile);
        byte[] jsonByte = expectedIn.readAllBytes();
        String json = new String(jsonByte);

        String convertJsonValue = new JskXmlChanger.builder(xmlFile).build();
        assertThat(convertJsonValue).isEqualTo(json);
    }

    @Test
    void testCase2() throws IOException {
        // Origin xml value
        File xmlFile = Paths.get("sample", "case2.xml").toFile();
        if (!xmlFile.isFile()) {
            throw new FileNotFoundException(xmlFile.getAbsolutePath());
        }

        // Expected json value
        File expectedFile = Paths.get("sample", "case2.json").toFile();
        if (!expectedFile.isFile()) {
            throw new FileNotFoundException(expectedFile.getAbsolutePath());
        }

        FileInputStream expectedIn = new FileInputStream(expectedFile);
        byte[] jsonByte = expectedIn.readAllBytes();
        String json = new String(jsonByte);

        String convertJsonValue = new JskXmlChanger.builder(xmlFile).build();
        assertThat(convertJsonValue).isEqualTo(json);
    }
}