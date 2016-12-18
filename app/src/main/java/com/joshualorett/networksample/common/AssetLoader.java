package com.joshualorett.networksample.common;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Joshua on 12/18/2016.
 */

public class AssetLoader {
    public static String load(Context context, int resourceId) throws IOException {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getResources().openRawResource(resourceId);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            return buf.toString();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
