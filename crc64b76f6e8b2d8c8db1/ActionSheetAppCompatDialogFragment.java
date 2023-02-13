package crc64b76f6e8b2d8c8db1;

import android.content.DialogInterface;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class ActionSheetAppCompatDialogFragment extends AbstractAppCompatDialogFragment_1 implements IGCUserPeer {
    public static final String __md_methods = "n_onCancel:(Landroid/content/DialogInterface;)V:GetOnCancel_Landroid_content_DialogInterface_Handler\nn_dismiss:()V:GetDismissHandler\n";
    private ArrayList refList;

    private native void n_dismiss();

    private native void n_onCancel(DialogInterface dialogInterface);

    static {
        Runtime.register("Acr.UserDialogs.Fragments.ActionSheetAppCompatDialogFragment, Acr.UserDialogs", ActionSheetAppCompatDialogFragment.class, __md_methods);
    }

    public ActionSheetAppCompatDialogFragment() {
        if (getClass() == ActionSheetAppCompatDialogFragment.class) {
            TypeManager.Activate("Acr.UserDialogs.Fragments.ActionSheetAppCompatDialogFragment, Acr.UserDialogs", "", this, new Object[0]);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        n_onCancel(dialogInterface);
    }

    @Override // androidx.fragment.app.DialogFragment
    public void dismiss() {
        n_dismiss();
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
