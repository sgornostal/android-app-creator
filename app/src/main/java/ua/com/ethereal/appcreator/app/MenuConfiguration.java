package ua.com.ethereal.appcreator.app;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Slava
 */
public class MenuConfiguration {

    private static volatile List<MenuConfiguration> menuConfiguration;
    public static List<MenuConfiguration> getInstance(Context context) throws Exception {
        if(menuConfiguration == null) {
            synchronized (MenuConfiguration.class) {
                if(menuConfiguration == null) {
                    menuConfiguration = init(context);
                }
            }
        }
        return menuConfiguration;
    }

    private static List<MenuConfiguration> init(Context context) throws Exception{
        Properties properties = new Properties();
        properties.load(context.getResources().openRawResource(R.raw.config));
        return MenuConfiguration.Builder.build(properties);
    }

    private MenuConfiguration() {}

    public static enum MenuIcon {
        AT("ic_at", R.drawable.ic_at),
        HOME("ic_home", R.drawable.ic_home),
        INFO("ic_info", R.drawable.ic_info),
        NEW("ic_new", R.drawable.ic_new),
        CLOSE("ic_close", R.drawable.ic_close),
        REFRESH("ic_refresh", R.drawable.ic_refresh),
        SETTING("ic_setting", R.drawable.ic_setting),
        SHARE("ic_share", R.drawable.ic_share);

        MenuIcon(String icon, Integer id) {
            this.id = id;
            this.icon = icon;
        }

        private String icon;
        private Integer id;

        public String getIcon() {
            return icon;
        }

        public Integer getId() {
            return id;
        }

        public static MenuIcon parseMenuIcon(String iconStr) {
            for (MenuIcon menuIcon : MenuIcon.values()) {
                if (menuIcon.getIcon().equals(iconStr)) {
                    return menuIcon;
                }
            }
            return MenuIcon.INFO;
        }
    }

    public static enum MenuAction {
        DISABLE,
        REFRESH,
        SHARE,
        ABOUT,
        QUIT,
        URL;

        public static MenuAction parseMenuAction(String action) {
            for (MenuAction menuAction : MenuAction.values()) {
                if (menuAction.name().equalsIgnoreCase(action)) {
                    return menuAction;
                }
            }
            return DISABLE;
        }
    }

    public static class Builder {

        public static List<MenuConfiguration> build(Properties properties) {
            List<MenuConfiguration> configurationList = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                String name = properties.getProperty("menu." + i + ".name");
                String action = properties.getProperty("menu." + i + ".action");
                String icon = properties.getProperty("menu." + i + ".icon");
                String url = properties.getProperty("menu." + i + ".url");
                MenuConfiguration menu = buildMenu(name, action, icon, url);
                if (menu != null) {
                    configurationList.add(menu);
                }
            }
            return configurationList;
        }

        private static MenuConfiguration buildMenu(String name, String action, String icon, String url) {
            MenuConfiguration menuConfiguration = new MenuConfiguration();
            menuConfiguration.setName(name);
            menuConfiguration.setAction(MenuAction.parseMenuAction(action));
            menuConfiguration.setIcon(MenuIcon.parseMenuIcon(icon));
            menuConfiguration.setUrl(getUrl(url));
            return menuConfiguration;
        }

        private static String getUrl(String url) {
            if (!(url.startsWith("http://") || url.startsWith("https://"))) {
                url = "http://" + url;
            }
            return url;
        }

    }

    private String name;
    private MenuAction action;
    private MenuIcon icon;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuAction getAction() {
        return action;
    }

    public void setAction(MenuAction action) {
        this.action = action;
    }

    public MenuIcon getIcon() {
        return icon;
    }

    public void setIcon(MenuIcon icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
