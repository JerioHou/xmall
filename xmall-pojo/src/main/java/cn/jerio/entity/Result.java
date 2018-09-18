package cn.jerio.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Jerio on 2018/09/18
 */
@Data
public class Result implements Serializable {

    private boolean success;

    private String message;

    private Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static Result success(){

        return new Result(true,"成功");
    }

    public static Result success(String msg){

        return new Result(true,msg);
    }

    public static Result fail(){
        return new Result(false,"失败");
    }

    public static Result fail(String msg){
        return new Result(false,msg);
    }
}
