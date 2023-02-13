package crc64350623dcb797cc38;

import com.microsoft.appcenter.http.HttpClient;
import com.microsoft.appcenter.http.ServiceCallback;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Map;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class AndroidHttpClientAdapter implements IGCUserPeer, HttpClient, Closeable {
    public static final String __md_methods = "n_callAsync:(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Lcom/microsoft/appcenter/http/HttpClient$CallTemplate;Lcom/microsoft/appcenter/http/ServiceCallback;)Lcom/microsoft/appcenter/http/ServiceCall;:GetCallAsync_Ljava_lang_String_Ljava_lang_String_Ljava_util_Map_Lcom_microsoft_appcenter_http_HttpClient_CallTemplate_Lcom_microsoft_appcenter_http_ServiceCallback_Handler:Com.Microsoft.Appcenter.Http.IAndroidHttpClientInvoker, Microsoft.AppCenter.Android.Bindings\nn_reopen:()V:GetReopenHandler:Com.Microsoft.Appcenter.Http.IAndroidHttpClientInvoker, Microsoft.AppCenter.Android.Bindings\nn_close:()V:GetCloseHandler:Java.IO.ICloseableInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n";
    private ArrayList refList;

    private native com.microsoft.appcenter.http.ServiceCall n_callAsync(String str, String str2, Map map, HttpClient.CallTemplate callTemplate, ServiceCallback serviceCallback);

    private native void n_close();

    private native void n_reopen();

    static {
        Runtime.register("Microsoft.AppCenter.AndroidHttpClientAdapter, Microsoft.AppCenter", AndroidHttpClientAdapter.class, __md_methods);
    }

    public AndroidHttpClientAdapter() {
        if (getClass() == AndroidHttpClientAdapter.class) {
            TypeManager.Activate("Microsoft.AppCenter.AndroidHttpClientAdapter, Microsoft.AppCenter", "", this, new Object[0]);
        }
    }

    @Override // com.microsoft.appcenter.http.HttpClient
    public com.microsoft.appcenter.http.ServiceCall callAsync(String str, String str2, Map map, HttpClient.CallTemplate callTemplate, ServiceCallback serviceCallback) {
        return n_callAsync(str, str2, map, callTemplate, serviceCallback);
    }

    @Override // com.microsoft.appcenter.http.HttpClient
    public void reopen() {
        n_reopen();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        n_close();
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
