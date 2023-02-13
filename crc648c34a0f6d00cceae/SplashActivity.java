package crc648c34a0f6d00cceae;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class SplashActivity extends AppCompatActivity implements IGCUserPeer {
    public static final String __md_methods = "n_onResume:()V:GetOnResumeHandler\n";
    private ArrayList refList;

    private native void n_onResume();

    static {
        Runtime.register("iSmart.Droid.SplashActivity, iSmart.Android", SplashActivity.class, __md_methods);
    }

    public SplashActivity() {
        if (getClass() == SplashActivity.class) {
            TypeManager.Activate("iSmart.Droid.SplashActivity, iSmart.Android", "", this, new Object[0]);
        }
    }

    public SplashActivity(int i) {
        super(i);
        if (getClass() == SplashActivity.class) {
            TypeManager.Activate("iSmart.Droid.SplashActivity, iSmart.Android", "System.Int32, mscorlib", this, new Object[]{Integer.valueOf(i)});
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        n_onResume();
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
