package crc64b76f6e8b2d8c8db1;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public abstract class AbstractAppCompatDialogFragment_1 extends AppCompatDialogFragment implements IGCUserPeer {
    public static final String __md_methods = "n_onSaveInstanceState:(Landroid/os/Bundle;)V:GetOnSaveInstanceState_Landroid_os_Bundle_Handler\nn_onCreateDialog:(Landroid/os/Bundle;)Landroid/app/Dialog;:GetOnCreateDialog_Landroid_os_Bundle_Handler\nn_onDetach:()V:GetOnDetachHandler\n";
    private ArrayList refList;

    private native Dialog n_onCreateDialog(Bundle bundle);

    private native void n_onDetach();

    private native void n_onSaveInstanceState(Bundle bundle);

    static {
        Runtime.register("Acr.UserDialogs.Fragments.AbstractAppCompatDialogFragment`1, Acr.UserDialogs", AbstractAppCompatDialogFragment_1.class, __md_methods);
    }

    public AbstractAppCompatDialogFragment_1() {
        if (getClass() == AbstractAppCompatDialogFragment_1.class) {
            TypeManager.Activate("Acr.UserDialogs.Fragments.AbstractAppCompatDialogFragment`1, Acr.UserDialogs", "", this, new Object[0]);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        n_onSaveInstanceState(bundle);
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        return n_onCreateDialog(bundle);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDetach() {
        n_onDetach();
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
