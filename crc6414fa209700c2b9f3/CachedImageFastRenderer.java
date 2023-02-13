package crc6414fa209700c2b9f3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class CachedImageFastRenderer extends CachedImageView implements IGCUserPeer {
    public static final String __md_methods = "n_onTouchEvent:(Landroid/view/MotionEvent;)Z:GetOnTouchEvent_Landroid_view_MotionEvent_Handler\nn_onLayout:(ZIIII)V:GetOnLayout_ZIIIIHandler\n";
    private ArrayList refList;

    private native void n_onLayout(boolean z, int i, int i2, int i3, int i4);

    private native boolean n_onTouchEvent(MotionEvent motionEvent);

    static {
        Runtime.register("FFImageLoading.Forms.Platform.CachedImageFastRenderer, FFImageLoading.Forms.Platform", CachedImageFastRenderer.class, __md_methods);
    }

    public CachedImageFastRenderer(Context context) {
        super(context);
        if (getClass() == CachedImageFastRenderer.class) {
            TypeManager.Activate("FFImageLoading.Forms.Platform.CachedImageFastRenderer, FFImageLoading.Forms.Platform", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    public CachedImageFastRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == CachedImageFastRenderer.class) {
            TypeManager.Activate("FFImageLoading.Forms.Platform.CachedImageFastRenderer, FFImageLoading.Forms.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public CachedImageFastRenderer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == CachedImageFastRenderer.class) {
            TypeManager.Activate("FFImageLoading.Forms.Platform.CachedImageFastRenderer, FFImageLoading.Forms.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public CachedImageFastRenderer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (getClass() == CachedImageFastRenderer.class) {
            TypeManager.Activate("FFImageLoading.Forms.Platform.CachedImageFastRenderer, FFImageLoading.Forms.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i), Integer.valueOf(i2)});
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return n_onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        n_onLayout(z, i, i2, i3, i4);
    }

    @Override // crc6414fa209700c2b9f3.CachedImageView, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc6414fa209700c2b9f3.CachedImageView, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
