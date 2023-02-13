package crc64d32ffa835eadac0e;

import com.google.android.gms.maps.GoogleMap;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class DelegateCancelableCallback implements IGCUserPeer, GoogleMap.CancelableCallback {
    public static final String __md_methods = "n_onCancel:()V:GetOnCancelHandler:Android.Gms.Maps.GoogleMap/ICancelableCallbackInvoker, Xamarin.GooglePlayServices.Maps\nn_onFinish:()V:GetOnFinishHandler:Android.Gms.Maps.GoogleMap/ICancelableCallbackInvoker, Xamarin.GooglePlayServices.Maps\n";
    private ArrayList refList;

    private native void n_onCancel();

    private native void n_onFinish();

    static {
        Runtime.register("Xamarin.Forms.GoogleMaps.Android.Logics.DelegateCancelableCallback, Xamarin.Forms.GoogleMaps.Android", DelegateCancelableCallback.class, __md_methods);
    }

    public DelegateCancelableCallback() {
        if (getClass() == DelegateCancelableCallback.class) {
            TypeManager.Activate("Xamarin.Forms.GoogleMaps.Android.Logics.DelegateCancelableCallback, Xamarin.Forms.GoogleMaps.Android", "", this, new Object[0]);
        }
    }

    @Override // com.google.android.gms.maps.GoogleMap.CancelableCallback
    public void onCancel() {
        n_onCancel();
    }

    @Override // com.google.android.gms.maps.GoogleMap.CancelableCallback
    public void onFinish() {
        n_onFinish();
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
