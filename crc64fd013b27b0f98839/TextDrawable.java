package crc64fd013b27b0f98839;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class TextDrawable extends ColorDrawable implements IGCUserPeer {
    public static final String __md_methods = "n_draw:(Landroid/graphics/Canvas;)V:GetDraw_Landroid_graphics_Canvas_Handler\n";
    private ArrayList refList;

    private native void n_draw(Canvas canvas);

    static {
        Runtime.register("XamForms.Controls.Droid.TextDrawable, XamForms.Controls.Calendar.Droid", TextDrawable.class, "n_draw:(Landroid/graphics/Canvas;)V:GetDraw_Landroid_graphics_Canvas_Handler\n");
    }

    public TextDrawable() {
        if (getClass() == TextDrawable.class) {
            TypeManager.Activate("XamForms.Controls.Droid.TextDrawable, XamForms.Controls.Calendar.Droid", "", this, new Object[0]);
        }
    }

    public TextDrawable(int i) {
        super(i);
        if (getClass() == TextDrawable.class) {
            TypeManager.Activate("XamForms.Controls.Droid.TextDrawable, XamForms.Controls.Calendar.Droid", "Android.Graphics.Color, Mono.Android", this, new Object[]{Integer.valueOf(i)});
        }
    }

    @Override // android.graphics.drawable.ColorDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        n_draw(canvas);
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
