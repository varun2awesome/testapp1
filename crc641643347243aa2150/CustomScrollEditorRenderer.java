package crc641643347243aa2150;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import crc643f46942d9dd1fff9.EditorRenderer;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class CustomScrollEditorRenderer extends EditorRenderer implements IGCUserPeer, View.OnLongClickListener {
    public static final String __md_methods = "n_onLongClick:(Landroid/view/View;)Z:GetOnLongClick_Landroid_view_View_Handler:Android.Views.View/IOnLongClickListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n";
    private ArrayList refList;

    private native boolean n_onLongClick(View view);

    static {
        Runtime.register("iSmart.Droid.CustomRenderers.CustomScrollEditorRenderer, iSmart.Android", CustomScrollEditorRenderer.class, "n_onLongClick:(Landroid/view/View;)Z:GetOnLongClick_Landroid_view_View_Handler:Android.Views.View/IOnLongClickListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n");
    }

    public CustomScrollEditorRenderer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == CustomScrollEditorRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.CustomScrollEditorRenderer, iSmart.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public CustomScrollEditorRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == CustomScrollEditorRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.CustomScrollEditorRenderer, iSmart.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public CustomScrollEditorRenderer(Context context) {
        super(context);
        if (getClass() == CustomScrollEditorRenderer.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.CustomScrollEditorRenderer, iSmart.Android", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        return n_onLongClick(view);
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
