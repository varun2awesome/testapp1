package crc6434af9c19aa01b597;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class GoogleApiClientOnConnectionFailedListenerImpl implements IGCUserPeer, GoogleApiClient.OnConnectionFailedListener, OnConnectionFailedListener {
    public static final String __md_methods = "n_onConnectionFailed:(Lcom/google/android/gms/common/ConnectionResult;)V:GetOnConnectionFailed_Lcom_google_android_gms_common_ConnectionResult_Handler:Android.Gms.Common.Api.Internal.IOnConnectionFailedListenerInvoker, Xamarin.GooglePlayServices.Base\n";
    private ArrayList refList;

    private native void n_onConnectionFailed(ConnectionResult connectionResult);

    static {
        Runtime.register("Android.Gms.Common.Apis.GoogleApiClientOnConnectionFailedListenerImpl, Xamarin.GooglePlayServices.Base", GoogleApiClientOnConnectionFailedListenerImpl.class, "n_onConnectionFailed:(Lcom/google/android/gms/common/ConnectionResult;)V:GetOnConnectionFailed_Lcom_google_android_gms_common_ConnectionResult_Handler:Android.Gms.Common.Api.Internal.IOnConnectionFailedListenerInvoker, Xamarin.GooglePlayServices.Base\n");
    }

    public GoogleApiClientOnConnectionFailedListenerImpl() {
        if (getClass() == GoogleApiClientOnConnectionFailedListenerImpl.class) {
            TypeManager.Activate("Android.Gms.Common.Apis.GoogleApiClientOnConnectionFailedListenerImpl, Xamarin.GooglePlayServices.Base", "", this, new Object[0]);
        }
    }

    @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult connectionResult) {
        n_onConnectionFailed(connectionResult);
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
