package androidx.core.widget;

import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;
/* compiled from: TextView.kt */
/* loaded from: classes.dex */
public final class TextViewKt$addTextChangedListener$2 extends Lambda implements Function4<CharSequence, Integer, Integer, Integer, Unit> {
    public static final TextViewKt$addTextChangedListener$2 INSTANCE = new TextViewKt$addTextChangedListener$2();

    public TextViewKt$addTextChangedListener$2() {
        super(4);
    }

    public final void invoke(@Nullable CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ Unit invoke(CharSequence charSequence, Integer num, Integer num2, Integer num3) {
        invoke(charSequence, num.intValue(), num2.intValue(), num3.intValue());
        return Unit.INSTANCE;
    }
}
