package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
/* compiled from: TextView.kt */
/* loaded from: classes.dex */
public final class TextViewKt {
    public static /* synthetic */ TextWatcher addTextChangedListener$default(TextView textView, Function4 beforeTextChanged, Function4 onTextChanged, Function1 afterTextChanged, int i, Object obj) {
        if ((i & 1) != 0) {
            beforeTextChanged = TextViewKt$addTextChangedListener$1.INSTANCE;
        }
        if ((i & 2) != 0) {
            onTextChanged = TextViewKt$addTextChangedListener$2.INSTANCE;
        }
        if ((i & 4) != 0) {
            afterTextChanged = TextViewKt$addTextChangedListener$3.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(beforeTextChanged, "beforeTextChanged");
        Intrinsics.checkNotNullParameter(onTextChanged, "onTextChanged");
        Intrinsics.checkNotNullParameter(afterTextChanged, "afterTextChanged");
        TextViewKt$addTextChangedListener$textWatcher$1 textViewKt$addTextChangedListener$textWatcher$1 = new TextViewKt$addTextChangedListener$textWatcher$1(afterTextChanged, beforeTextChanged, onTextChanged);
        textView.addTextChangedListener(textViewKt$addTextChangedListener$textWatcher$1);
        return textViewKt$addTextChangedListener$textWatcher$1;
    }

    @NotNull
    public static final TextWatcher addTextChangedListener(@NotNull TextView textView, @NotNull Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> beforeTextChanged, @NotNull Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> onTextChanged, @NotNull Function1<? super Editable, Unit> afterTextChanged) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(beforeTextChanged, "beforeTextChanged");
        Intrinsics.checkNotNullParameter(onTextChanged, "onTextChanged");
        Intrinsics.checkNotNullParameter(afterTextChanged, "afterTextChanged");
        TextViewKt$addTextChangedListener$textWatcher$1 textViewKt$addTextChangedListener$textWatcher$1 = new TextViewKt$addTextChangedListener$textWatcher$1(afterTextChanged, beforeTextChanged, onTextChanged);
        textView.addTextChangedListener(textViewKt$addTextChangedListener$textWatcher$1);
        return textViewKt$addTextChangedListener$textWatcher$1;
    }

    @NotNull
    public static final TextWatcher doBeforeTextChanged(@NotNull TextView textView, @NotNull final Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> action) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        TextWatcher textWatcher = new TextWatcher() { // from class: androidx.core.widget.TextViewKt$doBeforeTextChanged$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence charSequence, int i, int i2, int i3) {
                Function4.this.invoke(charSequence, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
            }
        };
        textView.addTextChangedListener(textWatcher);
        return textWatcher;
    }

    @NotNull
    public static final TextWatcher doOnTextChanged(@NotNull TextView textView, @NotNull final Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> action) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        TextWatcher textWatcher = new TextWatcher() { // from class: androidx.core.widget.TextViewKt$doOnTextChanged$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence charSequence, int i, int i2, int i3) {
                Function4.this.invoke(charSequence, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
            }
        };
        textView.addTextChangedListener(textWatcher);
        return textWatcher;
    }

    @NotNull
    public static final TextWatcher doAfterTextChanged(@NotNull TextView textView, @NotNull final Function1<? super Editable, Unit> action) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        TextWatcher textWatcher = new TextWatcher() { // from class: androidx.core.widget.TextViewKt$doAfterTextChanged$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable editable) {
                Function1.this.invoke(editable);
            }
        };
        textView.addTextChangedListener(textWatcher);
        return textWatcher;
    }
}
