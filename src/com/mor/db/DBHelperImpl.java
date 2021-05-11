package com.mor.db;

public class DBHelperImpl implements DBHelper {
    String user;
    String pass;

    public DBHelperImpl(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    @Override
    public Boolean storeInfo(String result) {
        return null;
    }

    @Override
    public Boolean storeCSVfileFields(String filePath) {
        return true;
    }

    private Boolean setConnection(String user, String pass){
        return null;
    }

    private Boolean closeConnection(){
        return null;
    }

    private String sendQuery(String query){
        return null;
    }

}
