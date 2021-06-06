package com.example.keepup_v1.bean;

import java.io.Serializable;

/**
 * Description: 接口统一的返回实体
 */
public class BaseResponse implements Serializable {

    private int code = -1;//服务状态代码   200成功 201未注册  202 密码错误  203 邮箱被注册 204 邮箱未注册 else 失败
    private String msg = "";//普通服务返回消息
    private UserInfo data;//实体返回

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }


    public class PageInfo implements Serializable {
        private boolean first;
        private boolean last;
        private int number;
        private int numberOfElements;
        private int size;
        private long totalElements;
        private int totalPages;

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
    }
}
