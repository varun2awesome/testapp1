package crc64a0e0a82d0db9a07d;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class SingleLocationListener implements IGCUserPeer, LocationListener {
    public static final String __md_methods = "n_onLocationChanged:(Landroid/location/Location;)V:GetOnLocationChanged_Landroid_location_Location_Handler:Android.Locations.ILocationListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onProviderDisabled:(Ljava/lang/String;)V:GetOnProviderDisabled_Ljava_lang_String_Handler:Android.Locations.ILocationListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onProviderEnabled:(Ljava/lang/String;)V:GetOnProviderEnabled_Ljava_lang_String_Handler:Android.Locations.ILocationListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onStatusChanged:(Ljava/lang/String;ILandroid/os/Bundle;)V:GetOnStatusChanged_Ljava_lang_String_ILandroid_os_Bundle_Handler:Android.Locations.ILocationListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onFlushComplete:(I)V:GetOnFlushComplete_IHandler:Android.Locations.ILocationListener, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n";
    private ArrayList refList;

    private native void n_onFlushComplete(int i);

    private native void n_onLocationChanged(Location location);

    private native void n_onProviderDisabled(String str);

    private native void n_onProviderEnabled(String str);

    private native void n_onStatusChanged(String str, int i, Bundle bundle);

    static {
        Runtime.register("Xamarin.Essentials.SingleLocationListener, Xamarin.Essentials", SingleLocationListener.class, "n_onLocationChanged:(Landroid/location/Location;)V:GetOnLocationChanged_Landroid_location_Location_Handler:Android.Locations.ILocationListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onProviderDisabled:(Ljava/lang/String;)V:GetOnProviderDisabled_Ljava_lang_String_Handler:Android.Locations.ILocationListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onProviderEnabled:(Ljava/lang/String;)V:GetOnProviderEnabled_Ljava_lang_String_Handler:Android.Locations.ILocationListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onStatusChanged:(Ljava/lang/String;ILandroid/os/Bundle;)V:GetOnStatusChanged_Ljava_lang_String_ILandroid_os_Bundle_Handler:Android.Locations.ILocationListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onFlushComplete:(I)V:GetOnFlushComplete_IHandler:Android.Locations.ILocationListener, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n");
    }

    public SingleLocationListener() {
        if (getClass() == SingleLocationListener.class) {
            TypeManager.Activate("Xamarin.Essentials.SingleLocationListener, Xamarin.Essentials", "", this, new Object[0]);
        }
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        n_onLocationChanged(location);
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String str) {
        n_onProviderDisabled(str);
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String str) {
        n_onProviderEnabled(str);
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String str, int i, Bundle bundle) {
        n_onStatusChanged(str, i, bundle);
    }

    @Override // android.location.LocationListener
    public void onFlushComplete(int i) {
        n_onFlushComplete(i);
    }

    @Override // mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
