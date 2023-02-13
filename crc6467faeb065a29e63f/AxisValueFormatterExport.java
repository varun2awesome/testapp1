package crc6467faeb065a29e63f;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class AxisValueFormatterExport implements IGCUserPeer, IAxisValueFormatter {
    public static final String __md_methods = "n_getFormattedValue:(FLcom/github/mikephil/charting/components/AxisBase;)Ljava/lang/String;:GetGetFormattedValue_FLcom_github_mikephil_charting_components_AxisBase_Handler:MikePhil.Charting.Formatter.IAxisValueFormatterInvoker, MPAndroidChart\n";
    private ArrayList refList;

    private native String n_getFormattedValue(float f, AxisBase axisBase);

    static {
        Runtime.register("UltimateXF.Droid.Renderers.Exporters.AxisValueFormatterExport, UltimateXF.Droid", AxisValueFormatterExport.class, __md_methods);
    }

    public AxisValueFormatterExport() {
        if (getClass() == AxisValueFormatterExport.class) {
            TypeManager.Activate("UltimateXF.Droid.Renderers.Exporters.AxisValueFormatterExport, UltimateXF.Droid", "", this, new Object[0]);
        }
    }

    @Override // com.github.mikephil.charting.formatter.IAxisValueFormatter
    public String getFormattedValue(float f, AxisBase axisBase) {
        return n_getFormattedValue(f, axisBase);
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
