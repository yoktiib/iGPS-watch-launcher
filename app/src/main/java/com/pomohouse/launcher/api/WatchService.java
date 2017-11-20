package com.pomohouse.launcher.api;

import com.pomohouse.launcher.api.requests.ImeiRequest;
import com.pomohouse.launcher.api.requests.InitDeviceRequest;
import com.pomohouse.launcher.api.requests.LocationUpdateRequest;
import com.pomohouse.launcher.api.requests.RefreshLocationRequest;
import com.pomohouse.launcher.api.requests.StepRequest;
import com.pomohouse.launcher.api.requests.TimezoneUpdateRequest;
import com.pomohouse.launcher.api.requests.UpdateFirebaseRequest;
import com.pomohouse.launcher.api.requests.WearerStatusRequest;
import com.pomohouse.launcher.models.DeviceInfoModel;
import com.pomohouse.launcher.models.EventDataListModel;
import com.pomohouse.launcher.models.PinCodeModel;
import com.pomohouse.launcher.models.contacts.ContactCollection;
import com.pomohouse.launcher.models.events.CallContact;
import com.pomohouse.library.networks.ResponseDao;
import com.pomohouse.library.networks.ResultGenerator;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Admin on 8/17/16 AD.
 */
public interface WatchService {

    @POST("initDevice")
    Observable<ResultGenerator<DeviceInfoModel>> callInitialDevice(@Body InitDeviceRequest param);

    @POST("requestStatusShutdown")
    Observable<ResponseDao> callShutdownDevice(@Body ImeiRequest param);

    @POST("contact/requestAllContacts")
    Observable<ContactCollection> callReadyRequestFriend(@Body ImeiRequest param);

    @POST("pair/pinCode")
    Observable<ResultGenerator<PinCodeModel>> callPinCode(@Body ImeiRequest param);

    @POST("requestSOS")
    Observable<ResponseDao> callSOS(@Body ImeiRequest param);

    @POST("event/updateFCMToken")
    Observable<ResponseDao> callUpdateFCMToken(@Body UpdateFirebaseRequest requestParam);

    @POST("requestEventAndUpdateInfo")
    Observable<EventDataListModel> callUpdateInfoAndGetEventService(@Body LocationUpdateRequest request);

    @GET("contact/checkAllowCalling")
    Observable<ResultGenerator<CallContact>> callCheckAllowCalling(@Query("to") String toContactId, @Query("from") String fromContactId);

    @POST("requestUpdateWearerStatus")
    Observable<ResponseDao> callSenderWearerStatusService(@Body WearerStatusRequest request);

    @POST("requestAccident")
    Observable<ResponseDao> callFallStatusService(@Body ImeiRequest request);

    @POST("setting/setTimeZone")
    Observable<ResponseDao> callUpdateTimezone(@Body TimezoneUpdateRequest timeZoneParam);

    @POST("fitness/sendFitness")
    Observable<ResponseDao> callUpdateStep(@Body StepRequest timeZoneParam);

    @POST("location/updateLocation")
    Observable<ResponseDao> callUpdateLocation(@Body RefreshLocationRequest locationInfo);
}