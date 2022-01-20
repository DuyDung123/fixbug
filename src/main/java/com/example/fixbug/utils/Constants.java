package com.example.fixbug.utils;

public final class Constants {
    public static final String BASE_URL_YAHOO_REFRESH = "https://yjapp.auth.login.yahoo.co.jp";
    public static final String BASE_URL_YAHOO = "https://auctions.yahooapis.jp";
    public static final String CLIENT_ID_YAHOO_AUCTION = "dj0zaiZpPTZSNEZWZ1hZTTc2TSZzPWNvbnN1bWVyc2VjcmV0Jng9NDc-";
    public static final String BASE_URL_RAKUMA = "https://api.fril.jp";
    public static final String SERVER_DEMO = "https://entv6zrz64b2azv.m.pipedream.net";

    public static final String YAHOO_AUCTION_TITLE_PRODUCTS_HAVE_BUYERS = "ヤフオク! - 終了（落札者あり）";
    public static final String YAHOO_AUCTION_TITLE_PRODUCTS_HAVE_BUYERS_2 = "ヤフオク! - 即決価格での落札";
    public static final String YAHOO_AUCTION_TITLE_PRODUCT_ENTER_SHIPPING_ADDRESS = "ヤフオク! - お届け先住所確定";
    public static final String YAHOO_AUCTION_TITLE_PAID_AND_WAITING_TO_SHIP = "ヤフオク! - 支払い完了";
    public static final String YAHOO_AUCTION_TITLE_PAID_AND_WAITING_TO_SHIP_2 = "ヤフオク! - 商品発送のお願い";
    public static final String YAHOO_AUCTION_TITLE_PRODUCT_RECEIVED = "ヤフオク! - 商品受け取り完了";
    public static final String YAHOO_AUCTION_TITLE_REVIEWED_PRODUCTS = "ヤフオク!評価　通知";

    public static final String MERCARI_TITLE_PRODUCTS_HAVE_BUYERS = "を購入しました";
    public static final String MERCARI_TITLE_PAID_AND_WAITING_TO_SHIP = "の発送をお願いします";
    public static final String MERCARI_TITLE_PRODUCT_RECEIVED = "の評価をお願いします";
    public static final String MERCARI_TITLE_PRODUCT_RECEIVED_2 = "【重要】取引の進行をお願いします";
    public static final String MERCARI_TITLE_TRANSACTION_COMPLETED = "【メルカリ】取引が完了しました";

    public static final String RAKUMA_TITLE_PRODUCTS_HAVE_BUYERS = "[ラクマ] 購入申請がありました";
    public static final String RAKUMA_TITLE_PAID_AND_WAITING_TO_SHIP = "[ラクマ] 決済完了のお知らせ";
    public static final String RAKUMA_TITLE_PAID_AND_WAITING_TO_SHIP_2 = "[ラクマ] 発送のお願い";
    public static final String RAKUMA_TITLE_PRODUCT_RECEIVED = "[ラクマ] 受取のご連絡";
    public static final String RAKUMA_TITLE_PRODUCT_RECEIVED_2 = "[ラクマ] 取引評価のお願い";

    public static final String MERCARI_REGEX = "商品ID\\s:\\s(\\w{1,20})";
    public static final String MERCARI_REGEX_2 = "商品ID：\\s(\\w{1,20})";

    public static final String RAKUMA_REGEX = "オーダーID : ([a-zA-Z0-9]{1,20})";

    public static final String YAHOO_REGEX = "[()](\\w{1,20})";
    public static final String YAHOO_REGEX_2 = "オークションID：(\\w{1,20})";//オークションID:\s(\w{1,20})

    public static final String HOST_MAIL = "imap.gmail.com";
    public static final String HOST_MAIL_YAHOO = "imap.mail.yahoo.co.jp";

    public static final int MERCARI_TYPE = 1;
    public static final int RAKUMA_TYPE = 2;
    public static final int YAHOO_AUCTION_TYPE = 3;
    public static final int PAYPAY_TYPE = 4;

    public static final int STATUS_CODE_SUCCESS = 200;


    public static final int JOB_ACTION_PUBLISH_MERUKARI = 1;
    public static final int JOB_ACTION_PUBLISH_RAKUMA = 2;

    public static final int JOB_ACTION_REMOVE_MERUKARI = 11;
    public static final int JOB_ACTION_REMOVE_RAKUMA = 12;
    public static final int JOB_ACTION_REMOVE_YAHOO_AUCTION = 13;
    public static final int JOB_ACTION_REMOVE_PAYPAY = 14;

    public static final int JOB_ACTION_SEND_MESSAGE_MERCARI_BUYER_NOT_PAID = 100;
    public static final int JOB_ACTION_SEND_MESSAGE_MERCARI_BUYER_PAID = 101;
    public static final int JOB_ACTION_SEND_MESSAGE_MERCARI_BUYER_REVIEWS = 102;

    public static final int JOB_ACTION_SEND_MESSAGE_RAKUMA_BUYER_NOT_PAID = 200;
    public static final int JOB_ACTION_SEND_MESSAGE_RAKUMA_BUYER_PAID = 201;
    public static final int JOB_ACTION_SEND_MESSAGE_RAKUMA_BUYER_REVIEWS = 202;

    public static final int JOB_ACTION_SEND_MESSAGE_YAHOO_BUYER_NOT_ENTERED_INFORMATION = 303;
    public static final int JOB_ACTION_SEND_MESSAGE_YAHOO_BUYER_NOT_PAID = 300;
    public static final int JOB_ACTION_SEND_MESSAGE_YAHOO_BUYER_PAID = 301;
    public static final int JOB_ACTION_SEND_MESSAGE_YAHOO_BUYER_REVIEWS = 302;
}
