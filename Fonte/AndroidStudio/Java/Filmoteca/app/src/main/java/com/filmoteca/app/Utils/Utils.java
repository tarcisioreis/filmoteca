package com.filmoteca.app.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class Utils {

    /*	ConnectivityManager
     *.TYPE_MOBILE	0
     *.TYPE_WIFI	    1
     *.TYPE_WIMAX	6
     *.TYPE_ETHERNET 9
     */

    public static boolean isConnected(Context c) {
        try {
            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            int[] p = {0, 1, 6, 9};
            for (int i : p) {
                if (cm.getNetworkInfo(i).isConnected()) return true;
            }
        } catch (Exception e) {
            Log.e("NETWORK", e.getMessage());
        }

        return false;
    }

}
