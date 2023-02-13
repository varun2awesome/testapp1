package crc64b75d9ddab39d6c30;

import android.util.LruCache;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class LRUCache extends LruCache implements IGCUserPeer {
    public static final String __md_methods = "n_sizeOf:(Ljava/lang/Object;Ljava/lang/Object;)I:GetSizeOf_Ljava_lang_Object_Ljava_lang_Object_Handler\nn_entryRemoved:(ZLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V:GetEntryRemoved_ZLjava_lang_Object_Ljava_lang_Object_Ljava_lang_Object_Handler\n";
    private ArrayList refList;

    private native void n_entryRemoved(boolean z, Object obj, Object obj2, Object obj3);

    private native int n_sizeOf(Object obj, Object obj2);

    static {
        Runtime.register("FFImageLoading.Cache.LRUCache, FFImageLoading.Platform", LRUCache.class, __md_methods);
    }

    public LRUCache(int i) {
        super(i);
        if (getClass() == LRUCache.class) {
            TypeManager.Activate("FFImageLoading.Cache.LRUCache, FFImageLoading.Platform", "System.Int32, mscorlib", this, new Object[]{Integer.valueOf(i)});
        }
    }

    @Override // android.util.LruCache
    public int sizeOf(Object obj, Object obj2) {
        return n_sizeOf(obj, obj2);
    }

    @Override // android.util.LruCache
    public void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
        n_entryRemoved(z, obj, obj2, obj3);
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
