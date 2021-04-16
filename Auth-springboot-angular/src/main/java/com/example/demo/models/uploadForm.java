package com.example.demo.models;


import org.springframework.web.multipart.MultipartFile;

public class uploadForm {
    private MultipartFile fileData;

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }

    public uploadForm(MultipartFile fileData) {
        this.fileData = fileData;
    }
}
