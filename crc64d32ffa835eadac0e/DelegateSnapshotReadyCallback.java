package crc64d32ffa835eadac0e;

import android.graphics.Bitmap;
import com.google.android.gms.maps.GoogleMap;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class DelegateSnapshotReadyCallback implements IGCUserPeer, GoogleMap.SnapshotReadyCallback {
    public static final String __md_methods = "n_onSnapshotReady:(Landroid/graphics/Bitmap;)V:GetOnSnapshotReady_Landroid_graphics_Bitmap_Handler:Android.Gms.Maps.GoogleMap/ISnapshotReadyCallbackInvoker, Xamarin.GooglePlayServices.Maps\n";
    private ArrayList refList;

    private native void n_onSnapshotReady(Bitmap bitmap);

    static {
        Runtime.register("Xamarin.Forms.GoogleMaps.Android.Logics.DelegateSnapshotReadyCallback, Xamarin.Forms.GoogleMaps.Android", DelegateSnapshotReadyCallback.class, __md_methods);
    }

    public DelegateSnapshotReadyCallback() {
        if (getClass() == DelegateSnapshotReadyCallback.class) {
            TypeManager.Activate("Xamarin.Forms.GoogleMaps.Android.Logics.DelegateSnapshotReadyCallback, Xamarin.Forms.GoogleMaps.Android", "", this, new Object[0]);
        }
    }

    @Override // com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback
    public void onSnapshotReady(Bitmap bitmap) {
        n_onSnapshotReady(bitmap);
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
