package crc64a0e0a82d0db9a07d;

import android.speech.tts.TextToSpeech;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class TextToSpeechImplementation implements IGCUserPeer, TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {
    public static final String __md_methods = "n_onInit:(I)V:GetOnInit_IHandler:Android.Speech.Tts.TextToSpeech/IOnInitListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onUtteranceCompleted:(Ljava/lang/String;)V:GetOnUtteranceCompleted_Ljava_lang_String_Handler:Android.Speech.Tts.TextToSpeech/IOnUtteranceCompletedListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n";
    private ArrayList refList;

    private native void n_onInit(int i);

    private native void n_onUtteranceCompleted(String str);

    static {
        Runtime.register("Xamarin.Essentials.TextToSpeechImplementation, Xamarin.Essentials", TextToSpeechImplementation.class, __md_methods);
    }

    public TextToSpeechImplementation() {
        if (getClass() == TextToSpeechImplementation.class) {
            TypeManager.Activate("Xamarin.Essentials.TextToSpeechImplementation, Xamarin.Essentials", "", this, new Object[0]);
        }
    }

    @Override // android.speech.tts.TextToSpeech.OnInitListener
    public void onInit(int i) {
        n_onInit(i);
    }

    @Override // android.speech.tts.TextToSpeech.OnUtteranceCompletedListener
    public void onUtteranceCompleted(String str) {
        n_onUtteranceCompleted(str);
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
