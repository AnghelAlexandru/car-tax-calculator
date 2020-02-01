package clcvam.elitiv.calculatorvamal.analytics;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Sir on 1/26/2016.
 */
public class CalcVamAnalytics extends Application {
    public static final String TAG=CalcVamAnalytics.class.getSimpleName();
    public static CalcVamAnalytics mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=new CalcVamAnalytics();
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
        AnalyticsTrackers.initialize(getApplicationContext());

    }
    public static synchronized CalcVamAnalytics getInstance(){
        return mInstance=new CalcVamAnalytics();
    }
    public static synchronized Tracker getGoogleAnalyticsTracker(){
        AnalyticsTrackers analyticsTrackers=AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }
    public void trackScreenView(String screenName){
        Tracker t=getGoogleAnalyticsTracker();
        t.setScreenName(screenName);
        t.send(new HitBuilders.ScreenViewBuilder().build());
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }
    public void trackException(Exception e){
        if(e!=null){
            Tracker t=getGoogleAnalyticsTracker();
            t.send(new HitBuilders.ExceptionBuilder()
                .setDescription(new StandardExceptionParser(this,null)
                .getDescription(Thread.currentThread().getName(),e))
                .setFatal(false)
                .build()
            );
        }
    }
    public void trackEvent(String category,String action,String label){
        Tracker t=getGoogleAnalyticsTracker();
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }
}
