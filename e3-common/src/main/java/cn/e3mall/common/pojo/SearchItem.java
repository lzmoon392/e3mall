package cn.e3mall.common.pojo;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author: jerry
 * @create: 2020-04-13 16:49
 */
public class SearchItem implements Serializable {

    private static final long serialVersionUID = 7750728872234259866L;

    private String id;
    private String title;
    private String sell_point;
    private Long price;
    private String image;
    private String category_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public String[] getImages() {
        String image = this.getImage();
        if (StringUtils.isNoneBlank(image)) {
            return image.split(",");
        }
        return null;
    }
}
