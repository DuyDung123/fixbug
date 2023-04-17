package com.example.fixbug.api.line.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CsvExport extends BaseResponse {

    public Data data;

    public Data getData() {
        return data;
    }

    public static class Data {

        @SerializedName("data_export")
        private List<List<Object>> dataExport;

        @SerializedName("heads")
        private List<List<Object>> heads;

        public List<List<Object>> getDataExport() {
            return dataExport;
        }

        public List<List<Object>> getHeads() {
            return heads;
        }
    }
}
