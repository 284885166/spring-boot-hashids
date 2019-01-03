package org.yanshen.demo.vo;

/**
 * Created by limengyi on 2017/7/24.
 */
public class Response {

    private int code;
    private Object data;
    private String message;

    public Response() {

    }

    public Response(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
