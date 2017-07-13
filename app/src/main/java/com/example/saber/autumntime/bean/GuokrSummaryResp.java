package com.example.saber.autumntime.bean;

import java.util.List;

/**
 * Created by saber on 2017/7/13.
 */

public class GuokrSummaryResp {

    private String now;
    private boolean ok;
    private List<ResultResp> result;

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<ResultResp> getResult() {
        return result;
    }

    public void setResult(List<ResultResp> result) {
        this.result = result;
    }
}
