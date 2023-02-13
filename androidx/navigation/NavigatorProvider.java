package androidx.navigation;

import android.annotation.SuppressLint;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigator;
import java.util.HashMap;
import java.util.Map;
@SuppressLint({"TypeParameterUnusedInFormals"})
/* loaded from: classes.dex */
public class NavigatorProvider {
    private static final HashMap<Class<?>, String> sAnnotationNames = new HashMap<>();
    private final HashMap<String, Navigator<? extends NavDestination>> mNavigators = new HashMap<>();

    private static boolean validateName(String str) {
        return (str == null || str.isEmpty()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static String getNameForNavigator(@NonNull Class<? extends Navigator> cls) {
        HashMap<Class<?>, String> hashMap = sAnnotationNames;
        String str = hashMap.get(cls);
        if (str == null) {
            Navigator.Name name = (Navigator.Name) cls.getAnnotation(Navigator.Name.class);
            str = name != null ? name.value() : null;
            if (!validateName(str)) {
                throw new IllegalArgumentException("No @Navigator.Name annotation found for " + cls.getSimpleName());
            }
            hashMap.put(cls, str);
        }
        return str;
    }

    @NonNull
    public final <T extends Navigator<?>> T getNavigator(@NonNull Class<T> cls) {
        return (T) getNavigator(getNameForNavigator(cls));
    }

    @NonNull
    @CallSuper
    public <T extends Navigator<?>> T getNavigator(@NonNull String str) {
        if (!validateName(str)) {
            throw new IllegalArgumentException("navigator name cannot be an empty string");
        }
        Navigator<? extends NavDestination> navigator = this.mNavigators.get(str);
        if (navigator != null) {
            return navigator;
        }
        throw new IllegalStateException("Could not find Navigator with name \"" + str + "\". You must call NavController.addNavigator() for each navigation type.");
    }

    @Nullable
    public final Navigator<? extends NavDestination> addNavigator(@NonNull Navigator<? extends NavDestination> navigator) {
        return addNavigator(getNameForNavigator(navigator.getClass()), navigator);
    }

    @Nullable
    @CallSuper
    public Navigator<? extends NavDestination> addNavigator(@NonNull String str, @NonNull Navigator<? extends NavDestination> navigator) {
        if (!validateName(str)) {
            throw new IllegalArgumentException("navigator name cannot be an empty string");
        }
        return this.mNavigators.put(str, navigator);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Map<String, Navigator<? extends NavDestination>> getNavigators() {
        return this.mNavigators;
    }
}
