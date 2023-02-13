package crc647a155f775cb6802f;

import android.view.GestureDetector;
import android.view.MotionEvent;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class RgGestureDetectorListener extends GestureDetector.SimpleOnGestureListener implements IGCUserPeer {
    public static final String __md_methods = "n_onSingleTapUp:(Landroid/view/MotionEvent;)Z:GetOnSingleTapUp_Landroid_view_MotionEvent_Handler\n";
    private ArrayList refList;

    private native boolean n_onSingleTapUp(MotionEvent motionEvent);

    static {
        Runtime.register("Rg.Plugins.Popup.Droid.Gestures.RgGestureDetectorListener, Rg.Plugins.Popup.Droid", RgGestureDetectorListener.class, __md_methods);
    }

    public RgGestureDetectorListener() {
        if (getClass() == RgGestureDetectorListener.class) {
            TypeManager.Activate("Rg.Plugins.Popup.Droid.Gestures.RgGestureDetectorListener, Rg.Plugins.Popup.Droid", "", this, new Object[0]);
        }
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return n_onSingleTapUp(motionEvent);
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
