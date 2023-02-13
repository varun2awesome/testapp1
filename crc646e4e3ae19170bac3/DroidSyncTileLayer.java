package crc646e4e3ae19170bac3;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class DroidSyncTileLayer implements IGCUserPeer, TileProvider {
    public static final String __md_methods = "n_getTile:(III)Lcom/google/android/gms/maps/model/Tile;:GetGetTile_IIIHandler:Android.Gms.Maps.Model.ITileProviderInvoker, Xamarin.GooglePlayServices.Maps\n";
    private ArrayList refList;

    private native Tile n_getTile(int i, int i2, int i3);

    static {
        Runtime.register("Xamarin.Forms.GoogleMaps.Android.DroidSyncTileLayer, Xamarin.Forms.GoogleMaps.Android", DroidSyncTileLayer.class, "n_getTile:(III)Lcom/google/android/gms/maps/model/Tile;:GetGetTile_IIIHandler:Android.Gms.Maps.Model.ITileProviderInvoker, Xamarin.GooglePlayServices.Maps\n");
    }

    public DroidSyncTileLayer() {
        if (getClass() == DroidSyncTileLayer.class) {
            TypeManager.Activate("Xamarin.Forms.GoogleMaps.Android.DroidSyncTileLayer, Xamarin.Forms.GoogleMaps.Android", "", this, new Object[0]);
        }
    }

    @Override // com.google.android.gms.maps.model.TileProvider
    public Tile getTile(int i, int i2, int i3) {
        return n_getTile(i, i2, i3);
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
