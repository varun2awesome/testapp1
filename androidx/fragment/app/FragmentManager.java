package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.core.app.MultiWindowModeChangedInfo;
import androidx.core.app.OnMultiWindowModeChangedProvider;
import androidx.core.app.OnPictureInPictureModeChangedProvider;
import androidx.core.app.PictureInPictureModeChangedInfo;
import androidx.core.content.OnConfigurationChangedProvider;
import androidx.core.content.OnTrimMemoryProvider;
import androidx.core.util.Consumer;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import com.microsoft.appcenter.Constants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
/* loaded from: classes.dex */
public abstract class FragmentManager implements FragmentResultOwner {
    private static boolean DEBUG = false;
    private static final String EXTRA_CREATED_FILLIN_INTENT = "androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE";
    static final String FRAGMENT_MANAGER_STATE_TAG = "state";
    static final String FRAGMENT_NAME_PREFIX = "fragment_";
    static final String FRAGMENT_STATE_TAG = "state";
    public static final int POP_BACK_STACK_INCLUSIVE = 1;
    static final String RESULT_NAME_PREFIX = "result_";
    static final String SAVED_STATE_TAG = "android:support:fragments";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String TAG = "FragmentManager";
    ArrayList<BackStackRecord> mBackStack;
    private ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    private FragmentContainer mContainer;
    private ArrayList<Fragment> mCreatedMenus;
    private boolean mDestroyed;
    private boolean mExecutingActions;
    private boolean mHavePendingDeferredStart;
    private FragmentHostCallback<?> mHost;
    private boolean mNeedMenuInvalidate;
    private FragmentManagerViewModel mNonConfig;
    private OnBackPressedDispatcher mOnBackPressedDispatcher;
    private Fragment mParent;
    @Nullable
    Fragment mPrimaryNav;
    private ActivityResultLauncher<String[]> mRequestPermissions;
    private ActivityResultLauncher<Intent> mStartActivityForResult;
    private ActivityResultLauncher<IntentSenderRequest> mStartIntentSenderForResult;
    private boolean mStateSaved;
    private boolean mStopped;
    private FragmentStrictMode.Policy mStrictModePolicy;
    private ArrayList<Fragment> mTmpAddedFragments;
    private ArrayList<Boolean> mTmpIsPop;
    private ArrayList<BackStackRecord> mTmpRecords;
    private final ArrayList<OpGenerator> mPendingActions = new ArrayList<>();
    private final FragmentStore mFragmentStore = new FragmentStore();
    private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) { // from class: androidx.fragment.app.FragmentManager.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            FragmentManager.this.handleOnBackPressed();
        }
    };
    private final AtomicInteger mBackStackIndex = new AtomicInteger();
    private final Map<String, BackStackState> mBackStackStates = Collections.synchronizedMap(new HashMap());
    private final Map<String, Bundle> mResults = Collections.synchronizedMap(new HashMap());
    private final Map<String, LifecycleAwareResultListener> mResultListeners = Collections.synchronizedMap(new HashMap());
    private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
    private final CopyOnWriteArrayList<FragmentOnAttachListener> mOnAttachListeners = new CopyOnWriteArrayList<>();
    private final Consumer<Configuration> mOnConfigurationChangedListener = new Consumer() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            FragmentManager.this.lambda$new$0((Configuration) obj);
        }
    };
    private final Consumer<Integer> mOnTrimMemoryListener = new Consumer() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            FragmentManager.this.lambda$new$1((Integer) obj);
        }
    };
    private final Consumer<MultiWindowModeChangedInfo> mOnMultiWindowModeChangedListener = new Consumer() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda2
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            FragmentManager.this.lambda$new$2((MultiWindowModeChangedInfo) obj);
        }
    };
    private final Consumer<PictureInPictureModeChangedInfo> mOnPictureInPictureModeChangedListener = new Consumer() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda3
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            FragmentManager.this.lambda$new$3((PictureInPictureModeChangedInfo) obj);
        }
    };
    private final MenuProvider mMenuProvider = new MenuProvider() { // from class: androidx.fragment.app.FragmentManager.2
        @Override // androidx.core.view.MenuProvider
        public void onPrepareMenu(@NonNull Menu menu) {
            FragmentManager.this.dispatchPrepareOptionsMenu(menu);
        }

        @Override // androidx.core.view.MenuProvider
        public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
            FragmentManager.this.dispatchCreateOptionsMenu(menu, menuInflater);
        }

        @Override // androidx.core.view.MenuProvider
        public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            return FragmentManager.this.dispatchOptionsItemSelected(menuItem);
        }

        @Override // androidx.core.view.MenuProvider
        public void onMenuClosed(@NonNull Menu menu) {
            FragmentManager.this.dispatchOptionsMenuClosed(menu);
        }
    };
    int mCurState = -1;
    private FragmentFactory mFragmentFactory = null;
    private FragmentFactory mHostFragmentFactory = new FragmentFactory() { // from class: androidx.fragment.app.FragmentManager.3
        @Override // androidx.fragment.app.FragmentFactory
        @NonNull
        public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String str) {
            return FragmentManager.this.getHost().instantiate(FragmentManager.this.getHost().getContext(), str, null);
        }
    };
    private SpecialEffectsControllerFactory mSpecialEffectsControllerFactory = null;
    private SpecialEffectsControllerFactory mDefaultSpecialEffectsControllerFactory = new SpecialEffectsControllerFactory() { // from class: androidx.fragment.app.FragmentManager.4
        @Override // androidx.fragment.app.SpecialEffectsControllerFactory
        @NonNull
        public SpecialEffectsController createController(@NonNull ViewGroup viewGroup) {
            return new DefaultSpecialEffectsController(viewGroup);
        }
    };
    ArrayDeque<LaunchedFragmentInfo> mLaunchedFragments = new ArrayDeque<>();
    private Runnable mExecCommit = new Runnable() { // from class: androidx.fragment.app.FragmentManager.5
        @Override // java.lang.Runnable
        public void run() {
            FragmentManager.this.execPendingActions(true);
        }
    };

    /* loaded from: classes.dex */
    public interface BackStackEntry {
        @Nullable
        @Deprecated
        CharSequence getBreadCrumbShortTitle();

        @StringRes
        @Deprecated
        int getBreadCrumbShortTitleRes();

        @Nullable
        @Deprecated
        CharSequence getBreadCrumbTitle();

        @StringRes
        @Deprecated
        int getBreadCrumbTitleRes();

        int getId();

        @Nullable
        String getName();
    }

    /* loaded from: classes.dex */
    public static abstract class FragmentLifecycleCallbacks {
        @Deprecated
        public void onFragmentActivityCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentAttached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Context context) {
        }

        public void onFragmentCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentDestroyed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentDetached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentPaused(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentPreAttached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Context context) {
        }

        public void onFragmentPreCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentResumed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentSaveInstanceState(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Bundle bundle) {
        }

        public void onFragmentStarted(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentStopped(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentViewCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull View view, @Nullable Bundle bundle) {
        }

        public void onFragmentViewDestroyed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }
    }

    /* loaded from: classes.dex */
    public interface OnBackStackChangedListener {
        @MainThread
        void onBackStackChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface OpGenerator {
        boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int reverseTransit(int i) {
        if (i != 4097) {
            if (i != 8194) {
                if (i != 8197) {
                    if (i != 4099) {
                        if (i != 4100) {
                            return 0;
                        }
                        return FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE;
                    }
                    return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
                }
                return FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN;
            }
            return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
        }
        return 8194;
    }

    @Deprecated
    public static void enableDebugLogging(boolean z) {
        DEBUG = z;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static boolean isLoggingEnabled(int i) {
        return DEBUG || Log.isLoggable(TAG, i);
    }

    /* loaded from: classes.dex */
    private static class LifecycleAwareResultListener implements FragmentResultListener {
        private final Lifecycle mLifecycle;
        private final FragmentResultListener mListener;
        private final LifecycleEventObserver mObserver;

        LifecycleAwareResultListener(@NonNull Lifecycle lifecycle, @NonNull FragmentResultListener fragmentResultListener, @NonNull LifecycleEventObserver lifecycleEventObserver) {
            this.mLifecycle = lifecycle;
            this.mListener = fragmentResultListener;
            this.mObserver = lifecycleEventObserver;
        }

        public boolean isAtLeast(Lifecycle.State state) {
            return this.mLifecycle.getCurrentState().isAtLeast(state);
        }

        @Override // androidx.fragment.app.FragmentResultListener
        public void onFragmentResult(@NonNull String str, @NonNull Bundle bundle) {
            this.mListener.onFragmentResult(str, bundle);
        }

        public void removeObserver() {
            this.mLifecycle.removeObserver(this.mObserver);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Configuration configuration) {
        if (isParentAdded()) {
            dispatchConfigurationChanged(configuration);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Integer num) {
        if (isParentAdded() && num.intValue() == 80) {
            dispatchLowMemory();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(MultiWindowModeChangedInfo multiWindowModeChangedInfo) {
        if (isParentAdded()) {
            dispatchMultiWindowModeChanged(multiWindowModeChangedInfo.isInMultiWindowMode());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo) {
        if (isParentAdded()) {
            dispatchPictureInPictureModeChanged(pictureInPictureModeChangedInfo.isInPictureInPictureMode());
        }
    }

    private void throwException(RuntimeException runtimeException) {
        Log.e(TAG, runtimeException.getMessage());
        Log.e(TAG, "Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            try {
                fragmentHostCallback.onDump("  ", null, printWriter, new String[0]);
            } catch (Exception e) {
                Log.e(TAG, "Failed dumping state", e);
            }
        } else {
            try {
                dump("  ", null, printWriter, new String[0]);
            } catch (Exception e2) {
                Log.e(TAG, "Failed dumping state", e2);
            }
        }
        throw runtimeException;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    @NonNull
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public boolean executePendingTransactions() {
        boolean execPendingActions = execPendingActions(true);
        forcePostponedTransactions();
        return execPendingActions;
    }

    private void updateOnBackPressedCallbackEnabled() {
        synchronized (this.mPendingActions) {
            boolean z = true;
            if (!this.mPendingActions.isEmpty()) {
                this.mOnBackPressedCallback.setEnabled(true);
            } else {
                this.mOnBackPressedCallback.setEnabled((getBackStackEntryCount() <= 0 || !isPrimaryNavigation(this.mParent)) ? false : false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isPrimaryNavigation(@Nullable Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        FragmentManager fragmentManager = fragment.mFragmentManager;
        return fragment.equals(fragmentManager.getPrimaryNavigationFragment()) && isPrimaryNavigation(fragmentManager.mParent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isParentMenuVisible(@Nullable Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        return fragment.isMenuVisible();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isParentHidden(@Nullable Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        return fragment.isHidden();
    }

    void handleOnBackPressed() {
        execPendingActions(true);
        if (this.mOnBackPressedCallback.isEnabled()) {
            popBackStackImmediate();
        } else {
            this.mOnBackPressedDispatcher.onBackPressed();
        }
    }

    public void restoreBackStack(@NonNull String str) {
        enqueueAction(new RestoreBackStackState(str), false);
    }

    public void saveBackStack(@NonNull String str) {
        enqueueAction(new SaveBackStackState(str), false);
    }

    public void clearBackStack(@NonNull String str) {
        enqueueAction(new ClearBackStackState(str), false);
    }

    public void popBackStack() {
        enqueueAction(new PopBackStackState(null, -1, 0), false);
    }

    public boolean popBackStackImmediate() {
        return popBackStackImmediate(null, -1, 0);
    }

    public void popBackStack(@Nullable String str, int i) {
        enqueueAction(new PopBackStackState(str, -1, i), false);
    }

    public boolean popBackStackImmediate(@Nullable String str, int i) {
        return popBackStackImmediate(str, -1, i);
    }

    public void popBackStack(int i, int i2) {
        popBackStack(i, i2, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void popBackStack(int i, int i2, boolean z) {
        if (i < 0) {
            throw new IllegalArgumentException("Bad id: " + i);
        }
        enqueueAction(new PopBackStackState(null, i, i2), z);
    }

    public boolean popBackStackImmediate(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("Bad id: " + i);
        }
        return popBackStackImmediate(null, i, i2);
    }

    private boolean popBackStackImmediate(@Nullable String str, int i, int i2) {
        execPendingActions(false);
        ensureExecReady(true);
        Fragment fragment = this.mPrimaryNav;
        if (fragment == null || i >= 0 || str != null || !fragment.getChildFragmentManager().popBackStackImmediate()) {
            boolean popBackStackState = popBackStackState(this.mTmpRecords, this.mTmpIsPop, str, i, i2);
            if (popBackStackState) {
                this.mExecutingActions = true;
                try {
                    removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                } finally {
                    cleanupExec();
                }
            }
            updateOnBackPressedCallbackEnabled();
            doPendingDeferredStart();
            this.mFragmentStore.burpActive();
            return popBackStackState;
        }
        return true;
    }

    public int getBackStackEntryCount() {
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @NonNull
    public BackStackEntry getBackStackEntryAt(int i) {
        return this.mBackStack.get(i);
    }

    public void addOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    public void removeOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener) {
        ArrayList<OnBackStackChangedListener> arrayList = this.mBackStackChangeListeners;
        if (arrayList != null) {
            arrayList.remove(onBackStackChangedListener);
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void setFragmentResult(@NonNull String str, @NonNull Bundle bundle) {
        LifecycleAwareResultListener lifecycleAwareResultListener = this.mResultListeners.get(str);
        if (lifecycleAwareResultListener != null && lifecycleAwareResultListener.isAtLeast(Lifecycle.State.STARTED)) {
            lifecycleAwareResultListener.onFragmentResult(str, bundle);
        } else {
            this.mResults.put(str, bundle);
        }
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "Setting fragment result with key " + str + " and result " + bundle);
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void clearFragmentResult(@NonNull String str) {
        this.mResults.remove(str);
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "Clearing fragment result with key " + str);
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    @SuppressLint({"SyntheticAccessor"})
    public final void setFragmentResultListener(@NonNull final String str, @NonNull LifecycleOwner lifecycleOwner, @NonNull final FragmentResultListener fragmentResultListener) {
        final Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() { // from class: androidx.fragment.app.FragmentManager.6
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner2, @NonNull Lifecycle.Event event) {
                Bundle bundle;
                if (event == Lifecycle.Event.ON_START && (bundle = (Bundle) FragmentManager.this.mResults.get(str)) != null) {
                    fragmentResultListener.onFragmentResult(str, bundle);
                    FragmentManager.this.clearFragmentResult(str);
                }
                if (event == Lifecycle.Event.ON_DESTROY) {
                    lifecycle.removeObserver(this);
                    FragmentManager.this.mResultListeners.remove(str);
                }
            }
        };
        lifecycle.addObserver(lifecycleEventObserver);
        LifecycleAwareResultListener put = this.mResultListeners.put(str, new LifecycleAwareResultListener(lifecycle, fragmentResultListener, lifecycleEventObserver));
        if (put != null) {
            put.removeObserver();
        }
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "Setting FragmentResultListener with key " + str + " lifecycleOwner " + lifecycle + " and listener " + fragmentResultListener);
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void clearFragmentResultListener(@NonNull String str) {
        LifecycleAwareResultListener remove = this.mResultListeners.remove(str);
        if (remove != null) {
            remove.removeObserver();
        }
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "Clearing FragmentResultListener for key " + str);
        }
    }

    public void putFragment(@NonNull Bundle bundle, @NonNull String str, @NonNull Fragment fragment) {
        if (fragment.mFragmentManager != this) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putString(str, fragment.mWho);
    }

    @Nullable
    public Fragment getFragment(@NonNull Bundle bundle, @NonNull String str) {
        String string = bundle.getString(str);
        if (string == null) {
            return null;
        }
        Fragment findActiveFragment = findActiveFragment(string);
        if (findActiveFragment == null) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + str + ": unique id " + string));
        }
        return findActiveFragment;
    }

    @NonNull
    public static <F extends Fragment> F findFragment(@NonNull View view) {
        F f = (F) findViewFragment(view);
        if (f != null) {
            return f;
        }
        throw new IllegalStateException("View " + view + " does not have a Fragment set");
    }

    @Nullable
    private static Fragment findViewFragment(@NonNull View view) {
        while (view != null) {
            Fragment viewFragment = getViewFragment(view);
            if (viewFragment != null) {
                return viewFragment;
            }
            ViewParent parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public static Fragment getViewFragment(@NonNull View view) {
        Object tag = view.getTag(R.id.fragment_container_view_tag);
        if (tag instanceof Fragment) {
            return (Fragment) tag;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onContainerAvailable(@NonNull FragmentContainerView fragmentContainerView) {
        View view;
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
            Fragment fragment = fragmentStateManager.getFragment();
            if (fragment.mContainerId == fragmentContainerView.getId() && (view = fragment.mView) != null && view.getParent() == null) {
                fragment.mContainer = fragmentContainerView;
                fragmentStateManager.addViewToContainer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static FragmentManager findFragmentManager(@NonNull View view) {
        Fragment findViewFragment = findViewFragment(view);
        if (findViewFragment != null) {
            if (!findViewFragment.isAdded()) {
                throw new IllegalStateException("The Fragment " + findViewFragment + " that owns View " + view + " has already been destroyed. Nested fragments should always use the child FragmentManager.");
            }
            return findViewFragment.getChildFragmentManager();
        }
        Context context = view.getContext();
        FragmentActivity fragmentActivity = null;
        while (true) {
            if (!(context instanceof ContextWrapper)) {
                break;
            } else if (context instanceof FragmentActivity) {
                fragmentActivity = (FragmentActivity) context;
                break;
            } else {
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        if (fragmentActivity != null) {
            return fragmentActivity.getSupportFragmentManager();
        }
        throw new IllegalStateException("View " + view + " is not within a subclass of FragmentActivity.");
    }

    @NonNull
    public List<Fragment> getFragments() {
        return this.mFragmentStore.getFragments();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public ViewModelStore getViewModelStore(@NonNull Fragment fragment) {
        return this.mNonConfig.getViewModelStore(fragment);
    }

    @NonNull
    private FragmentManagerViewModel getChildNonConfig(@NonNull Fragment fragment) {
        return this.mNonConfig.getChildNonConfig(fragment);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addRetainedFragment(@NonNull Fragment fragment) {
        this.mNonConfig.addRetainedFragment(fragment);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeRetainedFragment(@NonNull Fragment fragment) {
        this.mNonConfig.removeRetainedFragment(fragment);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public List<Fragment> getActiveFragments() {
        return this.mFragmentStore.getActiveFragments();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getActiveFragmentCount() {
        return this.mFragmentStore.getActiveFragmentCount();
    }

    @Nullable
    public Fragment.SavedState saveFragmentInstanceState(@NonNull Fragment fragment) {
        FragmentStateManager fragmentStateManager = this.mFragmentStore.getFragmentStateManager(fragment.mWho);
        if (fragmentStateManager == null || !fragmentStateManager.getFragment().equals(fragment)) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        return fragmentStateManager.saveInstanceState();
    }

    private void clearBackStackStateViewModels() {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        boolean z = true;
        if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            z = this.mFragmentStore.getNonConfig().isCleared();
        } else if (fragmentHostCallback.getContext() instanceof Activity) {
            z = true ^ ((Activity) this.mHost.getContext()).isChangingConfigurations();
        }
        if (z) {
            for (BackStackState backStackState : this.mBackStackStates.values()) {
                for (String str : backStackState.mFragments) {
                    this.mFragmentStore.getNonConfig().clearNonConfigState(str);
                }
            }
        }
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment = this.mParent;
        if (fragment != null) {
            sb.append(fragment.getClass().getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(this.mParent)));
            sb.append("}");
        } else {
            FragmentHostCallback<?> fragmentHostCallback = this.mHost;
            if (fragmentHostCallback != null) {
                sb.append(fragmentHostCallback.getClass().getSimpleName());
                sb.append("{");
                sb.append(Integer.toHexString(System.identityHashCode(this.mHost)));
                sb.append("}");
            } else {
                sb.append("null");
            }
        }
        sb.append("}}");
        return sb.toString();
    }

    public void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        int size;
        int size2;
        String str2 = str + "    ";
        this.mFragmentStore.dump(str, fileDescriptor, printWriter, strArr);
        ArrayList<Fragment> arrayList = this.mCreatedMenus;
        if (arrayList != null && (size2 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i = 0; i < size2; i++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(this.mCreatedMenus.get(i).toString());
            }
        }
        ArrayList<BackStackRecord> arrayList2 = this.mBackStack;
        if (arrayList2 != null && (size = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i2 = 0; i2 < size; i2++) {
                BackStackRecord backStackRecord = this.mBackStack.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(str2, printWriter);
            }
        }
        printWriter.print(str);
        printWriter.println("Back Stack Index: " + this.mBackStackIndex.get());
        synchronized (this.mPendingActions) {
            int size3 = this.mPendingActions.size();
            if (size3 > 0) {
                printWriter.print(str);
                printWriter.println("Pending Actions:");
                for (int i3 = 0; i3 < size3; i3++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i3);
                    printWriter.print(": ");
                    printWriter.println(this.mPendingActions.get(i3));
                }
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void performPendingDeferredStart(@NonNull FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.getFragment();
        if (fragment.mDeferStart) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true;
                return;
            }
            fragment.mDeferStart = false;
            fragmentStateManager.moveToExpectedState();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isStateAtLeast(int i) {
        return this.mCurState >= i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setExitAnimationOrder(@NonNull Fragment fragment, boolean z) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer == null || !(fragmentContainer instanceof FragmentContainerView)) {
            return;
        }
        ((FragmentContainerView) fragmentContainer).setDrawDisappearingViewsLast(!z);
    }

    void moveToState(int i, boolean z) {
        FragmentHostCallback<?> fragmentHostCallback;
        if (this.mHost == null && i != -1) {
            throw new IllegalStateException("No activity");
        }
        if (z || i != this.mCurState) {
            this.mCurState = i;
            this.mFragmentStore.moveToExpectedState();
            startPendingDeferredFragments();
            if (this.mNeedMenuInvalidate && (fragmentHostCallback = this.mHost) != null && this.mCurState == 7) {
                fragmentHostCallback.onSupportInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    private void startPendingDeferredFragments() {
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
            performPendingDeferredStart(fragmentStateManager);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public FragmentStateManager createOrGetFragmentStateManager(@NonNull Fragment fragment) {
        FragmentStateManager fragmentStateManager = this.mFragmentStore.getFragmentStateManager(fragment.mWho);
        if (fragmentStateManager != null) {
            return fragmentStateManager;
        }
        FragmentStateManager fragmentStateManager2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment);
        fragmentStateManager2.restoreState(this.mHost.getContext().getClassLoader());
        fragmentStateManager2.setFragmentManagerState(this.mCurState);
        return fragmentStateManager2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FragmentStateManager addFragment(@NonNull Fragment fragment) {
        String str = fragment.mPreviousWho;
        if (str != null) {
            FragmentStrictMode.onFragmentReuse(fragment, str);
        }
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "add: " + fragment);
        }
        FragmentStateManager createOrGetFragmentStateManager = createOrGetFragmentStateManager(fragment);
        fragment.mFragmentManager = this;
        this.mFragmentStore.makeActive(createOrGetFragmentStateManager);
        if (!fragment.mDetached) {
            this.mFragmentStore.addFragment(fragment);
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
        return createOrGetFragmentStateManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean z = !fragment.isInBackStack();
        if (!fragment.mDetached || z) {
            this.mFragmentStore.removeFragment(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mRemoving = true;
            setVisibleRemovingFragment(fragment);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void hideFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "hide: " + fragment);
        }
        if (fragment.mHidden) {
            return;
        }
        fragment.mHidden = true;
        fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        setVisibleRemovingFragment(fragment);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void showFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void detachFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "detach: " + fragment);
        }
        if (fragment.mDetached) {
            return;
        }
        fragment.mDetached = true;
        if (fragment.mAdded) {
            if (isLoggingEnabled(2)) {
                Log.v(TAG, "remove from detach: " + fragment);
            }
            this.mFragmentStore.removeFragment(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            setVisibleRemovingFragment(fragment);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void attachFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (fragment.mAdded) {
                return;
            }
            this.mFragmentStore.addFragment(fragment);
            if (isLoggingEnabled(2)) {
                Log.v(TAG, "add from attach: " + fragment);
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
    }

    @Nullable
    public Fragment findFragmentById(@IdRes int i) {
        return this.mFragmentStore.findFragmentById(i);
    }

    @Nullable
    public Fragment findFragmentByTag(@Nullable String str) {
        return this.mFragmentStore.findFragmentByTag(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Fragment findFragmentByWho(@NonNull String str) {
        return this.mFragmentStore.findFragmentByWho(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Fragment findActiveFragment(@NonNull String str) {
        return this.mFragmentStore.findActiveFragment(str);
    }

    private void checkStateLoss() {
        if (isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
    }

    public boolean isStateSaved() {
        return this.mStateSaved || this.mStopped;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void enqueueAction(@NonNull OpGenerator opGenerator, boolean z) {
        if (!z) {
            if (this.mHost == null) {
                if (this.mDestroyed) {
                    throw new IllegalStateException("FragmentManager has been destroyed");
                }
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
            checkStateLoss();
        }
        synchronized (this.mPendingActions) {
            if (this.mHost == null) {
                if (!z) {
                    throw new IllegalStateException("Activity has been destroyed");
                }
                return;
            }
            this.mPendingActions.add(opGenerator);
            scheduleCommit();
        }
    }

    void scheduleCommit() {
        synchronized (this.mPendingActions) {
            boolean z = true;
            if (this.mPendingActions.size() != 1) {
                z = false;
            }
            if (z) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
                updateOnBackPressedCallbackEnabled();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int allocBackStackIndex() {
        return this.mBackStackIndex.getAndIncrement();
    }

    private void ensureExecReady(boolean z) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (this.mHost == null) {
            if (this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has been destroyed");
            }
            throw new IllegalStateException("FragmentManager has not been attached to a host.");
        } else if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        } else {
            if (!z) {
                checkStateLoss();
            }
            if (this.mTmpRecords == null) {
                this.mTmpRecords = new ArrayList<>();
                this.mTmpIsPop = new ArrayList<>();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void execSingleAction(@NonNull OpGenerator opGenerator, boolean z) {
        if (z && (this.mHost == null || this.mDestroyed)) {
            return;
        }
        ensureExecReady(z);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.burpActive();
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean execPendingActions(boolean z) {
        ensureExecReady(z);
        boolean z2 = false;
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                z2 = true;
            } catch (Throwable th) {
                cleanupExec();
                throw th;
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.burpActive();
        return z2;
    }

    private void removeRedundantOperationsAndExecute(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() != arrayList2.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
        }
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            if (!arrayList.get(i).mReorderingAllowed) {
                if (i2 != i) {
                    executeOpsTogether(arrayList, arrayList2, i2, i);
                }
                i2 = i + 1;
                if (arrayList2.get(i).booleanValue()) {
                    while (i2 < size && arrayList2.get(i2).booleanValue() && !arrayList.get(i2).mReorderingAllowed) {
                        i2++;
                    }
                }
                executeOpsTogether(arrayList, arrayList2, i, i2);
                i = i2 - 1;
            }
            i++;
        }
        if (i2 != size) {
            executeOpsTogether(arrayList, arrayList2, i2, size);
        }
    }

    private void executeOpsTogether(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, int i, int i2) {
        boolean z = arrayList.get(i).mReorderingAllowed;
        ArrayList<Fragment> arrayList3 = this.mTmpAddedFragments;
        if (arrayList3 == null) {
            this.mTmpAddedFragments = new ArrayList<>();
        } else {
            arrayList3.clear();
        }
        this.mTmpAddedFragments.addAll(this.mFragmentStore.getFragments());
        Fragment primaryNavigationFragment = getPrimaryNavigationFragment();
        boolean z2 = false;
        for (int i3 = i; i3 < i2; i3++) {
            BackStackRecord backStackRecord = arrayList.get(i3);
            if (!arrayList2.get(i3).booleanValue()) {
                primaryNavigationFragment = backStackRecord.expandOps(this.mTmpAddedFragments, primaryNavigationFragment);
            } else {
                primaryNavigationFragment = backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, primaryNavigationFragment);
            }
            z2 = z2 || backStackRecord.mAddToBackStack;
        }
        this.mTmpAddedFragments.clear();
        if (!z && this.mCurState >= 1) {
            for (int i4 = i; i4 < i2; i4++) {
                Iterator<FragmentTransaction.Op> it = arrayList.get(i4).mOps.iterator();
                while (it.hasNext()) {
                    Fragment fragment = it.next().mFragment;
                    if (fragment != null && fragment.mFragmentManager != null) {
                        this.mFragmentStore.makeActive(createOrGetFragmentStateManager(fragment));
                    }
                }
            }
        }
        executeOps(arrayList, arrayList2, i, i2);
        boolean booleanValue = arrayList2.get(i2 - 1).booleanValue();
        for (int i5 = i; i5 < i2; i5++) {
            BackStackRecord backStackRecord2 = arrayList.get(i5);
            if (booleanValue) {
                for (int size = backStackRecord2.mOps.size() - 1; size >= 0; size--) {
                    Fragment fragment2 = backStackRecord2.mOps.get(size).mFragment;
                    if (fragment2 != null) {
                        createOrGetFragmentStateManager(fragment2).moveToExpectedState();
                    }
                }
            } else {
                Iterator<FragmentTransaction.Op> it2 = backStackRecord2.mOps.iterator();
                while (it2.hasNext()) {
                    Fragment fragment3 = it2.next().mFragment;
                    if (fragment3 != null) {
                        createOrGetFragmentStateManager(fragment3).moveToExpectedState();
                    }
                }
            }
        }
        moveToState(this.mCurState, true);
        for (SpecialEffectsController specialEffectsController : collectChangedControllers(arrayList, i, i2)) {
            specialEffectsController.updateOperationDirection(booleanValue);
            specialEffectsController.markPostponedState();
            specialEffectsController.executePendingOperations();
        }
        while (i < i2) {
            BackStackRecord backStackRecord3 = arrayList.get(i);
            if (arrayList2.get(i).booleanValue() && backStackRecord3.mIndex >= 0) {
                backStackRecord3.mIndex = -1;
            }
            backStackRecord3.runOnCommitRunnables();
            i++;
        }
        if (z2) {
            reportBackStackChanged();
        }
    }

    private Set<SpecialEffectsController> collectChangedControllers(@NonNull ArrayList<BackStackRecord> arrayList, int i, int i2) {
        ViewGroup viewGroup;
        HashSet hashSet = new HashSet();
        while (i < i2) {
            Iterator<FragmentTransaction.Op> it = arrayList.get(i).mOps.iterator();
            while (it.hasNext()) {
                Fragment fragment = it.next().mFragment;
                if (fragment != null && (viewGroup = fragment.mContainer) != null) {
                    hashSet.add(SpecialEffectsController.getOrCreateController(viewGroup, this));
                }
            }
            i++;
        }
        return hashSet;
    }

    private static void executeOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, int i, int i2) {
        while (i < i2) {
            BackStackRecord backStackRecord = arrayList.get(i);
            if (arrayList2.get(i).booleanValue()) {
                backStackRecord.bumpBackStackNesting(-1);
                backStackRecord.executePopOps();
            } else {
                backStackRecord.bumpBackStackNesting(1);
                backStackRecord.executeOps();
            }
            i++;
        }
    }

    private void setVisibleRemovingFragment(@NonNull Fragment fragment) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer == null || fragment.getEnterAnim() + fragment.getExitAnim() + fragment.getPopEnterAnim() + fragment.getPopExitAnim() <= 0) {
            return;
        }
        int i = R.id.visible_removing_fragment_view_tag;
        if (fragmentContainer.getTag(i) == null) {
            fragmentContainer.setTag(i, fragment);
        }
        ((Fragment) fragmentContainer.getTag(i)).setPopDirection(fragment.getPopDirection());
    }

    private ViewGroup getFragmentContainer(@NonNull Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null) {
            return viewGroup;
        }
        if (fragment.mContainerId > 0 && this.mContainer.onHasView()) {
            View onFindViewById = this.mContainer.onFindViewById(fragment.mContainerId);
            if (onFindViewById instanceof ViewGroup) {
                return (ViewGroup) onFindViewById;
            }
        }
        return null;
    }

    private void forcePostponedTransactions() {
        for (SpecialEffectsController specialEffectsController : collectAllSpecialEffectsController()) {
            specialEffectsController.forcePostponedExecutePendingOperations();
        }
    }

    private void endAnimatingAwayFragments() {
        for (SpecialEffectsController specialEffectsController : collectAllSpecialEffectsController()) {
            specialEffectsController.forceCompleteAllOperations();
        }
    }

    private Set<SpecialEffectsController> collectAllSpecialEffectsController() {
        HashSet hashSet = new HashSet();
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
            ViewGroup viewGroup = fragmentStateManager.getFragment().mContainer;
            if (viewGroup != null) {
                hashSet.add(SpecialEffectsController.getOrCreateController(viewGroup, getSpecialEffectsControllerFactory()));
            }
        }
        return hashSet;
    }

    private boolean generateOpsForPendingActions(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
        synchronized (this.mPendingActions) {
            if (this.mPendingActions.isEmpty()) {
                return false;
            }
            int size = this.mPendingActions.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                z |= this.mPendingActions.get(i).generateOps(arrayList, arrayList2);
            }
            this.mPendingActions.clear();
            this.mHost.getHandler().removeCallbacks(this.mExecCommit);
            return z;
        }
    }

    private void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    private void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addBackStackState(BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList<>();
        }
        this.mBackStack.add(backStackRecord);
    }

    boolean restoreBackStackState(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, @NonNull String str) {
        boolean z;
        BackStackState remove = this.mBackStackStates.remove(str);
        if (remove == null) {
            return false;
        }
        HashMap hashMap = new HashMap();
        Iterator<BackStackRecord> it = arrayList.iterator();
        while (it.hasNext()) {
            BackStackRecord next = it.next();
            if (next.mBeingSaved) {
                Iterator<FragmentTransaction.Op> it2 = next.mOps.iterator();
                while (it2.hasNext()) {
                    Fragment fragment = it2.next().mFragment;
                    if (fragment != null) {
                        hashMap.put(fragment.mWho, fragment);
                    }
                }
            }
        }
        while (true) {
            for (BackStackRecord backStackRecord : remove.instantiate(this, hashMap)) {
                z = backStackRecord.generateOps(arrayList, arrayList2) || z;
            }
            return z;
        }
    }

    boolean saveBackStackState(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, @NonNull String str) {
        int i;
        int findBackStackIndex = findBackStackIndex(str, -1, true);
        if (findBackStackIndex < 0) {
            return false;
        }
        for (int i2 = findBackStackIndex; i2 < this.mBackStack.size(); i2++) {
            BackStackRecord backStackRecord = this.mBackStack.get(i2);
            if (!backStackRecord.mReorderingAllowed) {
                throwException(new IllegalArgumentException("saveBackStack(\"" + str + "\") included FragmentTransactions must use setReorderingAllowed(true) to ensure that the back stack can be restored as an atomic operation. Found " + backStackRecord + " that did not use setReorderingAllowed(true)."));
            }
        }
        HashSet hashSet = new HashSet();
        for (int i3 = findBackStackIndex; i3 < this.mBackStack.size(); i3++) {
            BackStackRecord backStackRecord2 = this.mBackStack.get(i3);
            HashSet hashSet2 = new HashSet();
            HashSet hashSet3 = new HashSet();
            Iterator<FragmentTransaction.Op> it = backStackRecord2.mOps.iterator();
            while (it.hasNext()) {
                FragmentTransaction.Op next = it.next();
                Fragment fragment = next.mFragment;
                if (fragment != null) {
                    if (!next.mFromExpandedOp || (i = next.mCmd) == 1 || i == 2 || i == 8) {
                        hashSet.add(fragment);
                        hashSet2.add(fragment);
                    }
                    int i4 = next.mCmd;
                    if (i4 == 1 || i4 == 2) {
                        hashSet3.add(fragment);
                    }
                }
            }
            hashSet2.removeAll(hashSet3);
            if (!hashSet2.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("saveBackStack(\"");
                sb.append(str);
                sb.append("\") must be self contained and not reference fragments from non-saved FragmentTransactions. Found reference to fragment");
                sb.append(hashSet2.size() == 1 ? " " + hashSet2.iterator().next() : "s " + hashSet2);
                sb.append(" in ");
                sb.append(backStackRecord2);
                sb.append(" that were previously added to the FragmentManager through a separate FragmentTransaction.");
                throwException(new IllegalArgumentException(sb.toString()));
            }
        }
        ArrayDeque arrayDeque = new ArrayDeque(hashSet);
        while (!arrayDeque.isEmpty()) {
            Fragment fragment2 = (Fragment) arrayDeque.removeFirst();
            if (fragment2.mRetainInstance) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("saveBackStack(\"");
                sb2.append(str);
                sb2.append("\") must not contain retained fragments. Found ");
                sb2.append(hashSet.contains(fragment2) ? "direct reference to retained " : "retained child ");
                sb2.append("fragment ");
                sb2.append(fragment2);
                throwException(new IllegalArgumentException(sb2.toString()));
            }
            for (Fragment fragment3 : fragment2.mChildFragmentManager.getActiveFragments()) {
                if (fragment3 != null) {
                    arrayDeque.addLast(fragment3);
                }
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((Fragment) it2.next()).mWho);
        }
        ArrayList arrayList4 = new ArrayList(this.mBackStack.size() - findBackStackIndex);
        for (int i5 = findBackStackIndex; i5 < this.mBackStack.size(); i5++) {
            arrayList4.add(null);
        }
        BackStackState backStackState = new BackStackState(arrayList3, arrayList4);
        for (int size = this.mBackStack.size() - 1; size >= findBackStackIndex; size--) {
            BackStackRecord remove = this.mBackStack.remove(size);
            BackStackRecord backStackRecord3 = new BackStackRecord(remove);
            backStackRecord3.collapseOps();
            arrayList4.set(size - findBackStackIndex, new BackStackRecordState(backStackRecord3));
            remove.mBeingSaved = true;
            arrayList.add(remove);
            arrayList2.add(Boolean.TRUE);
        }
        this.mBackStackStates.put(str, backStackState);
        return true;
    }

    boolean clearBackStackState(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, @NonNull String str) {
        if (restoreBackStackState(arrayList, arrayList2, str)) {
            return popBackStackState(arrayList, arrayList2, str, -1, 1);
        }
        return false;
    }

    boolean popBackStackState(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, @Nullable String str, int i, int i2) {
        int findBackStackIndex = findBackStackIndex(str, i, (i2 & 1) != 0);
        if (findBackStackIndex < 0) {
            return false;
        }
        for (int size = this.mBackStack.size() - 1; size >= findBackStackIndex; size--) {
            arrayList.add(this.mBackStack.remove(size));
            arrayList2.add(Boolean.TRUE);
        }
        return true;
    }

    private int findBackStackIndex(@Nullable String str, int i, boolean z) {
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        if (arrayList == null || arrayList.isEmpty()) {
            return -1;
        }
        if (str == null && i < 0) {
            if (z) {
                return 0;
            }
            return this.mBackStack.size() - 1;
        }
        int size = this.mBackStack.size() - 1;
        while (size >= 0) {
            BackStackRecord backStackRecord = this.mBackStack.get(size);
            if ((str != null && str.equals(backStackRecord.getName())) || (i >= 0 && i == backStackRecord.mIndex)) {
                break;
            }
            size--;
        }
        if (size < 0) {
            return size;
        }
        if (!z) {
            if (size == this.mBackStack.size() - 1) {
                return -1;
            }
            return size + 1;
        }
        while (size > 0) {
            BackStackRecord backStackRecord2 = this.mBackStack.get(size - 1);
            if ((str == null || !str.equals(backStackRecord2.getName())) && (i < 0 || i != backStackRecord2.mIndex)) {
                return size;
            }
            size--;
        }
        return size;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Deprecated
    public FragmentManagerNonConfig retainNonConfig() {
        if (this.mHost instanceof ViewModelStoreOwner) {
            throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
        }
        return this.mNonConfig.getSnapshot();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Parcelable saveAllState() {
        if (this.mHost instanceof SavedStateRegistryOwner) {
            throwException(new IllegalStateException("You cannot use saveAllState when your FragmentHostCallback implements SavedStateRegistryOwner."));
        }
        Bundle lambda$attachController$4 = lambda$attachController$4();
        if (lambda$attachController$4.isEmpty()) {
            return null;
        }
        return lambda$attachController$4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    /* renamed from: saveAllStateInternal */
    public Bundle lambda$attachController$4() {
        int size;
        Bundle bundle = new Bundle();
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        execPendingActions(true);
        this.mStateSaved = true;
        this.mNonConfig.setIsStateSaved(true);
        ArrayList<String> saveActiveFragments = this.mFragmentStore.saveActiveFragments();
        ArrayList<FragmentState> allSavedState = this.mFragmentStore.getAllSavedState();
        if (allSavedState.isEmpty()) {
            if (isLoggingEnabled(2)) {
                Log.v(TAG, "saveAllState: no fragments!");
            }
        } else {
            ArrayList<String> saveAddedFragments = this.mFragmentStore.saveAddedFragments();
            BackStackRecordState[] backStackRecordStateArr = null;
            ArrayList<BackStackRecord> arrayList = this.mBackStack;
            if (arrayList != null && (size = arrayList.size()) > 0) {
                backStackRecordStateArr = new BackStackRecordState[size];
                for (int i = 0; i < size; i++) {
                    backStackRecordStateArr[i] = new BackStackRecordState(this.mBackStack.get(i));
                    if (isLoggingEnabled(2)) {
                        Log.v(TAG, "saveAllState: adding back stack #" + i + ": " + this.mBackStack.get(i));
                    }
                }
            }
            FragmentManagerState fragmentManagerState = new FragmentManagerState();
            fragmentManagerState.mActive = saveActiveFragments;
            fragmentManagerState.mAdded = saveAddedFragments;
            fragmentManagerState.mBackStack = backStackRecordStateArr;
            fragmentManagerState.mBackStackIndex = this.mBackStackIndex.get();
            Fragment fragment = this.mPrimaryNav;
            if (fragment != null) {
                fragmentManagerState.mPrimaryNavActiveWho = fragment.mWho;
            }
            fragmentManagerState.mBackStackStateKeys.addAll(this.mBackStackStates.keySet());
            fragmentManagerState.mBackStackStates.addAll(this.mBackStackStates.values());
            fragmentManagerState.mLaunchedFragments = new ArrayList<>(this.mLaunchedFragments);
            bundle.putParcelable("state", fragmentManagerState);
            for (String str : this.mResults.keySet()) {
                bundle.putBundle(RESULT_NAME_PREFIX + str, this.mResults.get(str));
            }
            Iterator<FragmentState> it = allSavedState.iterator();
            while (it.hasNext()) {
                FragmentState next = it.next();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("state", next);
                bundle.putBundle(FRAGMENT_NAME_PREFIX + next.mWho, bundle2);
            }
        }
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void restoreAllState(@Nullable Parcelable parcelable, @Nullable FragmentManagerNonConfig fragmentManagerNonConfig) {
        if (this.mHost instanceof ViewModelStoreOwner) {
            throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
        }
        this.mNonConfig.restoreFromSnapshot(fragmentManagerNonConfig);
        restoreSaveStateInternal(parcelable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void restoreSaveState(@Nullable Parcelable parcelable) {
        if (this.mHost instanceof SavedStateRegistryOwner) {
            throwException(new IllegalStateException("You cannot use restoreSaveState when your FragmentHostCallback implements SavedStateRegistryOwner."));
        }
        restoreSaveStateInternal(parcelable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void restoreSaveStateInternal(@Nullable Parcelable parcelable) {
        FragmentStateManager fragmentStateManager;
        Bundle bundle;
        Bundle bundle2;
        if (parcelable == null) {
            return;
        }
        Bundle bundle3 = (Bundle) parcelable;
        for (String str : bundle3.keySet()) {
            if (str.startsWith(RESULT_NAME_PREFIX) && (bundle2 = bundle3.getBundle(str)) != null) {
                bundle2.setClassLoader(this.mHost.getContext().getClassLoader());
                this.mResults.put(str.substring(7), bundle2);
            }
        }
        ArrayList<FragmentState> arrayList = new ArrayList<>();
        for (String str2 : bundle3.keySet()) {
            if (str2.startsWith(FRAGMENT_NAME_PREFIX) && (bundle = bundle3.getBundle(str2)) != null) {
                bundle.setClassLoader(this.mHost.getContext().getClassLoader());
                arrayList.add((FragmentState) bundle.getParcelable("state"));
            }
        }
        this.mFragmentStore.restoreSaveState(arrayList);
        FragmentManagerState fragmentManagerState = (FragmentManagerState) bundle3.getParcelable("state");
        if (fragmentManagerState == null) {
            return;
        }
        this.mFragmentStore.resetActiveFragments();
        Iterator<String> it = fragmentManagerState.mActive.iterator();
        while (it.hasNext()) {
            FragmentState savedState = this.mFragmentStore.setSavedState(it.next(), null);
            if (savedState != null) {
                Fragment findRetainedFragmentByWho = this.mNonConfig.findRetainedFragmentByWho(savedState.mWho);
                if (findRetainedFragmentByWho != null) {
                    if (isLoggingEnabled(2)) {
                        Log.v(TAG, "restoreSaveState: re-attaching retained " + findRetainedFragmentByWho);
                    }
                    fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, findRetainedFragmentByWho, savedState);
                } else {
                    fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, this.mHost.getContext().getClassLoader(), getFragmentFactory(), savedState);
                }
                Fragment fragment = fragmentStateManager.getFragment();
                fragment.mFragmentManager = this;
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "restoreSaveState: active (" + fragment.mWho + "): " + fragment);
                }
                fragmentStateManager.restoreState(this.mHost.getContext().getClassLoader());
                this.mFragmentStore.makeActive(fragmentStateManager);
                fragmentStateManager.setFragmentManagerState(this.mCurState);
            }
        }
        for (Fragment fragment2 : this.mNonConfig.getRetainedFragments()) {
            if (!this.mFragmentStore.containsActiveFragment(fragment2.mWho)) {
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "Discarding retained Fragment " + fragment2 + " that was not found in the set of active Fragments " + fragmentManagerState.mActive);
                }
                this.mNonConfig.removeRetainedFragment(fragment2);
                fragment2.mFragmentManager = this;
                FragmentStateManager fragmentStateManager2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment2);
                fragmentStateManager2.setFragmentManagerState(1);
                fragmentStateManager2.moveToExpectedState();
                fragment2.mRemoving = true;
                fragmentStateManager2.moveToExpectedState();
            }
        }
        this.mFragmentStore.restoreAddedFragments(fragmentManagerState.mAdded);
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList<>(fragmentManagerState.mBackStack.length);
            int i = 0;
            while (true) {
                BackStackRecordState[] backStackRecordStateArr = fragmentManagerState.mBackStack;
                if (i >= backStackRecordStateArr.length) {
                    break;
                }
                BackStackRecord instantiate = backStackRecordStateArr[i].instantiate(this);
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "restoreAllState: back stack #" + i + " (index " + instantiate.mIndex + "): " + instantiate);
                    PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
                    instantiate.dump("  ", printWriter, false);
                    printWriter.close();
                }
                this.mBackStack.add(instantiate);
                i++;
            }
        } else {
            this.mBackStack = null;
        }
        this.mBackStackIndex.set(fragmentManagerState.mBackStackIndex);
        String str3 = fragmentManagerState.mPrimaryNavActiveWho;
        if (str3 != null) {
            Fragment findActiveFragment = findActiveFragment(str3);
            this.mPrimaryNav = findActiveFragment;
            dispatchParentPrimaryNavigationFragmentChanged(findActiveFragment);
        }
        ArrayList<String> arrayList2 = fragmentManagerState.mBackStackStateKeys;
        if (arrayList2 != null) {
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                this.mBackStackStates.put(arrayList2.get(i2), fragmentManagerState.mBackStackStates.get(i2));
            }
        }
        this.mLaunchedFragments = new ArrayDeque<>(fragmentManagerState.mLaunchedFragments);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public FragmentHostCallback<?> getHost() {
        return this.mHost;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Fragment getParent() {
        return this.mParent;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public FragmentContainer getContainer() {
        return this.mContainer;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public FragmentStore getFragmentStore() {
        return this.mFragmentStore;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @SuppressLint({"SyntheticAccessor"})
    public void attachController(@NonNull FragmentHostCallback<?> fragmentHostCallback, @NonNull FragmentContainer fragmentContainer, @Nullable final Fragment fragment) {
        String str;
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = fragmentHostCallback;
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
        if (fragment != null) {
            addFragmentOnAttachListener(new FragmentOnAttachListener() { // from class: androidx.fragment.app.FragmentManager.7
                @Override // androidx.fragment.app.FragmentOnAttachListener
                public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment2) {
                    fragment.onAttachFragment(fragment2);
                }
            });
        } else if (fragmentHostCallback instanceof FragmentOnAttachListener) {
            addFragmentOnAttachListener((FragmentOnAttachListener) fragmentHostCallback);
        }
        if (this.mParent != null) {
            updateOnBackPressedCallbackEnabled();
        }
        if (fragmentHostCallback instanceof OnBackPressedDispatcherOwner) {
            OnBackPressedDispatcherOwner onBackPressedDispatcherOwner = (OnBackPressedDispatcherOwner) fragmentHostCallback;
            OnBackPressedDispatcher onBackPressedDispatcher = onBackPressedDispatcherOwner.getOnBackPressedDispatcher();
            this.mOnBackPressedDispatcher = onBackPressedDispatcher;
            LifecycleOwner lifecycleOwner = onBackPressedDispatcherOwner;
            if (fragment != null) {
                lifecycleOwner = fragment;
            }
            onBackPressedDispatcher.addCallback(lifecycleOwner, this.mOnBackPressedCallback);
        }
        if (fragment != null) {
            this.mNonConfig = fragment.mFragmentManager.getChildNonConfig(fragment);
        } else if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            this.mNonConfig = FragmentManagerViewModel.getInstance(((ViewModelStoreOwner) fragmentHostCallback).getViewModelStore());
        } else {
            this.mNonConfig = new FragmentManagerViewModel(false);
        }
        this.mNonConfig.setIsStateSaved(isStateSaved());
        this.mFragmentStore.setNonConfig(this.mNonConfig);
        FragmentHostCallback<?> fragmentHostCallback2 = this.mHost;
        if ((fragmentHostCallback2 instanceof SavedStateRegistryOwner) && fragment == null) {
            SavedStateRegistry savedStateRegistry = ((SavedStateRegistryOwner) fragmentHostCallback2).getSavedStateRegistry();
            savedStateRegistry.registerSavedStateProvider(SAVED_STATE_TAG, new SavedStateRegistry.SavedStateProvider() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda4
                @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
                public final Bundle saveState() {
                    Bundle lambda$attachController$4;
                    lambda$attachController$4 = FragmentManager.this.lambda$attachController$4();
                    return lambda$attachController$4;
                }
            });
            Bundle consumeRestoredStateForKey = savedStateRegistry.consumeRestoredStateForKey(SAVED_STATE_TAG);
            if (consumeRestoredStateForKey != null) {
                restoreSaveStateInternal(consumeRestoredStateForKey);
            }
        }
        FragmentHostCallback<?> fragmentHostCallback3 = this.mHost;
        if (fragmentHostCallback3 instanceof ActivityResultRegistryOwner) {
            ActivityResultRegistry activityResultRegistry = ((ActivityResultRegistryOwner) fragmentHostCallback3).getActivityResultRegistry();
            if (fragment != null) {
                str = fragment.mWho + Constants.COMMON_SCHEMA_PREFIX_SEPARATOR;
            } else {
                str = "";
            }
            String str2 = "FragmentManager:" + str;
            this.mStartActivityForResult = activityResultRegistry.register(str2 + "StartActivityForResult", new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: androidx.fragment.app.FragmentManager.8
                @Override // androidx.activity.result.ActivityResultCallback
                public void onActivityResult(ActivityResult activityResult) {
                    LaunchedFragmentInfo pollFirst = FragmentManager.this.mLaunchedFragments.pollFirst();
                    if (pollFirst == null) {
                        Log.w(FragmentManager.TAG, "No Activities were started for result for " + this);
                        return;
                    }
                    String str3 = pollFirst.mWho;
                    int i = pollFirst.mRequestCode;
                    Fragment findFragmentByWho = FragmentManager.this.mFragmentStore.findFragmentByWho(str3);
                    if (findFragmentByWho == null) {
                        Log.w(FragmentManager.TAG, "Activity result delivered for unknown Fragment " + str3);
                        return;
                    }
                    findFragmentByWho.onActivityResult(i, activityResult.getResultCode(), activityResult.getData());
                }
            });
            this.mStartIntentSenderForResult = activityResultRegistry.register(str2 + "StartIntentSenderForResult", new FragmentIntentSenderContract(), new ActivityResultCallback<ActivityResult>() { // from class: androidx.fragment.app.FragmentManager.9
                @Override // androidx.activity.result.ActivityResultCallback
                public void onActivityResult(ActivityResult activityResult) {
                    LaunchedFragmentInfo pollFirst = FragmentManager.this.mLaunchedFragments.pollFirst();
                    if (pollFirst == null) {
                        Log.w(FragmentManager.TAG, "No IntentSenders were started for " + this);
                        return;
                    }
                    String str3 = pollFirst.mWho;
                    int i = pollFirst.mRequestCode;
                    Fragment findFragmentByWho = FragmentManager.this.mFragmentStore.findFragmentByWho(str3);
                    if (findFragmentByWho == null) {
                        Log.w(FragmentManager.TAG, "Intent Sender result delivered for unknown Fragment " + str3);
                        return;
                    }
                    findFragmentByWho.onActivityResult(i, activityResult.getResultCode(), activityResult.getData());
                }
            });
            this.mRequestPermissions = activityResultRegistry.register(str2 + "RequestPermissions", new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() { // from class: androidx.fragment.app.FragmentManager.10
                @Override // androidx.activity.result.ActivityResultCallback
                @SuppressLint({"SyntheticAccessor"})
                public void onActivityResult(Map<String, Boolean> map) {
                    String[] strArr = (String[]) map.keySet().toArray(new String[0]);
                    ArrayList arrayList = new ArrayList(map.values());
                    int[] iArr = new int[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        iArr[i] = ((Boolean) arrayList.get(i)).booleanValue() ? 0 : -1;
                    }
                    LaunchedFragmentInfo pollFirst = FragmentManager.this.mLaunchedFragments.pollFirst();
                    if (pollFirst == null) {
                        Log.w(FragmentManager.TAG, "No permissions were requested for " + this);
                        return;
                    }
                    String str3 = pollFirst.mWho;
                    int i2 = pollFirst.mRequestCode;
                    Fragment findFragmentByWho = FragmentManager.this.mFragmentStore.findFragmentByWho(str3);
                    if (findFragmentByWho == null) {
                        Log.w(FragmentManager.TAG, "Permission request result delivered for unknown Fragment " + str3);
                        return;
                    }
                    findFragmentByWho.onRequestPermissionsResult(i2, strArr, iArr);
                }
            });
        }
        FragmentHostCallback<?> fragmentHostCallback4 = this.mHost;
        if (fragmentHostCallback4 instanceof OnConfigurationChangedProvider) {
            ((OnConfigurationChangedProvider) fragmentHostCallback4).addOnConfigurationChangedListener(this.mOnConfigurationChangedListener);
        }
        FragmentHostCallback<?> fragmentHostCallback5 = this.mHost;
        if (fragmentHostCallback5 instanceof OnTrimMemoryProvider) {
            ((OnTrimMemoryProvider) fragmentHostCallback5).addOnTrimMemoryListener(this.mOnTrimMemoryListener);
        }
        FragmentHostCallback<?> fragmentHostCallback6 = this.mHost;
        if (fragmentHostCallback6 instanceof OnMultiWindowModeChangedProvider) {
            ((OnMultiWindowModeChangedProvider) fragmentHostCallback6).addOnMultiWindowModeChangedListener(this.mOnMultiWindowModeChangedListener);
        }
        FragmentHostCallback<?> fragmentHostCallback7 = this.mHost;
        if (fragmentHostCallback7 instanceof OnPictureInPictureModeChangedProvider) {
            ((OnPictureInPictureModeChangedProvider) fragmentHostCallback7).addOnPictureInPictureModeChangedListener(this.mOnPictureInPictureModeChangedListener);
        }
        FragmentHostCallback<?> fragmentHostCallback8 = this.mHost;
        if ((fragmentHostCallback8 instanceof MenuHost) && fragment == null) {
            ((MenuHost) fragmentHostCallback8).addMenuProvider(this.mMenuProvider);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void noteStateNotSaved() {
        if (this.mHost == null) {
            return;
        }
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.noteStateNotSaved();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void launchStartActivityForResult(@NonNull Fragment fragment, @SuppressLint({"UnknownNullness"}) Intent intent, int i, @Nullable Bundle bundle) {
        if (this.mStartActivityForResult != null) {
            this.mLaunchedFragments.addLast(new LaunchedFragmentInfo(fragment.mWho, i));
            if (intent != null && bundle != null) {
                intent.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundle);
            }
            this.mStartActivityForResult.launch(intent);
            return;
        }
        this.mHost.onStartActivityFromFragment(fragment, intent, i, bundle);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void launchStartIntentSenderForResult(@NonNull Fragment fragment, @SuppressLint({"UnknownNullness"}) IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, @Nullable Bundle bundle) throws IntentSender.SendIntentException {
        Intent intent2;
        if (this.mStartIntentSenderForResult != null) {
            if (bundle != null) {
                if (intent == null) {
                    intent2 = new Intent();
                    intent2.putExtra(EXTRA_CREATED_FILLIN_INTENT, true);
                } else {
                    intent2 = intent;
                }
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "ActivityOptions " + bundle + " were added to fillInIntent " + intent2 + " for fragment " + fragment);
                }
                intent2.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundle);
            } else {
                intent2 = intent;
            }
            IntentSenderRequest build = new IntentSenderRequest.Builder(intentSender).setFillInIntent(intent2).setFlags(i3, i2).build();
            this.mLaunchedFragments.addLast(new LaunchedFragmentInfo(fragment.mWho, i));
            if (isLoggingEnabled(2)) {
                Log.v(TAG, "Fragment " + fragment + "is launching an IntentSender for result ");
            }
            this.mStartIntentSenderForResult.launch(build);
            return;
        }
        this.mHost.onStartIntentSenderFromFragment(fragment, intentSender, i, intent, i2, i3, i4, bundle);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void launchRequestPermissions(@NonNull Fragment fragment, @NonNull String[] strArr, int i) {
        if (this.mRequestPermissions != null) {
            this.mLaunchedFragments.addLast(new LaunchedFragmentInfo(fragment.mWho, i));
            this.mRequestPermissions.launch(strArr);
            return;
        }
        this.mHost.onRequestPermissionsFromFragment(fragment, strArr, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchAttach() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchCreate() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchViewCreated() {
        dispatchStateChange(2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchStart() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(5);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchResume() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(7);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchPause() {
        dispatchStateChange(5);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchStop() {
        this.mStopped = true;
        this.mNonConfig.setIsStateSaved(true);
        dispatchStateChange(4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchDestroyView() {
        dispatchStateChange(1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions(true);
        endAnimatingAwayFragments();
        clearBackStackStateViewModels();
        dispatchStateChange(-1);
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback instanceof OnTrimMemoryProvider) {
            ((OnTrimMemoryProvider) fragmentHostCallback).removeOnTrimMemoryListener(this.mOnTrimMemoryListener);
        }
        FragmentHostCallback<?> fragmentHostCallback2 = this.mHost;
        if (fragmentHostCallback2 instanceof OnConfigurationChangedProvider) {
            ((OnConfigurationChangedProvider) fragmentHostCallback2).removeOnConfigurationChangedListener(this.mOnConfigurationChangedListener);
        }
        FragmentHostCallback<?> fragmentHostCallback3 = this.mHost;
        if (fragmentHostCallback3 instanceof OnMultiWindowModeChangedProvider) {
            ((OnMultiWindowModeChangedProvider) fragmentHostCallback3).removeOnMultiWindowModeChangedListener(this.mOnMultiWindowModeChangedListener);
        }
        FragmentHostCallback<?> fragmentHostCallback4 = this.mHost;
        if (fragmentHostCallback4 instanceof OnPictureInPictureModeChangedProvider) {
            ((OnPictureInPictureModeChangedProvider) fragmentHostCallback4).removeOnPictureInPictureModeChangedListener(this.mOnPictureInPictureModeChangedListener);
        }
        FragmentHostCallback<?> fragmentHostCallback5 = this.mHost;
        if (fragmentHostCallback5 instanceof MenuHost) {
            ((MenuHost) fragmentHostCallback5).removeMenuProvider(this.mMenuProvider);
        }
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
        if (this.mOnBackPressedDispatcher != null) {
            this.mOnBackPressedCallback.remove();
            this.mOnBackPressedDispatcher = null;
        }
        ActivityResultLauncher<Intent> activityResultLauncher = this.mStartActivityForResult;
        if (activityResultLauncher != null) {
            activityResultLauncher.unregister();
            this.mStartIntentSenderForResult.unregister();
            this.mRequestPermissions.unregister();
        }
    }

    private void dispatchStateChange(int i) {
        try {
            this.mExecutingActions = true;
            this.mFragmentStore.dispatchStateChange(i);
            moveToState(i, false);
            for (SpecialEffectsController specialEffectsController : collectAllSpecialEffectsController()) {
                specialEffectsController.forceCompleteAllOperations();
            }
            this.mExecutingActions = false;
            execPendingActions(true);
        } catch (Throwable th) {
            this.mExecutingActions = false;
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchMultiWindowModeChanged(boolean z) {
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performMultiWindowModeChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchPictureInPictureModeChanged(boolean z) {
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performPictureInPictureModeChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchConfigurationChanged(@NonNull Configuration configuration) {
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performConfigurationChanged(configuration);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchLowMemory() {
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performLowMemory();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean dispatchCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        if (this.mCurState < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        boolean z = false;
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && isParentMenuVisible(fragment) && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(fragment);
                z = true;
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i = 0; i < this.mCreatedMenus.size(); i++) {
                Fragment fragment2 = this.mCreatedMenus.get(i);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean dispatchPrepareOptionsMenu(@NonNull Menu menu) {
        boolean z = false;
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && isParentMenuVisible(fragment) && fragment.performPrepareOptionsMenu(menu)) {
                z = true;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean dispatchOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean dispatchContextItemSelected(@NonNull MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && fragment.performContextItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchOptionsMenuClosed(@NonNull Menu menu) {
        if (this.mCurState < 1) {
            return;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performOptionsMenuClosed(menu);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setPrimaryNavigationFragment(@Nullable Fragment fragment) {
        if (fragment != null && (!fragment.equals(findActiveFragment(fragment.mWho)) || (fragment.mHost != null && fragment.mFragmentManager != this))) {
            throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
        }
        Fragment fragment2 = this.mPrimaryNav;
        this.mPrimaryNav = fragment;
        dispatchParentPrimaryNavigationFragmentChanged(fragment2);
        dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
    }

    private void dispatchParentPrimaryNavigationFragmentChanged(@Nullable Fragment fragment) {
        if (fragment == null || !fragment.equals(findActiveFragment(fragment.mWho))) {
            return;
        }
        fragment.performPrimaryNavigationFragmentChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchPrimaryNavigationFragmentChanged() {
        updateOnBackPressedCallbackEnabled();
        dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
    }

    @Nullable
    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setMaxLifecycle(@NonNull Fragment fragment, @NonNull Lifecycle.State state) {
        if (!fragment.equals(findActiveFragment(fragment.mWho)) || (fragment.mHost != null && fragment.mFragmentManager != this)) {
            throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
        }
        fragment.mMaxState = state;
    }

    public void setFragmentFactory(@NonNull FragmentFactory fragmentFactory) {
        this.mFragmentFactory = fragmentFactory;
    }

    @NonNull
    public FragmentFactory getFragmentFactory() {
        FragmentFactory fragmentFactory = this.mFragmentFactory;
        if (fragmentFactory != null) {
            return fragmentFactory;
        }
        Fragment fragment = this.mParent;
        if (fragment != null) {
            return fragment.mFragmentManager.getFragmentFactory();
        }
        return this.mHostFragmentFactory;
    }

    void setSpecialEffectsControllerFactory(@NonNull SpecialEffectsControllerFactory specialEffectsControllerFactory) {
        this.mSpecialEffectsControllerFactory = specialEffectsControllerFactory;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public SpecialEffectsControllerFactory getSpecialEffectsControllerFactory() {
        SpecialEffectsControllerFactory specialEffectsControllerFactory = this.mSpecialEffectsControllerFactory;
        if (specialEffectsControllerFactory != null) {
            return specialEffectsControllerFactory;
        }
        Fragment fragment = this.mParent;
        if (fragment != null) {
            return fragment.mFragmentManager.getSpecialEffectsControllerFactory();
        }
        return this.mDefaultSpecialEffectsControllerFactory;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public FragmentLifecycleCallbacksDispatcher getLifecycleCallbacksDispatcher() {
        return this.mLifecycleCallbacksDispatcher;
    }

    public void registerFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
        this.mLifecycleCallbacksDispatcher.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, z);
    }

    public void unregisterFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        this.mLifecycleCallbacksDispatcher.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
    }

    public void addFragmentOnAttachListener(@NonNull FragmentOnAttachListener fragmentOnAttachListener) {
        this.mOnAttachListeners.add(fragmentOnAttachListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchOnAttachFragment(@NonNull Fragment fragment) {
        Iterator<FragmentOnAttachListener> it = this.mOnAttachListeners.iterator();
        while (it.hasNext()) {
            it.next().onAttachFragment(this, fragment);
        }
    }

    public void removeFragmentOnAttachListener(@NonNull FragmentOnAttachListener fragmentOnAttachListener) {
        this.mOnAttachListeners.remove(fragmentOnAttachListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dispatchOnHiddenChanged() {
        for (Fragment fragment : this.mFragmentStore.getActiveFragments()) {
            if (fragment != null) {
                fragment.onHiddenChanged(fragment.isHidden());
                fragment.mChildFragmentManager.dispatchOnHiddenChanged();
            }
        }
    }

    boolean checkForMenus() {
        boolean z = false;
        for (Fragment fragment : this.mFragmentStore.getActiveFragments()) {
            if (fragment != null) {
                z = isMenuAvailable(fragment);
                continue;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    private boolean isMenuAvailable(@NonNull Fragment fragment) {
        return (fragment.mHasMenu && fragment.mMenuVisible) || fragment.mChildFragmentManager.checkForMenus();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void invalidateMenuForFragment(@NonNull Fragment fragment) {
        if (fragment.mAdded && isMenuAvailable(fragment)) {
            this.mNeedMenuInvalidate = true;
        }
    }

    private boolean isParentAdded() {
        Fragment fragment = this.mParent;
        if (fragment == null) {
            return true;
        }
        return fragment.isAdded() && this.mParent.getParentFragmentManager().isParentAdded();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this.mLayoutInflaterFactory;
    }

    @Nullable
    public FragmentStrictMode.Policy getStrictModePolicy() {
        return this.mStrictModePolicy;
    }

    public void setStrictModePolicy(@Nullable FragmentStrictMode.Policy policy) {
        this.mStrictModePolicy = policy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class PopBackStackState implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        PopBackStackState(@Nullable String str, int i, int i2) {
            this.mName = str;
            this.mId = i;
            this.mFlags = i2;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
            Fragment fragment = FragmentManager.this.mPrimaryNav;
            if (fragment == null || this.mId >= 0 || this.mName != null || !fragment.getChildFragmentManager().popBackStackImmediate()) {
                return FragmentManager.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
            }
            return false;
        }
    }

    /* loaded from: classes.dex */
    private class RestoreBackStackState implements OpGenerator {
        private final String mName;

        RestoreBackStackState(@NonNull String str) {
            this.mName = str;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
            return FragmentManager.this.restoreBackStackState(arrayList, arrayList2, this.mName);
        }
    }

    /* loaded from: classes.dex */
    private class SaveBackStackState implements OpGenerator {
        private final String mName;

        SaveBackStackState(@NonNull String str) {
            this.mName = str;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
            return FragmentManager.this.saveBackStackState(arrayList, arrayList2, this.mName);
        }
    }

    /* loaded from: classes.dex */
    private class ClearBackStackState implements OpGenerator {
        private final String mName;

        ClearBackStackState(@NonNull String str) {
            this.mName = str;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
            return FragmentManager.this.clearBackStackState(arrayList, arrayList2, this.mName);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @SuppressLint({"BanParcelableUsage"})
    /* loaded from: classes.dex */
    public static class LaunchedFragmentInfo implements Parcelable {
        public static final Parcelable.Creator<LaunchedFragmentInfo> CREATOR = new Parcelable.Creator<LaunchedFragmentInfo>() { // from class: androidx.fragment.app.FragmentManager.LaunchedFragmentInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LaunchedFragmentInfo createFromParcel(Parcel parcel) {
                return new LaunchedFragmentInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LaunchedFragmentInfo[] newArray(int i) {
                return new LaunchedFragmentInfo[i];
            }
        };
        int mRequestCode;
        String mWho;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        LaunchedFragmentInfo(@NonNull String str, int i) {
            this.mWho = str;
            this.mRequestCode = i;
        }

        LaunchedFragmentInfo(@NonNull Parcel parcel) {
            this.mWho = parcel.readString();
            this.mRequestCode = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mWho);
            parcel.writeInt(this.mRequestCode);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class FragmentIntentSenderContract extends ActivityResultContract<IntentSenderRequest, ActivityResult> {
        FragmentIntentSenderContract() {
        }

        @Override // androidx.activity.result.contract.ActivityResultContract
        @NonNull
        public Intent createIntent(@NonNull Context context, IntentSenderRequest intentSenderRequest) {
            Bundle bundleExtra;
            Intent intent = new Intent(ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST);
            Intent fillInIntent = intentSenderRequest.getFillInIntent();
            if (fillInIntent != null && (bundleExtra = fillInIntent.getBundleExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE)) != null) {
                intent.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundleExtra);
                fillInIntent.removeExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE);
                if (fillInIntent.getBooleanExtra(FragmentManager.EXTRA_CREATED_FILLIN_INTENT, false)) {
                    intentSenderRequest = new IntentSenderRequest.Builder(intentSenderRequest.getIntentSender()).setFillInIntent(null).setFlags(intentSenderRequest.getFlagsValues(), intentSenderRequest.getFlagsMask()).build();
                }
            }
            intent.putExtra(ActivityResultContracts.StartIntentSenderForResult.EXTRA_INTENT_SENDER_REQUEST, intentSenderRequest);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(FragmentManager.TAG, "CreateIntent created the following intent: " + intent);
            }
            return intent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.activity.result.contract.ActivityResultContract
        @NonNull
        public ActivityResult parseResult(int i, @Nullable Intent intent) {
            return new ActivityResult(i, intent);
        }
    }
}
