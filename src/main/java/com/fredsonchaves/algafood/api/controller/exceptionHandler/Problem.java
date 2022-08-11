package com.fredsonchaves.algafood.api.controller.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private Integer status;
    private String type;
    private String title;
    private String detail;

    private Problem() {

    }

    public static Problem builder() {
        return new Problem();
    }

    public Integer getStatus() {
        return status;
    }

    public Problem setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getType() {
        return type;
    }

    public Problem setType(String type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Problem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public Problem setDetail(String detail) {
        this.detail = detail;
        return this;
    }
}
