package com.example.fixbug.facebug;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APINodeList;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.Campaign;

public class Instagram {
    public static final APIContext context = new APIContext(
            "{access-token}",
            "{appsecret}"
    );

    private void test() {
        AdAccount account = new AdAccount("act_{{adaccount-id}}", context);
        try {
            APINodeList<Campaign> campaigns = account.getCampaigns().requestAllFields().execute();
            for(Campaign campaign : campaigns) {
                System.out.println(campaign.getFieldName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
