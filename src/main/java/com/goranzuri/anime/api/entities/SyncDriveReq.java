package com.goranzuri.anime.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by gzuri on 14/10/2017.
 */
public class SyncDriveReq {

    @JsonProperty
    @NotEmpty
    List<String> namesOnDisk;

    @JsonProperty
    @NotEmpty
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
