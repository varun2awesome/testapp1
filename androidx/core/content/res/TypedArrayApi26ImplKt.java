package androidx.core.content.res;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleableRes;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
/* compiled from: TypedArray.kt */
@RequiresApi(26)
/* loaded from: classes.dex */
final class TypedArrayApi26ImplKt {
    @NotNull
    public static final TypedArrayApi26ImplKt INSTANCE = new TypedArrayApi26ImplKt();

    private TypedArrayApi26ImplKt() {
    }

    @JvmStatic
    @DoNotInline
    @NotNull
    public static final Typeface getFont(@NotNull TypedArray typedArray, @StyleableRes int i) {
        Intrinsics.checkNotNullParameter(typedArray, "typedArray");
        Typeface font = typedArray.getFont(i);
        Intrinsics.checkNotNull(font);
        return font;
    }
}
