package crc648c34a0f6d00cceae;

import androidx.multidex.MultiDexApplication;
import java.util.ArrayList;
import mono.MonoPackageManager;
import mono.android.IGCUserPeer;
/* loaded from: classes.dex */
public class MyApp extends MultiDexApplication implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    public MyApp() {
        MonoPackageManager.setContext(this);
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
