package com.yiwucheguanjia.carmgr.my;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
public class TestGson {
    /**
     * username : 18617376560
     * services_list : [{"icon_path":"http://112.74.13.51:8080/carmgr/upload/image/service/service_1.png","service_name":"车险","url":"default"},{"icon_path":"http://112.74.13.51:8080/carmgr/upload/image/service/service_2.png","service_name":"上牌","url":"default"},{"icon_path":"http://112.74.13.51:8080/carmgr/upload/image/service/service_3.png","service_name":"车检","url":"default"},{"icon_path":"http://112.74.13.51:8080/carmgr/upload/image/service/service_4.png","service_name":"维修","url":"default"},{"icon_path":"http://112.74.13.51:8080/carmgr/upload/image/service/service_5.png","service_name":"驾考","url":"default"},{"icon_path":"http://112.74.13.51:8080/carmgr/upload/image/service/service_6.png","service_name":"保养","url":"default"},{"icon_path":"http://112.74.13.51:8080/carmgr/upload/image/service/service_7.png","service_name":"车贷","url":"default"},{"icon_path":"http://112.74.13.51:8080/carmgr/upload/image/service/service_8.png","service_name":"租车","url":"default"},{"icon_path":"http://112.74.13.51:8080/carmgr/upload/image/service/service_9.png","service_name":"二手","url":"default"},{"icon_path":"http://112.74.13.51:8080/carmgr/upload/image/service/service_10.png","service_name":"分类","url":"default"}]
     * list_size : 10
     * opt_info :
     * opt_state : success
     */

    private String username;
    private int list_size;
    private String opt_info;
    private String opt_state;
    /**
     * icon_path : http://112.74.13.51:8080/carmgr/upload/image/service/service_1.png
     * service_name : 车险
     * url : default
     */

    private List<ServicesListBean> services_list;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getList_size() {
        return list_size;
    }

    public void setList_size(int list_size) {
        this.list_size = list_size;
    }

    public String getOpt_info() {
        return opt_info;
    }

    public void setOpt_info(String opt_info) {
        this.opt_info = opt_info;
    }

    public String getOpt_state() {
        return opt_state;
    }

    public void setOpt_state(String opt_state) {
        this.opt_state = opt_state;
    }

    public List<ServicesListBean> getServices_list() {
        return services_list;
    }

    public void setServices_list(List<ServicesListBean> services_list) {
        this.services_list = services_list;
    }

    public static class ServicesListBean {
        private String icon_path;
        private String service_name;
        private String url;

        public String getIcon_path() {
            return icon_path;
        }

        public void setIcon_path(String icon_path) {
            this.icon_path = icon_path;
        }

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
