package crc6440a7cc8802fa6d07;

import com.google.android.gms.maps.GoogleMap;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class OnMapReadyCallback implements IGCUserPeer, com.google.android.gms.maps.OnMapReadyCallback {
    public static final String __md_methods = "n_onMapReady:(Lcom/google/android/gms/maps/GoogleMap;)V:GetOnMapReady_Lcom_google_android_gms_maps_GoogleMap_Handler:Android.Gms.Maps.IOnMapReadyCallbackInvoker, Xamarin.GooglePlayServices.Maps\n";
    private ArrayList refList;

    private native void n_onMapReady(GoogleMap googleMap);

    static {
        Runtime.register("Xamarin.Forms.GoogleMaps.Android.Extensions.OnMapReadyCallback, Xamarin.Forms.GoogleMaps.Android", OnMapReadyCallback.class, __md_methods);
    }

    public OnMapReadyCallback() {
        if (getClass() == OnMapReadyCallback.class) {
            TypeManager.Activate("Xamarin.Forms.GoogleMaps.Android.Extensions.OnMapReadyCallback, Xamarin.Forms.GoogleMaps.Android", "", this, new Object[0]);
        }
    }

    @Override // com.google.android.gms.maps.OnMapReadyCallback
    public void onMapReady(GoogleMap googleMap) {
        n_onMapReady(googleMap);
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
