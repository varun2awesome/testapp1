package crc641a0511e99377ff3e;

import android.content.Context;
import android.util.AttributeSet;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class SupportPieRadarChartBaseExtendedRenderer_2 extends SupportChartExtendedRenderer_2 implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("UltimateXF.Droid.Renderers.Extendeds.SupportPieRadarChartBaseExtendedRenderer`2, UltimateXF.Droid", SupportPieRadarChartBaseExtendedRenderer_2.class, "");
    }

    public SupportPieRadarChartBaseExtendedRenderer_2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == SupportPieRadarChartBaseExtendedRenderer_2.class) {
            TypeManager.Activate("UltimateXF.Droid.Renderers.Extendeds.SupportPieRadarChartBaseExtendedRenderer`2, UltimateXF.Droid", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public SupportPieRadarChartBaseExtendedRenderer_2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == SupportPieRadarChartBaseExtendedRenderer_2.class) {
            TypeManager.Activate("UltimateXF.Droid.Renderers.Extendeds.SupportPieRadarChartBaseExtendedRenderer`2, UltimateXF.Droid", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public SupportPieRadarChartBaseExtendedRenderer_2(Context context) {
        super(context);
        if (getClass() == SupportPieRadarChartBaseExtendedRenderer_2.class) {
            TypeManager.Activate("UltimateXF.Droid.Renderers.Extendeds.SupportPieRadarChartBaseExtendedRenderer`2, UltimateXF.Droid", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    @Override // crc641a0511e99377ff3e.SupportChartExtendedRenderer_2, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc641a0511e99377ff3e.SupportChartExtendedRenderer_2, crc643f46942d9dd1fff9.ViewRenderer_2, crc643f46942d9dd1fff9.VisualElementRenderer_1, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
