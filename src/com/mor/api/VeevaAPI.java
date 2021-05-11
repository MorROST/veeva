package com.mor.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mor.db.DBHelper;
import com.mor.db.DBHelperImpl;
import com.mor.utility.Utility;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class VeevaAPI implements IVeevaAPI {
    public static final String ZIP_FILE_PATH = "c\\download";
    public static final String UNZIP_DEST = "c\\temp\\veeva";
    public static final String CSV_DATA_RESULT_FOLDER = "c\\temp\\veeva\\csv_file";
    Utility utility = new Utility();
    DBHelperImpl dbHelper = new DBHelperImpl("user", "pass");

    public static final String SERVER_URI = "https://servername/";

    private void initRestAssured(String endpoint) {
        RestAssured.baseURI = SERVER_URI;
        RestAssured.basePath = endpoint;
        RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Override
    public String getData(String code) throws IOException {
        Response res = getRestActionWithParameters("", "getdata", code);
        if (res.getStatusCode() != 200) {
            System.out.println("The request was failed");
            throw new RequestFailedException("status code is " + res.getStatusCode());
        }
        File file = utility.getFileFromResponse(res);
        if (utility.isZipFile(file)) {
            utility.extractZipFile(file.getPath(), UNZIP_DEST);
            return utility.readCodeFromTxtFile(UNZIP_DEST);
        } else {
            return utility.readCodeFromTxtFile(file.getPath());
        }
    }

    @Override
    public String sendCode(String code) {
        Response res = postRestActionWithParameters("", "code", code);
        if (res.getStatusCode() != 200) {
            System.out.println("The request was failed");
            throw new RequestFailedException("status code is " + res.getStatusCode());
        }
        JsonObject jsonRes = getJsonObject(res.getBody().asString());
        utility.printResponse(jsonRes);
        try{
        return jsonRes.get("code").getAsString();
        }catch (NullPointerException e){
            System.out.println("Trying to get the download link of the CSV data file");
            return utility.isDataReady(jsonRes);
        }
    }

    @Override
    public Boolean downloadCSVFileFromServer(String downloadLink) throws IOException {
        String filePath = utility.downloadCsvDataFile(downloadLink);
        utility.extractZipFile(filePath, CSV_DATA_RESULT_FOLDER);
        return dbHelper.storeCSVfileFields(filePath);
    }

    private Response getRestActionWithParameters(String endpoint, String paramKey, String paramVal) {
        initRestAssured(endpoint);
        return RestAssured
                .given()
                .basePath(endpoint)
                .contentType(ContentType.JSON)
                .header("accept", "application/json")
                .params(paramKey, paramVal)
                .get();
    }

    private Response postRestActionWithParameters(String endpoint, String paramKey, String paramVal) {
        initRestAssured(endpoint);
        return RestAssured
                .given()
                .basePath(endpoint)
                .contentType(ContentType.JSON)
                .header("accept", "application/json")
                .params(paramKey, paramVal)
                .post();
    }

    private JsonObject getJsonObject(String s) {
        return new Gson().fromJson(s, JsonObject.class);
    }

    public class RequestFailedException extends RuntimeException {
        public RequestFailedException(String message) {
            super(message);
        }
    }
}
