package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
/* compiled from: InitializerViewModelFactory.kt */
@ViewModelFactoryDsl
/* loaded from: classes.dex */
public final class InitializerViewModelFactoryBuilder {
    @NotNull
    private final List<ViewModelInitializer<?>> initializers = new ArrayList();

    public final <T extends ViewModel> void addInitializer(@NotNull KClass<T> clazz, @NotNull Function1<? super CreationExtras, ? extends T> initializer) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        this.initializers.add(new ViewModelInitializer<>(JvmClassMappingKt.getJavaClass((KClass) clazz), initializer));
    }

    @NotNull
    public final ViewModelProvider.Factory build() {
        Object[] array = this.initializers.toArray(new ViewModelInitializer[0]);
        if (array != null) {
            ViewModelInitializer[] viewModelInitializerArr = (ViewModelInitializer[]) array;
            return new InitializerViewModelFactory((ViewModelInitializer[]) Arrays.copyOf(viewModelInitializerArr, viewModelInitializerArr.length));
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    }
}
