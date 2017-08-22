package org.tiger.proxy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by fish on 16/7/16.
 */
public class FrontendProxy {

    private String schema;

    private int port;

    private boolean bio = false;

    private byte charsetIndex = 45;

    private List<FrontendAccount> users;

    private List<FrontendTable> tables;

    private List<BackendDataSource> dataSources = new ArrayList<>();
    /**
     * 执行线程池。
     */
    private int executor = 128;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getExecutor() {
        return executor;
    }

    public void setExecutor(int executor) {
        this.executor = executor;
    }

    public byte getCharsetIndex() {
        return charsetIndex;
    }

    public void setCharsetIndex(byte charsetIndex) {
        this.charsetIndex = charsetIndex;
    }

    public List<FrontendAccount> getUsers() {
        return users;
    }

    public void setUsers(List<FrontendAccount> users) {
        this.users = users;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<FrontendTable> getTables() {
        return tables;
    }

    public void setTables(List<FrontendTable> tables) {
        Collections.sort(tables, new Comparator<FrontendTable>() {
            @Override
            public int compare(FrontendTable table1, FrontendTable table2) {
                return table1.getName().compareToIgnoreCase(table2.getName());
            }
        });
        this.tables = tables;
    }

    public boolean isBio() {
        return bio;
    }

    public void setBio(boolean io) {
        this.bio = io;
    }

    public void addDataSources(int index,BackendDataSource dataSource) {
         dataSources.add(index,dataSource);
    }

    public List<BackendDataSource> getDataSources() {
        return dataSources;
    }

}
