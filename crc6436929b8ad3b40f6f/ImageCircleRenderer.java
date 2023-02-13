package crc6436929b8ad3b40f6f;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import crc643f46942d9dd1fff9.ImageRenderer;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class ImageCircleRenderer extends ImageRenderer implements IGCUserPeer {
    public static final String __md_methods = "n_drawChild:(Landroid/graphics/Canvas;Landroid/view/View;J)Z:GetDrawChild_Landroid_graphics_Canvas_Landroid_view_View_JHandler\n";
    private ArrayList refList;

    private native boolean n_drawChild(Canvas canvas, View view, long j);

    static {
        Runtime.register("ImageCircle.Forms.Plugin.Droid.ImageCircleRenderer, ImageCircle.Forms.Plugin", ImageCircleRenderer.class, __md_methods);
    }

    public ImageCircleRenderer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == ImageCircleRenderer.class) {
            TypeManager.Activate("ImageCircle.Forms.Plugin.Droid.ImageCircleRenderer, ImageCircle.Forms.Plugin", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public ImageCircleRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == ImageCircleRenderer.class) {
            TypeManager.Activate("ImageCircle.Forms.Plugin.Droid.ImageCircleRenderer, ImageCircle.Forms.Plugin", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public ImageCircleRenderer(Context context) {
        super(context);
        if (getClass() == ImageCircleRenderer.class) {
            TypeManager.Activate("ImageCircle.Forms.Plugin.Droid.ImageCircleRenderer, ImageCircle.Forms.Plugin", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view, long j) {
        return n_drawChild(canvas, view, j);
    }

    @Override // crc643f46942d9dd1fff9.ImageRenderer, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc643f46942d9dd1fff9.ImageRenderer, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
