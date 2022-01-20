package com.example.fixbug.api.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Error", strict = false)
public class CancelProductYahoo {

    @Element(name = "Message", required = false)
    private String message;

    @Element(name = "Detail", required = false)
    private String detail;

    @Element(name = "Code", required = false)
    private int code;

    @Element(name = "xmlns", required = false)
    public String xmlns;

    @Element(name = "text", required = false)
    public String text;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
