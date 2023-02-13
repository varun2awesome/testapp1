package androidx.navigation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
/* loaded from: classes.dex */
class NavControllerViewModel extends ViewModel {
    private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() { // from class: androidx.navigation.NavControllerViewModel.1
        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public /* synthetic */ ViewModel create(Class cls, CreationExtras creationExtras) {
            return ViewModelProvider.Factory.CC.$default$create(this, cls, creationExtras);
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> cls) {
            return new NavControllerViewModel();
        }
    };
    private final HashMap<UUID, ViewModelStore> mViewModelStores = new HashMap<>();

    NavControllerViewModel() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static NavControllerViewModel getInstance(ViewModelStore viewModelStore) {
        return (NavControllerViewModel) new ViewModelProvider(viewModelStore, FACTORY).get(NavControllerViewModel.class);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void clear(@NonNull UUID uuid) {
        ViewModelStore remove = this.mViewModelStores.remove(uuid);
        if (remove != null) {
            remove.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        for (ViewModelStore viewModelStore : this.mViewModelStores.values()) {
            viewModelStore.clear();
        }
        this.mViewModelStores.clear();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public ViewModelStore getViewModelStore(@NonNull UUID uuid) {
        ViewModelStore viewModelStore = this.mViewModelStores.get(uuid);
        if (viewModelStore == null) {
            ViewModelStore viewModelStore2 = new ViewModelStore();
            this.mViewModelStores.put(uuid, viewModelStore2);
            return viewModelStore2;
        }
        return viewModelStore;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("NavControllerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} ViewModelStores (");
        Iterator<UUID> it = this.mViewModelStores.keySet().iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
