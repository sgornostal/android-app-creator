package ua.com.ethereal.appcreator.app;

import android.app.Application;
import android.util.Log;
import com.parse.*;

/**
 * Created by Slava
 */
public class BasicApplication extends Application {

    private static final String TAG = "APPLICATION";
    private ApplicationConfiguration applicationConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            applicationConfiguration = ApplicationConfiguration.getInstance(this);
        } catch (Exception ex) {
            Log.e(TAG, "Failed to init configuration");
        }
        if(!applicationConfiguration.getPushConfiguration().getEnabled()) return;
        try {
            Parse.initialize(this, applicationConfiguration.getPushConfiguration().getAppId(),
                    applicationConfiguration.getPushConfiguration().getClientKey());
            PushService.setDefaultPushCallback(this, MainActivity.class);
            ParseInstallation.getCurrentInstallation().saveInBackground();
        } catch (Exception ex) {
            Log.w(TAG, "Failed to register Parse", ex);
            //register once, dumb bug ...
            try {
                Parse.initialize(this, applicationConfiguration.getPushConfiguration().getAppId(),
                        applicationConfiguration.getPushConfiguration().getClientKey());
                PushService.setDefaultPushCallback(this, MainActivity.class);
                ParseInstallation.getCurrentInstallation().saveInBackground();
            } catch (Exception e) {
                Log.w(TAG, "Failed to register Parse", e);
            }
        }
    }
}
