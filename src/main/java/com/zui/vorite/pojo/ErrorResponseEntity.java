package com.zui.vorite.pojo;


import lombok.Data;

/**
 * 异常信息的格式
 * @author Dusk
 */

@Data
public class ErrorResponseEntity{
    private int code;
    private String field;
    private String message;

    public ErrorResponseEntity(){}

    public ErrorResponseEntity(int code, String field, String message) {
        this.code = code;
        this.field = field;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponseEntity{" +
                "code=" + code +
                ", field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
