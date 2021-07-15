package com.example.projecttravel;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Upload{

    public String title, descript, uploadId;
    public String url, uid;

    public Upload() {
    }

    public Upload(String title,String descript, String url, String uid, String uploadId) {
        this.title = title;
        this.descript = descript;
        this.url= url;
        this.uid = uid;
        this.uploadId = uploadId;
    }

    public Upload(String title,String url) {
        this.title = title;
        this.url= url;
    }

    public String getTitle() {
        return title;
    }

    public String getUid() {
        return uid;
    }

    public String getDescript() {
        return descript;
    }

    public String getUrl() {
        return url;
    }
    public String getUploadId() {
        return uploadId;
    }



}