package crc6450e07d0e82e86181;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class AwaitableResultCallback_1 implements IGCUserPeer, ResultCallback {
    public static final String __md_methods = "n_onResult:(Lcom/google/android/gms/common/api/Result;)V:GetOnResult_Lcom_google_android_gms_common_api_Result_Handler:Android.Gms.Common.Apis.IResultCallbackInvoker, Xamarin.GooglePlayServices.Basement\n";
    private ArrayList refList;

    private native void n_onResult(Result result);

    static {
        Runtime.register("Android.Gms.Common.Apis.AwaitableResultCallback`1, Xamarin.GooglePlayServices.Basement", AwaitableResultCallback_1.class, "n_onResult:(Lcom/google/android/gms/common/api/Result;)V:GetOnResult_Lcom_google_android_gms_common_api_Result_Handler:Android.Gms.Common.Apis.IResultCallbackInvoker, Xamarin.GooglePlayServices.Basement\n");
    }

    public AwaitableResultCallback_1() {
        if (getClass() == AwaitableResultCallback_1.class) {
            TypeManager.Activate("Android.Gms.Common.Apis.AwaitableResultCallback`1, Xamarin.GooglePlayServices.Basement", "", this, new Object[0]);
        }
    }

    @Override // com.google.android.gms.common.api.ResultCallback
    public void onResult(Result result) {
        n_onResult(result);
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
