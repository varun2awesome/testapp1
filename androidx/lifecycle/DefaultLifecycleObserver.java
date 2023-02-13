package androidx.lifecycle;

import androidx.annotation.NonNull;
/* loaded from: classes.dex */
public interface DefaultLifecycleObserver extends FullLifecycleObserver {

    /* renamed from: androidx.lifecycle.DefaultLifecycleObserver$-CC  reason: invalid class name */
    /* loaded from: classes.dex */
    public final /* synthetic */ class CC {
        public static void $default$onCreate(DefaultLifecycleObserver defaultLifecycleObserver, @NonNull LifecycleOwner lifecycleOwner) {
        }

        public static void $default$onDestroy(DefaultLifecycleObserver defaultLifecycleObserver, @NonNull LifecycleOwner lifecycleOwner) {
        }

        public static void $default$onPause(DefaultLifecycleObserver defaultLifecycleObserver, @NonNull LifecycleOwner lifecycleOwner) {
        }

        public static void $default$onResume(DefaultLifecycleObserver defaultLifecycleObserver, @NonNull LifecycleOwner lifecycleOwner) {
        }

        public static void $default$onStart(DefaultLifecycleObserver defaultLifecycleObserver, @NonNull LifecycleOwner lifecycleOwner) {
        }

        public static void $default$onStop(DefaultLifecycleObserver defaultLifecycleObserver, @NonNull LifecycleOwner lifecycleOwner) {
        }
    }

    @Override // androidx.lifecycle.FullLifecycleObserver
    void onCreate(@NonNull LifecycleOwner lifecycleOwner);

    @Override // androidx.lifecycle.FullLifecycleObserver
    void onDestroy(@NonNull LifecycleOwner lifecycleOwner);

    @Override // androidx.lifecycle.FullLifecycleObserver
    void onPause(@NonNull LifecycleOwner lifecycleOwner);

    @Override // androidx.lifecycle.FullLifecycleObserver
    void onResume(@NonNull LifecycleOwner lifecycleOwner);

    @Override // androidx.lifecycle.FullLifecycleObserver
    void onStart(@NonNull LifecycleOwner lifecycleOwner);

    @Override // androidx.lifecycle.FullLifecycleObserver
    void onStop(@NonNull LifecycleOwner lifecycleOwner);
}
