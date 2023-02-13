package crc648c34a0f6d00cceae;

import crc648316b0a9aa8cfd61.BrowserTabActivity;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class MsalActivity extends BrowserTabActivity implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("iSmart.Droid.MsalActivity, iSmart.Android", MsalActivity.class, "");
    }

    public MsalActivity() {
        if (getClass() == MsalActivity.class) {
            TypeManager.Activate("iSmart.Droid.MsalActivity, iSmart.Android", "", this, new Object[0]);
        }
    }

    @Override // crc648316b0a9aa8cfd61.BrowserTabActivity, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc648316b0a9aa8cfd61.BrowserTabActivity, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
