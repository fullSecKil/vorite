package com.zui.vorite.help.page;

import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.util.StringUtil;

/**
 * 分页对象
 *
 * @file: DataGrid.class
 * @author: Dusk
 * @since: 2018/12/5 10:20
 * @desc:
 */
public class DataGrid {

    private int pageSize =10;
    private int pageNum =1;
    private String sort;
    private String order = " desc";

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void getOrder() {
        PageHelper.orderBy(StringUtil.isEmpty(sort)+ " " + order);
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
