package org.ads.myads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.bumptech.glide.Glide;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.LoggingLevel;
import com.chartboost.sdk.ads.Banner;
import com.chartboost.sdk.ads.Interstitial;
import com.chartboost.sdk.ads.Rewarded;
import com.chartboost.sdk.callbacks.BannerCallback;
import com.chartboost.sdk.callbacks.InterstitialCallback;
import com.chartboost.sdk.callbacks.RewardedCallback;
import com.chartboost.sdk.callbacks.StartCallback;
import com.chartboost.sdk.events.CacheError;
import com.chartboost.sdk.events.CacheEvent;
import com.chartboost.sdk.events.ClickError;
import com.chartboost.sdk.events.ClickEvent;
import com.chartboost.sdk.events.DismissEvent;
import com.chartboost.sdk.events.ImpressionEvent;
import com.chartboost.sdk.events.RewardEvent;
import com.chartboost.sdk.events.ShowError;
import com.chartboost.sdk.events.ShowEvent;
import com.chartboost.sdk.events.StartError;
import com.chartboost.sdk.privacy.model.CCPA;
import com.chartboost.sdk.privacy.model.COPPA;
import com.chartboost.sdk.privacy.model.GDPR;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.gson.Gson;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;
import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.nativeads.NativeAd;
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener;
import com.yandex.mobile.ads.nativeads.NativeAdLoader;
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration;
import com.yandex.mobile.ads.nativeads.template.NativeBannerView;
import com.yandex.mobile.ads.rewarded.Reward;
import com.yandex.mobile.ads.rewarded.RewardedAd;
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener;

import org.ads.myads.Models.IpResponse;
import org.ads.myads.Models.MainResponse;
import org.ads.myads.Models.MoreAppsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MyAds {
    public static String CountryCode;
    public static AdView mAdView;
    @SuppressLint("StaticFieldLeak")
    public static IronSourceBannerLayout banner;
    @SuppressLint("StaticFieldLeak")
    public static MaxAdView BannerMax;
    public static BannerAdView bannerAdView;
    public static  com.chartboost.sdk.ads.Banner chartboostBanner;
    public static com.google.android.gms.ads.nativead.NativeAd NativeAdmob;
    public static MaxNativeAdLoader nativeAdLoader;
    public static MaxAd loadedNativeAd;
    public static NativeAdLoader nativeAdLoaderYandex;
    static boolean testmode =false;
    public static com.google.android.gms.ads.interstitial.InterstitialAd InterstitialAdmob;
    public static String TAG = "Splash";
    static int retryAttempt;
    static MaxInterstitialAd maxInterstitialAd;
    public static com.yandex.mobile.ads.interstitial.InterstitialAd mInterstitialAd;
    public static Interstitial chartboostInterstitial;
    public static int count = 1;
    public static RewardedAd mRewardedAd;
    public static Rewarded chartboostRewarded;
    public static MaxRewardedAd rewardedAd;
    public static com.google.android.gms.ads.rewarded.RewardedAd mRewardedAdAdmob;

    public static void InitSdk(Activity activity, String Jsonlink, InitListener initListener) {
        getUserIpInfo(activity);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest request = new StringRequest(Request.Method.GET, Jsonlink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                MainResponse mainResponse = gson.fromJson(response, MainResponse.class);
                Config.response = mainResponse;
                Config.myAds = mainResponse.getAds();
                IronSource.loadInterstitial();
                Chartboost.startWithAppId(activity, Config.myAds.getChartBoostAppID(), Config.myAds.getChartBoostAppSignature(), startError -> {
                    if (startError == null) {
                        // handle success
                        LoadChart(activity);
                        LoadYandexReward(activity);

                    } else {
                        // handle failure
                    }
                });
                Chartboost.setLoggingLevel(LoggingLevel.ALL);
                //ironSource initialization
                IronSource.init(activity, Config.response.getAds().getIronAppKey(), IronSource.AD_UNIT.INTERSTITIAL, IronSource.AD_UNIT.REWARDED_VIDEO, IronSource.AD_UNIT.BANNER);
                IronSource.loadInterstitial();
                IronSource.loadRewardedVideo();
                //max initialization
                AppLovinSdk.initializeSdk(activity, new AppLovinSdk.SdkInitializationListener() {
                    @Override
                    public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                        maxInterstitialAd = new MaxInterstitialAd(Config.myAds.getMaxInterstital(), activity );
                        // Load the first ad
                        maxInterstitialAd.loadAd();
                        rewardedAd = MaxRewardedAd.getInstance(Config.myAds.getMaxReward(), activity );
                        rewardedAd.loadAd();

                    }
                });
                AppLovinSdk.getInstance(activity).setMediationProvider("max");
                //admob initialization

                MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                        LoadAdmobReward(activity);
                        LoadAdmobInterstitial(activity);

                    }
                });

                //yandex initialization
                com.yandex.mobile.ads.common.MobileAds.initialize(activity, new InitializationListener() {
                    @Override
                    public void onInitializationCompleted() {
                        LoadYandex(activity);
                    }
                });

                //unityads initialization
                UnityAds.initialize(activity, Config.myAds.getUnityGameID(), testmode, new IUnityAdsInitializationListener() {
                    @Override
                    public void onInitializationComplete() {
                        LoadUnity(activity);
                        UnityAds.load(Config.myAds.getUnityInterstitial(), new IUnityAdsLoadListener() {
                            @Override
                            public void onUnityAdsAdLoaded(String placementId) {

                            }

                            @Override
                            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {

                            }
                        });
                    }

                    @Override
                    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {

                    }
                });



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (Config.myAds.isShowUpdateDialog()) {
                            ShowDialog(activity);
                        } else {
                            initListener.OnInitialized(activity);
                        }
                    }
                }, 4000);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                initListener.OnFailed(error.getMessage());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    public static void getUserIpInfo(Activity activity) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest request = new StringRequest(Request.Method.GET, "https://iplist.cc/api", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                IpResponse ipResponse = gson.fromJson(response, IpResponse.class);
                Config.ipResponse = ipResponse;
                CountryCode = ipResponse.getCountrycode();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    public static void MoreApps(Activity activity, RecyclerView recyclerView) {
        List<MoreAppsItem> moreAppsItems = new ArrayList<>();
        moreAppsItems = Config.response.getMoreApps();

        recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        recyclerView.setAdapter(new MoreAppsAdapter(activity, moreAppsItems));
    }

    public static void ShowDialog(Activity activity) {
        //TODO : Add theme for this dialog
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.updatedialog);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        ImageView Gif = dialog.findViewById(R.id.Gif);
        Button UpdateApp = dialog.findViewById(R.id.UpdateApp);
        Glide.with(activity).load(R.drawable.update).into(Gif);
        UpdateApp.setOnClickListener(view -> {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.myAds.getUpdateLink())));
        });
    }

    public static void AdmobBanner(Activity activity, LinearLayout Banner) {
        mAdView = new AdView(activity);
        mAdView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        mAdView.setAdUnitId(Config.myAds.getAdmobBanner());
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Banner.addView(mAdView);
    }

    public static void IronBanner(Activity activity, LinearLayout Banner) {
        ISBannerSize size = ISBannerSize.BANNER;

        // instantiate IronSourceBanner object, using the IronSource.createBanner API
        banner = IronSource.createBanner(activity, size);

        // add IronSourceBanner to your container
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        Banner.addView(banner, 0, layoutParams);

        if (banner != null) {
            // set the banner listener
            banner.setBannerListener(new BannerListener() {
                @Override
                public void onBannerAdLoaded() {

                }

                @Override
                public void onBannerAdLoadFailed(IronSourceError error) {
                    Toast.makeText(activity, error.getErrorMessage(), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onBannerAdClicked() {

                }

                @Override
                public void onBannerAdScreenPresented() {

                }

                @Override
                public void onBannerAdScreenDismissed() {

                }

                @Override
                public void onBannerAdLeftApplication() {

                }
            });

            // load ad into the created banner
            IronSource.loadBanner(banner,Config.myAds.getIronBanner());
        } else {
        }

    }

    public static void MaxBanner(Activity activity, LinearLayout Banner) {
        BannerMax = new MaxAdView(Config.myAds.getMaxBanner(), activity);
        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = activity.getResources().getDimensionPixelSize(R.dimen.banner_height);
        BannerMax.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));
        // Set background or background color for banners to be fully functional
        BannerMax.setBackgroundColor(activity.getResources().getColor(R.color.black));
        Banner.addView(BannerMax);
        // Load the ad
        BannerMax.loadAd();
    }

    public static void UnityBanner(Activity activity, LinearLayout Banner) {
        IUnityBannerListener unityBannerListener = new IUnityBannerListener() {
            @Override
            public void onUnityBannerLoaded(String placementId, View view) {
                Banner.removeView(view);
                Banner.addView(view);
            }

            @Override
            public void onUnityBannerUnloaded(String placementId) {

            }

            @Override
            public void onUnityBannerShow(String placementId) {

            }

            @Override
            public void onUnityBannerClick(String placementId) {

            }

            @Override
            public void onUnityBannerHide(String placementId) {

            }

            @Override
            public void onUnityBannerError(String message) {
                UnityBanners.loadBanner(activity, Config.myAds.getUnityBanner());

            }
        };
        UnityBanners.setBannerListener(unityBannerListener);
        UnityBanners.loadBanner(activity, Config.myAds.getUnityBanner());
    }

    public static void YandexBanner(Activity activity, LinearLayout Banner) {
        bannerAdView = new BannerAdView(activity);
        bannerAdView.setAdUnitId(Config.myAds.getYandexBanner());
        bannerAdView.setAdSize(AdSize.stickySize(AdSize.FULL_SCREEN.getWidth()));
        Banner.addView(bannerAdView);
        com.yandex.mobile.ads.common.AdRequest adRequest = new com.yandex.mobile.ads.common.AdRequest.Builder().build();
        bannerAdView.loadAd(adRequest);
    }

    public static void ChartBoostBanner(Activity activity, LinearLayout MyBanner) {
        chartboostBanner = new Banner(activity, "location", Banner.BannerSize.STANDARD, new BannerCallback() {
            @Override
            public void onAdLoaded(@NonNull CacheEvent cacheEvent, @Nullable CacheError cacheError) {


            }

            @Override
            public void onAdRequestedToShow(@NonNull ShowEvent showEvent) {

            }

            @Override
            public void onAdShown(@NonNull ShowEvent showEvent, @Nullable ShowError showError) {
                chartboostBanner.cache();

            }

            @Override
            public void onAdClicked(@NonNull ClickEvent clickEvent, @Nullable ClickError clickError) {

            }

            @Override
            public void onImpressionRecorded(@NonNull ImpressionEvent impressionEvent) {

            }
        }, null);
        MyBanner.removeAllViews();
        MyBanner.addView(chartboostBanner);
        chartboostBanner.cache();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chartboostBanner.show();
            }
        },1000);

    }

    public static void ShowBanner(Activity activity, LinearLayout Banner) {
        if (CountryCode!=null){
            switch (CountryCode) {
                case "RU":
                    switch (Config.response.getRussia().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "BR":
                    switch (Config.response.getBrazil().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "SA":
                    switch (Config.response.getSaudiArabia().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "US":
                    switch (Config.response.getUnitedStates().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "AE":
                    switch (Config.response.getEmirates().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "TR":
                    switch (Config.response.getTurkey().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "MY":
                    switch (Config.response.getMalaysia().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "MX":
                    switch (Config.response.getMexico().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "ZA":
                    switch (Config.response.getSouthAfrica().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "AR":
                    switch (Config.response.getArgentina().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "GB":
                    switch (Config.response.getUnitedKingdom().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "DE":
                    switch (Config.response.getGermany().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "JP":
                    switch (Config.response.getJapan().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "PL":
                    switch (Config.response.getPoland().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "TW":
                    switch (Config.response.getTaiwan().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "FR":
                    switch (Config.response.getFrance().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "IT":
                    switch (Config.response.getItaly().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "KR":
                    switch (Config.response.getSouthKorea().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "IL":
                    switch (Config.response.getIsrael().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "SE":
                    switch (Config.response.getSweden().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "FI":
                    switch (Config.response.getFinland().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "AU":
                    switch (Config.response.getAustralia().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "BE":
                    switch (Config.response.getBelgium().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "CH":
                    switch (Config.response.getSwitzerland().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "AT":
                    switch (Config.response.getAustria().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "SG":
                    switch (Config.response.getSingapore().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "NL":
                    switch (Config.response.getNetherlands().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "HK":
                    switch (Config.response.getHongKong().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "CA":
                    switch (Config.response.getCanada().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "NZ":
                    switch (Config.response.getNewZealand().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "Dk":
                    switch (Config.response.getDenmark().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "IE":
                    switch (Config.response.getIreland().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "NO":
                    switch (Config.response.getNorway().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "LU":
                    switch (Config.response.getLoxumberg().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                case "ES":
                    switch (Config.response.getSpain().getBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }
                    break;
                default:
                    switch (Config.response.getAds().getPriorityBanner()) {
                        case "admob":
                            AdmobBanner(activity, Banner);
                            break;
                        case "iron":
                            IronBanner(activity, Banner);
                            break;
                        case "max":
                            MaxBanner(activity, Banner);
                            break;
                        case "unity":
                            UnityBanner(activity, Banner);
                            break;
                        case "yandex":
                            YandexBanner(activity, Banner);
                            break;
                        case "chart":
                            ChartBoostBanner(activity, Banner);
                            break;
                        default:
                            IronBanner(activity, Banner);
                    }

            }

        }else {
            switch (Config.response.getAds().getPriorityBanner()) {
                case "admob":
                    AdmobBanner(activity, Banner);
                    break;
                case "iron":
                    IronBanner(activity, Banner);
                    break;
                case "max":
                    MaxBanner(activity, Banner);
                    break;
                case "unity":
                    UnityBanner(activity, Banner);
                    break;
                case "yandex":
                    YandexBanner(activity, Banner);
                    break;
                case "chart":
                    ChartBoostBanner(activity, Banner);
                    break;
                default:
                    IronBanner(activity, Banner);
            }
        }
    }

    public static void populateUnifiedNativeAdView(com.google.android.gms.ads.nativead.NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView(adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        Objects.requireNonNull(adView.getMediaView()).setMediaContent(Objects.requireNonNull(nativeAd.getMediaContent()));


        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);

        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.INVISIBLE);

        } else {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }
        if (nativeAd.getStarRating() == null) {
            Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView())).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
        } else
            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
        adView.getAdvertiserView().setVisibility(View.VISIBLE);

        adView.setNativeAd(nativeAd);


    }

    public static void ShowNativeAdmob(Activity ctx, FrameLayout Native) {
        AdLoader.Builder builder = new AdLoader.Builder(ctx, Config.myAds.getAdmobNative());
        builder.forNativeAd(unifiedNativeAd -> {
            if (NativeAdmob != null) {
                NativeAdmob.destroy();
            }
            NativeAdmob = unifiedNativeAd;
            NativeAdView adView = (NativeAdView) ctx.getLayoutInflater().inflate(R.layout.admobnative, null);
            populateUnifiedNativeAdView(unifiedNativeAd, adView);
            Native.removeAllViews();
            Native.addView(adView);
        }).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public static MaxNativeAdView createNativeAdView(Activity activity) {
        MaxNativeAdViewBinder binder = new MaxNativeAdViewBinder.Builder(R.layout.maxnative)
                .setTitleTextViewId(R.id.title_text_view)
                .setBodyTextViewId(R.id.body_text_view)
                .setAdvertiserTextViewId(R.id.advertiser_textView)
                .setIconImageViewId(R.id.icon_image_view)
                .setMediaContentViewGroupId(R.id.media_view_container)
                .setOptionsContentViewGroupId(R.id.ad_options_view)
                .setCallToActionButtonId(R.id.cta_button)
                .build();
        return new MaxNativeAdView(binder, activity);
    }

    public static void ShowMaxNative(Activity activity, FrameLayout Native) {
        nativeAdLoader = new MaxNativeAdLoader(Config.myAds.getMaxNative(), activity);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(@Nullable MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                if (loadedNativeAd != null) {
                    nativeAdLoader.destroy(loadedNativeAd);
                }
                // Save ad for cleanup.
                loadedNativeAd = maxAd;
                Native.removeAllViews();
                Native.addView(maxNativeAdView);
            }

            @Override
            public void onNativeAdLoadFailed(String s, MaxError maxError) {
                super.onNativeAdLoadFailed(s, maxError);
            }
        });
        nativeAdLoader.loadAd(createNativeAdView(activity));
    }

    public static void ShowYandexNative(Activity activity, FrameLayout Native) {
        nativeAdLoaderYandex = new NativeAdLoader(activity);
        nativeAdLoaderYandex.setNativeAdLoadListener(new NativeAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull final NativeAd nativeAd) {
                //bind nativeAd
                NativeBannerView nativeBannerView = new NativeBannerView(activity);
                nativeBannerView.setAd(nativeAd);
                Native.addView(nativeBannerView);
            }

            @Override
            public void onAdFailedToLoad(@NonNull final AdRequestError error) {
                //log error
                Toast.makeText(activity, error.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });

        final NativeAdRequestConfiguration nativeAdRequestConfiguration =
                new NativeAdRequestConfiguration.Builder(Config.myAds.getYandexNative()).build();
        nativeAdLoaderYandex.loadAd(nativeAdRequestConfiguration);
    }
    public static void ShowNative(Activity activity,FrameLayout Native){
        if (CountryCode!=null){
            switch (CountryCode) {
                case "RU":
                    switch (Config.response.getRussia().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "BR":
                    switch (Config.response.getBrazil().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "SA":
                    switch (Config.response.getSaudiArabia().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "US":
                    switch (Config.response.getUnitedStates().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "AE":
                    switch (Config.response.getEmirates().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "TR":
                    switch (Config.response.getTurkey().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "MY":
                    switch (Config.response.getMalaysia().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "MX":
                    switch (Config.response.getMexico().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "ZA":
                    switch (Config.response.getSouthAfrica().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "AR":
                    switch (Config.response.getArgentina().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "GB":
                    switch (Config.response.getUnitedKingdom().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "DE":
                    switch (Config.response.getGermany().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "JP":
                    switch (Config.response.getJapan().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "PL":
                    switch (Config.response.getPoland().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "TW":
                    switch (Config.response.getTaiwan().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "FR":
                    switch (Config.response.getFrance().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "IT":
                    switch (Config.response.getItaly().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "KR":
                    switch (Config.response.getSouthKorea().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "IL":
                    switch (Config.response.getIsrael().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "SE":
                    switch (Config.response.getSweden().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "FI":
                    switch (Config.response.getFinland().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "AU":
                    switch (Config.response.getAustralia().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "BE":
                    switch (Config.response.getBelgium().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "CH":
                    switch (Config.response.getSwitzerland().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "AT":
                    switch (Config.response.getAustria().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "SG":
                    switch (Config.response.getSingapore().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "NL":
                    switch (Config.response.getNetherlands().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "HK":
                    switch (Config.response.getHongKong().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "CA":
                    switch (Config.response.getCanada().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "NZ":
                    switch (Config.response.getNewZealand().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "Dk":
                    switch (Config.response.getDenmark().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "IE":
                    switch (Config.response.getIreland().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "NO":
                    switch (Config.response.getNorway().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "LU":
                    switch (Config.response.getLoxumberg().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                case "ES":
                    switch (Config.response.getSpain().getNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }
                    break;
                default:
                    switch (Config.response.getAds().getPriorityNative()) {
                        case "admob":
                            ShowNativeAdmob(activity, Native);
                            break;
                        case "max":
                            ShowMaxNative(activity, Native);
                            break;
                        case "yandex":
                            ShowYandexNative(activity, Native);
                            break;
                        default:
                            ShowMaxNative(activity, Native);
                    }

            }

        }else {
            switch (Config.response.getAds().getPriorityNative()) {
                case "admob":
                    ShowNativeAdmob(activity, Native);
                    break;
                case "max":
                    ShowMaxNative(activity, Native);
                    break;
                case "yandex":
                    ShowYandexNative(activity, Native);
                    break;
                default:
                    ShowMaxNative(activity, Native);
            }
        }

    }
    public static void LoadAdmobInterstitial(Activity activity) {
        AdRequest adRequest = new AdRequest.Builder().build();
        com.google.android.gms.ads.interstitial.InterstitialAd
                .load(activity, Config.myAds.getAdmobInterstital(),
                        adRequest, new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                                // The InterstitialAdmob reference will be null until
                                // an ad is loaded.
                                InterstitialAdmob = interstitialAd;
                                InterstitialAdmob.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        Log.d("TAG", "The ad was dismissed.");
                                        LoadAdmobInterstitial(activity);
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                        super.onAdFailedToShowFullScreenContent(adError);
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        //InterstitialAdmob = null;
                                        Log.d("TAG", "The ad was shown.");

                                    }
                                });

                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error
                                InterstitialAdmob = null;
                            }
                        });

    }
    static void ShowAdmobInterstitial(Activity activity) {
        if (InterstitialAdmob != null) {
            InterstitialAdmob.show(activity);
            InterstitialAdmob.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    LoadAdmobInterstitial(activity);

                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {

                    LoadAdmobInterstitial(activity);

                }
            });
        } else {
            LoadAdmobInterstitial(activity);

        }
    }
    public static void ShowIronSourceInterstitial(Activity activity) {
        if (IronSource.isInterstitialReady()) {
            IronSource.showInterstitial(Config.myAds.getIronInterstital());
            IronSource.setInterstitialListener(new InterstitialListener() {
                @Override
                public void onInterstitialAdReady() {
                    Log.e("MYAPPDEBUG","Ironsourec Interstitial ready");

                }

                @Override
                public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
                    Log.e("MYAPPDEBUG",ironSourceError.getErrorMessage());

                }

                @Override
                public void onInterstitialAdOpened() {

                }

                @Override
                public void onInterstitialAdClosed() {

                    IronSource.loadInterstitial();
                }

                @Override
                public void onInterstitialAdShowSucceeded() {

                }

                @Override
                public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {

                    IronSource.loadInterstitial();
                    Log.e("MYAPPDEBUG",ironSourceError.getErrorMessage());

                }

                @Override
                public void onInterstitialAdClicked() {

                }
            });
        } else {
            IronSource.loadInterstitial();
        }
    }
    public static void ShowMaxInterstitial(Activity activity) {
        if (maxInterstitialAd!=null){
            if (maxInterstitialAd.isReady()){
                maxInterstitialAd.showAd();
                maxInterstitialAd.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd ad) {
                        retryAttempt = 0;
                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {
                        maxInterstitialAd.loadAd();
                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        retryAttempt++;
                        long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                maxInterstitialAd.loadAd();
                            }
                        }, delayMillis );
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                        maxInterstitialAd.loadAd();
                    }
                });
            }else {
                maxInterstitialAd.loadAd();
            }
        }

    }
    public static void ShowUnityInterstitial(Activity activity){
        UnityAds.show(activity, Config.myAds.getUnityInterstitial(), new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                UnityAds.load(Config.myAds.getUnityInterstitial());
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {

            }

            @Override
            public void onUnityAdsShowClick(String placementId) {

            }

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                UnityAds.load(Config.myAds.getUnityInterstitial());
            }
        });
    }
    public static void LoadYandex(Activity activity){
        mInterstitialAd = new com.yandex.mobile.ads.interstitial.InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(Config.myAds.getYandexInterstitial());

        // Creating an ad targeting object.
        final com.yandex.mobile.ads.common.AdRequest adRequest = new com.yandex.mobile.ads.common.AdRequest.Builder().build();

        // Registering a listener to track events in the ad.
        mInterstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(AdRequestError adRequestError) {

            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });

        // Loading ads.
        mInterstitialAd.loadAd(adRequest);
    }
    public static void ShowYandexInterstitial(Activity activity){
        mInterstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                LoadYandex(activity);
            }

            @Override
            public void onAdShown() {
                LoadYandex(activity);

            }

            @Override
            public void onAdDismissed() {
                LoadYandex(activity);

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });
        mInterstitialAd.show();
    }
    public static void LoadChart(Activity activity){
        chartboostInterstitial = new Interstitial("location", new InterstitialCallback() {
            @Override
            public void onAdDismiss(@NonNull DismissEvent dismissEvent) {
                LoadChart(activity);
            }

            @Override
            public void onAdLoaded(@NonNull CacheEvent cacheEvent, @Nullable CacheError cacheError) {

            }

            @Override
            public void onAdRequestedToShow(@NonNull ShowEvent showEvent) {

            }

            @Override
            public void onAdShown(@NonNull ShowEvent showEvent, @Nullable ShowError showError) {
                LoadChart(activity);
            }

            @Override
            public void onAdClicked(@NonNull ClickEvent clickEvent, @Nullable ClickError clickError) {

            }

            @Override
            public void onImpressionRecorded(@NonNull ImpressionEvent impressionEvent) {

            }
        }, null);
        chartboostInterstitial.cache();

    }
    public static void ShowChartInterstitial(Activity activity){
        chartboostInterstitial.show();

    }
    public static void ShowInterstitial(Activity activity){
        if (Config.myAds.getClickCount() == count) {
            count = 1;
            if (CountryCode!=null){
                switch (CountryCode) {
                    case "RU":
                        switch (Config.response.getRussia().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "BR":
                        switch (Config.response.getBrazil().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "SA":
                        switch (Config.response.getSaudiArabia().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "US":
                        switch (Config.response.getUnitedStates().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "AE":
                        switch (Config.response.getEmirates().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "TR":
                        switch (Config.response.getTurkey().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "MY":
                        switch (Config.response.getMalaysia().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "MX":
                        switch (Config.response.getMexico().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "ZA":
                        switch (Config.response.getSouthAfrica().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "AR":
                        switch (Config.response.getArgentina().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "GB":
                        switch (Config.response.getUnitedKingdom().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "DE":
                        switch (Config.response.getGermany().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "JP":
                        switch (Config.response.getJapan().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "PL":
                        switch (Config.response.getPoland().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "TW":
                        switch (Config.response.getTaiwan().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "FR":
                        switch (Config.response.getFrance().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "IT":
                        switch (Config.response.getItaly().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "KR":
                        switch (Config.response.getSouthKorea().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "IL":
                        switch (Config.response.getIsrael().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "SE":
                        switch (Config.response.getSweden().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "FI":
                        switch (Config.response.getFinland().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "AU":
                        switch (Config.response.getAustralia().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "BE":
                        switch (Config.response.getBelgium().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "CH":
                        switch (Config.response.getSwitzerland().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "AT":
                        switch (Config.response.getAustria().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "SG":
                        switch (Config.response.getSingapore().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "NL":
                        switch (Config.response.getNetherlands().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "HK":
                        switch (Config.response.getHongKong().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "CA":
                        switch (Config.response.getCanada().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "NZ":
                        switch (Config.response.getNewZealand().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "Dk":
                        switch (Config.response.getDenmark().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "IE":
                        switch (Config.response.getIreland().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "NO":
                        switch (Config.response.getNorway().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "LU":
                        switch (Config.response.getLoxumberg().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    case "ES":
                        switch (Config.response.getSpain().getInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }
                        break;
                    default:
                        switch (Config.response.getAds().getPriotiyInterstitial()) {
                            case "admob":
                                ShowAdmobInterstitial(activity);
                                break;
                            case "max":
                                ShowMaxInterstitial(activity);
                                break;
                            case "yandex":
                                ShowYandexInterstitial(activity);
                                break;
                            case "iron":
                                ShowIronSourceInterstitial(activity);
                                break;
                            case "unity":
                                ShowUnityInterstitial(activity);
                                break;
                            case "chart":
                                ShowChartInterstitial(activity);
                                break;
                            default:
                                ShowIronSourceInterstitial(activity);
                        }

                }

            }else {
                switch (Config.response.getAds().getPriotiyInterstitial()) {
                    case "admob":
                        ShowAdmobInterstitial(activity);
                        break;
                    case "max":
                        ShowMaxInterstitial(activity);
                        break;
                    case "yandex":
                        ShowYandexInterstitial(activity);
                        break;
                    case "iron":
                        ShowIronSourceInterstitial(activity);
                        break;
                    case "unity":
                        ShowUnityInterstitial(activity);
                        break;
                    case "chart":
                        ShowChartInterstitial(activity);
                        break;
                    default:
                        ShowIronSourceInterstitial(activity);
                }
            }
        }
        else {
            count++;

        }
    }

    public static void LoadYandexReward(Activity activity){
        mRewardedAd = new RewardedAd(activity);
        mRewardedAd.setAdUnitId(Config.myAds.getYandexReward());
        // Creating an ad targeting object.
        final com.yandex.mobile.ads.common.AdRequest adRequest = new com.yandex.mobile.ads.common.AdRequest.Builder().build();
        mRewardedAd.loadAd(adRequest);
    }
    public static void ShowYandexReward(Activity activity,RewardListener rewardListener){
        mRewardedAd.setRewardedAdEventListener(new RewardedAdEventListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                LoadYandexReward(activity);
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {
                rewardListener.OnClose();
                LoadYandexReward(activity);
            }

            @Override
            public void onRewarded(@NonNull Reward reward) {
                rewardListener.OnReward();
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });
        mRewardedAd.show();
    }
    public static void ShowRewardChart(Activity activity,RewardListener rewardListener){
        chartboostRewarded = new Rewarded("start", new RewardedCallback() {
            @Override
            public void onRewardEarned(@NonNull RewardEvent rewardEvent) {
                rewardListener.OnReward();
            }

            @Override
            public void onAdDismiss(@NonNull DismissEvent dismissEvent) {
                rewardListener.OnClose();
            }

            @Override
            public void onAdLoaded(@NonNull CacheEvent cacheEvent, @Nullable CacheError cacheError) {

            }

            @Override
            public void onAdRequestedToShow(@NonNull ShowEvent showEvent) {

            }

            @Override
            public void onAdShown(@NonNull ShowEvent showEvent, @Nullable ShowError showError) {

            }

            @Override
            public void onAdClicked(@NonNull ClickEvent clickEvent, @Nullable ClickError clickError) {

            }

            @Override
            public void onImpressionRecorded(@NonNull ImpressionEvent impressionEvent) {

            }
        }, null);
        chartboostRewarded.cache();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chartboostRewarded.show();

            }
        },1000);

    }
    public static void LoadUnity(Activity activity){
        UnityAds.load(Config.myAds.getUnityReward(), new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {

            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {

            }
        });

    }
    public static void ShowUnityReward(Activity activity,RewardListener rewardListener){
        UnityAds.show(activity, Config.myAds.getUnityReward(), new UnityAdsShowOptions(), new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                LoadUnity(activity);
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {

            }

            @Override
            public void onUnityAdsShowClick(String placementId) {

            }

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                LoadUnity(activity);
                rewardListener.OnReward();
                rewardListener.OnClose();
            }
        });

    }

    public static void LoadAdmobReward(Activity activity){
        AdRequest adRequest = new AdRequest.Builder().build();

        com.google.android.gms.ads.rewarded.RewardedAd.load(activity, Config.myAds.getAdmobReward(),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        mRewardedAd = null;
                        LoadAdmobReward(activity);
                    }

                    @Override
                    public void onAdLoaded(@NonNull com.google.android.gms.ads.rewarded.RewardedAd rewardedAd) {
                        mRewardedAdAdmob = rewardedAd;
                    }
                });
    }
    public static void ShowMaxReward(Activity activity,RewardListener rewardListener){
        if ( rewardedAd.isReady() ) {
            rewardedAd.showAd();
            rewardedAd.setListener(new MaxRewardedAdListener() {
                @Override
                public void onRewardedVideoStarted(MaxAd ad) {

                }

                @Override
                public void onRewardedVideoCompleted(MaxAd ad) {
                    rewardedAd.loadAd();
                    rewardListener.OnClose();
                }

                @Override
                public void onUserRewarded(MaxAd ad, MaxReward reward) {
                    rewardListener.OnReward();

                }

                @Override
                public void onAdLoaded(MaxAd ad) {

                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {

                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {

                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    rewardedAd.loadAd();

                }
            });
        }else {
            rewardedAd.loadAd();

        }
    }
    public static void ShowIronReward(Activity activity,RewardListener rewardListener){
        if (IronSource.isRewardedVideoAvailable()){
            IronSource.showRewardedVideo(Config.myAds.getIronReward());
            IronSource.setRewardedVideoListener(new RewardedVideoListener() {
                @Override
                public void onRewardedVideoAdOpened() {

                }

                @Override
                public void onRewardedVideoAdClosed() {
                    IronSource.loadRewardedVideo();
                    rewardListener.OnClose();
                }

                @Override
                public void onRewardedVideoAvailabilityChanged(boolean b) {

                }

                @Override
                public void onRewardedVideoAdStarted() {

                }

                @Override
                public void onRewardedVideoAdEnded() {

                }

                @Override
                public void onRewardedVideoAdRewarded(Placement placement) {
                    rewardListener.OnReward();
                }

                @Override
                public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
                    IronSource.loadRewardedVideo();

                }

                @Override
                public void onRewardedVideoAdClicked(Placement placement) {

                }
            });
        }else {
            IronSource.loadRewardedVideo();
        }
    }
    public static void ShowAdmobReward(Activity activity,RewardListener rewardListener){
        if (mRewardedAdAdmob != null) {
            mRewardedAdAdmob.show(activity, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    rewardListener.OnReward();
                }
            });
            mRewardedAdAdmob.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    LoadAdmobReward(activity);
                    rewardListener.OnClose();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);

                }
            });
        } else {
            LoadAdmobReward(activity);
        }
    }


    public static void ShowReward(Activity activity,RewardListener rewardListener){
        if (CountryCode!=null){
            switch (CountryCode) {
                case "RU":
                    switch (Config.response.getRussia().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "BR":
                    switch (Config.response.getBrazil().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "SA":
                    switch (Config.response.getSaudiArabia().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "US":
                    switch (Config.response.getUnitedStates().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "AE":
                    switch (Config.response.getEmirates().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "TR":
                    switch (Config.response.getTurkey().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "MY":
                    switch (Config.response.getMalaysia().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "MX":
                    switch (Config.response.getMexico().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "ZA":
                    switch (Config.response.getSouthAfrica().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "AR":
                    switch (Config.response.getArgentina().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "GB":
                    switch (Config.response.getUnitedKingdom().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "DE":
                    switch (Config.response.getGermany().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "JP":
                    switch (Config.response.getJapan().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "PL":
                    switch (Config.response.getPoland().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "TW":
                    switch (Config.response.getTaiwan().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "FR":
                    switch (Config.response.getFrance().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "IT":
                    switch (Config.response.getItaly().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "KR":
                    switch (Config.response.getSouthKorea().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "IL":
                    switch (Config.response.getIsrael().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "SE":
                    switch (Config.response.getSweden().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "FI":
                    switch (Config.response.getFinland().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "AU":
                    switch (Config.response.getAustralia().getInterstitial()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "BE":
                    switch (Config.response.getBelgium().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "CH":
                    switch (Config.response.getSwitzerland().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "AT":
                    switch (Config.response.getAustria().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "SG":
                    switch (Config.response.getSingapore().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "NL":
                    switch (Config.response.getNetherlands().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "HK":
                    switch (Config.response.getHongKong().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "CA":
                    switch (Config.response.getCanada().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "NZ":
                    switch (Config.response.getNewZealand().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "Dk":
                    switch (Config.response.getDenmark().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "IE":
                    switch (Config.response.getIreland().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "NO":
                    switch (Config.response.getNorway().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "LU":
                    switch (Config.response.getLoxumberg().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                case "ES":
                    switch (Config.response.getSpain().getReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }
                    break;
                default:
                    switch (Config.response.getAds().getPriorityReward()) {
                        case "admob":
                            ShowAdmobReward(activity,rewardListener);
                            break;
                        case "max":
                            ShowMaxReward(activity,rewardListener);
                            break;
                        case "yandex":
                            ShowYandexReward(activity,rewardListener);
                            break;
                        case "iron":
                            ShowIronReward(activity,rewardListener);
                            break;
                        case "unity":
                            ShowUnityReward(activity,rewardListener);
                            break;
                        case "chart":
                            ShowRewardChart(activity,rewardListener);
                            break;
                        default:
                            ShowIronReward(activity,rewardListener);
                    }

            }

        }else {
            switch (Config.response.getAds().getPriorityReward()) {
                case "admob":
                    ShowAdmobReward(activity,rewardListener);
                    break;
                case "max":
                    ShowMaxReward(activity,rewardListener);
                    break;
                case "yandex":
                    ShowYandexReward(activity,rewardListener);
                    break;
                case "iron":
                    ShowIronReward(activity,rewardListener);
                    break;
                case "unity":
                    ShowUnityReward(activity,rewardListener);
                    break;
                case "chart":
                    ShowRewardChart(activity,rewardListener);
                    break;
                default:
                    ShowIronReward(activity,rewardListener);
            }
        }
    }

    public static void DestroyAds() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        if (banner != null) {
            IronSource.destroyBanner(banner);
        }

        if (bannerAdView != null) {
            bannerAdView.destroy();
        }
        if (chartboostBanner != null) {
            chartboostBanner.detach();
            chartboostBanner.clearCache();
        }
        if (NativeAdmob != null) {
            NativeAdmob.destroy();
        }
        if (loadedNativeAd != null) {
            nativeAdLoader.destroy(loadedNativeAd);
        }
        if (nativeAdLoaderYandex != null) {
            nativeAdLoaderYandex.cancelLoading();
        }
        UnityBanners.destroy();
    }

}
