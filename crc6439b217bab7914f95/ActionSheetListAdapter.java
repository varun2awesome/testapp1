package crc6439b217bab7914f95;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class ActionSheetListAdapter extends ArrayAdapter implements IGCUserPeer {
    public static final String __md_methods = "n_getView:(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;:GetGetView_ILandroid_view_View_Landroid_view_ViewGroup_Handler\n";
    private ArrayList refList;

    private native View n_getView(int i, View view, ViewGroup viewGroup);

    static {
        Runtime.register("Acr.UserDialogs.Builders.ActionSheetListAdapter, Acr.UserDialogs", ActionSheetListAdapter.class, __md_methods);
    }

    public ActionSheetListAdapter(Context context, int i) {
        super(context, i);
        if (getClass() == ActionSheetListAdapter.class) {
            TypeManager.Activate("Acr.UserDialogs.Builders.ActionSheetListAdapter, Acr.UserDialogs", "Android.Content.Context, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, Integer.valueOf(i)});
        }
    }

    public ActionSheetListAdapter(Context context, int i, int i2) {
        super(context, i, i2);
        if (getClass() == ActionSheetListAdapter.class) {
            TypeManager.Activate("Acr.UserDialogs.Builders.ActionSheetListAdapter, Acr.UserDialogs", "Android.Content.Context, Mono.Android:System.Int32, mscorlib:System.Int32, mscorlib", this, new Object[]{context, Integer.valueOf(i), Integer.valueOf(i2)});
        }
    }

    public ActionSheetListAdapter(Context context, int i, Object[] objArr) {
        super(context, i, objArr);
        if (getClass() == ActionSheetListAdapter.class) {
            TypeManager.Activate("Acr.UserDialogs.Builders.ActionSheetListAdapter, Acr.UserDialogs", "Android.Content.Context, Mono.Android:System.Int32, mscorlib:T[], Mono.Android", this, new Object[]{context, Integer.valueOf(i), objArr});
        }
    }

    public ActionSheetListAdapter(Context context, int i, int i2, Object[] objArr) {
        super(context, i, i2, objArr);
        if (getClass() == ActionSheetListAdapter.class) {
            TypeManager.Activate("Acr.UserDialogs.Builders.ActionSheetListAdapter, Acr.UserDialogs", "Android.Content.Context, Mono.Android:System.Int32, mscorlib:System.Int32, mscorlib:T[], Mono.Android", this, new Object[]{context, Integer.valueOf(i), Integer.valueOf(i2), objArr});
        }
    }

    public ActionSheetListAdapter(Context context, int i, List list) {
        super(context, i, list);
        if (getClass() == ActionSheetListAdapter.class) {
            TypeManager.Activate("Acr.UserDialogs.Builders.ActionSheetListAdapter, Acr.UserDialogs", "Android.Content.Context, Mono.Android:System.Int32, mscorlib:System.Collections.Generic.IList`1<T>, mscorlib", this, new Object[]{context, Integer.valueOf(i), list});
        }
    }

    public ActionSheetListAdapter(Context context, int i, int i2, List list) {
        super(context, i, i2, list);
        if (getClass() == ActionSheetListAdapter.class) {
            TypeManager.Activate("Acr.UserDialogs.Builders.ActionSheetListAdapter, Acr.UserDialogs", "Android.Content.Context, Mono.Android:System.Int32, mscorlib:System.Int32, mscorlib:System.Collections.Generic.IList`1<T>, mscorlib", this, new Object[]{context, Integer.valueOf(i), Integer.valueOf(i2), list});
        }
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return n_getView(i, view, viewGroup);
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
