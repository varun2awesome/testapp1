package androidx.lifecycle;

import androidx.annotation.NonNull;
@Deprecated
/* loaded from: classes.dex */
public interface LifecycleRegistryOwner extends LifecycleOwner {
    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    /* bridge */ /* synthetic */ Lifecycle getLifecycle();

    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    LifecycleRegistry getLifecycle();

    /* renamed from: androidx.lifecycle.LifecycleRegistryOwner$-CC  reason: invalid class name */
    /* loaded from: classes.dex */
    public final /* synthetic */ class CC {
    }
}
