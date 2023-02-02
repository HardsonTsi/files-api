package com.hardtech.fileapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "filesapi")
public class FileDetails {

    @Id
    private String id;
    private String filename;
    private String extension;
    private String localURL;
    private String contentType;
    private String url;

}
