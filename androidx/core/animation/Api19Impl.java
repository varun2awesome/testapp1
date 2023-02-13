package androidx.core.animation;

import android.animation.Animator;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
/* compiled from: Animator.kt */
@RequiresApi(19)
/* loaded from: classes.dex */
final class Api19Impl {
    @NotNull
    public static final Api19Impl INSTANCE = new Api19Impl();

    private Api19Impl() {
    }

    @JvmStatic
    @DoNotInline
    public static final void addPauseListener(@NotNull Animator animator, @NotNull Animator.AnimatorPauseListener listener) {
        Intrinsics.checkNotNullParameter(animator, "animator");
        Intrinsics.checkNotNullParameter(listener, "listener");
        animator.addPauseListener(listener);
    }
}
