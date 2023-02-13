package crc641c819c28fcaf2ded;

import android.app.Activity;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class OpenAppService extends Activity implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("iSmart.Droid.DependencyServices.OpenAppService, iSmart.Android", OpenAppService.class, "");
    }

    public OpenAppService() {
        if (getClass() == OpenAppService.class) {
            TypeManager.Activate("iSmart.Droid.DependencyServices.OpenAppService, iSmart.Android", "", this, new Object[0]);
        }
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
