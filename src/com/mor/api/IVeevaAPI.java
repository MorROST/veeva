package com.mor.api;

import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;

public interface IVeevaAPI {

    String getData(String code) throws IOException;

    String sendCode(String code);

    Boolean downloadCSVFileFromServer(String downloadLink) throws IOException;
}
