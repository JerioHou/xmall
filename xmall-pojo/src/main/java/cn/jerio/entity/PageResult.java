package cn.jerio.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jerio on 2018/09/18
 */
@Data
public class PageResult implements Serializable{

    private long total;//总记录数

    private List rows;//当前页结果

    public PageResult(long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
}
