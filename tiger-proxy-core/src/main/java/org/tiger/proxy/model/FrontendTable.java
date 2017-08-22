package org.tiger.proxy.model;

/**
 * Created by liufish on 16/8/3.
 */
public class FrontendTable {

    private String name;

    private String shareColumn;

    private String shareRule;

    private boolean isGlobal = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShareColumn() {
        return shareColumn;
    }

    public void setShareColumn(String shareColumn) {
        this.shareColumn = shareColumn;
    }

    public String getShareRule() {
        return shareRule;
    }

    public void setShareRule(String shareRule) {
        this.shareRule = shareRule;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }
}
