package crc64a25b61d9f8ee364f;

import android.animation.TypeEvaluator;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class TransitionUtils_MatrixEvaluator implements IGCUserPeer, TypeEvaluator {
    public static final String __md_methods = "n_evaluate:(FLjava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;:GetEvaluate_FLjava_lang_Object_Ljava_lang_Object_Handler:Android.Animation.ITypeEvaluatorInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n";
    private ArrayList refList;

    private native Object n_evaluate(float f, Object obj, Object obj2);

    static {
        Runtime.register("AndroidX.Transitions.TransitionUtils+MatrixEvaluator, Xamarin.AndroidX.Transition", TransitionUtils_MatrixEvaluator.class, "n_evaluate:(FLjava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;:GetEvaluate_FLjava_lang_Object_Ljava_lang_Object_Handler:Android.Animation.ITypeEvaluatorInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n");
    }

    public TransitionUtils_MatrixEvaluator() {
        if (getClass() == TransitionUtils_MatrixEvaluator.class) {
            TypeManager.Activate("AndroidX.Transitions.TransitionUtils+MatrixEvaluator, Xamarin.AndroidX.Transition", "", this, new Object[0]);
        }
    }

    @Override // android.animation.TypeEvaluator
    public Object evaluate(float f, Object obj, Object obj2) {
        return n_evaluate(f, obj, obj2);
    }

    @Override // mono.android.IGCUserPeer
    public void monodroidAddReference(Object obj) {
        if (this.refList == null) {
            this.refList = new ArrayList();
        }
        this.refList.add(obj);
    }

    @Override // mono.android.IGCUserPeer
    public void monodroidClearReferences() {
        ArrayList arrayList = this.refList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
