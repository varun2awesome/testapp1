package xamarin.essentials;

import androidx.core.content.FileProvider;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes2.dex */
public class fileProvider extends FileProvider implements IGCUserPeer {
    public static final String __md_methods = "";
    private ArrayList refList;

    static {
        Runtime.register("Xamarin.Essentials.FileProvider, Xamarin.Essentials", fileProvider.class, "");
    }

    public fileProvider() {
        if (getClass() == fileProvider.class) {
            TypeManager.Activate("Xamarin.Essentials.FileProvider, Xamarin.Essentials", "", this, new Object[0]);
        }
    }

    public fileProvider(int i) {
        super(i);
        if (getClass() == fileProvider.class) {
            TypeManager.Activate("Xamarin.Essentials.FileProvider, Xamarin.Essentials", "System.Int32, mscorlib", this, new Object[]{Integer.valueOf(i)});
        }
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
