package com.oc.bashalir.mynews.Controllers.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.oc.bashalir.mynews.Controllers.Activities.ListSearchActivity;
import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.R;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 *
 */
public class NotificationReceiver extends BroadcastReceiver {

    final String NOTIFY = "NOTIFY";
    final String SEARCH = "SEARCH";
    final String CATEGORY = "CATEGORY";
    final String ID_SEARCH = "ID_SEARCH";
    final String DATE_SEARCH = "DATE_SEARCH";
    final String CHANNEL_ID = "1";
    private final String mTag = getClass().getSimpleName();
    private Disposable mDisp;
    private Boolean mGoSearch = true;
    private String mIdSearch;
    private SharedPreferences mSharedPref;
    private String mQuery;
    private String mCategory;
    private Context mContext;
    private String mDateSearch = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        mSharedPref = context.getSharedPreferences(NOTIFY, Context.MODE_PRIVATE);
        mQuery = mSharedPref.getString(SEARCH, "");
        mCategory = mSharedPref.getString(CATEGORY, "");
        mIdSearch = mSharedPref.getString(ID_SEARCH, "");
        mDateSearch = mSharedPref.getString(DATE_SEARCH, null);

        this.requestSearchNotification(context);
    }

    /**
     *
     * @param context
     */
    private void requestSearchNotification(Context context) {

        mContext = context;
        mDisp = NYTStreams.streamFetchSearch(mQuery, mCategory, mDateSearch, null).subscribeWith(new DisposableObserver<ArticleSearch>() {

            @Override
            public void onNext(ArticleSearch articleSearch) {
                Log.d(mTag, "NEXT");
                String idFirst = null;

                if (!articleSearch.getResponse().getDocs().isEmpty()) {
                    idFirst = articleSearch.getResponse().getDocs().get(0).getId();

                    if (idFirst.equals(mIdSearch)) {
                        mGoSearch = false;
                        Log.e(mTag, "FALSE");
                    } else {
                        mGoSearch = true;
                        Log.e(mTag, "TRUE");
                    }
                } else {
                    mGoSearch = true;
                }
               // mGoSearch = true;

                Log.e(mTag, mGoSearch + " " + idFirst + ' ' + mIdSearch);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(mTag, "On Error " + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e(mTag, "On Complete !!");


                if (mGoSearch) {
                    Log.e(mTag, "START NOTIFICATION");
                    configureNotificationChannel(mContext);
                    newNotification(mContext);
                }
            }
        });

    }

    /**
     * Launch Notification
     * @param context
     */
    private void newNotification(Context context) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Open List of news selected after clicking notification
        Intent launchNotificationIntent = new Intent(context, ListSearchActivity.class);
        launchNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        launchNotificationIntent.putExtra("category", mCategory);
        launchNotificationIntent.putExtra("query", mQuery);
        launchNotificationIntent.putExtra("notif", true);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1, launchNotificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        //set the notification display
        Notification.Builder builder = new Notification.Builder(context)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(mQuery)
                .setContentText("We found you new items")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(1, builder.build());
    }

    /**
     * Configure notification
     * @param context
     */
    private void configureNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
