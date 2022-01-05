package com.project.gimme.pojo.vo;

import java.util.List;

import lombok.Data;

/**
 * @author Gilbert
 * @date 2020/8/11 18:51
 * 输出用vo
 */
@Data
public class ReturnPage<T> {
    /**
     * 分页、排序完的数据
     */
    private List<T> data;
    /**
     * 数据总数（未分页的）
     */
    private Long total;
    /**
     * 每页容量
     */
    private Integer pageSize;
    /**
     * 当前页
     */
    private Integer current;
}
