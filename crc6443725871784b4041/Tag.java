package crc6443725871784b4041;

import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class Tag implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("CarouselView.FormsPlugin.Android.Tag, CarouselView.FormsPlugin.Android", Tag.class, "");
    }

    public Tag() {
        if (getClass() == Tag.class) {
            TypeManager.Activate("CarouselView.FormsPlugin.Android.Tag, CarouselView.FormsPlugin.Android", "", this, new Object[0]);
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
