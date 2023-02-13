package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
/* compiled from: FragmentReuseViolation.kt */
/* loaded from: classes.dex */
public final class FragmentReuseViolation extends Violation {
    @NotNull
    private final String previousFragmentId;

    @NotNull
    public final String getPreviousFragmentId() {
        return this.previousFragmentId;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FragmentReuseViolation(@NotNull Fragment fragment, @NotNull String previousFragmentId) {
        super(fragment, "Attempting to reuse fragment " + fragment + " with previous ID " + previousFragmentId);
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(previousFragmentId, "previousFragmentId");
        this.previousFragmentId = previousFragmentId;
    }
}
