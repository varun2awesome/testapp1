package crc648c34a0f6d00cceae;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class MyMessagingService extends FirebaseMessagingService implements IGCUserPeer {
    public static final String __md_methods = "n_onMessageReceived:(Lcom/google/firebase/messaging/RemoteMessage;)V:GetOnMessageReceived_Lcom_google_firebase_messaging_RemoteMessage_Handler\n";
    private ArrayList refList;

    private native void n_onMessageReceived(RemoteMessage remoteMessage);

    static {
        Runtime.register("iSmart.Droid.MyMessagingService, iSmart.Android", MyMessagingService.class, __md_methods);
    }

    public MyMessagingService() {
        if (getClass() == MyMessagingService.class) {
            TypeManager.Activate("iSmart.Droid.MyMessagingService, iSmart.Android", "", this, new Object[0]);
        }
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        n_onMessageReceived(remoteMessage);
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
