package androidx.lifecycle.viewmodel;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
/* compiled from: CreationExtras.kt */
/* loaded from: classes.dex */
public abstract class CreationExtras {
    @NotNull
    private final Map<Key<?>, Object> map = new LinkedHashMap();

    /* compiled from: CreationExtras.kt */
    /* loaded from: classes.dex */
    public interface Key<T> {
    }

    @Nullable
    public abstract <T> T get(@NotNull Key<T> key);

    @NotNull
    public final Map<Key<?>, Object> getMap$lifecycle_viewmodel_release() {
        return this.map;
    }

    /* compiled from: CreationExtras.kt */
    /* loaded from: classes.dex */
    public static final class Empty extends CreationExtras {
        @NotNull
        public static final Empty INSTANCE = new Empty();

        @Override // androidx.lifecycle.viewmodel.CreationExtras
        @Nullable
        public <T> T get(@NotNull Key<T> key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return null;
        }

        private Empty() {
        }
    }
}
