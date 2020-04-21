package com.udacity.jwdnd.course1.cloudstorage.entities;

import lombok.Data;

@Data
public class Notes {
    public String userId;
    public String noteId;
    public String noteTitle;
    public String noteDescription;
}
