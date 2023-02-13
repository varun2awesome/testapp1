package crc64a0e0a82d0db9a07d;

import android.content.Context;
import android.view.OrientationEventListener;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class Listener extends OrientationEventListener implements IGCUserPeer {
    public static final String __md_methods = "n_onOrientationChanged:(I)V:GetOnOrientationChanged_IHandler\n";
    private ArrayList refList;

    private native void n_onOrientationChanged(int i);

    static {
        Runtime.register("Xamarin.Essentials.Listener, Xamarin.Essentials", Listener.class, __md_methods);
    }

    public Listener(Context context) {
        super(context);
        if (getClass() == Listener.class) {
            TypeManager.Activate("Xamarin.Essentials.Listener, Xamarin.Essentials", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    public Listener(Context context, int i) {
        super(context, i);
        if (getClass() == Listener.class) {
            TypeManager.Activate("Xamarin.Essentials.Listener, Xamarin.Essentials", "Android.Content.Context, Mono.Android:Android.Hardware.SensorDelay, Mono.Android", this, new Object[]{context, Integer.valueOf(i)});
        }
    }

    @Override // android.view.OrientationEventListener
    public void onOrientationChanged(int i) {
        n_onOrientationChanged(i);
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
