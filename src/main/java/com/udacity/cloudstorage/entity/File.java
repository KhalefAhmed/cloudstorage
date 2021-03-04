package com.udacity.cloudstorage.entity;

public class File {

    private Integer fileId;
    private String fileName;
    private String contentType;
    private String filesize;
    private Integer userid;
    private byte[] filedata;

    public File(Integer fileId, String filename, String contenttype, String filesize, Integer userid, byte[] filedata) {
        this.fileId = fileId;
        this.fileName = filename;
        this.contentType = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public File() {
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
}
