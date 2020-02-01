package clcvam.elitiv.calculatorvamal.analytics;


import android.content.Context;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.Map;

import clcvam.elitiv.com.calculatorvamal.R;

/**
 * Created by Sir on 1/26/2016.
 */
public class AnalyticsTrackers {
    public final Map<Target,Tracker> mTrackers=new HashMap<Target,Tracker>();
    public final Context mContext;
    public AnalyticsTrackers(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public enum Target{
        APP
    }
    private static AnalyticsTrackers sInstance;
    public static synchronized void initialize(Context context){
        if(sInstance!=null){
            Log.e("Error","initialize");
           // throw new IllegalStateException("Extra call to initialize analytics trackers");
        }
        sInstance=new AnalyticsTrackers(context);
    }
    public static synchronized AnalyticsTrackers getInstance(){
        if(sInstance ==null){
            Log.e("Error","getInstance");
            throw new IllegalStateException("Call initialize before getInstance");

        }
        return sInstance ;
    }
    public synchronized Tracker get(Target target){
        if(!mTrackers.containsKey(target)){
            Tracker tracker;
            switch (target){
                case APP:
                    tracker= GoogleAnalytics.getInstance(mContext).newTracker(R.xml.app_tracker);
                break;
                default:
                    throw new IllegalArgumentException("Unhandled analytics target "+target);
            }
            mTrackers.put(target,tracker);
        }
        return mTrackers.get(target);
    }
}
