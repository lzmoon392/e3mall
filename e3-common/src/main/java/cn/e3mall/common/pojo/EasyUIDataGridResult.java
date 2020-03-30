package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-03-30 22:15
 */
public class EasyUIDataGridResult implements Serializable {

    private long total;
    private List rows;

    public EasyUIDataGridResult() {
    }

    public EasyUIDataGridResult(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
