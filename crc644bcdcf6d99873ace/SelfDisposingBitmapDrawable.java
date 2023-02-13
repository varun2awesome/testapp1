package crc644bcdcf6d99873ace;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import java.io.InputStream;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class SelfDisposingBitmapDrawable extends BitmapDrawable implements IGCUserPeer {
    public static final String __md_methods = "n_finalize:()V:GetJavaFinalizeHandler\n";
    private ArrayList refList;

    private native void n_finalize();

    static {
        Runtime.register("FFImageLoading.Drawables.SelfDisposingBitmapDrawable, FFImageLoading.Platform", SelfDisposingBitmapDrawable.class, __md_methods);
    }

    public SelfDisposingBitmapDrawable() {
        if (getClass() == SelfDisposingBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.SelfDisposingBitmapDrawable, FFImageLoading.Platform", "", this, new Object[0]);
        }
    }

    public SelfDisposingBitmapDrawable(Resources resources) {
        super(resources);
        if (getClass() == SelfDisposingBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.SelfDisposingBitmapDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android", this, new Object[]{resources});
        }
    }

    public SelfDisposingBitmapDrawable(Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
        if (getClass() == SelfDisposingBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.SelfDisposingBitmapDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android:Android.Graphics.Bitmap, Mono.Android", this, new Object[]{resources, bitmap});
        }
    }

    public SelfDisposingBitmapDrawable(Resources resources, InputStream inputStream) {
        super(resources, inputStream);
        if (getClass() == SelfDisposingBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.SelfDisposingBitmapDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android:System.IO.Stream, mscorlib", this, new Object[]{resources, inputStream});
        }
    }

    public SelfDisposingBitmapDrawable(Resources resources, String str) {
        super(resources, str);
        if (getClass() == SelfDisposingBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.SelfDisposingBitmapDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android:System.String, mscorlib", this, new Object[]{resources, str});
        }
    }

    public SelfDisposingBitmapDrawable(Bitmap bitmap) {
        super(bitmap);
        if (getClass() == SelfDisposingBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.SelfDisposingBitmapDrawable, FFImageLoading.Platform", "Android.Graphics.Bitmap, Mono.Android", this, new Object[]{bitmap});
        }
    }

    public SelfDisposingBitmapDrawable(InputStream inputStream) {
        super(inputStream);
        if (getClass() == SelfDisposingBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.SelfDisposingBitmapDrawable, FFImageLoading.Platform", "System.IO.Stream, mscorlib", this, new Object[]{inputStream});
        }
    }

    public SelfDisposingBitmapDrawable(String str) {
        super(str);
        if (getClass() == SelfDisposingBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.SelfDisposingBitmapDrawable, FFImageLoading.Platform", "System.String, mscorlib", this, new Object[]{str});
        }
    }

    public void finalize() {
        n_finalize();
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
