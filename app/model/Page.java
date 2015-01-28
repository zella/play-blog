package model;

/**
 * Created by dru on 27.01.2015.
 */

import java.util.List;

/**
 * Used to represent a computers page.
 */
public class Page {

    private final int pageSize;
    private final long totalRowCount;
    private final int pageIndex;
    private final List<Post> list;

    /**
     * @param data     posts on page
     * @param total    total posts
     * @param page     number
     * @param pageSize count posts on page
     */
    public Page(List<Post> data, long total, int page, int pageSize) {
        this.list = data;
        this.totalRowCount = total;
        this.pageIndex = page;
        this.pageSize = pageSize;
    }

    public long getTotalRowCount() {
        return totalRowCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public List<Post> getList() {
        return list;
    }

    public boolean hasPrev() {
        return pageIndex > 1;
    }

    public boolean hasNext() {
        return (totalRowCount / pageSize) >= pageIndex;
    }

    public int getTotalPages() {
        //TODO
        int res = (int) Math.ceil((float)totalRowCount / (float)pageSize);
        System.out.println(res);
        return res;
    }

    public String getDisplayXtoYofZ() {
        int start = ((pageIndex - 1) * pageSize + 1);
        int end = start + Math.min(pageSize, list.size()) - 1;
        return start + " to " + end + " of " + totalRowCount;
    }

}