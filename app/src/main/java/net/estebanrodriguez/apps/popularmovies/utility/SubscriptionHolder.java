package net.estebanrodriguez.apps.popularmovies.utility;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * This class is utilized to remove any possible memory leaks with any open subscriptions.
 */

public class SubscriptionHolder {
    private static List<Subscription> sSubscriptions;
    private static String LOG_TAG = SubscriptionHolder.class.getSimpleName();

    public static void holdSubscription(Subscription subscription){
        if(sSubscriptions == null){
            sSubscriptions = new ArrayList<>();
        }
        sSubscriptions.add(subscription);
    }

    public static void unsubscribeAll(){
        if(sSubscriptions != null){
            for(Subscription subscription: sSubscriptions){
                if(!subscription.isUnsubscribed()){
                    subscription.unsubscribe();
                    Log.v(LOG_TAG, "Unsubscribed");
                }
            }
        }

    }
}
