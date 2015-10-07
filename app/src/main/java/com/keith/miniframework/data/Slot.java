package com.keith.miniframework.data;

import android.view.View;

/**
 * Created by keith on 15/10/1.
 */
public class Slot {

    private int id;
    private int sid;
    public int leftId;
    public int rightId;

    private int x;
    private int y;
    private int width;
    private int height;

    public int left;
    public int right;
    public int top;
    public int bottom;

    private String image;

    public Slot(int id, int sid, int x, int y, int width, int height, String image) {
        this.id = id;
        this.sid = sid;
        this.leftId = View.NO_ID;
        this.rightId = View.NO_ID;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.left = x + 1920 * sid;
        this.right = left + width;
        this.top = y;
        this.bottom = y + height;
        this.image = image;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "slot | " + " id = " + id
                + ", sid = " + sid
                + ", leftId = " + leftId
                + ", rightId = " + rightId
                + ", left = " + left
                + ", right = " + right
                + ", x = " + x
                + ", y = " + y
                + ", width = " + width
                + ", height = " + height;
    }
}
