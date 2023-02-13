package ffimageloading.cross;

import android.content.Context;
import android.util.AttributeSet;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class MvxSvgCachedImageView extends MvxCachedImageView implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("FFImageLoading.Cross.MvxSvgCachedImageView, FFImageLoading.Svg.Platform", MvxSvgCachedImageView.class, "");
    }

    public MvxSvgCachedImageView(Context context) {
        super(context);
        if (getClass() == MvxSvgCachedImageView.class) {
            TypeManager.Activate("FFImageLoading.Cross.MvxSvgCachedImageView, FFImageLoading.Svg.Platform", "Android.Content.Context, Mono.Android", this, new Object[]{context});
        }
    }

    public MvxSvgCachedImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getClass() == MvxSvgCachedImageView.class) {
            TypeManager.Activate("FFImageLoading.Cross.MvxSvgCachedImageView, FFImageLoading.Svg.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new Object[]{context, attributeSet});
        }
    }

    public MvxSvgCachedImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (getClass() == MvxSvgCachedImageView.class) {
            TypeManager.Activate("FFImageLoading.Cross.MvxSvgCachedImageView, FFImageLoading.Svg.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i)});
        }
    }

    public MvxSvgCachedImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (getClass() == MvxSvgCachedImageView.class) {
            TypeManager.Activate("FFImageLoading.Cross.MvxSvgCachedImageView, FFImageLoading.Svg.Platform", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib:System.Int32, mscorlib", this, new Object[]{context, attributeSet, Integer.valueOf(i), Integer.valueOf(i2)});
        }
    }

    @Override // ffimageloading.cross.MvxCachedImageView, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // ffimageloading.cross.MvxCachedImageView, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
