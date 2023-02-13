package androidx.savedstate;

import androidx.lifecycle.LifecycleOwner;
import org.jetbrains.annotations.NotNull;
/* compiled from: SavedStateRegistryOwner.kt */
/* loaded from: classes.dex */
public interface SavedStateRegistryOwner extends LifecycleOwner {
    @NotNull
    SavedStateRegistry getSavedStateRegistry();
}
