package androidx.core.os;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Method;
/* loaded from: classes.dex */
public final class ProcessCompat {
    private ProcessCompat() {
    }

    public static boolean isApplicationUid(int i) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.isApplicationUid(i);
        }
        return Api17Impl.isApplicationUid(i);
    }

    @RequiresApi(24)
    /* loaded from: classes.dex */
    static class Api24Impl {
        private Api24Impl() {
        }

        static boolean isApplicationUid(int i) {
            return Process.isApplicationUid(i);
        }
    }

    @RequiresApi(17)
    /* loaded from: classes.dex */
    static class Api17Impl {
        private static Method sMethodUserHandleIsAppMethod;
        private static boolean sResolved;
        private static final Object sResolvedLock = new Object();

        private Api17Impl() {
        }

        @SuppressLint({"DiscouragedPrivateApi"})
        static boolean isApplicationUid(int i) {
            try {
                synchronized (sResolvedLock) {
                    if (!sResolved) {
                        sResolved = true;
                        sMethodUserHandleIsAppMethod = UserHandle.class.getDeclaredMethod("isApp", Integer.TYPE);
                    }
                }
                Method method = sMethodUserHandleIsAppMethod;
                if (method != null) {
                    Boolean bool = (Boolean) method.invoke(null, Integer.valueOf(i));
                    if (bool == null) {
                        throw new NullPointerException();
                    }
                    return bool.booleanValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @RequiresApi(16)
    /* loaded from: classes.dex */
    static class Api16Impl {
        private static Method sMethodUserIdIsAppMethod;
        private static boolean sResolved;
        private static final Object sResolvedLock = new Object();

        private Api16Impl() {
        }

        @SuppressLint({"PrivateApi"})
        static boolean isApplicationUid(int i) {
            try {
                synchronized (sResolvedLock) {
                    if (!sResolved) {
                        sResolved = true;
                        sMethodUserIdIsAppMethod = Class.forName("android.os.UserId").getDeclaredMethod("isApp", Integer.TYPE);
                    }
                }
                Method method = sMethodUserIdIsAppMethod;
                if (method != null) {
                    Boolean bool = (Boolean) method.invoke(null, Integer.valueOf(i));
                    if (bool == null) {
                        throw new NullPointerException();
                    }
                    return bool.booleanValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
