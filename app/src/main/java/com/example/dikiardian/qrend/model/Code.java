package com.example.dikiardian.qrend.model;

import java.sql.Time;

/**
 * Created by Diki on 4/23/2018.
 */

public class Code {
    private int codeId;
    private String code;
    private Time created_at;
    private User creator;

    public Code(int codeId, String code, Time created_at, User creator) {
        this.codeId = codeId;
        this.code = code;
        this.created_at = created_at;
        this.creator = creator;
    }

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Time getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Time created_at) {
        this.created_at = created_at;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
