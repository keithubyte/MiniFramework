package com.keith.miniframework;

/**
 * Created by keith on 15/10/6.
 */
public class ChangeScreenEvent {

    private int sid;

    public ChangeScreenEvent(int sid) {
        this.sid = sid;
    }

    public int getSid() {
        return sid;
    }

}
