package com.example.demo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    ResourceLoader resourceLoader;

    public Long getFileLength(String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/" + filename);
        System.out.println("FileService.getFileLength: " + filename);

        if (!resource.exists()) {
            throw new NoSuchElementException();
        }

        return resource.getFile().length();
    }

    public String getFileString(String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/" + filename);
        System.out.println("FileService.getFileEncoded: " + filename);

        if (!resource.exists()) {
            throw new NoSuchElementException();
        }

        BufferedReader fileReader = new BufferedReader(new FileReader(resource.getFile()));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = fileReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        fileReader.close();

        return stringBuilder.toString();
    }

    public String fileForm(MultipartFile file) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/");

        System.out.println("FileService.fileForm: " + file.getOriginalFilename());
        System.out.println(resource.getFile().getAbsolutePath());

        File newFile = new File(resource.getFile().getAbsolutePath(), file.getOriginalFilename());

        file.transferTo(newFile);

        return resource.getFile().getAbsolutePath();
    }

    public int fileConcat(MultipartFile file, String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/" + filename);

        System.out.println("FileService.fileAppend: " + file.getOriginalFilename() + " append to " + filename);
        System.out.println("at: " + resource.getFile().getAbsolutePath());

        if (!resource.exists()) {
            throw new NoSuchElementException();
        }

        FileWriter fileWriter = new FileWriter(resource.getFile(), true);

        Scanner scanner = new Scanner(file.getInputStream());

        fileWriter.write("\n");

        int size = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            size += line.length();
            System.out.println(line);
            fileWriter.write(line);
        }

        scanner.close();
        fileWriter.close();

        return size;
    }

    public String appendTextToFile(String string, String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/" + filename);

        System.out.println("FileService.fileAppend: " + string + " append to " + filename);
        System.out.println("at: " + resource.getFile().getAbsolutePath());

        if (!resource.exists()) {
            throw new NoSuchElementException();
        }

        FileWriter fileWriter = new FileWriter(resource.getFile(), true);

        fileWriter.write("\n" + string);
        fileWriter.close();

        return "Size of added content: " + string.length() + " Bytes";
    }

    public ResponseEntity<Resource> download(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/" + fileName);

        InputStreamResource inputStream = new InputStreamResource(new FileInputStream(resource.getFile()));

        ContentDisposition contentDisposition = ContentDisposition.builder("inline").filename(resource.getFilename())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);

        return ResponseEntity.ok().contentLength(resource.getFile().length()).headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(inputStream);
    }

    public ResponseEntity<Object> downloadFile(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/" + fileName);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", resource.getFilename()));

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(resource.getFile().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

        return responseEntity;
    }

    public String getFileEncoded(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/" + fileName);

        if (!resource.exists()) {
            throw new NoSuchElementException();
        }

        FileInputStream stream = new FileInputStream(resource.getFile());

        int bufLength = 2048;
        byte[] buffer = new byte[2048];
        byte[] data;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int readLength;
        while ((readLength = stream.read(buffer, 0, bufLength)) != -1) {
            out.write(buffer, 0, readLength);
        }

        data = out.toByteArray();
        String imageString = Base64.getEncoder().withoutPadding().encodeToString(data);

        out.close();
        stream.close();

        return imageString;
    }
}
