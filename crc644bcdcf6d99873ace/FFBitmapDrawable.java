package crc644bcdcf6d99873ace;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import java.io.InputStream;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class FFBitmapDrawable extends SelfDisposingBitmapDrawable implements IGCUserPeer {
    public static final String __md_methods = "n_onBoundsChange:(Landroid/graphics/Rect;)V:GetOnBoundsChange_Landroid_graphics_Rect_Handler\nn_draw:(Landroid/graphics/Canvas;)V:GetDraw_Landroid_graphics_Canvas_Handler\nn_setAlpha:(I)V:GetSetAlpha_IHandler\nn_setColorFilter:(ILandroid/graphics/PorterDuff$Mode;)V:GetSetColorFilter_ILandroid_graphics_PorterDuff_Mode_Handler\n";
    private ArrayList refList;

    private native void n_draw(Canvas canvas);

    private native void n_onBoundsChange(Rect rect);

    private native void n_setAlpha(int i);

    private native void n_setColorFilter(int i, PorterDuff.Mode mode);

    static {
        Runtime.register("FFImageLoading.Drawables.FFBitmapDrawable, FFImageLoading.Platform", FFBitmapDrawable.class, __md_methods);
    }

    public FFBitmapDrawable() {
        if (getClass() == FFBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFBitmapDrawable, FFImageLoading.Platform", "", this, new Object[0]);
        }
    }

    public FFBitmapDrawable(Resources resources) {
        super(resources);
        if (getClass() == FFBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFBitmapDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android", this, new Object[]{resources});
        }
    }

    public FFBitmapDrawable(Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
        if (getClass() == FFBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFBitmapDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android:Android.Graphics.Bitmap, Mono.Android", this, new Object[]{resources, bitmap});
        }
    }

    public FFBitmapDrawable(Resources resources, InputStream inputStream) {
        super(resources, inputStream);
        if (getClass() == FFBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFBitmapDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android:System.IO.Stream, mscorlib", this, new Object[]{resources, inputStream});
        }
    }

    public FFBitmapDrawable(Resources resources, String str) {
        super(resources, str);
        if (getClass() == FFBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFBitmapDrawable, FFImageLoading.Platform", "Android.Content.Res.Resources, Mono.Android:System.String, mscorlib", this, new Object[]{resources, str});
        }
    }

    public FFBitmapDrawable(Bitmap bitmap) {
        super(bitmap);
        if (getClass() == FFBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFBitmapDrawable, FFImageLoading.Platform", "Android.Graphics.Bitmap, Mono.Android", this, new Object[]{bitmap});
        }
    }

    public FFBitmapDrawable(InputStream inputStream) {
        super(inputStream);
        if (getClass() == FFBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFBitmapDrawable, FFImageLoading.Platform", "System.IO.Stream, mscorlib", this, new Object[]{inputStream});
        }
    }

    public FFBitmapDrawable(String str) {
        super(str);
        if (getClass() == FFBitmapDrawable.class) {
            TypeManager.Activate("FFImageLoading.Drawables.FFBitmapDrawable, FFImageLoading.Platform", "System.String, mscorlib", this, new Object[]{str});
        }
    }

    @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        n_onBoundsChange(rect);
    }

    @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        n_draw(canvas);
    }

    @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        n_setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(int i, PorterDuff.Mode mode) {
        n_setColorFilter(i, mode);
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
