package crc64bb223c2be3a01e03;

import android.content.Context;
import android.util.AttributeSet;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class SKCanvasViewRenderer extends SKCanvasViewRendererBase_2 implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("SkiaSharp.Views.Forms.SKCanvasViewRenderer, SkiaSharp.Views.Forms", SKCanvasViewRenderer.class, "");
    }

    public SKCanvasViewRenderer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == SKCanvasViewRenderer.class) {
            TypeManager.Activate("SkiaSharp.Views.Forms.SKCanvasViewRenderer, SkiaSharp.Views.Forms", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public SKCanvasViewRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == SKCanvasViewRenderer.class) {
            TypeManager.Activate("SkiaSharp.Views.Forms.SKCanvasViewRenderer, SkiaSharp.Views.Forms", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public SKCanvasViewRenderer(Context context) {
        super(context);
        if (getClass() == SKCanvasViewRenderer.class) {
            TypeManager.Activate("SkiaSharp.Views.Forms.SKCanvasViewRenderer, SkiaSharp.Views.Forms", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    @Override // crc64bb223c2be3a01e03.SKCanvasViewRendererBase_2, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc64bb223c2be3a01e03.SKCanvasViewRendererBase_2, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
