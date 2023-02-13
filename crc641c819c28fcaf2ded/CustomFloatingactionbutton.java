package crc641c819c28fcaf2ded;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class CustomFloatingactionbutton extends FloatingActionButton implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("iSmart.Droid.DependencyServices.CustomFloatingactionbutton, iSmart.Android", CustomFloatingactionbutton.class, "");
    }

    public CustomFloatingactionbutton(Context context) {
        super(context);
        if (getClass() == CustomFloatingactionbutton.class) {
            TypeManager.Activate("iSmart.Droid.DependencyServices.CustomFloatingactionbutton, iSmart.Android", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    public CustomFloatingactionbutton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == CustomFloatingactionbutton.class) {
            TypeManager.Activate("iSmart.Droid.DependencyServices.CustomFloatingactionbutton, iSmart.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public CustomFloatingactionbutton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == CustomFloatingactionbutton.class) {
            TypeManager.Activate("iSmart.Droid.DependencyServices.CustomFloatingactionbutton, iSmart.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
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
