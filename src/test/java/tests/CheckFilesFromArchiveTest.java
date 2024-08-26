package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import tests.components.FileHandling;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CheckFilesFromArchiveTest {
    FileHandling fileHandling = new FileHandling();
    private File file;

    @AfterEach
    void afterEach() {
        fileHandling.deleteFile(file);
    }

    @Test
    void checkPDFTest() throws Exception {
        file = fileHandling.getFile(".pdf");
        PDF pdfFile = new PDF(file);

        assertThat(pdfFile.text)
                .contains("Этот PDF файл предназначен для тестирования.");

    }

    @Test
    void checkXLSTest() throws Exception {
        file = fileHandling.getFile(".xlsx");
        XLS xlsFile = new XLS(file);
        String valueCell = xlsFile.excel.getSheetAt(0).getRow(3).getCell(4).getStringCellValue();

        assertThat(valueCell)
                .isEqualTo("thewitcher@test.com");

    }

    @Test
    void checkCSVTest() throws Exception {
        file = fileHandling.getFile(".csv");
        try (CSVReader csvReader = new CSVReader(new FileReader(file))
        ) {
            List<String[]> data = csvReader.readAll();

            assertThat(data.get(1))
                    .containsExactly("admin", "1admin_1");

        }
    }
}

