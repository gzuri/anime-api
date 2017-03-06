package com.goranzuri.anime.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by gzuri on 20.01.2017..
 */
@Entity(name = "anime")
@NamedQueries({
        @NamedQuery(name = "anime.findAll", query = "select a from anime a"),
})
public class Anime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anime_id")
    private int animeId;

    @Column(name = "name")
    private String name;

    @Column(name = "date_added", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;

    @Column(name = "name_on_disk")
    private String nameOnDisk;

    public int getAnimeId() {
        return animeId;
    }
    public void setAnimeId(int animeId){ this.animeId = animeId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getNameOnDisk() {
        return nameOnDisk;
    }

    public void setNameOnDisk(String nameOnDisk) {
        this.nameOnDisk = nameOnDisk;
    }
}
