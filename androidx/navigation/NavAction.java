package androidx.navigation;

import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
/* loaded from: classes.dex */
public final class NavAction {
    private Bundle mDefaultArguments;
    @IdRes
    private final int mDestinationId;
    private NavOptions mNavOptions;

    public NavAction(@IdRes int i) {
        this(i, null);
    }

    public NavAction(@IdRes int i, @Nullable NavOptions navOptions) {
        this(i, navOptions, null);
    }

    public NavAction(@IdRes int i, @Nullable NavOptions navOptions, @Nullable Bundle bundle) {
        this.mDestinationId = i;
        this.mNavOptions = navOptions;
        this.mDefaultArguments = bundle;
    }

    public int getDestinationId() {
        return this.mDestinationId;
    }

    public void setNavOptions(@Nullable NavOptions navOptions) {
        this.mNavOptions = navOptions;
    }

    @Nullable
    public NavOptions getNavOptions() {
        return this.mNavOptions;
    }

    @Nullable
    public Bundle getDefaultArguments() {
        return this.mDefaultArguments;
    }

    public void setDefaultArguments(@Nullable Bundle bundle) {
        this.mDefaultArguments = bundle;
    }
}
