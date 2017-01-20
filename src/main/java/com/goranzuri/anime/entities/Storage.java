package com.goranzuri.anime.entities;

import javax.persistence.*;

/**
 * Created by gzuri on 20.01.2017..
 */
@Entity(name = "storage")
@NamedQueries({
        @NamedQuery(name="storage.findAll", query = "select s from storage s")
})
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id")
    private int storageId;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getStorageId() {
        return storageId;
    }
}
