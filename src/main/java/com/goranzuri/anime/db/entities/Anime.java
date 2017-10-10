package com.goranzuri.anime.db.entities;

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
    private Date createdOn;

    @Column(name = "date_modified", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "name_on_disk")
    private String nameOnDisk;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "anidb_code")
    private String anidbCode;

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

    public void setDateAdded(Date dateAdded) {
        this.createdOn = dateAdded;
    }
    public void setCreatedOn(Date dateAdded) {
        this.createdOn = dateAdded;
    }

    public String getNameOnDisk() {
        return nameOnDisk;
    }

    public void setNameOnDisk(String nameOnDisk) {
        this.nameOnDisk = nameOnDisk;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAnidbCode() {
        return anidbCode;
    }

    public void setAnidbCode(String anidbCode) {
        this.anidbCode = anidbCode;
    }
}
