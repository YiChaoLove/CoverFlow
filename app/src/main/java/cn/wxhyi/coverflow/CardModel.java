package cn.wxhyi.coverflow;

import java.io.Serializable;

/**
 * Created by yichao on 16/2/26.
 */
public class CardModel implements Serializable {

    private String title;
    private String img = "0";
    public boolean isBorder = false;

    public CardModel(String title, String img) {
        this.title = title;
        this.img = img;
    }

    public CardModel(boolean isBorder){
        this.isBorder = isBorder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
