package com.zui.vorite.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * cariature的种类
 *
 * @file: GenreCaricature.class
 * @since : 2018/11/29 22:01
 * @author: Dusk
 * @desc:
 */
@Data
@Table(name = "genre_caricature")
public class GenreCaricature implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String genre;
    private String genreDescribe;
    private List<Caricature> caricatureList;

    public GenreCaricature(){
        super();
    }

    public GenreCaricature(Long id, String genre, String genreDescribe) {
        super();
        this.id = id;
        this.genre = genre;
        this.genreDescribe = genreDescribe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenreDescribe() {
        return genreDescribe;
    }

    public void setGenreDescribe(String genreDescribe) {
        this.genreDescribe = genreDescribe;
    }

    public List<Caricature> getCaricatureList() {
        return caricatureList;
    }

    public void setCaricatureList(List<Caricature> caricatureList) {
        this.caricatureList = caricatureList;
    }

    @Override
    public String toString() {
        return "GenreCaricature{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                ", genreDescribe='" + genreDescribe + '\'' +
                '}';
    }
}
