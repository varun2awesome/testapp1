package crc64b76f6e8b2d8c8db1;

import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class LoginAppCompatDialogFragment extends AbstractAppCompatDialogFragment_1 implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("Acr.UserDialogs.Fragments.LoginAppCompatDialogFragment, Acr.UserDialogs", LoginAppCompatDialogFragment.class, "");
    }

    public LoginAppCompatDialogFragment() {
        if (getClass() == LoginAppCompatDialogFragment.class) {
            TypeManager.Activate("Acr.UserDialogs.Fragments.LoginAppCompatDialogFragment, Acr.UserDialogs", "", this, new Object[0]);
        }
    }

    @Override // crc64b76f6e8b2d8c8db1.AbstractAppCompatDialogFragment_1, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc64b76f6e8b2d8c8db1.AbstractAppCompatDialogFragment_1, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
