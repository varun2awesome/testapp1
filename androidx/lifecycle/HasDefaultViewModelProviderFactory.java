package androidx.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
/* loaded from: classes.dex */
public interface HasDefaultViewModelProviderFactory {
    @NonNull
    CreationExtras getDefaultViewModelCreationExtras();

    @NonNull
    ViewModelProvider.Factory getDefaultViewModelProviderFactory();

    /* renamed from: androidx.lifecycle.HasDefaultViewModelProviderFactory$-CC  reason: invalid class name */
    /* loaded from: classes.dex */
    public final /* synthetic */ class CC {
    }
}
