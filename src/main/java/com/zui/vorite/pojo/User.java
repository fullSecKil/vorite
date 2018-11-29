package com.zui.vorite.pojo;

import lombok.Data;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User实体类
 *
 * @author Dusk
 */
@Data
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 10, message = "用户名长度必须在{min}-{max}之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 15, message = "密码长度必须在{min}-{max}之间")
    private String password;

    @Email
    private String email;

    private String header;

    private Timestamp createTime;

    private String collection;

    // @MapKey()
    // @ResultMap(value = "")
    private String roles;

    public User() {

    }

    public User(String username, String password, String email, String header,Timestamp createTime, String collection, String roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.header = header;
        this.createTime = createTime;
        this.collection = collection;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", header='" + header + '\'' +
                ", createTime=" + createTime +
                ", collection='" + collection + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
