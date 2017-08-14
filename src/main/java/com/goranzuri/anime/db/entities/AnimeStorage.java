package com.goranzuri.anime.entities;

import org.hibernate.annotations.LazyCollection;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gzuri on 20.01.2017..
 */
@Entity(name = "anime_storage")
public class AnimeStorage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anime_storage_id")
    private int animeStorageId;

    @ManyToOne(targetEntity = Anime.class)
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }
}
