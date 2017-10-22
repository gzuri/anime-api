package com.goranzuri.anime.api.syncservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by gzuri on 22/10/2017.
 */
public class SyncDriveReq {
    @JsonProperty

    List<String> namesOnDisk;

    @JsonProperty
    String storage;

    public SyncDriveReq(){

    }

    public List<String> getNamesOnDisk() {
        return namesOnDisk;
    }

    public void setNamesOnDisk(List<String> namesOnDisk) {
        this.namesOnDisk = namesOnDisk;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
}
