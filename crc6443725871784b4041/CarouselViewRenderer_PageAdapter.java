package crc6443725871784b4041;

import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class CarouselViewRenderer_PageAdapter extends PagerAdapter implements IGCUserPeer {
    public static final String __md_methods = "n_getCount:()I:GetGetCountHandler\nn_isViewFromObject:(Landroid/view/View;Ljava/lang/Object;)Z:GetIsViewFromObject_Landroid_view_View_Ljava_lang_Object_Handler\nn_instantiateItem:(Landroid/view/ViewGroup;I)Ljava/lang/Object;:GetInstantiateItem_Landroid_view_ViewGroup_IHandler\nn_destroyItem:(Landroid/view/ViewGroup;ILjava/lang/Object;)V:GetDestroyItem_Landroid_view_ViewGroup_ILjava_lang_Object_Handler\nn_getItemPosition:(Ljava/lang/Object;)I:GetGetItemPosition_Ljava_lang_Object_Handler\n";
    private ArrayList refList;

    private native void n_destroyItem(ViewGroup viewGroup, int i, Object obj);

    private native int n_getCount();

    private native int n_getItemPosition(Object obj);

    private native Object n_instantiateItem(ViewGroup viewGroup, int i);

    private native boolean n_isViewFromObject(View view, Object obj);

    static {
        Runtime.register("CarouselView.FormsPlugin.Android.CarouselViewRenderer+PageAdapter, CarouselView.FormsPlugin.Android", CarouselViewRenderer_PageAdapter.class, __md_methods);
    }

    public CarouselViewRenderer_PageAdapter() {
        if (getClass() == CarouselViewRenderer_PageAdapter.class) {
            TypeManager.Activate("CarouselView.FormsPlugin.Android.CarouselViewRenderer+PageAdapter, CarouselView.FormsPlugin.Android", "", this, new Object[0]);
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return n_getCount();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return n_isViewFromObject(view, obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        return n_instantiateItem(viewGroup, i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        n_destroyItem(viewGroup, i, obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        return n_getItemPosition(obj);
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
