package crc6414fa209700c2b9f3;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class CachedImageView extends ImageView implements IGCUserPeer {
    public static final String __md_methods = "n_invalidate:()V:GetInvalidateHandler\n";
    private ArrayList refList;

    private native void n_invalidate();

    static {
        Runtime.register("FFImageLoading.Forms.Platform.CachedImageView, FFImageLoading.Forms.Platform", CachedImageView.class, "n_invalidate:()V:GetInvalidateHandler\n");
    }

    public CachedImageView(Context context) {
        super(context);
        if (getClass() == CachedImageView.class) {
            TypeManager.Activate("FFImageLoading.Forms.Platform.CachedImageView, FFImageLoading.Forms.Platform", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    public CachedImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == CachedImageView.class) {
            TypeManager.Activate("FFImageLoading.Forms.Platform.CachedImageView, FFImageLoading.Forms.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public CachedImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == CachedImageView.class) {
            TypeManager.Activate("FFImageLoading.Forms.Platform.CachedImageView, FFImageLoading.Forms.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public CachedImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (getClass() == CachedImageView.class) {
            TypeManager.Activate("FFImageLoading.Forms.Platform.CachedImageView, FFImageLoading.Forms.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i), Integer.valueOf(i2)});
        }
    }

    @Override // android.view.View
    public void invalidate() {
        n_invalidate();
    }

    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
