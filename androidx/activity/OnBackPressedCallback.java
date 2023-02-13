package androidx.activity;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.core.os.BuildCompat;
import androidx.core.util.Consumer;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public abstract class OnBackPressedCallback {
    private CopyOnWriteArrayList<Cancellable> mCancellables = new CopyOnWriteArrayList<>();
    private boolean mEnabled;
    private Consumer<Boolean> mEnabledConsumer;

    @MainThread
    public abstract void handleOnBackPressed();

    public OnBackPressedCallback(boolean z) {
        this.mEnabled = z;
    }

    @OptIn(markerClass = {BuildCompat.PrereleaseSdkCheck.class})
    @MainThread
    public final void setEnabled(boolean z) {
        this.mEnabled = z;
        Consumer<Boolean> consumer = this.mEnabledConsumer;
        if (consumer != null) {
            consumer.accept(Boolean.valueOf(z));
        }
    }

    @MainThread
    public final boolean isEnabled() {
        return this.mEnabled;
    }

    @MainThread
    public final void remove() {
        Iterator<Cancellable> it = this.mCancellables.iterator();
        while (it.hasNext()) {
            it.next().cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addCancellable(@NonNull Cancellable cancellable) {
        this.mCancellables.add(cancellable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeCancellable(@NonNull Cancellable cancellable) {
        this.mCancellables.remove(cancellable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIsEnabledConsumer(@Nullable Consumer<Boolean> consumer) {
        this.mEnabledConsumer = consumer;
    }
}