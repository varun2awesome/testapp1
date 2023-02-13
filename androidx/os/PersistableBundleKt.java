package androidx.core.os;

import android.os.PersistableBundle;
import androidx.annotation.RequiresApi;
import java.util.Map;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
/* compiled from: PersistableBundle.kt */
/* loaded from: classes.dex */
public final class PersistableBundleKt {
    @RequiresApi(21)
    @NotNull
    public static final PersistableBundle persistableBundleOf(@NotNull Pair<String, ? extends Object>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        PersistableBundle createPersistableBundle = PersistableBundleApi21ImplKt.createPersistableBundle(pairs.length);
        for (Pair<String, ? extends Object> pair : pairs) {
            PersistableBundleApi21ImplKt.putValue(createPersistableBundle, pair.component1(), pair.component2());
        }
        return createPersistableBundle;
    }

    @RequiresApi(21)
    @NotNull
    public static final PersistableBundle persistableBundleOf() {
        return PersistableBundleApi21ImplKt.createPersistableBundle(0);
    }

    @RequiresApi(21)
    @NotNull
    public static final PersistableBundle toPersistableBundle(@NotNull Map<String, ? extends Object> map) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        PersistableBundle createPersistableBundle = PersistableBundleApi21ImplKt.createPersistableBundle(map.size());
        for (Map.Entry<String, ? extends Object> entry : map.entrySet()) {
            PersistableBundleApi21ImplKt.putValue(createPersistableBundle, entry.getKey(), entry.getValue());
        }
        return createPersistableBundle;
    }
}
