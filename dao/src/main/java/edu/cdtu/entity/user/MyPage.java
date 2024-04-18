package edu.cdtu.entity.user;

import java.util.ArrayList;
import java.util.List;

public class MyPage<T> {
    private int currentPage; // 当前页码
    private int pageSize; // 每页大小
    private long totalCount; // 总记录数
    private List<T> records; // 当前页的数据列表

    public MyPage(int currentPage, int pageSize, long totalCount) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.records = new ArrayList<>();
    }

    // getter 和 setter 方法
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    // 可能还需要其他方法，比如计算总页数等
}