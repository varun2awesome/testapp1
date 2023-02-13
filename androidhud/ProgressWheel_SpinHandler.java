package androidhud;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class ProgressWheel_SpinHandler extends Handler implements IGCUserPeer {
    public static final String __md_methods = "n_handleMessage:(Landroid/os/Message;)V:GetHandleMessage_Landroid_os_Message_Handler\n";
    private ArrayList refList;

    private native void n_handleMessage(Message message);

    static {
        Runtime.register("AndroidHUD.ProgressWheel+SpinHandler, AndHUD", ProgressWheel_SpinHandler.class, "n_handleMessage:(Landroid/os/Message;)V:GetHandleMessage_Landroid_os_Message_Handler\n");
    }

    public ProgressWheel_SpinHandler() {
        if (getClass() == ProgressWheel_SpinHandler.class) {
            TypeManager.Activate("AndroidHUD.ProgressWheel+SpinHandler, AndHUD", "", this, new Object[0]);
        }
    }

    public ProgressWheel_SpinHandler(Handler.Callback callback) {
        super(callback);
        if (getClass() == ProgressWheel_SpinHandler.class) {
            TypeManager.Activate("AndroidHUD.ProgressWheel+SpinHandler, AndHUD", "Android.OS.Handler+ICallback, Mono.Android", this, new Object[]{callback});
        }
    }

    public ProgressWheel_SpinHandler(Looper looper) {
        super(looper);
        if (getClass() == ProgressWheel_SpinHandler.class) {
            TypeManager.Activate("AndroidHUD.ProgressWheel+SpinHandler, AndHUD", "Android.OS.Looper, Mono.Android", this, new Object[]{looper});
        }
    }

    public ProgressWheel_SpinHandler(Looper looper, Handler.Callback callback) {
        super(looper, callback);
        if (getClass() == ProgressWheel_SpinHandler.class) {
            TypeManager.Activate("AndroidHUD.ProgressWheel+SpinHandler, AndHUD", "Android.OS.Looper, Mono.Android:Android.OS.Handler+ICallback, Mono.Android", this, new Object[]{looper, callback});
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
