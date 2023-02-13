package crc641643347243aa2150;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import crc643f46942d9dd1fff9.VisualElementRenderer_1;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class GradientColorStackRenderer extends VisualElementRenderer_1 implements IGCUserPeer {
    public static final String __md_methods = "n_dispatchDraw:(Landroid/graphics/Canvas;)V:GetDispatchDraw_Landroid_graphics_Canvas_Handler\n";
    private ArrayList refList;

    private native void n_dispatchDraw(Canvas canvas);

    static {
        Runtime.register("iSmart.Droid.CustomRenderers.GradientColorStackRenderer, iSmart.Android", GradientColorStackRenderer.class, "n_dispatchDraw:(Landroid/graphics/Canvas;)V:GetDispatchDraw_Landroid_graphics_Canvas_Handler\n");
    }

    public GradientColorStackRenderer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == GradientColorStackRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.GradientColorStackRenderer, iSmart.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public GradientColorStackRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == GradientColorStackRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.GradientColorStackRenderer, iSmart.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public GradientColorStackRenderer(Context context) {
        super(context);
        if (getClass() == GradientColorStackRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.GradientColorStackRenderer, iSmart.Android", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        n_dispatchDraw(canvas);
    }

    @Override // crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
