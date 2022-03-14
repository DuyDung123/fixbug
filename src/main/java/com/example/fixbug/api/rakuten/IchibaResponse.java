package com.example.fixbug.api.rakuten;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IchibaResponse {
    @SerializedName("Items")
    private List<Items> itemsList;

    public List<Items> getItemsList() {
        return itemsList;
    }

    public static class Items {
        @SerializedName("Item")
        private Item item;

        public Item getItem() {
            return item;
        }
    }

    public static class Item {
        @SerializedName("affiliateRate")
        private Integer affiliateRate;
        @SerializedName("availability")
        private Integer availability;
        @SerializedName("itemCaption")
        private String itemCaption;
        @SerializedName("catchcopy")
        private String catchCopy;
        @SerializedName("itemCode")
        private String itemCode;
        @SerializedName("itemName")
        private String itemName;
        @SerializedName("itemPrice")
        private Integer itemPrice;
        @SerializedName("postageFlag")
        private Integer postageFlag;
        @SerializedName("pointRate")
        private Integer pointRate;
        @SerializedName("shopCode")
        private String shopCode;
        @SerializedName("shopName")
        private String shopName;
        @SerializedName("shopUrl")
        private String shopUrl;
        @SerializedName("genreId")
        private String genreId;
        @SerializedName("itemUrl")
        private String itemUrl;

        public Integer getAvailability() {
            return availability;
        }

        public void setAvailability(Integer availability) {
            this.availability = availability;
        }

        public Integer getPostageFlag() {
            return postageFlag;
        }

        public void setPostageFlag(Integer postageFlag) {
            this.postageFlag = postageFlag;
        }

        public Integer getPointRate() {
            return pointRate;
        }

        public Integer getAffiliateRate() {
            return affiliateRate;
        }

        public String getItemCaption() {
            return itemCaption;
        }

        public String getCatchCopy() {
            return catchCopy;
        }

        public String getItemCode() {
            return itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public Integer getItemPrice() {
            return itemPrice;
        }

        public String getShopCode() {
            return shopCode;
        }

        public String getShopName() {
            return shopName;
        }

        public String getShopUrl() {
            return shopUrl;
        }

        public String getGenreId() {
            return genreId;
        }

        public String getItemUrl() {
            return itemUrl;
        }
    }
}
