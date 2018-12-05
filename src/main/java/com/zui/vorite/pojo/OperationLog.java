package com.zui.vorite.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 日志类, 每个请求都会创建bean的实例
 * @file: OperationLog.class
 * @author: Dusk
 * @since: 2018/12/5 11:07
 * @desc:
 */
@Component
@RequestScope
@Data
@Table(name = "operation_log")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String httpMethod;
    private String ip;
    private String classMethod;
    private String args;
    private String response;
    private Timestamp time;

    public OperationLog(){}

    public OperationLog(String url, String httpMethod, String ip, String classMethod, String args, String response) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.ip = ip;
        this.classMethod = classMethod;
        this.args = args;
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", ip='" + ip + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", args='" + args + '\'' +
                ", response='" + response + '\'' +
                ", time=" + time +
                '}';
    }
}
