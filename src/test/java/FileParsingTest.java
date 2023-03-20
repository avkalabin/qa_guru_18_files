import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.io.Files;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.fasterxml.jackson.databind.ObjectMapper;


public class FileParsingTest {


    private final ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    void zippedPdfParseTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("zipFiles.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                switch (Files.getFileExtension(entry.getName())) {
                    case "pdf" -> {
                        PDF pdf = new PDF(zis);
                        Assertions.assertEquals("Bradley SHAW", pdf.author,
                                "pdf parsed failed");
                    }
                    case "xlsx" -> {
                        XLS xls = new XLS(zis);
                        Assertions.assertTrue(
                                xls.excel.getSheetAt(0).getRow(5).getCell(1).getStringCellValue().contains("192.168.1.100"),
                                "xlsx parsed failed");
                    }
                    case "csv" -> {
                        CSVReader reader = new CSVReader(new InputStreamReader(zis));
                        List<String[]> content = reader.readAll();
                        Assertions.assertArrayEquals(new String[]{"Jack", "McGinnis", "220 hobo Av.", "Phila", " PA", "09119"}, content.get(1),
                                "csv parsed failed");
                    }
                }
            }
        }
    }

    @Test
    void jsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("jackson.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            ParserLibrary parserLibrary = mapper.readValue(isr, ParserLibrary.class);

            Assertions.assertEquals("2.13.1", parserLibrary.version);
            Assertions.assertEquals("Tatu Saloranta", parserLibrary.developers.get(0).name);
        }
    }
}

