package androidx.activity;

import android.view.View;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
/* compiled from: ViewTreeOnBackPressedDispatcherOwner.kt */
@JvmName(name = "ViewTreeOnBackPressedDispatcherOwner")
/* loaded from: classes.dex */
public final class ViewTreeOnBackPressedDispatcherOwner {
    @JvmName(name = "set")
    public static final void set(@NotNull View view, @NotNull OnBackPressedDispatcherOwner onBackPressedDispatcherOwner) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(onBackPressedDispatcherOwner, "onBackPressedDispatcherOwner");
        view.setTag(R.id.view_tree_on_back_pressed_dispatcher_owner, onBackPressedDispatcherOwner);
    }

    @JvmName(name = "get")
    @Nullable
    public static final OnBackPressedDispatcherOwner get(@NotNull View view) {
        Sequence generateSequence;
        Sequence mapNotNull;
        Object firstOrNull;
        Intrinsics.checkNotNullParameter(view, "<this>");
        generateSequence = SequencesKt__SequencesKt.generateSequence(view, ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$1.INSTANCE);
        mapNotNull = SequencesKt___SequencesKt.mapNotNull(generateSequence, ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$2.INSTANCE);
        firstOrNull = SequencesKt___SequencesKt.firstOrNull(mapNotNull);
        return (OnBackPressedDispatcherOwner) firstOrNull;
    }
}
