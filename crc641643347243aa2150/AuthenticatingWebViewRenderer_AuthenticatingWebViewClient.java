package crc641643347243aa2150;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class AuthenticatingWebViewRenderer_AuthenticatingWebViewClient extends WebViewClient implements IGCUserPeer {
    public static final String __md_methods = "n_doUpdateVisitedHistory:(Landroid/webkit/WebView;Ljava/lang/String;Z)V:GetDoUpdateVisitedHistory_Landroid_webkit_WebView_Ljava_lang_String_ZHandler\nn_onFormResubmission:(Landroid/webkit/WebView;Landroid/os/Message;Landroid/os/Message;)V:GetOnFormResubmission_Landroid_webkit_WebView_Landroid_os_Message_Landroid_os_Message_Handler\nn_onLoadResource:(Landroid/webkit/WebView;Ljava/lang/String;)V:GetOnLoadResource_Landroid_webkit_WebView_Ljava_lang_String_Handler\nn_onPageFinished:(Landroid/webkit/WebView;Ljava/lang/String;)V:GetOnPageFinished_Landroid_webkit_WebView_Ljava_lang_String_Handler\nn_onPageStarted:(Landroid/webkit/WebView;Ljava/lang/String;Landroid/graphics/Bitmap;)V:GetOnPageStarted_Landroid_webkit_WebView_Ljava_lang_String_Landroid_graphics_Bitmap_Handler\nn_onReceivedError:(Landroid/webkit/WebView;Landroid/webkit/WebResourceRequest;Landroid/webkit/WebResourceError;)V:GetOnReceivedError_Landroid_webkit_WebView_Landroid_webkit_WebResourceRequest_Landroid_webkit_WebResourceError_Handler\nn_onReceivedHttpAuthRequest:(Landroid/webkit/WebView;Landroid/webkit/HttpAuthHandler;Ljava/lang/String;Ljava/lang/String;)V:GetOnReceivedHttpAuthRequest_Landroid_webkit_WebView_Landroid_webkit_HttpAuthHandler_Ljava_lang_String_Ljava_lang_String_Handler\nn_onReceivedSslError:(Landroid/webkit/WebView;Landroid/webkit/SslErrorHandler;Landroid/net/http/SslError;)V:GetOnReceivedSslError_Landroid_webkit_WebView_Landroid_webkit_SslErrorHandler_Landroid_net_http_SslError_Handler\nn_onScaleChanged:(Landroid/webkit/WebView;FF)V:GetOnScaleChanged_Landroid_webkit_WebView_FFHandler\nn_onTooManyRedirects:(Landroid/webkit/WebView;Landroid/os/Message;Landroid/os/Message;)V:GetOnTooManyRedirects_Landroid_webkit_WebView_Landroid_os_Message_Landroid_os_Message_Handler\nn_onUnhandledKeyEvent:(Landroid/webkit/WebView;Landroid/view/KeyEvent;)V:GetOnUnhandledKeyEvent_Landroid_webkit_WebView_Landroid_view_KeyEvent_Handler\nn_shouldOverrideKeyEvent:(Landroid/webkit/WebView;Landroid/view/KeyEvent;)Z:GetShouldOverrideKeyEvent_Landroid_webkit_WebView_Landroid_view_KeyEvent_Handler\nn_shouldOverrideUrlLoading:(Landroid/webkit/WebView;Landroid/webkit/WebResourceRequest;)Z:GetShouldOverrideUrlLoading_Landroid_webkit_WebView_Landroid_webkit_WebResourceRequest_Handler\n";
    private ArrayList refList;

    private native void n_doUpdateVisitedHistory(WebView webView, String str, boolean z);

    private native void n_onFormResubmission(WebView webView, Message message, Message message2);

    private native void n_onLoadResource(WebView webView, String str);

    private native void n_onPageFinished(WebView webView, String str);

    private native void n_onPageStarted(WebView webView, String str, Bitmap bitmap);

    private native void n_onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError);

    private native void n_onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2);

    private native void n_onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError);

    private native void n_onScaleChanged(WebView webView, float f, float f2);

    private native void n_onTooManyRedirects(WebView webView, Message message, Message message2);

    private native void n_onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent);

    private native boolean n_shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent);

    private native boolean n_shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest);

    static {
        Runtime.register("iSmart.Droid.CustomRenderers.AuthenticatingWebViewRenderer+AuthenticatingWebViewClient, iSmart.Android", AuthenticatingWebViewRenderer_AuthenticatingWebViewClient.class, __md_methods);
    }

    public AuthenticatingWebViewRenderer_AuthenticatingWebViewClient() {
        if (getClass() == AuthenticatingWebViewRenderer_AuthenticatingWebViewClient.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.AuthenticatingWebViewRenderer+AuthenticatingWebViewClient, iSmart.Android", "", this, new Object[0]);
        }
    }

    public AuthenticatingWebViewRenderer_AuthenticatingWebViewClient(AuthenticatingWebViewRenderer authenticatingWebViewRenderer, WebViewClient webViewClient) {
        if (getClass() == AuthenticatingWebViewRenderer_AuthenticatingWebViewClient.class) {
            TypeManager.Activate("iSmart.Droid.CustomRenderers.AuthenticatingWebViewRenderer+AuthenticatingWebViewClient, iSmart.Android", "iSmart.Droid.CustomRenderers.AuthenticatingWebViewRenderer, iSmart.Android:Android.Webkit.WebViewClient, Mono.Android", this, new Object[]{authenticatingWebViewRenderer, webViewClient});
        }
    }

    @Override // android.webkit.WebViewClient
    public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
        n_doUpdateVisitedHistory(webView, str, z);
    }

    @Override // android.webkit.WebViewClient
    public void onFormResubmission(WebView webView, Message message, Message message2) {
        n_onFormResubmission(webView, message, message2);
    }

    @Override // android.webkit.WebViewClient
    public void onLoadResource(WebView webView, String str) {
        n_onLoadResource(webView, str);
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        n_onPageFinished(webView, str);
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        n_onPageStarted(webView, str, bitmap);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        n_onReceivedError(webView, webResourceRequest, webResourceError);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        n_onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        n_onReceivedSslError(webView, sslErrorHandler, sslError);
    }

    @Override // android.webkit.WebViewClient
    public void onScaleChanged(WebView webView, float f, float f2) {
        n_onScaleChanged(webView, f, f2);
    }

    @Override // android.webkit.WebViewClient
    public void onTooManyRedirects(WebView webView, Message message, Message message2) {
        n_onTooManyRedirects(webView, message, message2);
    }

    @Override // android.webkit.WebViewClient
    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        n_onUnhandledKeyEvent(webView, keyEvent);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        return n_shouldOverrideKeyEvent(webView, keyEvent);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        return n_shouldOverrideUrlLoading(webView, webResourceRequest);
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
