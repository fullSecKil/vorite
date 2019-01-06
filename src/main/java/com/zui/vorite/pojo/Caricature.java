package com.zui.vorite.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 漫画类
 *
 * @author Dusk
 */

@Data
@Table(name = "caricature")
public class Caricature implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "漫画名不能为空")
    private String name;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String title;
    private String path;
    private String url;
    private Integer star;
    private String message;
    private Integer level;
    private Integer genre;
    private Boolean isAction;

    public Caricature() {
    }

    public Caricature(@NotBlank(message = "漫画名不能为空") String name, Timestamp createTime, Timestamp updateTime, String title, String path,String url, Integer star, String message, Integer level, Integer genre, Boolean isAction) {
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.title = title;
        this.path = path;
        this.url = url;
        this.star = star;
        this.message = message;
        this.level = level;
        this.genre = genre;
        this.isAction = isAction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public Boolean getIsAction() {
        return isAction;
    }

    public void setIsAction(Boolean isAction) {
        this.isAction = isAction;
    }

    @Override
    public String toString() {
        return "Caricature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", url='" + url + '\'' +
                ", star=" + star +
                ", message='" + message + '\'' +
                ", level=" + level +
                ", genre=" + genre +
                ", isAction=" + isAction +
                '}';
    }
}
