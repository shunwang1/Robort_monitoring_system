package cn.iua.caas.robotdatahub.common;

import lombok.Data;

import java.util.Date;

@Data
public class Result {

    private Integer code;//响应码，1 代表成功；0代表失败
    private String msg;//响应码 描述字符串
    private Object data; // 返回的数据
    private Date date;// 返回时间

    public Result() {}
    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.date = new Date();
    }

    //增删改 成功响应
    public static Result success() {
        return new Result(1,"message",null);
    }

    //查询 成功响应
    public static Result success(Object data) {
        return new Result(1,"message",data);
    }

    //失败响应
    public static Result error(String msg) {
        return new Result(0,msg,null);
    }



}
