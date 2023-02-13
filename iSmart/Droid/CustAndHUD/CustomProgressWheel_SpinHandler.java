package iSmart.Droid.CustAndHUD;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class CustomProgressWheel_SpinHandler extends Handler implements IGCUserPeer {
    public static final String __md_methods = "n_handleMessage:(Landroid/os/Message;)V:GetHandleMessage_Landroid_os_Message_Handler\n";
    private ArrayList refList;

    private native void n_handleMessage(Message message);

    static {
        Runtime.register("iSmart.Droid.CustAndHUD.CustomProgressWheel+SpinHandler, iSmart.Android", CustomProgressWheel_SpinHandler.class, "n_handleMessage:(Landroid/os/Message;)V:GetHandleMessage_Landroid_os_Message_Handler\n");
    }

    public CustomProgressWheel_SpinHandler() {
        if (getClass() == CustomProgressWheel_SpinHandler.class) {
            TypeManager.Activate("iSmart.Droid.CustAndHUD.CustomProgressWheel+SpinHandler, iSmart.Android", "", this, new Object[0]);
        }
    }

    public CustomProgressWheel_SpinHandler(Handler.Callback callback) {
        super(callback);
        if (getClass() == CustomProgressWheel_SpinHandler.class) {
            TypeManager.Activate("iSmart.Droid.CustAndHUD.CustomProgressWheel+SpinHandler, iSmart.Android", "Android.OS.Handler+ICallback, Mono.Android", this, new Object[]{callback});
        }
    }

    public CustomProgressWheel_SpinHandler(Looper looper) {
        super(looper);
        if (getClass() == CustomProgressWheel_SpinHandler.class) {
            TypeManager.Activate("iSmart.Droid.CustAndHUD.CustomProgressWheel+SpinHandler, iSmart.Android", "Android.OS.Looper, Mono.Android", this, new Object[]{looper});
        }
    }

    public CustomProgressWheel_SpinHandler(Looper looper, Handler.Callback callback) {
        super(looper, callback);
        if (getClass() == CustomProgressWheel_SpinHandler.class) {
            TypeManager.Activate("iSmart.Droid.CustAndHUD.CustomProgressWheel+SpinHandler, iSmart.Android", "Android.OS.Looper, Mono.Android:Android.OS.Handler+ICallback, Mono.Android", this, new Object[]{looper, callback});
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        n_handleMessage(message);
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
