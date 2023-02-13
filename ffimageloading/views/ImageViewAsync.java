package ffimageloading.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class ImageViewAsync extends ImageView implements IGCUserPeer {
    public static final String __md_methods = "n_onMeasure:(II)V:GetOnMeasure_IIHandler\nn_setFrame:(IIII)Z:GetSetFrame_IIIIHandler\n";
    private ArrayList refList;

    private native void n_onMeasure(int i, int i2);

    private native boolean n_setFrame(int i, int i2, int i3, int i4);

    static {
        Runtime.register("FFImageLoading.Views.ImageViewAsync, FFImageLoading.Platform", ImageViewAsync.class, __md_methods);
    }

    public ImageViewAsync(Context context) {
        super(context);
        if (getClass() == ImageViewAsync.class) {
            TypeManager.Activate("FFImageLoading.Views.ImageViewAsync, FFImageLoading.Platform", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    public ImageViewAsync(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == ImageViewAsync.class) {
            TypeManager.Activate("FFImageLoading.Views.ImageViewAsync, FFImageLoading.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public ImageViewAsync(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == ImageViewAsync.class) {
            TypeManager.Activate("FFImageLoading.Views.ImageViewAsync, FFImageLoading.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public ImageViewAsync(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (getClass() == ImageViewAsync.class) {
            TypeManager.Activate("FFImageLoading.Views.ImageViewAsync, FFImageLoading.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i), Integer.valueOf(i2)});
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int i, int i2) {
        n_onMeasure(i, i2);
    }

    @Override // android.widget.ImageView
    public boolean setFrame(int i, int i2, int i3, int i4) {
        return n_setFrame(i, i2, i3, i4);
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
