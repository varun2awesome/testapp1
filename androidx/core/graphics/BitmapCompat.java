package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
/* loaded from: classes.dex */
public final class BitmapCompat {
    @VisibleForTesting
    public static int sizeAtStep(int i, int i2, int i3, int i4) {
        return i3 == 0 ? i2 : i3 > 0 ? i * (1 << (i4 - i3)) : i2 << ((-i3) - 1);
    }

    public static boolean hasMipMap(@NonNull Bitmap bitmap) {
        return Api17Impl.hasMipMap(bitmap);
    }

    public static void setHasMipMap(@NonNull Bitmap bitmap, boolean z) {
        Api17Impl.setHasMipMap(bitmap, z);
    }

    public static int getAllocationByteCount(@NonNull Bitmap bitmap) {
        return Api19Impl.getAllocationByteCount(bitmap);
    }

    /* JADX WARN: Code restructure failed: missing block: B:113:0x01a6, code lost:
        if (androidx.core.graphics.BitmapCompat.Api27Impl.isAlreadyF16AndLinear(r11) == false) goto L96;
     */
    @NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap createScaledBitmap(@NonNull Bitmap bitmap, int i, int i2, @Nullable Rect rect, boolean z) {
        Paint paint;
        double floor;
        double floor2;
        Paint paint2;
        int i3;
        Rect rect2;
        Bitmap bitmap2;
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("dstW and dstH must be > 0!");
        }
        if (rect != null && (rect.isEmpty() || rect.left < 0 || rect.right > bitmap.getWidth() || rect.top < 0 || rect.bottom > bitmap.getHeight())) {
            throw new IllegalArgumentException("srcRect must be contained by srcBm!");
        }
        int i4 = Build.VERSION.SDK_INT;
        Bitmap copyBitmapIfHardware = i4 >= 27 ? Api27Impl.copyBitmapIfHardware(bitmap) : bitmap;
        int width = rect != null ? rect.width() : bitmap.getWidth();
        int height = rect != null ? rect.height() : bitmap.getHeight();
        float f = i / width;
        float f2 = i2 / height;
        int i5 = rect != null ? rect.left : 0;
        int i6 = rect != null ? rect.top : 0;
        if (i5 == 0 && i6 == 0 && i == bitmap.getWidth() && i2 == bitmap.getHeight()) {
            return (bitmap.isMutable() && bitmap == copyBitmapIfHardware) ? bitmap.copy(bitmap.getConfig(), true) : copyBitmapIfHardware;
        }
        Paint paint3 = new Paint(1);
        paint3.setFilterBitmap(true);
        if (i4 >= 29) {
            Api29Impl.setPaintBlendMode(paint3);
        } else {
            paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        }
        if (width == i && height == i2) {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, copyBitmapIfHardware.getConfig());
            new Canvas(createBitmap).drawBitmap(copyBitmapIfHardware, -i5, -i6, paint3);
            return createBitmap;
        }
        double log = Math.log(2.0d);
        if (f > 1.0f) {
            paint = paint3;
            floor = Math.ceil(Math.log(f) / log);
        } else {
            paint = paint3;
            floor = Math.floor(Math.log(f) / log);
        }
        int i7 = (int) floor;
        if (f2 > 1.0f) {
            floor2 = Math.ceil(Math.log(f2) / log);
        } else {
            floor2 = Math.floor(Math.log(f2) / log);
        }
        int i8 = (int) floor2;
        Bitmap bitmap3 = null;
        if (!z || i4 < 27 || Api27Impl.isAlreadyF16AndLinear(bitmap)) {
            paint2 = paint;
            i3 = 0;
        } else {
            Bitmap createBitmapWithSourceColorspace = Api27Impl.createBitmapWithSourceColorspace(i7 > 0 ? sizeAtStep(width, i, 1, i7) : width, i8 > 0 ? sizeAtStep(height, i2, 1, i8) : height, bitmap, true);
            paint2 = paint;
            new Canvas(createBitmapWithSourceColorspace).drawBitmap(copyBitmapIfHardware, -i5, -i6, paint2);
            i6 = 0;
            i5 = 0;
            i3 = 1;
            bitmap3 = copyBitmapIfHardware;
            copyBitmapIfHardware = createBitmapWithSourceColorspace;
        }
        Rect rect3 = new Rect(i5, i6, width, height);
        Rect rect4 = new Rect();
        int i9 = i7;
        int i10 = i8;
        while (true) {
            if (i9 == 0 && i10 == 0) {
                break;
            }
            if (i9 < 0) {
                i9++;
            } else if (i9 > 0) {
                i9--;
            }
            if (i10 < 0) {
                i10++;
            } else if (i10 > 0) {
                i10--;
            }
            int i11 = i10;
            Paint paint4 = paint2;
            Rect rect5 = rect3;
            rect4.set(0, 0, sizeAtStep(width, i, i9, i7), sizeAtStep(height, i2, i11, i8));
            boolean z2 = i9 == 0 && i11 == 0;
            boolean z3 = bitmap3 != null && bitmap3.getWidth() == i && bitmap3.getHeight() == i2;
            if (bitmap3 == null || bitmap3 == bitmap) {
                rect2 = rect4;
            } else {
                if (z) {
                    rect2 = rect4;
                    if (Build.VERSION.SDK_INT >= 27) {
                    }
                } else {
                    rect2 = rect4;
                }
                if (!z2 || (z3 && i3 == 0)) {
                    bitmap2 = bitmap3;
                    Rect rect6 = rect2;
                    new Canvas(bitmap2).drawBitmap(copyBitmapIfHardware, rect5, rect6, paint4);
                    rect5.set(rect6);
                    i10 = i11;
                    Bitmap bitmap4 = copyBitmapIfHardware;
                    copyBitmapIfHardware = bitmap2;
                    rect4 = rect6;
                    rect3 = rect5;
                    paint2 = paint4;
                    bitmap3 = bitmap4;
                }
            }
            if (bitmap3 != bitmap && bitmap3 != null) {
                bitmap3.recycle();
            }
            int sizeAtStep = sizeAtStep(width, i, i9 > 0 ? i3 : i9, i7);
            int sizeAtStep2 = sizeAtStep(height, i2, i11 > 0 ? i3 : i11, i8);
            if (Build.VERSION.SDK_INT >= 27) {
                bitmap2 = Api27Impl.createBitmapWithSourceColorspace(sizeAtStep, sizeAtStep2, bitmap, z && !z2);
            } else {
                bitmap2 = Bitmap.createBitmap(sizeAtStep, sizeAtStep2, copyBitmapIfHardware.getConfig());
            }
            Rect rect62 = rect2;
            new Canvas(bitmap2).drawBitmap(copyBitmapIfHardware, rect5, rect62, paint4);
            rect5.set(rect62);
            i10 = i11;
            Bitmap bitmap42 = copyBitmapIfHardware;
            copyBitmapIfHardware = bitmap2;
            rect4 = rect62;
            rect3 = rect5;
            paint2 = paint4;
            bitmap3 = bitmap42;
        }
        if (bitmap3 != bitmap && bitmap3 != null) {
            bitmap3.recycle();
        }
        return copyBitmapIfHardware;
    }

    private BitmapCompat() {
    }

    @RequiresApi(17)
    /* loaded from: classes.dex */
    static class Api17Impl {
        private Api17Impl() {
        }

        @DoNotInline
        static boolean hasMipMap(Bitmap bitmap) {
            return bitmap.hasMipMap();
        }

        @DoNotInline
        static void setHasMipMap(Bitmap bitmap, boolean z) {
            bitmap.setHasMipMap(z);
        }
    }

    @RequiresApi(19)
    /* loaded from: classes.dex */
    static class Api19Impl {
        private Api19Impl() {
        }

        @DoNotInline
        static int getAllocationByteCount(Bitmap bitmap) {
            return bitmap.getAllocationByteCount();
        }
    }

    @RequiresApi(27)
    /* loaded from: classes.dex */
    static class Api27Impl {
        private Api27Impl() {
        }

        @DoNotInline
        static Bitmap createBitmapWithSourceColorspace(int i, int i2, Bitmap bitmap, boolean z) {
            Bitmap.Config config = bitmap.getConfig();
            ColorSpace colorSpace = bitmap.getColorSpace();
            ColorSpace colorSpace2 = ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
            if (z && !bitmap.getColorSpace().equals(colorSpace2)) {
                config = Bitmap.Config.RGBA_F16;
                colorSpace = colorSpace2;
            } else if (bitmap.getConfig() == Bitmap.Config.HARDWARE) {
                config = Bitmap.Config.ARGB_8888;
                if (Build.VERSION.SDK_INT >= 31) {
                    config = Api31Impl.getHardwareBitmapConfig(bitmap);
                }
            }
            return Bitmap.createBitmap(i, i2, config, bitmap.hasAlpha(), colorSpace);
        }

        @DoNotInline
        static boolean isAlreadyF16AndLinear(Bitmap bitmap) {
            return bitmap.getConfig() == Bitmap.Config.RGBA_F16 && bitmap.getColorSpace().equals(ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB));
        }

        @DoNotInline
        static Bitmap copyBitmapIfHardware(Bitmap bitmap) {
            if (bitmap.getConfig() == Bitmap.Config.HARDWARE) {
                Bitmap.Config config = Bitmap.Config.ARGB_8888;
                if (Build.VERSION.SDK_INT >= 31) {
                    config = Api31Impl.getHardwareBitmapConfig(bitmap);
                }
                return bitmap.copy(config, true);
            }
            return bitmap;
        }
    }

    @RequiresApi(29)
    /* loaded from: classes.dex */
    static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static void setPaintBlendMode(Paint paint) {
            paint.setBlendMode(BlendMode.SRC);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(31)
    /* loaded from: classes.dex */
    public static class Api31Impl {
        private Api31Impl() {
        }

        @DoNotInline
        static Bitmap.Config getHardwareBitmapConfig(Bitmap bitmap) {
            if (bitmap.getHardwareBuffer().getFormat() == 22) {
                return Bitmap.Config.RGBA_F16;
            }
            return Bitmap.Config.ARGB_8888;
        }
    }
}
