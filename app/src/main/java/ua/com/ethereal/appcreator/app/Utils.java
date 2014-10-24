package ua.com.ethereal.appcreator.app;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Slava
 */
public class Utils {

    public static String readResource(Context context, int resourceId) throws IOException {
        Resources resources = context.getResources();
        InputStream is = resources.openRawResource(resourceId);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
