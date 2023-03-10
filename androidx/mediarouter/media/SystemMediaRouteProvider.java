package androidx.mediarouter.media;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Build;
import android.view.Display;
import androidx.annotation.RequiresApi;
import androidx.mediarouter.R;
import androidx.mediarouter.media.MediaRouteDescriptor;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteProviderDescriptor;
import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.MediaRouterApi24;
import androidx.mediarouter.media.MediaRouterJellybean;
import androidx.mediarouter.media.MediaRouterJellybeanMr1;
import androidx.mediarouter.media.MediaRouterJellybeanMr2;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class SystemMediaRouteProvider extends MediaRouteProvider {
    public static final String DEFAULT_ROUTE_ID = "DEFAULT_ROUTE";
    public static final String PACKAGE_NAME = "android";
    private static final String TAG = "SystemMediaRouteProvider";

    /* loaded from: classes.dex */
    public interface SyncCallback {
        void onSystemRouteSelectedByDescriptorId(String str);
    }

    protected Object getDefaultRoute() {
        return null;
    }

    protected Object getSystemRoute(MediaRouter.RouteInfo routeInfo) {
        return null;
    }

    public void onSyncRouteAdded(MediaRouter.RouteInfo routeInfo) {
    }

    public void onSyncRouteChanged(MediaRouter.RouteInfo routeInfo) {
    }

    public void onSyncRouteRemoved(MediaRouter.RouteInfo routeInfo) {
    }

    public void onSyncRouteSelected(MediaRouter.RouteInfo routeInfo) {
    }

    protected SystemMediaRouteProvider(Context context) {
        super(context, new MediaRouteProvider.ProviderMetadata(new ComponentName(PACKAGE_NAME, SystemMediaRouteProvider.class.getName())));
    }

    public static SystemMediaRouteProvider obtain(Context context, SyncCallback syncCallback) {
        if (Build.VERSION.SDK_INT >= 24) {
            return new Api24Impl(context, syncCallback);
        }
        return new JellybeanMr2Impl(context, syncCallback);
    }

    /* loaded from: classes.dex */
    static class LegacyImpl extends SystemMediaRouteProvider {
        private static final ArrayList<IntentFilter> CONTROL_FILTERS;
        static final int PLAYBACK_STREAM = 3;
        final AudioManager mAudioManager;
        int mLastReportedVolume;
        private final VolumeChangeReceiver mVolumeChangeReceiver;

        static {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO);
            intentFilter.addCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO);
            ArrayList<IntentFilter> arrayList = new ArrayList<>();
            CONTROL_FILTERS = arrayList;
            arrayList.add(intentFilter);
        }

        public LegacyImpl(Context context) {
            super(context);
            this.mLastReportedVolume = -1;
            this.mAudioManager = (AudioManager) context.getSystemService("audio");
            VolumeChangeReceiver volumeChangeReceiver = new VolumeChangeReceiver();
            this.mVolumeChangeReceiver = volumeChangeReceiver;
            context.registerReceiver(volumeChangeReceiver, new IntentFilter(VolumeChangeReceiver.VOLUME_CHANGED_ACTION));
            publishRoutes();
        }

        void publishRoutes() {
            Resources resources = getContext().getResources();
            int streamMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
            this.mLastReportedVolume = this.mAudioManager.getStreamVolume(3);
            setDescriptor(new MediaRouteProviderDescriptor.Builder().addRoute(new MediaRouteDescriptor.Builder(SystemMediaRouteProvider.DEFAULT_ROUTE_ID, resources.getString(R.string.mr_system_route_name)).addControlFilters(CONTROL_FILTERS).setPlaybackStream(3).setPlaybackType(0).setVolumeHandling(1).setVolumeMax(streamMaxVolume).setVolume(this.mLastReportedVolume).build()).build());
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider
        public MediaRouteProvider.RouteController onCreateRouteController(String str) {
            if (str.equals(SystemMediaRouteProvider.DEFAULT_ROUTE_ID)) {
                return new DefaultRouteController();
            }
            return null;
        }

        /* loaded from: classes.dex */
        final class DefaultRouteController extends MediaRouteProvider.RouteController {
            DefaultRouteController() {
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
            public void onSetVolume(int i) {
                LegacyImpl.this.mAudioManager.setStreamVolume(3, i, 0);
                LegacyImpl.this.publishRoutes();
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
            public void onUpdateVolume(int i) {
                int streamVolume = LegacyImpl.this.mAudioManager.getStreamVolume(3);
                if (Math.min(LegacyImpl.this.mAudioManager.getStreamMaxVolume(3), Math.max(0, i + streamVolume)) != streamVolume) {
                    LegacyImpl.this.mAudioManager.setStreamVolume(3, streamVolume, 0);
                }
                LegacyImpl.this.publishRoutes();
            }
        }

        /* loaded from: classes.dex */
        final class VolumeChangeReceiver extends BroadcastReceiver {
            public static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
            public static final String EXTRA_VOLUME_STREAM_VALUE = "android.media.EXTRA_VOLUME_STREAM_VALUE";
            public static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";

            VolumeChangeReceiver() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                int intExtra;
                if (intent.getAction().equals(VOLUME_CHANGED_ACTION) && intent.getIntExtra(EXTRA_VOLUME_STREAM_TYPE, -1) == 3 && (intExtra = intent.getIntExtra(EXTRA_VOLUME_STREAM_VALUE, -1)) >= 0) {
                    LegacyImpl legacyImpl = LegacyImpl.this;
                    if (intExtra != legacyImpl.mLastReportedVolume) {
                        legacyImpl.publishRoutes();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(16)
    /* loaded from: classes.dex */
    public static class JellybeanImpl extends SystemMediaRouteProvider implements MediaRouterJellybean.Callback, MediaRouterJellybean.VolumeCallback {
        private static final ArrayList<IntentFilter> LIVE_AUDIO_CONTROL_FILTERS;
        private static final ArrayList<IntentFilter> LIVE_VIDEO_CONTROL_FILTERS;
        protected boolean mActiveScan;
        protected final Object mCallbackObj;
        protected boolean mCallbackRegistered;
        private MediaRouterJellybean.GetDefaultRouteWorkaround mGetDefaultRouteWorkaround;
        protected int mRouteTypes;
        protected final Object mRouterObj;
        private MediaRouterJellybean.SelectRouteWorkaround mSelectRouteWorkaround;
        private final SyncCallback mSyncCallback;
        protected final ArrayList<SystemRouteRecord> mSystemRouteRecords;
        protected final Object mUserRouteCategoryObj;
        protected final ArrayList<UserRouteRecord> mUserRouteRecords;
        protected final Object mVolumeCallbackObj;

        @Override // androidx.mediarouter.media.MediaRouterJellybean.Callback
        public void onRouteGrouped(Object obj, Object obj2, int i) {
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean.Callback
        public void onRouteUngrouped(Object obj, Object obj2) {
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean.Callback
        public void onRouteUnselected(int i, Object obj) {
        }

        static {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO);
            ArrayList<IntentFilter> arrayList = new ArrayList<>();
            LIVE_AUDIO_CONTROL_FILTERS = arrayList;
            arrayList.add(intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO);
            ArrayList<IntentFilter> arrayList2 = new ArrayList<>();
            LIVE_VIDEO_CONTROL_FILTERS = arrayList2;
            arrayList2.add(intentFilter2);
        }

        public JellybeanImpl(Context context, SyncCallback syncCallback) {
            super(context);
            this.mSystemRouteRecords = new ArrayList<>();
            this.mUserRouteRecords = new ArrayList<>();
            this.mSyncCallback = syncCallback;
            Object mediaRouter = MediaRouterJellybean.getMediaRouter(context);
            this.mRouterObj = mediaRouter;
            this.mCallbackObj = createCallbackObj();
            this.mVolumeCallbackObj = createVolumeCallbackObj();
            this.mUserRouteCategoryObj = MediaRouterJellybean.createRouteCategory(mediaRouter, context.getResources().getString(R.string.mr_user_route_category_name), false);
            updateSystemRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider
        public MediaRouteProvider.RouteController onCreateRouteController(String str) {
            int findSystemRouteRecordByDescriptorId = findSystemRouteRecordByDescriptorId(str);
            if (findSystemRouteRecordByDescriptorId >= 0) {
                return new SystemRouteController(this.mSystemRouteRecords.get(findSystemRouteRecordByDescriptorId).mRouteObj);
            }
            return null;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider
        public void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
            boolean z;
            int i = 0;
            if (mediaRouteDiscoveryRequest != null) {
                List<String> controlCategories = mediaRouteDiscoveryRequest.getSelector().getControlCategories();
                int size = controlCategories.size();
                int i2 = 0;
                while (i < size) {
                    String str = controlCategories.get(i);
                    if (str.equals(MediaControlIntent.CATEGORY_LIVE_AUDIO)) {
                        i2 |= 1;
                    } else {
                        i2 = str.equals(MediaControlIntent.CATEGORY_LIVE_VIDEO) ? i2 | 2 : i2 | 8388608;
                    }
                    i++;
                }
                z = mediaRouteDiscoveryRequest.isActiveScan();
                i = i2;
            } else {
                z = false;
            }
            if (this.mRouteTypes == i && this.mActiveScan == z) {
                return;
            }
            this.mRouteTypes = i;
            this.mActiveScan = z;
            updateSystemRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean.Callback
        public void onRouteAdded(Object obj) {
            if (addSystemRouteNoPublish(obj)) {
                publishRoutes();
            }
        }

        private void updateSystemRoutes() {
            updateCallback();
            boolean z = false;
            for (Object obj : MediaRouterJellybean.getRoutes(this.mRouterObj)) {
                z |= addSystemRouteNoPublish(obj);
            }
            if (z) {
                publishRoutes();
            }
        }

        private boolean addSystemRouteNoPublish(Object obj) {
            if (getUserRouteRecord(obj) != null || findSystemRouteRecord(obj) >= 0) {
                return false;
            }
            SystemRouteRecord systemRouteRecord = new SystemRouteRecord(obj, assignRouteId(obj));
            updateSystemRouteDescriptor(systemRouteRecord);
            this.mSystemRouteRecords.add(systemRouteRecord);
            return true;
        }

        private String assignRouteId(Object obj) {
            String format = getDefaultRoute() == obj ? SystemMediaRouteProvider.DEFAULT_ROUTE_ID : String.format(Locale.US, "ROUTE_%08x", Integer.valueOf(getRouteName(obj).hashCode()));
            if (findSystemRouteRecordByDescriptorId(format) < 0) {
                return format;
            }
            int i = 2;
            while (true) {
                String format2 = String.format(Locale.US, "%s_%d", format, Integer.valueOf(i));
                if (findSystemRouteRecordByDescriptorId(format2) < 0) {
                    return format2;
                }
                i++;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean.Callback
        public void onRouteRemoved(Object obj) {
            int findSystemRouteRecord;
            if (getUserRouteRecord(obj) != null || (findSystemRouteRecord = findSystemRouteRecord(obj)) < 0) {
                return;
            }
            this.mSystemRouteRecords.remove(findSystemRouteRecord);
            publishRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean.Callback
        public void onRouteChanged(Object obj) {
            int findSystemRouteRecord;
            if (getUserRouteRecord(obj) != null || (findSystemRouteRecord = findSystemRouteRecord(obj)) < 0) {
                return;
            }
            updateSystemRouteDescriptor(this.mSystemRouteRecords.get(findSystemRouteRecord));
            publishRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean.Callback
        public void onRouteVolumeChanged(Object obj) {
            int findSystemRouteRecord;
            if (getUserRouteRecord(obj) != null || (findSystemRouteRecord = findSystemRouteRecord(obj)) < 0) {
                return;
            }
            SystemRouteRecord systemRouteRecord = this.mSystemRouteRecords.get(findSystemRouteRecord);
            int volume = MediaRouterJellybean.RouteInfo.getVolume(obj);
            if (volume != systemRouteRecord.mRouteDescriptor.getVolume()) {
                systemRouteRecord.mRouteDescriptor = new MediaRouteDescriptor.Builder(systemRouteRecord.mRouteDescriptor).setVolume(volume).build();
                publishRoutes();
            }
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean.Callback
        public void onRouteSelected(int i, Object obj) {
            if (obj != MediaRouterJellybean.getSelectedRoute(this.mRouterObj, 8388611)) {
                return;
            }
            UserRouteRecord userRouteRecord = getUserRouteRecord(obj);
            if (userRouteRecord != null) {
                userRouteRecord.mRoute.select();
                return;
            }
            int findSystemRouteRecord = findSystemRouteRecord(obj);
            if (findSystemRouteRecord >= 0) {
                this.mSyncCallback.onSystemRouteSelectedByDescriptorId(this.mSystemRouteRecords.get(findSystemRouteRecord).mRouteDescriptorId);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean.VolumeCallback
        public void onVolumeSetRequest(Object obj, int i) {
            UserRouteRecord userRouteRecord = getUserRouteRecord(obj);
            if (userRouteRecord != null) {
                userRouteRecord.mRoute.requestSetVolume(i);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean.VolumeCallback
        public void onVolumeUpdateRequest(Object obj, int i) {
            UserRouteRecord userRouteRecord = getUserRouteRecord(obj);
            if (userRouteRecord != null) {
                userRouteRecord.mRoute.requestUpdateVolume(i);
            }
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider
        public void onSyncRouteAdded(MediaRouter.RouteInfo routeInfo) {
            if (routeInfo.getProviderInstance() != this) {
                Object createUserRoute = MediaRouterJellybean.createUserRoute(this.mRouterObj, this.mUserRouteCategoryObj);
                UserRouteRecord userRouteRecord = new UserRouteRecord(routeInfo, createUserRoute);
                MediaRouterJellybean.RouteInfo.setTag(createUserRoute, userRouteRecord);
                MediaRouterJellybean.UserRouteInfo.setVolumeCallback(createUserRoute, this.mVolumeCallbackObj);
                updateUserRouteProperties(userRouteRecord);
                this.mUserRouteRecords.add(userRouteRecord);
                MediaRouterJellybean.addUserRoute(this.mRouterObj, createUserRoute);
                return;
            }
            int findSystemRouteRecord = findSystemRouteRecord(MediaRouterJellybean.getSelectedRoute(this.mRouterObj, 8388611));
            if (findSystemRouteRecord < 0 || !this.mSystemRouteRecords.get(findSystemRouteRecord).mRouteDescriptorId.equals(routeInfo.getDescriptorId())) {
                return;
            }
            routeInfo.select();
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider
        public void onSyncRouteRemoved(MediaRouter.RouteInfo routeInfo) {
            int findUserRouteRecord;
            if (routeInfo.getProviderInstance() == this || (findUserRouteRecord = findUserRouteRecord(routeInfo)) < 0) {
                return;
            }
            UserRouteRecord remove = this.mUserRouteRecords.remove(findUserRouteRecord);
            MediaRouterJellybean.RouteInfo.setTag(remove.mRouteObj, null);
            MediaRouterJellybean.UserRouteInfo.setVolumeCallback(remove.mRouteObj, null);
            MediaRouterJellybean.removeUserRoute(this.mRouterObj, remove.mRouteObj);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider
        public void onSyncRouteChanged(MediaRouter.RouteInfo routeInfo) {
            int findUserRouteRecord;
            if (routeInfo.getProviderInstance() == this || (findUserRouteRecord = findUserRouteRecord(routeInfo)) < 0) {
                return;
            }
            updateUserRouteProperties(this.mUserRouteRecords.get(findUserRouteRecord));
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider
        public void onSyncRouteSelected(MediaRouter.RouteInfo routeInfo) {
            if (routeInfo.isSelected()) {
                if (routeInfo.getProviderInstance() != this) {
                    int findUserRouteRecord = findUserRouteRecord(routeInfo);
                    if (findUserRouteRecord >= 0) {
                        selectRoute(this.mUserRouteRecords.get(findUserRouteRecord).mRouteObj);
                        return;
                    }
                    return;
                }
                int findSystemRouteRecordByDescriptorId = findSystemRouteRecordByDescriptorId(routeInfo.getDescriptorId());
                if (findSystemRouteRecordByDescriptorId >= 0) {
                    selectRoute(this.mSystemRouteRecords.get(findSystemRouteRecordByDescriptorId).mRouteObj);
                }
            }
        }

        protected void publishRoutes() {
            MediaRouteProviderDescriptor.Builder builder = new MediaRouteProviderDescriptor.Builder();
            int size = this.mSystemRouteRecords.size();
            for (int i = 0; i < size; i++) {
                builder.addRoute(this.mSystemRouteRecords.get(i).mRouteDescriptor);
            }
            setDescriptor(builder.build());
        }

        protected int findSystemRouteRecord(Object obj) {
            int size = this.mSystemRouteRecords.size();
            for (int i = 0; i < size; i++) {
                if (this.mSystemRouteRecords.get(i).mRouteObj == obj) {
                    return i;
                }
            }
            return -1;
        }

        protected int findSystemRouteRecordByDescriptorId(String str) {
            int size = this.mSystemRouteRecords.size();
            for (int i = 0; i < size; i++) {
                if (this.mSystemRouteRecords.get(i).mRouteDescriptorId.equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        protected int findUserRouteRecord(MediaRouter.RouteInfo routeInfo) {
            int size = this.mUserRouteRecords.size();
            for (int i = 0; i < size; i++) {
                if (this.mUserRouteRecords.get(i).mRoute == routeInfo) {
                    return i;
                }
            }
            return -1;
        }

        protected UserRouteRecord getUserRouteRecord(Object obj) {
            Object tag = MediaRouterJellybean.RouteInfo.getTag(obj);
            if (tag instanceof UserRouteRecord) {
                return (UserRouteRecord) tag;
            }
            return null;
        }

        protected void updateSystemRouteDescriptor(SystemRouteRecord systemRouteRecord) {
            MediaRouteDescriptor.Builder builder = new MediaRouteDescriptor.Builder(systemRouteRecord.mRouteDescriptorId, getRouteName(systemRouteRecord.mRouteObj));
            onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            systemRouteRecord.mRouteDescriptor = builder.build();
        }

        protected String getRouteName(Object obj) {
            CharSequence name = MediaRouterJellybean.RouteInfo.getName(obj, getContext());
            return name != null ? name.toString() : "";
        }

        protected void onBuildSystemRouteDescriptor(SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            int supportedTypes = MediaRouterJellybean.RouteInfo.getSupportedTypes(systemRouteRecord.mRouteObj);
            if ((supportedTypes & 1) != 0) {
                builder.addControlFilters(LIVE_AUDIO_CONTROL_FILTERS);
            }
            if ((supportedTypes & 2) != 0) {
                builder.addControlFilters(LIVE_VIDEO_CONTROL_FILTERS);
            }
            builder.setPlaybackType(MediaRouterJellybean.RouteInfo.getPlaybackType(systemRouteRecord.mRouteObj));
            builder.setPlaybackStream(MediaRouterJellybean.RouteInfo.getPlaybackStream(systemRouteRecord.mRouteObj));
            builder.setVolume(MediaRouterJellybean.RouteInfo.getVolume(systemRouteRecord.mRouteObj));
            builder.setVolumeMax(MediaRouterJellybean.RouteInfo.getVolumeMax(systemRouteRecord.mRouteObj));
            builder.setVolumeHandling(MediaRouterJellybean.RouteInfo.getVolumeHandling(systemRouteRecord.mRouteObj));
        }

        protected void updateUserRouteProperties(UserRouteRecord userRouteRecord) {
            MediaRouterJellybean.UserRouteInfo.setName(userRouteRecord.mRouteObj, userRouteRecord.mRoute.getName());
            MediaRouterJellybean.UserRouteInfo.setPlaybackType(userRouteRecord.mRouteObj, userRouteRecord.mRoute.getPlaybackType());
            MediaRouterJellybean.UserRouteInfo.setPlaybackStream(userRouteRecord.mRouteObj, userRouteRecord.mRoute.getPlaybackStream());
            MediaRouterJellybean.UserRouteInfo.setVolume(userRouteRecord.mRouteObj, userRouteRecord.mRoute.getVolume());
            MediaRouterJellybean.UserRouteInfo.setVolumeMax(userRouteRecord.mRouteObj, userRouteRecord.mRoute.getVolumeMax());
            MediaRouterJellybean.UserRouteInfo.setVolumeHandling(userRouteRecord.mRouteObj, userRouteRecord.mRoute.getVolumeHandling());
        }

        protected void updateCallback() {
            if (this.mCallbackRegistered) {
                this.mCallbackRegistered = false;
                MediaRouterJellybean.removeCallback(this.mRouterObj, this.mCallbackObj);
            }
            int i = this.mRouteTypes;
            if (i != 0) {
                this.mCallbackRegistered = true;
                MediaRouterJellybean.addCallback(this.mRouterObj, i, this.mCallbackObj);
            }
        }

        protected Object createCallbackObj() {
            return MediaRouterJellybean.createCallback(this);
        }

        protected Object createVolumeCallbackObj() {
            return MediaRouterJellybean.createVolumeCallback(this);
        }

        protected void selectRoute(Object obj) {
            if (this.mSelectRouteWorkaround == null) {
                this.mSelectRouteWorkaround = new MediaRouterJellybean.SelectRouteWorkaround();
            }
            this.mSelectRouteWorkaround.selectRoute(this.mRouterObj, 8388611, obj);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider
        protected Object getDefaultRoute() {
            if (this.mGetDefaultRouteWorkaround == null) {
                this.mGetDefaultRouteWorkaround = new MediaRouterJellybean.GetDefaultRouteWorkaround();
            }
            return this.mGetDefaultRouteWorkaround.getDefaultRoute(this.mRouterObj);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider
        protected Object getSystemRoute(MediaRouter.RouteInfo routeInfo) {
            int findSystemRouteRecordByDescriptorId;
            if (routeInfo != null && (findSystemRouteRecordByDescriptorId = findSystemRouteRecordByDescriptorId(routeInfo.getDescriptorId())) >= 0) {
                return this.mSystemRouteRecords.get(findSystemRouteRecordByDescriptorId).mRouteObj;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* loaded from: classes.dex */
        public static final class SystemRouteRecord {
            public MediaRouteDescriptor mRouteDescriptor;
            public final String mRouteDescriptorId;
            public final Object mRouteObj;

            public SystemRouteRecord(Object obj, String str) {
                this.mRouteObj = obj;
                this.mRouteDescriptorId = str;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* loaded from: classes.dex */
        public static final class UserRouteRecord {
            public final MediaRouter.RouteInfo mRoute;
            public final Object mRouteObj;

            public UserRouteRecord(MediaRouter.RouteInfo routeInfo, Object obj) {
                this.mRoute = routeInfo;
                this.mRouteObj = obj;
            }
        }

        /* loaded from: classes.dex */
        protected static final class SystemRouteController extends MediaRouteProvider.RouteController {
            private final Object mRouteObj;

            public SystemRouteController(Object obj) {
                this.mRouteObj = obj;
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
            public void onSetVolume(int i) {
                MediaRouterJellybean.RouteInfo.requestSetVolume(this.mRouteObj, i);
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
            public void onUpdateVolume(int i) {
                MediaRouterJellybean.RouteInfo.requestUpdateVolume(this.mRouteObj, i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(17)
    /* loaded from: classes.dex */
    public static class JellybeanMr1Impl extends JellybeanImpl implements MediaRouterJellybeanMr1.Callback {
        private MediaRouterJellybeanMr1.ActiveScanWorkaround mActiveScanWorkaround;
        private MediaRouterJellybeanMr1.IsConnectingWorkaround mIsConnectingWorkaround;

        public JellybeanMr1Impl(Context context, SyncCallback syncCallback) {
            super(context, syncCallback);
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybeanMr1.Callback
        public void onRoutePresentationDisplayChanged(Object obj) {
            int findSystemRouteRecord = findSystemRouteRecord(obj);
            if (findSystemRouteRecord >= 0) {
                JellybeanImpl.SystemRouteRecord systemRouteRecord = this.mSystemRouteRecords.get(findSystemRouteRecord);
                Display presentationDisplay = MediaRouterJellybeanMr1.RouteInfo.getPresentationDisplay(obj);
                int displayId = presentationDisplay != null ? presentationDisplay.getDisplayId() : -1;
                if (displayId != systemRouteRecord.mRouteDescriptor.getPresentationDisplayId()) {
                    systemRouteRecord.mRouteDescriptor = new MediaRouteDescriptor.Builder(systemRouteRecord.mRouteDescriptor).setPresentationDisplayId(displayId).build();
                    publishRoutes();
                }
            }
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void onBuildSystemRouteDescriptor(JellybeanImpl.SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            super.onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            if (!MediaRouterJellybeanMr1.RouteInfo.isEnabled(systemRouteRecord.mRouteObj)) {
                builder.setEnabled(false);
            }
            if (isConnecting(systemRouteRecord)) {
                builder.setConnecting(true);
            }
            Display presentationDisplay = MediaRouterJellybeanMr1.RouteInfo.getPresentationDisplay(systemRouteRecord.mRouteObj);
            if (presentationDisplay != null) {
                builder.setPresentationDisplayId(presentationDisplay.getDisplayId());
            }
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void updateCallback() {
            super.updateCallback();
            if (this.mActiveScanWorkaround == null) {
                this.mActiveScanWorkaround = new MediaRouterJellybeanMr1.ActiveScanWorkaround(getContext(), getHandler());
            }
            this.mActiveScanWorkaround.setActiveScanRouteTypes(this.mActiveScan ? this.mRouteTypes : 0);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected Object createCallbackObj() {
            return MediaRouterJellybeanMr1.createCallback(this);
        }

        protected boolean isConnecting(JellybeanImpl.SystemRouteRecord systemRouteRecord) {
            if (this.mIsConnectingWorkaround == null) {
                this.mIsConnectingWorkaround = new MediaRouterJellybeanMr1.IsConnectingWorkaround();
            }
            return this.mIsConnectingWorkaround.isConnecting(systemRouteRecord.mRouteObj);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(18)
    /* loaded from: classes.dex */
    public static class JellybeanMr2Impl extends JellybeanMr1Impl {
        public JellybeanMr2Impl(Context context, SyncCallback syncCallback) {
            super(context, syncCallback);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void onBuildSystemRouteDescriptor(JellybeanImpl.SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            super.onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            CharSequence description = MediaRouterJellybeanMr2.RouteInfo.getDescription(systemRouteRecord.mRouteObj);
            if (description != null) {
                builder.setDescription(description.toString());
            }
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void selectRoute(Object obj) {
            MediaRouterJellybean.selectRoute(this.mRouterObj, 8388611, obj);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl, androidx.mediarouter.media.SystemMediaRouteProvider
        protected Object getDefaultRoute() {
            return MediaRouterJellybeanMr2.getDefaultRoute(this.mRouterObj);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void updateUserRouteProperties(JellybeanImpl.UserRouteRecord userRouteRecord) {
            super.updateUserRouteProperties(userRouteRecord);
            MediaRouterJellybeanMr2.UserRouteInfo.setDescription(userRouteRecord.mRouteObj, userRouteRecord.mRoute.getDescription());
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void updateCallback() {
            if (this.mCallbackRegistered) {
                MediaRouterJellybean.removeCallback(this.mRouterObj, this.mCallbackObj);
            }
            this.mCallbackRegistered = true;
            MediaRouterJellybeanMr2.addCallback(this.mRouterObj, this.mRouteTypes, this.mCallbackObj, (this.mActiveScan ? 1 : 0) | 2);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl
        protected boolean isConnecting(JellybeanImpl.SystemRouteRecord systemRouteRecord) {
            return MediaRouterJellybeanMr2.RouteInfo.isConnecting(systemRouteRecord.mRouteObj);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(24)
    /* loaded from: classes.dex */
    public static class Api24Impl extends JellybeanMr2Impl {
        public Api24Impl(Context context, SyncCallback syncCallback) {
            super(context, syncCallback);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr2Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void onBuildSystemRouteDescriptor(JellybeanImpl.SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            super.onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            builder.setDeviceType(MediaRouterApi24.RouteInfo.getDeviceType(systemRouteRecord.mRouteObj));
        }
    }
}
