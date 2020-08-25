package com.ukrainer.infostroy;

public class Util {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean IsNUll(String str){
        boolean res = false;
        if(!str.equals("")){
            res = true;
        }
        return res;
    }

}