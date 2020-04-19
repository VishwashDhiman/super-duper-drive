package com.udacity.jwdnd.course1.cloudstorage.entities;
// FILEID  	FILENAME  	CONTENTTYPE  	FILESIZE  	USERID  	FILEDATA
import lombok.Data;

import java.sql.Blob;

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
