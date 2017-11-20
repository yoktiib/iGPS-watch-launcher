package com.pomohouse.launcher.main;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;
import com.google.gson.Gson;
import com.pomohouse.launcher.models.EventDataInfo;
import com.pomohouse.library.networks.MetaDataNetwork;

import static com.pomohouse.launcher.broadcast.BaseBroadcast.SEND_EVENT_UPDATE_INTENT;
import static com.pomohouse.launcher.main.presenter.LauncherPresenterImpl.EVENT_EXTRA;
import static com.pomohouse.launcher.main.presenter.LauncherPresenterImpl.EVENT_STATUS_EXTRA;
import static com.pomohouse.launcher.utils.EventConstant.EventLocal.EVENT_REFRESH_LOCATION_CODE;
import static com.pomohouse.launcher.utils.EventConstant.EventLocal.EVENT_UPDATE_LOCATION_CODE;

/**
 * Created by Admin on 6/3/2017 AD.
 */

public class LocationIntentService extends IntentService {
    public static boolean isRefreshLocation = false;
    public static String locationRefreshEndpoint = "";

    public LocationIntentService() {
        super("LocationIntentService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocationIntentService(String name) {
        super(name);
    }

    /**
     * Called when a new location update is available.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            if (LocationResult.hasResult(intent)) {
                LocationResult locationResult = LocationResult.extractResult(intent);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    //Timber.e("Updated location: " + location.toString() + " : Provider " + location.getProvider());
                    EventDataInfo eventContent = new EventDataInfo();
                    if (LocationIntentService.isRefreshLocation)
                        eventContent.setEventCode(EVENT_REFRESH_LOCATION_CODE);
                    else
                        eventContent.setEventCode(EVENT_UPDATE_LOCATION_CODE);
                    eventContent.setContent(new Gson().toJson(location));
                    final Intent intentEvent = new Intent(SEND_EVENT_UPDATE_INTENT);
                    intentEvent.putExtra(EVENT_STATUS_EXTRA, new MetaDataNetwork(0, "success"));
                    intentEvent.putExtra(EVENT_EXTRA, eventContent);
                    sendBroadcast(intentEvent);
                }
            }
        } catch (Exception ignore) {

        }
    }
}
