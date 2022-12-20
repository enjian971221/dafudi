package com.zkwl.qhzwj.bean;

import java.util.List;

/**
 * @author songkai
 * @date on 2019/8/21
 */
public class CommPage<T> {

    /**
     * page : 1
     * page_size : 10
     * count : 2
     */

    private int page;
    private int page_size;
    private int count;
    private List<T> list;

    @Override
    public String toString() {
        return "CommPage{" +
                "page=" + page +
                ", page_size=" + page_size +
                ", count=" + count +
                ", list=" + list +
                '}';
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
