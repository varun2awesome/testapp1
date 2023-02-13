package crc64a25b61d9f8ee364f;

import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class TransitionUtils implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("AndroidX.Transitions.TransitionUtils, Xamarin.AndroidX.Transition", TransitionUtils.class, "");
    }

    public TransitionUtils() {
        if (getClass() == TransitionUtils.class) {
            TypeManager.Activate("AndroidX.Transitions.TransitionUtils, Xamarin.AndroidX.Transition", "", this, new Object[0]);
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
