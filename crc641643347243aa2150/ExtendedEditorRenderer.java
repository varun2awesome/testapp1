package crc641643347243aa2150;

import android.content.Context;
import android.util.AttributeSet;
import crc643f46942d9dd1fff9.EditorRenderer;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class ExtendedEditorRenderer extends EditorRenderer implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("iSmart.Droid.CustomRenderers.ExtendedEditorRenderer, iSmart.Android", ExtendedEditorRenderer.class, "");
    }

    public ExtendedEditorRenderer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == ExtendedEditorRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.ExtendedEditorRenderer, iSmart.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public ExtendedEditorRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == ExtendedEditorRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.ExtendedEditorRenderer, iSmart.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public ExtendedEditorRenderer(Context context) {
        super(context);
        if (getClass() == ExtendedEditorRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.ExtendedEditorRenderer, iSmart.Android", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    @Override // crc643f46942d9dd1fff9.EditorRenderer, crc643f46942d9dd1fff9.EditorRendererBase_1, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc643f46942d9dd1fff9.EditorRenderer, crc643f46942d9dd1fff9.EditorRendererBase_1, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
