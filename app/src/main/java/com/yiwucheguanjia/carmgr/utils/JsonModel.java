package com.yiwucheguanjia.carmgr.utils;

import java.util.List;

public class JsonModel {
    private String username;

    private String opt_info;

    private String opt_state;

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setOpt_info(String opt_info){
        this.opt_info = opt_info;
    }
    public String getOpt_info(){
        return this.opt_info;
    }
    public void setOpt_state(String opt_state){
        this.opt_state = opt_state;
    }
    public String getOpt_state(){
        return this.opt_state;
    }

}