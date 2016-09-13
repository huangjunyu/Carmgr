package com.yiwucheguanjia.carmgr.utils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class ConfiModel {
    private String username;

    private int list_size;

    private String opt_info;

    private String config_key;

    private String opt_state;

    private List<Config_value_list> config_value_list;
    /**
     * name : 王五
     * gender : man
     * age : 15
     * height : 140cm
     */

    private String name;
    private String gender;
    private int age;
    private String height;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setList_size(int list_size) {
        this.list_size = list_size;
    }

    public int getList_size() {
        return this.list_size;
    }

    public void setOpt_info(String opt_info) {
        this.opt_info = opt_info;
    }

    public String getOpt_info() {
        return this.opt_info;
    }

    public void setConfig_key(String config_key) {
        this.config_key = config_key;
    }

    public String getConfig_key() {
        return this.config_key;
    }

    public void setOpt_state(String opt_state) {
        this.opt_state = opt_state;
    }

    public String getOpt_state() {
        return this.opt_state;
    }

    public void setConfig_value_list(List<Config_value_list> config_value_list) {
        this.config_value_list = config_value_list;
    }

    public List<Config_value_list> getConfig_value_list() {
        return this.config_value_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public class Config_value_list {
        private String detail;

        private String config_value;

        private String sequence;

        private String url;

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getDetail() {
            return this.detail;
        }

        public void setConfig_value(String config_value) {
            this.config_value = config_value;
        }

        public String getConfig_value() {
            return this.config_value;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getSequence() {
            return this.sequence;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }
    }

}
