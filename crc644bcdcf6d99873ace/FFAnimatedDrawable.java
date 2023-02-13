package crc644bcdcf6d99873ace;

import android.content.res.Resources;
import android.graphics.Bitmap;
import java.io.InputStream;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class FFAnimatedDrawable extends SelfDisposingBitmapDrawable implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("FFImageLoading.Drawables.FFAnimatedDrawable, FFImageLoading.Platform", FFAnimatedDrawable.class, "");
    }

    public FFAnimatedDrawable() {
        if (getClass() == FFAnimatedDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFAnimatedDrawable, FFImageLoading.Platform", "", this, new Object[0]);
        }
    }

    public FFAnimatedDrawable(Resources resources) {
        super(resources);
        if (getClass() == FFAnimatedDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFAnimatedDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android", this, new Object[]{resources});
        }
    }

    public FFAnimatedDrawable(Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
        if (getClass() == FFAnimatedDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFAnimatedDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android:Android.Graphics.Bitmap, Mono.Android", this, new Object[]{resources, bitmap});
        }
    }

    public FFAnimatedDrawable(Resources resources, InputStream inputStream) {
        super(resources, inputStream);
        if (getClass() == FFAnimatedDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFAnimatedDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android:System.IO.Stream, mscorlib", this, new Object[]{resources, inputStream});
        }
    }

    public FFAnimatedDrawable(Resources resources, String str) {
        super(resources, str);
        if (getClass() == FFAnimatedDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFAnimatedDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android:System.String, mscorlib", this, new Object[]{resources, str});
        }
    }

    public FFAnimatedDrawable(Bitmap bitmap) {
        super(bitmap);
        if (getClass() == FFAnimatedDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFAnimatedDrawable, FFImageLoading.Platform", "Android.Graphics.Bitmap, Mono.Android", this, new Object[]{bitmap});
        }
    }

    public FFAnimatedDrawable(InputStream inputStream) {
        super(inputStream);
        if (getClass() == FFAnimatedDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFAnimatedDrawable, FFImageLoading.Platform", "System.IO.Stream, mscorlib", this, new Object[]{inputStream});
        }
    }

    public FFAnimatedDrawable(String str) {
        super(str);
        if (getClass() == FFAnimatedDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFAnimatedDrawable, FFImageLoading.Platform", "System.String, mscorlib", this, new Object[]{str});
        }
    }

    @Override // crc644bcdcf6d99873ace.SelfDisposingBitmapDrawable, mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // crc644bcdcf6d99873ace.SelfDisposingBitmapDrawable, mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
