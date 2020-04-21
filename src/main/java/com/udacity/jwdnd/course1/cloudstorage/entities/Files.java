package com.udacity.jwdnd.course1.cloudstorage.entities;
import lombok.Data;

@Data
public class Files {
    public String fileId;
    public String fileName;
    public String contentType;
    public String fileSize;
    public String fileSrc;
    public String userId;
    public byte[] fileData;
}
