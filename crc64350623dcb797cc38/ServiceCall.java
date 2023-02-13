package crc64350623dcb797cc38;

import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class ServiceCall implements IGCUserPeer, com.microsoft.appcenter.http.ServiceCall {
    public static final String __md_methods = "n_cancel:()V:GetCancelHandler:Com.Microsoft.Appcenter.Http.IServiceCallInvoker, Microsoft.AppCenter.Android.Bindings\n";
    private ArrayList refList;

    private native void n_cancel();

    static {
        Runtime.register("Microsoft.AppCenter.ServiceCall, Microsoft.AppCenter", ServiceCall.class, __md_methods);
    }

    public ServiceCall() {
        if (getClass() == ServiceCall.class) {
            TypeManager.Activate("Microsoft.AppCenter.ServiceCall, Microsoft.AppCenter", "", this, new Object[0]);
        }
    }

    @Override // com.microsoft.appcenter.http.ServiceCall
    public void cancel() {
        n_cancel();
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
