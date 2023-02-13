package crc642038c4227d5ee6d7;

import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class ObjectWrapper_1 implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("UltimateXF.Droid.ObjectWrapper`1, UltimateXF.Droid", ObjectWrapper_1.class, "");
    }

    public ObjectWrapper_1() {
        if (getClass() == ObjectWrapper_1.class) {
            TypeManager.Activate("UltimateXF.Droid.ObjectWrapper`1, UltimateXF.Droid", "", this, new Object[0]);
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
