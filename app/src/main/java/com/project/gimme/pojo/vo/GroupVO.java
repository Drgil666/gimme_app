package com.project.gimme.pojo.vo;

import com.project.gimme.pojo.Group;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DrGilbert
 * @date 2022/1/14 14:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupVO extends Group {
    /**
     * 总群人数
     */
    private Integer totalCount;
}
