package crc641a0511e99377ff3e;

import android.content.Context;
import android.util.AttributeSet;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class SupportCandleStickChartExtendedRenderer extends SupportBarLineChartBaseExtendedRenderer_2 implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("UltimateXF.Droid.Renderers.Extendeds.SupportCandleStickChartExtendedRenderer, UltimateXF.Droid", SupportCandleStickChartExtendedRenderer.class, "");
    }

    public SupportCandleStickChartExtendedRenderer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == SupportCandleStickChartExtendedRenderer.class) {
            TypeManager.Activate("UltimateXF.Droid.Renderers.Extendeds.SupportCandleStickChartExtendedRenderer, UltimateXF.Droid", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public SupportCandleStickChartExtendedRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == SupportCandleStickChartExtendedRenderer.class) {
            TypeManager.Activate("UltimateXF.Droid.Renderers.Extendeds.SupportCandleStickChartExtendedRenderer, UltimateXF.Droid", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public SupportCandleStickChartExtendedRenderer(Context context) {
        super(context);
        if (getClass() == SupportCandleStickChartExtendedRenderer.class) {
            TypeManager.Activate("UltimateXF.Droid.Renderers.Extendeds.SupportCandleStickChartExtendedRenderer, UltimateXF.Droid", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    @Override // crc641a0511e99377ff3e.SupportBarLineChartBaseExtendedRenderer_2, crc641a0511e99377ff3e.SupportChartExtendedRenderer_2, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc641a0511e99377ff3e.SupportBarLineChartBaseExtendedRenderer_2, crc641a0511e99377ff3e.SupportChartExtendedRenderer_2, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
