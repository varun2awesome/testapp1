package crc64a0e0a82d0db9a07d;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import java.util.ArrayList;
import mono.android.IGCUserPeer;
import mono.android.Runtime;
import mono.android.TypeManager;
/* loaded from: classes.dex */
public class SensorListener implements IGCUserPeer, SensorEventListener {
    public static final String __md_methods = "n_onAccuracyChanged:(Landroid/hardware/Sensor;I)V:GetOnAccuracyChanged_Landroid_hardware_Sensor_IHandler:Android.Hardware.ISensorEventListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onSensorChanged:(Landroid/hardware/SensorEvent;)V:GetOnSensorChanged_Landroid_hardware_SensorEvent_Handler:Android.Hardware.ISensorEventListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n";
    private ArrayList refList;

    private native void n_onAccuracyChanged(Sensor sensor, int i);

    private native void n_onSensorChanged(SensorEvent sensorEvent);

    static {
        Runtime.register("Xamarin.Essentials.SensorListener, Xamarin.Essentials", SensorListener.class, "n_onAccuracyChanged:(Landroid/hardware/Sensor;I)V:GetOnAccuracyChanged_Landroid_hardware_Sensor_IHandler:Android.Hardware.ISensorEventListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\nn_onSensorChanged:(Landroid/hardware/SensorEvent;)V:GetOnSensorChanged_Landroid_hardware_SensorEvent_Handler:Android.Hardware.ISensorEventListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n");
    }

    public SensorListener() {
        if (getClass() == SensorListener.class) {
            TypeManager.Activate("Xamarin.Essentials.SensorListener, Xamarin.Essentials", "", this, new Object[0]);
        }
    }

    public SensorListener(String str, String str2, int i, boolean z) {
        if (getClass() == SensorListener.class) {
            TypeManager.Activate("Xamarin.Essentials.SensorListener, Xamarin.Essentials", "System.String, mscorlib:System.String, mscorlib:Android.Hardware.SensorDelay, Mono.Android:System.Boolean, mscorlib", this, new Object[]{str, str2, Integer.valueOf(i), Boolean.valueOf(z)});
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
        n_onAccuracyChanged(sensor, i);
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        n_onSensorChanged(sensorEvent);
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
