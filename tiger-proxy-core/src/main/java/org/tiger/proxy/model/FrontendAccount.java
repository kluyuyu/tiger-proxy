package org.tiger.proxy.model;

/**
 * Created by fish on 16/7/24.
 */
public class FrontendAccount {

    private String user;

    private String password;

    public FrontendAccount(String user , String password ){
        this.user = user ;
        this.password = password ;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
