package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: jerry
 * @create: 2020-04-17 22:35
 */
public class Item extends TbItem {

    public Item(TbItem tbItem) {
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    public String[] getImages() {
        String image = this.getImage();
        if (StringUtils.isNoneBlank(image)) {
            return image.split(",");
        }
        return null;
    }
}
