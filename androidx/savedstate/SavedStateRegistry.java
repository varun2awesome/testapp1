package androidx.savedstate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.Recreator;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
/* compiled from: SavedStateRegistry.kt */
@SuppressLint({"RestrictedApi"})
/* loaded from: classes.dex */
public final class SavedStateRegistry {
    @NotNull
    private static final Companion Companion = new Companion(null);
    @Deprecated
    @NotNull
    private static final String SAVED_COMPONENTS_KEY = "androidx.lifecycle.BundlableSavedStateRegistry.key";
    private boolean attached;
    @NotNull
    private final SafeIterableMap<String, SavedStateProvider> components = new SafeIterableMap<>();
    private boolean isAllowingSavingState = true;
    private boolean isRestored;
    @Nullable
    private Recreator.SavedStateProvider recreatorProvider;
    @Nullable
    private Bundle restoredState;

    /* compiled from: SavedStateRegistry.kt */
    /* loaded from: classes.dex */
    public interface AutoRecreated {
        void onRecreated(@NotNull SavedStateRegistryOwner savedStateRegistryOwner);
    }

    /* compiled from: SavedStateRegistry.kt */
    /* loaded from: classes.dex */
    public interface SavedStateProvider {
        @NotNull
        Bundle saveState();
    }

    @MainThread
    public final boolean isRestored() {
        return this.isRestored;
    }

    public final boolean isAllowingSavingState$savedstate_release() {
        return this.isAllowingSavingState;
    }

    public final void setAllowingSavingState$savedstate_release(boolean z) {
        this.isAllowingSavingState = z;
    }

    @MainThread
    @Nullable
    public final Bundle consumeRestoredStateForKey(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (!this.isRestored) {
            throw new IllegalStateException("You can consumeRestoredStateForKey only after super.onCreate of corresponding component".toString());
        }
        Bundle bundle = this.restoredState;
        if (bundle != null) {
            Bundle bundle2 = bundle != null ? bundle.getBundle(key) : null;
            Bundle bundle3 = this.restoredState;
            if (bundle3 != null) {
                bundle3.remove(key);
            }
            Bundle bundle4 = this.restoredState;
            boolean z = false;
            if (bundle4 != null && !bundle4.isEmpty()) {
                z = true;
            }
            if (!z) {
                this.restoredState = null;
            }
            return bundle2;
        }
        return null;
    }

    @MainThread
    public final void registerSavedStateProvider(@NotNull String key, @NotNull SavedStateProvider provider) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(provider, "provider");
        if (!(this.components.putIfAbsent(key, provider) == null)) {
            throw new IllegalArgumentException("SavedStateProvider with the given key is already registered".toString());
        }
    }

    @Nullable
    public final SavedStateProvider getSavedStateProvider(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Iterator<Map.Entry<String, SavedStateProvider>> it = this.components.iterator();
        while (it.hasNext()) {
            Map.Entry<String, SavedStateProvider> components = it.next();
            Intrinsics.checkNotNullExpressionValue(components, "components");
            SavedStateProvider value = components.getValue();
            if (Intrinsics.areEqual(components.getKey(), key)) {
                return value;
            }
        }
        return null;
    }

    @MainThread
    public final void unregisterSavedStateProvider(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.components.remove(key);
    }

    @MainThread
    public final void runOnNextRecreation(@NotNull Class<? extends AutoRecreated> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        if (!this.isAllowingSavingState) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState".toString());
        }
        Recreator.SavedStateProvider savedStateProvider = this.recreatorProvider;
        if (savedStateProvider == null) {
            savedStateProvider = new Recreator.SavedStateProvider(this);
        }
        this.recreatorProvider = savedStateProvider;
        try {
            clazz.getDeclaredConstructor(new Class[0]);
            Recreator.SavedStateProvider savedStateProvider2 = this.recreatorProvider;
            if (savedStateProvider2 != null) {
                String name = clazz.getName();
                Intrinsics.checkNotNullExpressionValue(name, "clazz.name");
                savedStateProvider2.add(name);
            }
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class " + clazz.getSimpleName() + " must have default constructor in order to be automatically recreated", e);
        }
    }

    @MainThread
    public final void performAttach$savedstate_release(@NotNull Lifecycle lifecycle) {
        Intrinsics.checkNotNullParameter(lifecycle, "lifecycle");
        if (!(!this.attached)) {
            throw new IllegalStateException("SavedStateRegistry was already attached.".toString());
        }
        lifecycle.addObserver(new LifecycleEventObserver() { // from class: androidx.savedstate.SavedStateRegistry$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                SavedStateRegistry.m52performAttach$lambda4(SavedStateRegistry.this, lifecycleOwner, event);
            }
        });
        this.attached = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: performAttach$lambda-4  reason: not valid java name */
    public static final void m52performAttach$lambda4(SavedStateRegistry this$0, LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(lifecycleOwner, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(event, "event");
        if (event == Lifecycle.Event.ON_START) {
            this$0.isAllowingSavingState = true;
        } else if (event == Lifecycle.Event.ON_STOP) {
            this$0.isAllowingSavingState = false;
        }
    }

    @MainThread
    public final void performRestore$savedstate_release(@Nullable Bundle bundle) {
        if (!this.attached) {
            throw new IllegalStateException("You must call performAttach() before calling performRestore(Bundle).".toString());
        }
        if (!(!this.isRestored)) {
            throw new IllegalStateException("SavedStateRegistry was already restored.".toString());
        }
        this.restoredState = bundle != null ? bundle.getBundle(SAVED_COMPONENTS_KEY) : null;
        this.isRestored = true;
    }

    @MainThread
    public final void performSave(@NotNull Bundle outBundle) {
        Intrinsics.checkNotNullParameter(outBundle, "outBundle");
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.restoredState;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
        SafeIterableMap<String, SavedStateProvider>.IteratorWithAdditions iteratorWithAdditions = this.components.iteratorWithAdditions();
        Intrinsics.checkNotNullExpressionValue(iteratorWithAdditions, "this.components.iteratorWithAdditions()");
        while (iteratorWithAdditions.hasNext()) {
            Map.Entry next = iteratorWithAdditions.next();
            bundle.putBundle((String) next.getKey(), ((SavedStateProvider) next.getValue()).saveState());
        }
        if (bundle.isEmpty()) {
            return;
        }
        outBundle.putBundle(SAVED_COMPONENTS_KEY, bundle);
    }

    /* compiled from: SavedStateRegistry.kt */
    /* loaded from: classes.dex */
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
