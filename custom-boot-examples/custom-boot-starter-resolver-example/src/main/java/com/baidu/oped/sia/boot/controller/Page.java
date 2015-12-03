package com.baidu.oped.sia.boot.controller;

import com.google.common.base.MoreObjects;
import com.baidu.oped.sia.boot.resolver.Resolvable;
import com.baidu.oped.sia.boot.resolver.annotation.FromQuery;

/**
 * Created by mason on 11/18/15.
 */
public class Page implements Resolvable {
    @FromQuery
    private int page;
    @FromQuery
    private int size;
    @FromQuery
    private int total;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("page", page)
                .add("size", size)
                .add("total", total)
                .toString();
    }
}
