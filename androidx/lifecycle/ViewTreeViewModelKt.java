package androidx.lifecycle;

import android.view.View;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
/* compiled from: ViewTreeViewModel.kt */
/* loaded from: classes.dex */
public final class ViewTreeViewModelKt {
    @Nullable
    public static final ViewModelStoreOwner findViewTreeViewModelStoreOwner(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return ViewTreeViewModelStoreOwner.get(view);
    }
}
