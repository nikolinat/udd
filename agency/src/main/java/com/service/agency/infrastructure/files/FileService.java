package com.service.agency.infrastructure.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static final String DATA_DIR_PATH = "src/main/java/files";
    public String saveUploadedFile(String fileName, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(DATA_DIR_PATH);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName + ".pdf");
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        return DATA_DIR_PATH + File.separator + fileName + ".pdf";
    }

    public String readFromPDFFile(String fileName) {
        File file = new File(fileName);

        PDDocument document;
        try {
            document = Loader.loadPDF(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PDFTextStripper pdfStripper;
        try {
            pdfStripper = new PDFTextStripper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return pdfStripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
