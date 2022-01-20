package com.example.fixbug.api.response;

import java.util.Date;
import java.util.List;

public class FuriruDetailProduct{
    public boolean result;
    public Item item;
    public List<Review> reviews;
    public Object recommend;
    public boolean display_official_icon;
    public Object additional_link;
    public Object official_info;
    public boolean inquiry_form;
    public boolean official_icon;

    public Item getItem() {
        return item;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    private class Review{
        public String r_type;
        public int review;

        public String getR_type() {
            return r_type;
        }

        public int getReview() {
            return review;
        }
    }


    public class Item{
        public Info info;
        public int imgs_count;
        public List<Img> imgs;
        public int comments_count;
        public Object comments;
        public int likes_count;
        public Object likes;
        public Object order;
        public Object source_item;

        public Info getInfo() {
            return info;
        }

        private class Img{
            public int id;
            public int img_id;
            public String file_name;
            public int cover_flag;
            public String img_date;
        }

        public class Info{
            public String item_id;
            public String item_name;
            public String detail;
            public int s_price;
            public String status;
            public int t_status;
            public int carriage;
            public int d_method;
            public String d_method_name;
            public boolean d_method_compensated;
            public boolean d_method_anonymous;
            public int d_date;
            public int d_area;
            public Date created_at;
            public Date updated_at;
            public int user_id;
            public String screen_name;
            public Object profile_img_url;
            public Object bio;
            public Object business_user_type;
            public int category_id;
            public int category_p_id;
            public String category_name;
            public int size_id;
            public String size_name;
            public Object brand_id;
            public Object brand_name;
            public Object i_brand_id;
            public Object i_brand_name;
            public String pc_url;
            public String item_hash;
            public boolean request_required;
            public String ad_item_name;
            public boolean reported;
            public Object like_id;
            public String email;
            public String last_name;
            public String first_name;
            public String kana_last_name;
            public String kana_first_name;
            public String tell;
            public String zipcode;
            public int province_id;
            public String cities;
            public String address1;
            public String address2;
            public String my_screen_name;
            public boolean verified_badge;
        }
    }
}
