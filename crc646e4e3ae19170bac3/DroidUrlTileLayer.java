package crc646e4e3ae19170bac3;

import com.google.android.gms.maps.model.UrlTileProvider;
import java.net.URL;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class DroidUrlTileLayer extends UrlTileProvider implements IGCUserPeer {
    public static final String __md_methods = "n_getTileUrl:(III)Ljava/net/URL;:GetGetTileUrl_IIIHandler\n";
    private ArrayList refList;

    private native URL n_getTileUrl(int i, int i2, int i3);

    static {
        Runtime.register("Xamarin.Forms.GoogleMaps.Android.DroidUrlTileLayer, Xamarin.Forms.GoogleMaps.Android", DroidUrlTileLayer.class, __md_methods);
    }

    public DroidUrlTileLayer(int i, int i2) {
        super(i, i2);
        if (getClass() == DroidUrlTileLayer.class) {
            TypeManager.Activate("Xamarin.Forms.GoogleMaps.Android.DroidUrlTileLayer, Xamarin.Forms.GoogleMaps.Android", "System.Int32, mscorlib:System.Int32, mscorlib", this, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
        }
    }

    @Override // com.google.android.gms.maps.model.UrlTileProvider
    public URL getTileUrl(int i, int i2, int i3) {
        return n_getTileUrl(i, i2, i3);
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
