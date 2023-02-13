package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import org.jetbrains.annotations.Nullable;
/* compiled from: TextView.kt */
/* loaded from: classes.dex */
public final class TextViewKt$addTextChangedListener$textWatcher$1 implements TextWatcher {
    final /* synthetic */ Function1<Editable, Unit> $afterTextChanged;
    final /* synthetic */ Function4<CharSequence, Integer, Integer, Integer, Unit> $beforeTextChanged;
    final /* synthetic */ Function4<CharSequence, Integer, Integer, Integer, Unit> $onTextChanged;

    /* JADX WARN: Multi-variable type inference failed */
    public TextViewKt$addTextChangedListener$textWatcher$1(Function1<? super Editable, Unit> function1, Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> function4, Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> function42) {
        this.$afterTextChanged = function1;
        this.$beforeTextChanged = function4;
        this.$onTextChanged = function42;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(@Nullable Editable editable) {
        this.$afterTextChanged.invoke(editable);
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(@Nullable CharSequence charSequence, int i, int i2, int i3) {
        this.$beforeTextChanged.invoke(charSequence, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(@Nullable CharSequence charSequence, int i, int i2, int i3) {
        this.$onTextChanged.invoke(charSequence, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }
}
