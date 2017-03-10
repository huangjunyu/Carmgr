package com.yiwucheguanjia.carmgr.home.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class HotRecommendBean {
    String hotRecommendUrlImg;//热闹推荐图片的URL
    /**
     * username : 18617376560
     * services_list : [{"img_path":"http://112.74.13.51:8080/carmgr/upload/image/recommend/honda.jpg","price":"200","merchant_name":"东风本田TIAN店","uptime":"2016-06-01 00:00:00.0","service_name":"经典机车保养","service_item":"汽车保养","mobile":"13888888888"},{"img_path":"http://112.74.13.51:8080/carmgr/upload/image/recommend/bmw.jpg","price":"3600","merchant_name":"宝马顺泽店","uptime":"2016-06-01 00:00:00.0","service_name":"二手车过户","service_item":"旧车过户","mobile":"13666666666"}]
     * list_size : 2
     * opt_info :
     * opt_state : success
     */

    private String username;
    private int list_size;
    private String opt_info;
    private String opt_state;
    /**
     * img_path : http://112.74.13.51:8080/carmgr/upload/image/recommend/honda.jpg
     * price : 200
     * merchant_name : 东风本田TIAN店
     * uptime : 2016-06-01 00:00:00.0
     * service_name : 经典机车保养
     * service_item : 汽车保养
     * mobile : 13888888888
     */

    private List<ServicesListBean> services_list;

    public String getHotRecommendUrlImg() {
        return hotRecommendUrlImg;
    }

    public void setHotRecommendUrlImg(String hotRecommendUrlImg) {
        this.hotRecommendUrlImg = hotRecommendUrlImg;
    }

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
        private String img_path;
        private String price;
        private String merchant_name;
        private String uptime;
        private String service_name;
        private String service_item;
        private String mobile;

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public String getUptime() {
            return uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public String getService_item() {
            return service_item;
        }

        public void setService_item(String service_item) {
            this.service_item = service_item;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
