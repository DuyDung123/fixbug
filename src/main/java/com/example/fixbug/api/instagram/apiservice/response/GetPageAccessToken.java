package com.example.fixbug.api.instagram.apiservice.response;

import com.example.fixbug.api.instagram.apiservice.BaseInstagramResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPageAccessToken extends BaseInstagramResponse {

    private List<Datum> data;
    private Paging paging;

    public List<Datum> getData() {
        return data;
    }

    public Paging getPaging() {
        return paging;
    }

    public static class CategoryList{
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class Cursors{
        private String before;
        private String after;

        public String getBefore() {
            return before;
        }

        public String getAfter() {
            return after;
        }
    }

    public static class Datum{
        @SerializedName("access_token")
        private String accessToken;

        private String category;

        @SerializedName("category_list")
        private List<CategoryList> categoryList;
        private String name;
        private String id;
        private List<String> tasks;

        public String getAccessToken() {
            return accessToken;
        }

        public String getCategory() {
            return category;
        }

        public List<CategoryList> getCategoryList() {
            return categoryList;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public List<String> getTasks() {
            return tasks;
        }
    }

    public static class Paging{
        private Cursors cursors;

        public Cursors getCursors() {
            return cursors;
        }
    }
}
