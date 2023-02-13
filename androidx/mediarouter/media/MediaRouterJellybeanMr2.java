package androidx.mediarouter.media;

import android.media.MediaRouter;
import androidx.annotation.RequiresApi;
@RequiresApi(18)
/* loaded from: classes.dex */
final class MediaRouterJellybeanMr2 {
    public static Object getDefaultRoute(Object obj) {
        return ((android.media.MediaRouter) obj).getDefaultRoute();
    }

    public static void addCallback(Object obj, int i, Object obj2, int i2) {
        ((android.media.MediaRouter) obj).addCallback(i, (MediaRouter.Callback) obj2, i2);
    }

    /* loaded from: classes.dex */
    public static final class RouteInfo {
        public static CharSequence getDescription(Object obj) {
            return ((MediaRouter.RouteInfo) obj).getDescription();
        }

        public static boolean isConnecting(Object obj) {
            return ((MediaRouter.RouteInfo) obj).isConnecting();
        }

        private RouteInfo() {
        }
    }

    /* loaded from: classes.dex */
    public static final class UserRouteInfo {
        public static void setDescription(Object obj, CharSequence charSequence) {
            ((MediaRouter.UserRouteInfo) obj).setDescription(charSequence);
        }

        private UserRouteInfo() {
        }
    }

    private MediaRouterJellybeanMr2() {
    }
}
