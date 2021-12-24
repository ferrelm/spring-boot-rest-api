package com.example.demo;

import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/{filename}/size")
    public String getFileLength(@PathVariable String filename) throws IOException {
        return "Size in bytes: " + fileService.getFileLength(filename).toString();
    }

    @GetMapping("/{filename}")
    public String getFileString(@PathVariable String filename) throws IOException {
        return fileService.getFileString(filename);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String fileForm(@RequestParam MultipartFile file) throws IOException {
        return fileService.fileForm(file);
    }

    @PutMapping("/{filename}/concat")
    public String fileConcat(@RequestParam MultipartFile file, @PathVariable String filename)
            throws IOException {
        return "Size of added content: " + fileService.fileConcat(file, filename) + " Bytes";
    }

    @PutMapping("/{filename}")
    public String appendTextToFile(@RequestParam String string, @PathVariable String filename)
            throws IOException {
        return fileService.appendTextToFile(string, filename);
    }

    @GetMapping("/{filename}/download")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws IOException {
        return fileService.download(filename);
    }

    @GetMapping("/{filename}/download2")
    public ResponseEntity<Object> downloadFile(@PathVariable String filename) throws IOException {
        return fileService.downloadFile(filename);
    }

    @GetMapping("/{filename}/encoded")
    public String getFileEncoded(@PathVariable String filename) throws IOException {
        return fileService.getFileEncoded(filename);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ MyResourceNotFoundException.class, NoSuchElementException.class })
    public void handleNotFound(Exception ex, HttpServletRequest req) {
        System.out.println("URL: " + req.getRequestURI());
        System.out.println("Message: " + ex.getMessage());
    }
}
