package crc641643347243aa2150;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import crc64720bb2db43a66fe9.NavigationPageRenderer;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class NavigationPageFontRenderer extends NavigationPageRenderer implements IGCUserPeer {
    public static final String __md_methods = "n_onViewAdded:(Landroid/view/View;)V:GetOnViewAdded_Landroid_view_View_Handler\n";
    private ArrayList refList;

    private native void n_onViewAdded(View view);

    static {
        Runtime.register("iSmart.Droid.CustomRenderers.NavigationPageFontRenderer, iSmart.Android", NavigationPageFontRenderer.class, "n_onViewAdded:(Landroid/view/View;)V:GetOnViewAdded_Landroid_view_View_Handler\n");
    }

    public NavigationPageFontRenderer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == NavigationPageFontRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.NavigationPageFontRenderer, iSmart.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public NavigationPageFontRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == NavigationPageFontRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.NavigationPageFontRenderer, iSmart.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public NavigationPageFontRenderer(Context context) {
        super(context);
        if (getClass() == NavigationPageFontRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.NavigationPageFontRenderer, iSmart.Android", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        n_onViewAdded(view);
    }

    @Override // crc64720bb2db43a66fe9.NavigationPageRenderer, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc64720bb2db43a66fe9.NavigationPageRenderer, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
