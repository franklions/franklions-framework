package com.franklions.example.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页返回结果
 * @author administrator
 * @version 1.0
 * @date 2019-06-30
 * @since Jdk 1.8
 */
@ApiModel(value = "分页返回结果")
@Data
public class PageReturnValue<T> {

    @ApiModelProperty(value = "返回记录数据")
    private List<T> rows;
    @ApiModelProperty(value = "记录总数")
    private Long total;

    public PageReturnValue(List<T> rows, Long total) {
        this.rows = rows;
        this.total = total;
    }

    public PageReturnValue(IPage<T> data) {
        this.rows = data.getRecords();
        this.total = data.getTotal();
    }

    public static PageReturnValue empty(){
        return new PageReturnValue(new ArrayList<>(),0L);
    }
}
