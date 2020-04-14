package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-13 19:24
 */
public class SearchResult implements Serializable {
    private static final long serialVersionUID = -5681099062531575064L;

    private Long recordCount;

    private Integer totalPages;

    private List<SearchItem> itemList;

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
