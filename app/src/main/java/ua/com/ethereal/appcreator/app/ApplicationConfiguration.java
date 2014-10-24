package ua.com.ethereal.appcreator.app;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

import java.util.Locale;
import java.util.Properties;

/**
 * Created by Slava
 */
public class ApplicationConfiguration {

    private static ApplicationConfiguration applicationConfiguration;

    public static ApplicationConfiguration getInstance(Context context) throws Exception{
        if(applicationConfiguration == null) {
            synchronized (ActionBarConfiguration.class) {
                if(applicationConfiguration == null) {
                    applicationConfiguration = init(context);
                }
            }
        }
        return applicationConfiguration;
    }

    private static ApplicationConfiguration init(Context context) throws Exception {
        Properties properties = new Properties();
        properties.load(context.getResources().openRawResource(R.raw.config));
        String infoText = Utils.readResource(context, R.raw.info);
        return ApplicationConfiguration.Builder.build(properties, infoText);
    }

    public static enum BrowserType {
        INTERNAL,
        EXTERNAL
    }

    public static enum AdPosition {
        ABOVE,
        BELOW
    }

    public static class ActionBarConfiguration {
        private Boolean enabled = true;
        private ColorDrawable color;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public ColorDrawable getColor() {
            return color;
        }

        public void setColor(ColorDrawable color) {
            this.color = color;
        }
    }

    public static class SplashConfiguration {
        private Boolean enabled = false;
        private Integer layoutType;
        private Integer color;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public Integer getLayoutType() {
            return layoutType;
        }

        public void setLayoutType(Integer layoutType) {
            this.layoutType = layoutType;
        }

        public Integer getColor() {
            return color;
        }

        public void setColor(Integer color) {
            this.color = color;
        }
    }

    public static class PushConfiguration {
        private Boolean enabled = false;
        private String appId;
        private String clientKey;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getClientKey() {
            return clientKey;
        }

        public void setClientKey(String clientKey) {
            this.clientKey = clientKey;
        }
    }

    public static class AdConfiguration {
        private Boolean enabled = false;
        private AdPosition position;
        private String admobId;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public AdPosition getPosition() {
            return position;
        }

        public void setPosition(AdPosition position) {
            this.position = position;
        }

        public String getAdmobId() {
            return admobId;
        }

        public void setAdmobId(String admobId) {
            this.admobId = admobId;
        }
    }

    public static class Builder {
        public static ApplicationConfiguration build(final Properties properties, String info) {
            ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
            applicationConfiguration.setUrl(getUrl(properties));
            applicationConfiguration.setLocale(createLocale(properties.getProperty("locale")));
            applicationConfiguration.setStatusBar(Boolean.parseBoolean(properties.getProperty("statusbar")));
            applicationConfiguration.setInfo(info);
            createConfiguration(applicationConfiguration, properties);
            setDisplayOrientation(applicationConfiguration, properties);
            setBrowserType(applicationConfiguration, properties);
            applicationConfiguration.setZoomControl(Boolean.parseBoolean("zoomcontrol"));
            setPushConfiguration(applicationConfiguration, properties);
            setSplashConfiguration(applicationConfiguration, properties);
            setAdConfiguration(applicationConfiguration, properties);
            return applicationConfiguration;
        }

        private static String getUrl(Properties properties) {
            String url = properties.getProperty("url");
            if (!(url.startsWith("http://") || url.startsWith("https://"))) {
                url = "http://" + url;
            }
            return url;
        }

        private static void setAdConfiguration(ApplicationConfiguration applicationConfiguration, Properties properties) {
            AdConfiguration adConfiguration = new AdConfiguration();
            adConfiguration.setEnabled(!"disabled".equals(properties.getProperty("ad.type")));
            adConfiguration.setAdmobId(properties.getProperty("ad.admobid"));
            String position = properties.getProperty("ad.type");
            switch (position) {
                case "top":
                    adConfiguration.setPosition(AdPosition.ABOVE);
                    break;
                case "bottom":
                    adConfiguration.setPosition(AdPosition.BELOW);
                    break;
                default:
                    adConfiguration.setPosition(AdPosition.ABOVE);
                    break;
            }
            applicationConfiguration.setAdConfiguration(adConfiguration);
        }

        private static void setSplashConfiguration(ApplicationConfiguration applicationConfiguration, Properties properties) {
            SplashConfiguration splashConfiguration = new SplashConfiguration();
            splashConfiguration.setEnabled(Boolean.parseBoolean(properties.getProperty("splash.enabled")));
            String splashLayout = properties.getProperty("splash.layout");
            switch (splashLayout) {
                case "wrap_content":
                    splashConfiguration.setLayoutType(ViewGroup.LayoutParams.WRAP_CONTENT);
                    break;
                case "match_parent":
                    splashConfiguration.setLayoutType(ViewGroup.LayoutParams.MATCH_PARENT);
                    break;
                default:
                    splashConfiguration.setLayoutType(ViewGroup.LayoutParams.MATCH_PARENT);
                    break;
            }
            try {
                splashConfiguration.setColor(Color.parseColor(properties.getProperty("splash.background")));
            } catch (IllegalArgumentException e) {
                // not parsed color
            }
            applicationConfiguration.setSplashConfiguration(splashConfiguration);
        }

        private static void setPushConfiguration(ApplicationConfiguration applicationConfiguration, Properties properties) {
            PushConfiguration pushConfiguration = new PushConfiguration();
            pushConfiguration.setEnabled(Boolean.parseBoolean(properties.getProperty("push.enabled")));
            pushConfiguration.setAppId(properties.getProperty("push.appid"));
            pushConfiguration.setClientKey(properties.getProperty("push.clientkey"));
            applicationConfiguration.setPushConfiguration(pushConfiguration);
        }

        private static void setBrowserType(ApplicationConfiguration applicationConfiguration, Properties properties) {
            String strBrowserType = properties.getProperty("browser");
            switch (strBrowserType) {
                case "internal":
                    applicationConfiguration.setBrowserType(BrowserType.INTERNAL);
                    break;
                case "external":
                    applicationConfiguration.setBrowserType(BrowserType.EXTERNAL);
                    break;
                default:
                    applicationConfiguration.setBrowserType(BrowserType.INTERNAL);
                    break;
            }
        }

        private static void setDisplayOrientation(ApplicationConfiguration applicationConfiguration, Properties properties) {
            String orientationStr = properties.getProperty("orientation");
            switch (orientationStr) {
                case "portrait":
                    applicationConfiguration.setDisplayOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
                case "landscape":
                    applicationConfiguration.setDisplayOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
                default:
                    break;
            }
        }

        private static void createConfiguration(ApplicationConfiguration applicationConfiguration, Properties properties) {
            ActionBarConfiguration actionBarConfiguration = new ActionBarConfiguration();
            actionBarConfiguration.setEnabled(Boolean.parseBoolean(properties.getProperty("actionbar.enabled")));
            try {
                actionBarConfiguration.setColor(new ColorDrawable(Color.parseColor(properties.getProperty("actionbar.color"))));
            } catch (IllegalArgumentException e) {
                //color not found
            }
            applicationConfiguration.setActionBarConfiguration(actionBarConfiguration);
        }

        private static Locale createLocale(String locale) {
            Locale mLocale = Locale.getDefault();
            String lang = mLocale.getLanguage();
            if (locale == null || lang.equalsIgnoreCase(locale)) {
                return mLocale;
            }
            return new Locale(locale, mLocale.getCountry());
        }
    }

    private String url;
    private Locale locale;
    private Boolean statusBar;
    private String info;
    private ActionBarConfiguration actionBarConfiguration;
    private Integer displayOrientation;
    private BrowserType browserType;
    private Boolean zoomControl;
    private PushConfiguration pushConfiguration;
    private SplashConfiguration splashConfiguration;
    private AdConfiguration adConfiguration;

    private ApplicationConfiguration() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public ActionBarConfiguration getActionBarConfiguration() {
        return actionBarConfiguration;
    }

    public void setActionBarConfiguration(ActionBarConfiguration actionBarConfiguration) {
        this.actionBarConfiguration = actionBarConfiguration;
    }

    public AdConfiguration getAdConfiguration() {
        return adConfiguration;
    }

    public void setAdConfiguration(AdConfiguration adConfiguration) {
        this.adConfiguration = adConfiguration;
    }

    public Integer getDisplayOrientation() {
        return displayOrientation;
    }

    public void setDisplayOrientation(Integer displayOrientation) {
        this.displayOrientation = displayOrientation;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    public Boolean getZoomControl() {
        return zoomControl;
    }

    public void setZoomControl(Boolean zoomControl) {
        this.zoomControl = zoomControl;
    }

    public PushConfiguration getPushConfiguration() {
        return pushConfiguration;
    }

    public void setPushConfiguration(PushConfiguration pushConfiguration) {
        this.pushConfiguration = pushConfiguration;
    }

    public SplashConfiguration getSplashConfiguration() {
        return splashConfiguration;
    }

    public void setSplashConfiguration(SplashConfiguration splashConfiguration) {
        this.splashConfiguration = splashConfiguration;
    }

    public Boolean getStatusBar() {
        return statusBar;
    }

    public void setStatusBar(Boolean statusBar) {
        this.statusBar = statusBar;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
