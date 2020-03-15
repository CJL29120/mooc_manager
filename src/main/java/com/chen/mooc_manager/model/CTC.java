package com.chen.mooc_manager.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
public class CTC extends Model<CTC> {
    private Integer id;
    private Integer cId;
    private Integer toCId;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
