package com.mor.utility;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.apache.tools.ant.taskdefs.Zip;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utility {

    public void printResponse(JsonObject response) {
        if (response != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(response));
        }
    }

    public void extractZipFile(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4000];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public String readCodeFromTxtFile(String filePath){
        String file = "";
        return getCodeFromFile(file);
    }

    public String getCodeFromFile(String file){
        return null;
    }

    public String readCsvFile(String filePath){
        return null;
    }

    public Boolean isZipFile(File f){
        int fileSignature = 0;
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            fileSignature = raf.readInt();
        } catch (IOException e) {
            // handle if you like
        }
        return fileSignature == 0x504B0304 || fileSignature == 0x504B0506 || fileSignature == 0x504B0708;
    }

    public File getFileFromResponse(Response res){
        return null;
    }


    public boolean isCodeReceived(String codeDataResponse) {
        if(codeDataResponse.contains("somecode:")){
            return true;
        }else{
            return false;
        }
    }

    public String isDataReady(JsonObject res) {
        String dataStatus = res.get("dataStatus").getAsString();
        if(dataStatus.equalsIgnoreCase("data is ready")){
            return res.get("downloadlink").getAsString();
        }else{
            return "processing";
        }
    }

    public String downloadCsvDataFile(String downloadLink) {
        //download file by FileOutputStream
        String filePath = "";
        return filePath;
    }
}
