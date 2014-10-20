package ua.com.ethereal.appcreator.app;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.*;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.List;
import java.util.Locale;
import java.util.Properties;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "AppCreator";

    private ApplicationConfiguration applicationConfiguration;
    private List<MenuConfiguration> menuConfigurationList;

    private RelativeLayout rootView;
    private WebView webView;
    private ViewFlipper splashFlipper;
    private ProgressBar progressBar;

    private boolean firstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!applicationConfiguration.getActionBarConfiguration().getEnabled()) {
            getSupportActionBar().hide();
        } else {
            if (applicationConfiguration.getActionBarConfiguration().getColor() != null) {
                getSupportActionBar().setBackgroundDrawable(applicationConfiguration.getActionBarConfiguration().getColor());
            }
        }
        setLocale(applicationConfiguration.getLocale());
        setContentView(R.layout.activity_main);

        if (applicationConfiguration.getDisplayOrientation() != null) {
            setRequestedOrientation(applicationConfiguration.getDisplayOrientation());
        }

        rootView = (RelativeLayout) findViewById(R.id.root_view);
        webView = (WebView) findViewById(R.id.web_view);
        splashFlipper = (ViewFlipper) findViewById(R.id.splash_flipper);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if (applicationConfiguration.getSplashConfiguration().getEnabled()) {
            RelativeLayout splashLayout = (RelativeLayout) findViewById(R.id.splash_layout);
            ImageView splashImage = (ImageView) splashLayout.findViewById(R.id.splash_image);
            splashLayout.setBackgroundColor(applicationConfiguration.getSplashConfiguration().getColor());
            splashImage.setLayoutParams(new RelativeLayout.LayoutParams(applicationConfiguration.getSplashConfiguration().getLayoutType(),
                    applicationConfiguration.getSplashConfiguration().getLayoutType()));
        } else {
            splashFlipper.showNext();
            firstLoad = false;
        }

        if (applicationConfiguration.getAdConfiguration().getEnabled()) {
            AdView adView = new AdView(this);
            adView.setAdUnitId(applicationConfiguration.getAdConfiguration().getAdmobId());
            adView.setAdSize(AdSize.BANNER);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (applicationConfiguration.getAdConfiguration().getPosition() == ApplicationConfiguration.AdPosition.ABOVE) {
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            } else {
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            }
            rootView.addView(adView);
            adView.setLayoutParams(params);
            AdRequest adRequest = new AdRequest.Builder().build();
            // Start loading the ad in the background.
            adView.loadAd(adRequest);
        }

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        if (applicationConfiguration.getBrowserType() == ApplicationConfiguration.BrowserType.INTERNAL) {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            if (firstLoad) {
                                splashFlipper.showNext();
                                firstLoad = false;
                            }
                        }
                    });
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.endsWith(".pdf")) {
                        view.loadUrl("https://docs.google.com/viewer?embedded=true&url=" + url);
                    } else if (url.startsWith("mailto:")) {
                        MailTo mt = MailTo.parse(url);
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mt.getTo()});
                        i.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
                        i.putExtra(Intent.EXTRA_CC, mt.getCc());
                        i.putExtra(Intent.EXTRA_TEXT, mt.getBody());
                        view.getContext().startActivity(i);
                    } else if (url.startsWith("tel:")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse(url));
                        startActivity(intent);
                    } else {
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                    return false;
                }

            });
        }

        webView.getSettings().setBuiltInZoomControls(applicationConfiguration.getZoomControl());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(applicationConfiguration.getUrl());
    }


    private void init() {
        try {
            Properties properties = new Properties();
            properties.load(getResources().openRawResource(R.raw.config));
            applicationConfiguration = ApplicationConfiguration.Builder.build(properties);
            menuConfigurationList = MenuConfiguration.Builder.build(properties);
        } catch (Exception ex) {
            Log.e(TAG, "Failed to load application properties.", ex);
            finish();
        }
    }

    private void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuConfigurationList.isEmpty()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        } else {
            for (final MenuConfiguration menuConfiguration : menuConfigurationList) {
                MenuItem menuItem = menu.add(menuConfiguration.getName());
                menuItem.setIcon(getResources().getDrawable(menuConfiguration.getIcon().getId()));
                menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        onMenuClick(menuConfiguration);
                        return true;
                    }
                });
            }
        }
        return true;
    }

    private void onMenuClick(MenuConfiguration menuConfiguration) {
        switch (menuConfiguration.getAction()) {
            case ABOUT:
                showAbout();
                break;
            case QUIT:
                finish();
                break;
            case REFRESH:
                webView.reload();
                break;
            case SHARE:
                share();
                break;
            case URL:
                if (menuConfiguration.getUrl() != null && !menuConfiguration.getUrl().equals("")) {
                    webView.loadUrl(menuConfiguration.getUrl());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!menuConfigurationList.isEmpty()) {
            return super.onOptionsItemSelected(item);
        }

        switch (item.getItemId()) {
            case R.id.exit_menuitem:
                finish();
                break;
            case R.id.refresh_menuitem:
                webView.reload();
                break;
            case R.id.about_menuitem:
                showAbout();
                break;
            case R.id.share_menuitem:
                share();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void share() {
        String packageName = getApplicationContext().getPackageName();
        final String url = "https://play.google.com/store/apps/details?id=" + packageName;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        String title = getString(R.string.app_name);
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_app).trim() + " " + title + "!");
        i.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(i, getString(R.string.share_title)));
    }

    private void showAbout() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_about);
        TextView infoView = (TextView) dialog.findViewById(R.id.text_info_view);
        String html = getResources().getString(R.string.app_info);
        infoView.setText(Html.fromHtml(html));
        infoView.setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
    }
}
