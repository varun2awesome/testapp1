package crc64fd013b27b0f98839;

import android.content.Context;
import android.util.AttributeSet;
import crc643f46942d9dd1fff9.ButtonRenderer;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class CalendarButtonRenderer extends ButtonRenderer implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("XamForms.Controls.Droid.CalendarButtonRenderer, XamForms.Controls.Calendar.Droid", CalendarButtonRenderer.class, "");
    }

    public CalendarButtonRenderer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == CalendarButtonRenderer.class) {
            TypeManager.Activate("XamForms.Controls.Droid.CalendarButtonRenderer, XamForms.Controls.Calendar.Droid", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public CalendarButtonRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == CalendarButtonRenderer.class) {
            TypeManager.Activate("XamForms.Controls.Droid.CalendarButtonRenderer, XamForms.Controls.Calendar.Droid", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public CalendarButtonRenderer(Context context) {
        super(context);
        if (getClass() == CalendarButtonRenderer.class) {
            TypeManager.Activate("XamForms.Controls.Droid.CalendarButtonRenderer, XamForms.Controls.Calendar.Droid", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    @Override // crc643f46942d9dd1fff9.ButtonRenderer, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc643f46942d9dd1fff9.ButtonRenderer, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
