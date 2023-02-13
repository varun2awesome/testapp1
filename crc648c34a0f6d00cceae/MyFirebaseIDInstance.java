package crc648c34a0f6d00cceae;

import com.google.firebase.iid.FirebaseInstanceIdService;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class MyFirebaseIDInstance extends FirebaseInstanceIdService implements IGCUserPeer {
    public static final String __md_methods = "n_onTokenRefresh:()V:GetOnTokenRefreshHandler\n";
    private ArrayList refList;

    private native void n_onTokenRefresh();

    static {
        Runtime.register("iSmart.Droid.MyFirebaseIDInstance, iSmart.Android", MyFirebaseIDInstance.class, __md_methods);
    }

    public MyFirebaseIDInstance() {
        if (getClass() == MyFirebaseIDInstance.class) {
            TypeManager.Activate("iSmart.Droid.MyFirebaseIDInstance, iSmart.Android", "", this, new Object[0]);
        }
    }

    @Override // com.google.firebase.iid.FirebaseInstanceIdService
    public void onTokenRefresh() {
        n_onTokenRefresh();
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
