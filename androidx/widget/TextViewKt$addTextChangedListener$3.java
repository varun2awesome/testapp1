package androidx.core.widget;

import android.text.Editable;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;
/* compiled from: TextView.kt */
/* loaded from: classes.dex */
public final class TextViewKt$addTextChangedListener$3 extends Lambda implements Function1<Editable, Unit> {
    public static final TextViewKt$addTextChangedListener$3 INSTANCE = new TextViewKt$addTextChangedListener$3();

    public TextViewKt$addTextChangedListener$3() {
        super(1);
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@Nullable Editable editable) {
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Editable editable) {
        invoke2(editable);
        return Unit.INSTANCE;
    }
}
