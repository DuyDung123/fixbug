package com.example.fixbug.elasticsearch;

public class EsLineUserObjectKey {
    /**
     * **** bot_line_user_{bot_id}
     * + id_line_user // id trong bảng line
     * + line_id  // ID string của line
     * <p>
     * # Name filter -> Lấy trong bảng line_user
     * + line_name
     * + view_name
     * + real_name
     * <p>
     * # Tag filter -> tag_line_user
     * + tag_{id_tag}.  // mỗi tag id một trường, data fill = 1
     * <p>
     * # Day add freind filter -> bot_line_user
     * + followed_at
     * <p>
     * # Scenario filter -> scenario_line_user
     * screnario_following = id scenario // id scenario đang follow
     * screnario_following_start_day = xx // start từ ngày xx
     * screnario_following_start_datetime = yyyy-mm-dd // ngày start scenario
     * <p>
     * screnario_done_{id} = 1 // scenario đã done
     * screnario_done_start_day_{id} = xx // start từ ngày xx
     * screnario_done_start_datetime_{id} = yyyy-mm-dd // ngày start scenario
     * <p>
     * screnario_stopped_{id} = 1 // scenario đã stop giữa trừng
     * screnario_stopped_start_day_{id} = xx // start từ ngày xx
     * screnario_stopped_start_datetime_{id} = yyyy-mm-dd // ngày start scenario
     * screnario_stopped_stop_datetime_{id} = yyyy-mm-dd // ngày stop scenario
     * <p>
     * # Conversion filter -> conversion_result
     * conversion_{id} = 1 // conversion đã access
     * <p>
     * # Status filter -> bot_line_user
     * status_last_message_{id} = 1 // mỗi 1 mark id một trường
     * id_status_{id} = 1 // mỡi status 1 trường
     * <p>
     * # QR code filter -> detail_landing_click
     * landing_{id} = 1 // mỗi landing từng access 1 trường
     * <p>
     * # Friend info filter -> friend_info_value
     * phone_number
     * email
     * birthday
     * age
     * province
     * friend_info_{id} = value // mỗi friend info có data tạo 1 trường
     * <p>
     * # Affliater filter -> bot_line_user
     * affiliater_id
     * <p>
     * # new/old friend filter -> bot_line_user
     * is_old_friend = 0;1
     */
    public static final String DOCUMENT_NAME = "bot_line_user";

    public static final String ID = "id";
    public static final String BOT_LINE_USER_ID = "bot_line_user_id";
    public static final String BOT_ID = "bot_id";
    public static final String ID_LINE_USER = "id_line_user"; //ID table line_user
    public static final String LINE_ID = "line_id"; // ID from Line system

    //# Name filter -> Lấy trong bảng line_user
    public static final String LINE_NAME = "line_name";
    public static final String VIEW_NAME = "view_name";
    public static final String REAL_NAME = "real_name";
    //Tag filter -> tag_line_user
    public static final String TAG_ = "tag_";
    //Day add freind filter -> bot_line_user
    public static final String FOLLOW_AT = "followed_at";
    //# Scenario filter -> scenario_line_user
    public static final String SCENARIO_STATUS_ = "SCENARIO_STATUS_";
    public static final String SCENARIO_START_DAY_ = "SCENARIO_START_DAY_";
    public static final String SCENARIO_START_DATETIME_ = "SCENARIO_START_TIME_";
    // # Conversion filter -> conversion_result
    public static final String CONVERSION_ = "conversion_";
    //# Status filter -> bot_line_user
    public static final String STATUS_LAST_MESSAGE = "status_last_message";
    public static final String STATUS_ID = "status_id";
    //# QR code filter -> detail_landing_click
    public static final String LANDING_ = "landing_";
    //# Friend info filter -> friend_info_value
    public static final String PHONE_NUMBER = "phone_number";
    public static final String EMAIL = "email";
    public static final String BIRTHDAY = "birthday";

    public static final String DAY = "day";

    public static final String MONTH = "month";

    public static final String YEAR = "year";
    public static final String AGE = "age";
    public static final String PROVINCE = "province";
    public static final String FRIEND_INFO_ = "friend_info_";
    // # Affliater filter -> bot_line_user
    public static final String AFFILIATE_ID = "affiliater_id";
    //# new/old friend filter -> conversion
    public static final String IS_OLD_FRIEND = "is_old_friend";
    // Blocked filter
    public static final String IS_BLOCKED = "is_blocked";
    public static final String BLOCKED_BY = "blocked_by";


    public static String makeId(long botId,long lineId){
        return botId+"@"+lineId;
    }
}