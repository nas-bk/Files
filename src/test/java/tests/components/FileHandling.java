package tests.components;

import java.io.*;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileHandling {
    private final ClassLoader cl = FileHandling.class.getClassLoader();
    private String fileName;

    public File getFile(String fileFormat) throws Exception {
        openZip(fileFormat);
        File file = new File(fileName);
        return file;
    }

    void openZip(String fileFormat) throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("files_test.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(fileFormat)) {
                    fileName = entry.getName();
                    unpack(zis, entry);
                    break;
                }
            }
        }
    }

    void unpack(ZipInputStream zis, ZipEntry entry) throws Exception {
        try (FileOutputStream fout = new FileOutputStream(entry.getName())
        ) {
            for (int c = zis.read(); c != -1; c = zis.read()) {
                fout.write(c);
            }
            fout.flush();
        }
    }

    public void deleteFile(File file_del) {
        file_del.delete();
    }

}
