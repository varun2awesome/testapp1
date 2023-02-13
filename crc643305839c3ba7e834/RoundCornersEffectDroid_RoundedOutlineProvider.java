package crc643305839c3ba7e834;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class RoundCornersEffectDroid_RoundedOutlineProvider extends ViewOutlineProvider implements IGCUserPeer {
    public static final String __md_methods = "n_getOutline:(Landroid/view/View;Landroid/graphics/Outline;)V:GetGetOutline_Landroid_view_View_Landroid_graphics_Outline_Handler\n";
    private ArrayList refList;

    private native void n_getOutline(View view, Outline outline);

    static {
        Runtime.register("iSmart.Droid.Effects.RoundCornersEffectDroid+RoundedOutlineProvider, iSmart.Android", RoundCornersEffectDroid_RoundedOutlineProvider.class, __md_methods);
    }

    public RoundCornersEffectDroid_RoundedOutlineProvider() {
        if (getClass() == RoundCornersEffectDroid_RoundedOutlineProvider.class) {
            TypeManager.Activate("iSmart.Droid.Effects.RoundCornersEffectDroid+RoundedOutlineProvider, iSmart.Android", "", this, new Object[0]);
        }
    }

    public RoundCornersEffectDroid_RoundedOutlineProvider(float f) {
        if (getClass() == RoundCornersEffectDroid_RoundedOutlineProvider.class) {
            TypeManager.Activate("iSmart.Droid.Effects.RoundCornersEffectDroid+RoundedOutlineProvider, iSmart.Android", "System.Single, mscorlib", this, new Object[]{Float.valueOf(f)});
        }
    }

    @Override // android.view.ViewOutlineProvider
    public void getOutline(View view, Outline outline) {
        n_getOutline(view, outline);
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
