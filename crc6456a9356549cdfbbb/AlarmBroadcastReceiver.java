package crc6456a9356549cdfbbb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class AlarmBroadcastReceiver extends BroadcastReceiver implements IGCUserPeer {
    public static final String __md_methods = "n_onReceive:(Landroid/content/Context;Landroid/content/Intent;)V:GetOnReceive_Landroid_content_Context_Landroid_content_Intent_Handler\n";
    private ArrayList refList;

    private native void n_onReceive(Context context, Intent intent);

    static {
        Runtime.register("Plugin.Notifications.AlarmBroadcastReceiver, Plugin.Notifications", AlarmBroadcastReceiver.class, "n_onReceive:(Landroid/content/Context;Landroid/content/Intent;)V:GetOnReceive_Landroid_content_Context_Landroid_content_Intent_Handler\n");
    }

    public AlarmBroadcastReceiver() {
        if (getClass() == AlarmBroadcastReceiver.class) {
            TypeManager.Activate("Plugin.Notifications.AlarmBroadcastReceiver, Plugin.Notifications", "", this, new Object[0]);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        n_onReceive(context, intent);
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
