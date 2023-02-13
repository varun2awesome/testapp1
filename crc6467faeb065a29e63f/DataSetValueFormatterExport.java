package crc6467faeb065a29e63f;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class DataSetValueFormatterExport implements IGCUserPeer, IValueFormatter {
    public static final String __md_methods = "n_getFormattedValue:(FLcom/github/mikephil/charting/data/Entry;ILcom/github/mikephil/charting/utils/ViewPortHandler;)Ljava/lang/String;:GetGetFormattedValue_FLcom_github_mikephil_charting_data_Entry_ILcom_github_mikephil_charting_utils_ViewPortHandler_Handler:MikePhil.Charting.Formatter.IValueFormatterInvoker, MPAndroidChart\n";
    private ArrayList refList;

    private native String n_getFormattedValue(float f, Entry entry, int i, ViewPortHandler viewPortHandler);

    static {
        Runtime.register("UltimateXF.Droid.Renderers.Exporters.DataSetValueFormatterExport, UltimateXF.Droid", DataSetValueFormatterExport.class, __md_methods);
    }

    public DataSetValueFormatterExport() {
        if (getClass() == DataSetValueFormatterExport.class) {
            TypeManager.Activate("UltimateXF.Droid.Renderers.Exporters.DataSetValueFormatterExport, UltimateXF.Droid", "", this, new Object[0]);
        }
    }

    @Override // com.github.mikephil.charting.formatter.IValueFormatter
    public String getFormattedValue(float f, Entry entry, int i, ViewPortHandler viewPortHandler) {
        return n_getFormattedValue(f, entry, i, viewPortHandler);
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
