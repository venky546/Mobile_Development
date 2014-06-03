package com.saddahaq.media.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.plus.PlusShare;
import com.saddahaq.media.MainActivity;

public class ParseDeepLinkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String deepLinkId = PlusShare.getDeepLinkId(this.getIntent());
        Intent target = parseDeepLinkId(deepLinkId);
        if (target != null) {
          startActivity(target);
        }

        finish();
    }

    /**
     * Get the intent for an activity corresponding to the deep-link ID.
     *
     * @param deepLinkId The deep-link ID to parse.
     * @return The intent corresponding to the deep-link ID.
     */
    private Intent parseDeepLinkId(String deepLinkId) {
    	Log.e("parsedeeplinkid :: ",""+deepLinkId);
        Intent route = new Intent();
        if ("/pages/create".equals(deepLinkId)) {
            route.setClass(getApplicationContext(), FbTrail.class);
        } else {
            // Fallback to the MainActivity in your app.
            route.setClass(getApplicationContext(), MainActivity.class);
        }
        return route;
    }
}