package androidx.mediarouter.media;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.core.util.ObjectsCompat;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteProviderDescriptor;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/* loaded from: classes.dex */
public abstract class MediaRouteProviderService extends Service {
    static final int PRIVATE_MSG_CLIENT_DIED = 1;
    public static final String SERVICE_INTERFACE = "android.media.MediaRouteProviderService";
    private final ArrayList<ClientRecord> mClients = new ArrayList<>();
    private MediaRouteDiscoveryRequest mCompositeDiscoveryRequest;
    final PrivateHandler mPrivateHandler;
    MediaRouteProvider mProvider;
    private final ProviderCallback mProviderCallback;
    private final ReceiveHandler mReceiveHandler;
    private final Messenger mReceiveMessenger;
    static final String TAG = "MediaRouteProviderSrv";
    static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public abstract MediaRouteProvider onCreateMediaRouteProvider();

    public MediaRouteProviderService() {
        ReceiveHandler receiveHandler = new ReceiveHandler(this);
        this.mReceiveHandler = receiveHandler;
        this.mReceiveMessenger = new Messenger(receiveHandler);
        this.mPrivateHandler = new PrivateHandler();
        this.mProviderCallback = new ProviderCallback();
    }

    public MediaRouteProvider getMediaRouteProvider() {
        return this.mProvider;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        MediaRouteProvider onCreateMediaRouteProvider;
        if (intent.getAction().equals("android.media.MediaRouteProviderService")) {
            if (this.mProvider == null && (onCreateMediaRouteProvider = onCreateMediaRouteProvider()) != null) {
                String packageName = onCreateMediaRouteProvider.getMetadata().getPackageName();
                if (!packageName.equals(getPackageName())) {
                    throw new IllegalStateException("onCreateMediaRouteProvider() returned a provider whose package name does not match the package name of the service.  A media route provider service can only export its own media route providers.  Provider package name: " + packageName + ".  Service package name: " + getPackageName() + ".");
                }
                this.mProvider = onCreateMediaRouteProvider;
                onCreateMediaRouteProvider.setCallback(this.mProviderCallback);
            }
            if (this.mProvider != null) {
                return this.mReceiveMessenger.getBinder();
            }
            return null;
        }
        return null;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        MediaRouteProvider mediaRouteProvider = this.mProvider;
        if (mediaRouteProvider != null) {
            mediaRouteProvider.setCallback(null);
        }
        return super.onUnbind(intent);
    }

    boolean onRegisterClient(Messenger messenger, int i, int i2) {
        if (i2 < 1 || findClient(messenger) >= 0) {
            return false;
        }
        ClientRecord clientRecord = new ClientRecord(messenger, i2);
        if (clientRecord.register()) {
            this.mClients.add(clientRecord);
            if (DEBUG) {
                Log.d(TAG, clientRecord + ": Registered, version=" + i2);
            }
            if (i != 0) {
                sendReply(messenger, 2, i, 2, createDescriptorBundleForClientVersion(this.mProvider.getDescriptor(), clientRecord.mVersion), null);
            }
            return true;
        }
        return false;
    }

    boolean onUnregisterClient(Messenger messenger, int i) {
        int findClient = findClient(messenger);
        if (findClient >= 0) {
            ClientRecord remove = this.mClients.remove(findClient);
            if (DEBUG) {
                Log.d(TAG, remove + ": Unregistered");
            }
            remove.dispose();
            sendGenericSuccess(messenger, i);
            return true;
        }
        return false;
    }

    void onBinderDied(Messenger messenger) {
        int findClient = findClient(messenger);
        if (findClient >= 0) {
            ClientRecord remove = this.mClients.remove(findClient);
            if (DEBUG) {
                Log.d(TAG, remove + ": Binder died");
            }
            remove.dispose();
        }
    }

    boolean onCreateRouteController(Messenger messenger, int i, int i2, String str, String str2) {
        ClientRecord client = getClient(messenger);
        if (client == null || !client.createRouteController(str, str2, i2)) {
            return false;
        }
        if (DEBUG) {
            Log.d(TAG, client + ": Route controller created, controllerId=" + i2 + ", routeId=" + str + ", routeGroupId=" + str2);
        }
        sendGenericSuccess(messenger, i);
        return true;
    }

    boolean onCreateDynamicGroupRouteController(Messenger messenger, int i, int i2, String str) {
        Bundle createDynamicGroupRouteController;
        ClientRecord client = getClient(messenger);
        if (client == null || (createDynamicGroupRouteController = client.createDynamicGroupRouteController(str, i2)) == null) {
            return false;
        }
        if (DEBUG) {
            Log.d(TAG, client + ": Route controller created, controllerId=" + i2 + ", initialMemberRouteId=" + str);
        }
        sendReply(messenger, 6, i, 2, createDynamicGroupRouteController, null);
        return true;
    }

    boolean onAddMemberRoute(Messenger messenger, int i, int i2, String str) {
        ClientRecord client = getClient(messenger);
        if (client != null) {
            MediaRouteProvider.RouteController routeController = client.getRouteController(i2);
            if (routeController instanceof MediaRouteProvider.DynamicGroupRouteController) {
                ((MediaRouteProvider.DynamicGroupRouteController) routeController).onAddMemberRoute(str);
                if (DEBUG) {
                    Log.d(TAG, client + ": Added a member route, controllerId=" + i2 + ", memberId=" + str);
                }
                sendGenericSuccess(messenger, i);
                return true;
            }
            return false;
        }
        return false;
    }

    boolean onRemoveMemberRoute(Messenger messenger, int i, int i2, String str) {
        ClientRecord client = getClient(messenger);
        if (client != null) {
            MediaRouteProvider.RouteController routeController = client.getRouteController(i2);
            if (routeController instanceof MediaRouteProvider.DynamicGroupRouteController) {
                ((MediaRouteProvider.DynamicGroupRouteController) routeController).onRemoveMemberRoute(str);
                if (DEBUG) {
                    Log.d(TAG, client + ": Removed a member route, controllerId=" + i2 + ", memberId=" + str);
                }
                sendGenericSuccess(messenger, i);
                return true;
            }
            return false;
        }
        return false;
    }

    boolean onUpdateMemberRoutes(Messenger messenger, int i, int i2, List<String> list) {
        ClientRecord client = getClient(messenger);
        if (client != null) {
            MediaRouteProvider.RouteController routeController = client.getRouteController(i2);
            if (routeController instanceof MediaRouteProvider.DynamicGroupRouteController) {
                ((MediaRouteProvider.DynamicGroupRouteController) routeController).onUpdateMemberRoutes(list);
                if (DEBUG) {
                    Log.d(TAG, client + ": Updated list of member routes, controllerId=" + i2 + ", memberIds=" + list);
                }
                sendGenericSuccess(messenger, i);
                return true;
            }
            return false;
        }
        return false;
    }

    boolean onReleaseRouteController(Messenger messenger, int i, int i2) {
        ClientRecord client = getClient(messenger);
        if (client == null || !client.releaseRouteController(i2)) {
            return false;
        }
        if (DEBUG) {
            Log.d(TAG, client + ": Route controller released, controllerId=" + i2);
        }
        sendGenericSuccess(messenger, i);
        return true;
    }

    boolean onSelectRoute(Messenger messenger, int i, int i2) {
        MediaRouteProvider.RouteController routeController;
        ClientRecord client = getClient(messenger);
        if (client == null || (routeController = client.getRouteController(i2)) == null) {
            return false;
        }
        routeController.onSelect();
        if (DEBUG) {
            Log.d(TAG, client + ": Route selected, controllerId=" + i2);
        }
        sendGenericSuccess(messenger, i);
        return true;
    }

    boolean onUnselectRoute(Messenger messenger, int i, int i2, int i3) {
        MediaRouteProvider.RouteController routeController;
        ClientRecord client = getClient(messenger);
        if (client == null || (routeController = client.getRouteController(i2)) == null) {
            return false;
        }
        routeController.onUnselect(i3);
        if (DEBUG) {
            Log.d(TAG, client + ": Route unselected, controllerId=" + i2);
        }
        sendGenericSuccess(messenger, i);
        return true;
    }

    boolean onSetRouteVolume(Messenger messenger, int i, int i2, int i3) {
        MediaRouteProvider.RouteController routeController;
        ClientRecord client = getClient(messenger);
        if (client == null || (routeController = client.getRouteController(i2)) == null) {
            return false;
        }
        routeController.onSetVolume(i3);
        if (DEBUG) {
            Log.d(TAG, client + ": Route volume changed, controllerId=" + i2 + ", volume=" + i3);
        }
        sendGenericSuccess(messenger, i);
        return true;
    }

    boolean onUpdateRouteVolume(Messenger messenger, int i, int i2, int i3) {
        MediaRouteProvider.RouteController routeController;
        ClientRecord client = getClient(messenger);
        if (client == null || (routeController = client.getRouteController(i2)) == null) {
            return false;
        }
        routeController.onUpdateVolume(i3);
        if (DEBUG) {
            Log.d(TAG, client + ": Route volume updated, controllerId=" + i2 + ", delta=" + i3);
        }
        sendGenericSuccess(messenger, i);
        return true;
    }

    boolean onRouteControlRequest(final Messenger messenger, final int i, final int i2, final Intent intent) {
        MediaRouteProvider.RouteController routeController;
        final ClientRecord client = getClient(messenger);
        if (client == null || (routeController = client.getRouteController(i2)) == null) {
            return false;
        }
        if (routeController.onControlRequest(intent, i != 0 ? new MediaRouter.ControlRequestCallback() { // from class: androidx.mediarouter.media.MediaRouteProviderService.1
            @Override // androidx.mediarouter.media.MediaRouter.ControlRequestCallback
            public void onResult(Bundle bundle) {
                if (MediaRouteProviderService.DEBUG) {
                    Log.d(MediaRouteProviderService.TAG, client + ": Route control request succeeded, controllerId=" + i2 + ", intent=" + intent + ", data=" + bundle);
                }
                if (MediaRouteProviderService.this.findClient(messenger) >= 0) {
                    MediaRouteProviderService.sendReply(messenger, 3, i, 0, bundle, null);
                }
            }

            @Override // androidx.mediarouter.media.MediaRouter.ControlRequestCallback
            public void onError(String str, Bundle bundle) {
                if (MediaRouteProviderService.DEBUG) {
                    Log.d(MediaRouteProviderService.TAG, client + ": Route control request failed, controllerId=" + i2 + ", intent=" + intent + ", error=" + str + ", data=" + bundle);
                }
                if (MediaRouteProviderService.this.findClient(messenger) >= 0) {
                    if (str != null) {
                        Bundle bundle2 = new Bundle();
                        bundle2.putString(MediaRouteProviderProtocol.SERVICE_DATA_ERROR, str);
                        MediaRouteProviderService.sendReply(messenger, 4, i, 0, bundle, bundle2);
                        return;
                    }
                    MediaRouteProviderService.sendReply(messenger, 4, i, 0, bundle, null);
                }
            }
        } : null)) {
            if (DEBUG) {
                Log.d(TAG, client + ": Route control request delivered, controllerId=" + i2 + ", intent=" + intent);
                return true;
            }
            return true;
        }
        return false;
    }

    boolean onSetDiscoveryRequest(Messenger messenger, int i, MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
        ClientRecord client = getClient(messenger);
        if (client != null) {
            boolean discoveryRequest = client.setDiscoveryRequest(mediaRouteDiscoveryRequest);
            if (DEBUG) {
                Log.d(TAG, client + ": Set discovery request, request=" + mediaRouteDiscoveryRequest + ", actuallyChanged=" + discoveryRequest + ", compositeDiscoveryRequest=" + this.mCompositeDiscoveryRequest);
            }
            sendGenericSuccess(messenger, i);
            return true;
        }
        return false;
    }

    void sendDescriptorChanged(MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
        int size = this.mClients.size();
        for (int i = 0; i < size; i++) {
            ClientRecord clientRecord = this.mClients.get(i);
            sendReply(clientRecord.mMessenger, 5, 0, 0, createDescriptorBundleForClientVersion(mediaRouteProviderDescriptor, clientRecord.mVersion), null);
            if (DEBUG) {
                Log.d(TAG, clientRecord + ": Sent descriptor change event, descriptor=" + mediaRouteProviderDescriptor);
            }
        }
    }

    @VisibleForTesting
    static Bundle createDescriptorBundleForClientVersion(MediaRouteProviderDescriptor mediaRouteProviderDescriptor, int i) {
        if (mediaRouteProviderDescriptor == null) {
            return null;
        }
        MediaRouteProviderDescriptor.Builder builder = new MediaRouteProviderDescriptor.Builder(mediaRouteProviderDescriptor);
        builder.setRoutes(null);
        for (MediaRouteDescriptor mediaRouteDescriptor : mediaRouteProviderDescriptor.getRoutes()) {
            if (i >= mediaRouteDescriptor.getMinClientVersion() && i <= mediaRouteDescriptor.getMaxClientVersion()) {
                builder.addRoute(mediaRouteDescriptor);
            }
        }
        return builder.build().asBundle();
    }

    boolean updateCompositeDiscoveryRequest() {
        int size = this.mClients.size();
        MediaRouteSelector.Builder builder = null;
        MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest = null;
        boolean z = false;
        for (int i = 0; i < size; i++) {
            MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest2 = this.mClients.get(i).mDiscoveryRequest;
            if (mediaRouteDiscoveryRequest2 != null && (!mediaRouteDiscoveryRequest2.getSelector().isEmpty() || mediaRouteDiscoveryRequest2.isActiveScan())) {
                z |= mediaRouteDiscoveryRequest2.isActiveScan();
                if (mediaRouteDiscoveryRequest == null) {
                    mediaRouteDiscoveryRequest = mediaRouteDiscoveryRequest2;
                } else {
                    if (builder == null) {
                        builder = new MediaRouteSelector.Builder(mediaRouteDiscoveryRequest.getSelector());
                    }
                    builder.addSelector(mediaRouteDiscoveryRequest2.getSelector());
                }
            }
        }
        if (builder != null) {
            mediaRouteDiscoveryRequest = new MediaRouteDiscoveryRequest(builder.build(), z);
        }
        if (ObjectsCompat.equals(this.mCompositeDiscoveryRequest, mediaRouteDiscoveryRequest)) {
            return false;
        }
        this.mCompositeDiscoveryRequest = mediaRouteDiscoveryRequest;
        this.mProvider.setDiscoveryRequest(mediaRouteDiscoveryRequest);
        return true;
    }

    private ClientRecord getClient(Messenger messenger) {
        int findClient = findClient(messenger);
        if (findClient >= 0) {
            return this.mClients.get(findClient);
        }
        return null;
    }

    int findClient(Messenger messenger) {
        int size = this.mClients.size();
        for (int i = 0; i < size; i++) {
            if (this.mClients.get(i).hasMessenger(messenger)) {
                return i;
            }
        }
        return -1;
    }

    static void sendGenericFailure(Messenger messenger, int i) {
        if (i != 0) {
            sendReply(messenger, 0, i, 0, null, null);
        }
    }

    private static void sendGenericSuccess(Messenger messenger, int i) {
        if (i != 0) {
            sendReply(messenger, 1, i, 0, null, null);
        }
    }

    static void sendReply(Messenger messenger, int i, int i2, int i3, Object obj, Bundle bundle) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        obtain.obj = obj;
        obtain.setData(bundle);
        try {
            messenger.send(obtain);
        } catch (DeadObjectException unused) {
        } catch (RemoteException e) {
            Log.e(TAG, "Could not send message to " + getClientId(messenger), e);
        }
    }

    static String getClientId(Messenger messenger) {
        return "Client connection " + messenger.getBinder().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class PrivateHandler extends Handler {
        PrivateHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            MediaRouteProviderService.this.onBinderDied((Messenger) message.obj);
        }
    }

    /* loaded from: classes.dex */
    private final class ProviderCallback extends MediaRouteProvider.Callback {
        ProviderCallback() {
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.Callback
        public void onDescriptorChanged(MediaRouteProvider mediaRouteProvider, MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
            MediaRouteProviderService.this.sendDescriptorChanged(mediaRouteProviderDescriptor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class ClientRecord implements IBinder.DeathRecipient {
        public MediaRouteDiscoveryRequest mDiscoveryRequest;
        public final Messenger mMessenger;
        public final int mVersion;
        private final SparseArray<MediaRouteProvider.RouteController> mControllers = new SparseArray<>();
        final MediaRouteProvider.DynamicGroupRouteController.OnDynamicRoutesChangedListener mDynamicRoutesChangedListener = new MediaRouteProvider.DynamicGroupRouteController.OnDynamicRoutesChangedListener() { // from class: androidx.mediarouter.media.MediaRouteProviderService.ClientRecord.1
            @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController.OnDynamicRoutesChangedListener
            public void onRoutesChanged(MediaRouteProvider.DynamicGroupRouteController dynamicGroupRouteController, Collection<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> collection) {
                ClientRecord.this.sendDynamicRouteDescriptors(dynamicGroupRouteController, collection);
            }
        };

        public ClientRecord(Messenger messenger, int i) {
            this.mMessenger = messenger;
            this.mVersion = i;
        }

        public boolean register() {
            try {
                this.mMessenger.getBinder().linkToDeath(this, 0);
                return true;
            } catch (RemoteException unused) {
                binderDied();
                return false;
            }
        }

        public void dispose() {
            int size = this.mControllers.size();
            for (int i = 0; i < size; i++) {
                this.mControllers.valueAt(i).onRelease();
            }
            this.mControllers.clear();
            this.mMessenger.getBinder().unlinkToDeath(this, 0);
            setDiscoveryRequest(null);
        }

        public boolean hasMessenger(Messenger messenger) {
            return this.mMessenger.getBinder() == messenger.getBinder();
        }

        public boolean createRouteController(String str, String str2, int i) {
            MediaRouteProvider.RouteController onCreateRouteController;
            if (this.mControllers.indexOfKey(i) < 0) {
                if (str2 == null) {
                    onCreateRouteController = MediaRouteProviderService.this.mProvider.onCreateRouteController(str);
                } else {
                    onCreateRouteController = MediaRouteProviderService.this.mProvider.onCreateRouteController(str, str2);
                }
                if (onCreateRouteController != null) {
                    this.mControllers.put(i, onCreateRouteController);
                    return true;
                }
                return false;
            }
            return false;
        }

        public Bundle createDynamicGroupRouteController(String str, int i) {
            if (this.mControllers.indexOfKey(i) < 0) {
                MediaRouteProvider.DynamicGroupRouteController onCreateDynamicGroupRouteController = MediaRouteProviderService.this.mProvider.onCreateDynamicGroupRouteController(str);
                onCreateDynamicGroupRouteController.setOnDynamicRoutesChangedListener(ContextCompat.getMainExecutor(MediaRouteProviderService.this.getApplicationContext()), this.mDynamicRoutesChangedListener);
                this.mControllers.put(i, onCreateDynamicGroupRouteController);
                Bundle bundle = new Bundle();
                bundle.putString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_ID, str);
                bundle.putString(MediaRouteProviderProtocol.DATA_KEY_GROUPABLE_SECION_TITLE, onCreateDynamicGroupRouteController.getGroupableSelectionTitle());
                bundle.putString(MediaRouteProviderProtocol.DATA_KEY_TRANSFERABLE_SECTION_TITLE, onCreateDynamicGroupRouteController.getTransferableSectionTitle());
                return bundle;
            }
            return null;
        }

        public boolean releaseRouteController(int i) {
            MediaRouteProvider.RouteController routeController = this.mControllers.get(i);
            if (routeController != null) {
                this.mControllers.remove(i);
                routeController.onRelease();
                return true;
            }
            return false;
        }

        public MediaRouteProvider.RouteController getRouteController(int i) {
            return this.mControllers.get(i);
        }

        public boolean setDiscoveryRequest(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
            if (ObjectsCompat.equals(this.mDiscoveryRequest, mediaRouteDiscoveryRequest)) {
                return false;
            }
            this.mDiscoveryRequest = mediaRouteDiscoveryRequest;
            return MediaRouteProviderService.this.updateCompositeDiscoveryRequest();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MediaRouteProviderService.this.mPrivateHandler.obtainMessage(1, this.mMessenger).sendToTarget();
        }

        public String toString() {
            return MediaRouteProviderService.getClientId(this.mMessenger);
        }

        void sendDynamicRouteDescriptors(MediaRouteProvider.DynamicGroupRouteController dynamicGroupRouteController, Collection<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> collection) {
            int indexOfValue = this.mControllers.indexOfValue(dynamicGroupRouteController);
            if (indexOfValue < 0) {
                Log.d(MediaRouteProviderService.TAG, "Ignoring unknown dynamic group route controller: " + dynamicGroupRouteController);
                return;
            }
            int keyAt = this.mControllers.keyAt(indexOfValue);
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            for (MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor : collection) {
                arrayList.add(dynamicRouteDescriptor.toBundle());
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(MediaRouteProviderProtocol.DATA_KEY_DYNAMIC_ROUTE_DESCRIPTORS, arrayList);
            MediaRouteProviderService.sendReply(this.mMessenger, 7, 0, keyAt, bundle, null);
        }
    }

    /* loaded from: classes.dex */
    private static final class ReceiveHandler extends Handler {
        private final WeakReference<MediaRouteProviderService> mServiceRef;

        public ReceiveHandler(MediaRouteProviderService mediaRouteProviderService) {
            this.mServiceRef = new WeakReference<>(mediaRouteProviderService);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Messenger messenger = message.replyTo;
            if (MediaRouteProviderProtocol.isValidRemoteMessenger(messenger)) {
                int i = message.what;
                int i2 = message.arg1;
                int i3 = message.arg2;
                Object obj = message.obj;
                Bundle peekData = message.peekData();
                if (processMessage(i, messenger, i2, i3, obj, peekData)) {
                    return;
                }
                if (MediaRouteProviderService.DEBUG) {
                    Log.d(MediaRouteProviderService.TAG, MediaRouteProviderService.getClientId(messenger) + ": Message failed, what=" + i + ", requestId=" + i2 + ", arg=" + i3 + ", obj=" + obj + ", data=" + peekData);
                }
                MediaRouteProviderService.sendGenericFailure(messenger, i2);
            } else if (MediaRouteProviderService.DEBUG) {
                Log.d(MediaRouteProviderService.TAG, "Ignoring message without valid reply messenger.");
            }
        }

        private boolean processMessage(int i, Messenger messenger, int i2, int i3, Object obj, Bundle bundle) {
            MediaRouteProviderService mediaRouteProviderService = this.mServiceRef.get();
            if (mediaRouteProviderService != null) {
                switch (i) {
                    case 1:
                        return mediaRouteProviderService.onRegisterClient(messenger, i2, i3);
                    case 2:
                        return mediaRouteProviderService.onUnregisterClient(messenger, i2);
                    case 3:
                        String string = bundle.getString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_ID);
                        String string2 = bundle.getString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_LIBRARY_GROUP);
                        if (string != null) {
                            return mediaRouteProviderService.onCreateRouteController(messenger, i2, i3, string, string2);
                        }
                        break;
                    case 4:
                        return mediaRouteProviderService.onReleaseRouteController(messenger, i2, i3);
                    case 5:
                        return mediaRouteProviderService.onSelectRoute(messenger, i2, i3);
                    case 6:
                        return mediaRouteProviderService.onUnselectRoute(messenger, i2, i3, bundle != null ? bundle.getInt(MediaRouteProviderProtocol.CLIENT_DATA_UNSELECT_REASON, 0) : 0);
                    case 7:
                        int i4 = bundle.getInt(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, -1);
                        if (i4 >= 0) {
                            return mediaRouteProviderService.onSetRouteVolume(messenger, i2, i3, i4);
                        }
                        break;
                    case 8:
                        int i5 = bundle.getInt(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, 0);
                        if (i5 != 0) {
                            return mediaRouteProviderService.onUpdateRouteVolume(messenger, i2, i3, i5);
                        }
                        break;
                    case 9:
                        if (obj instanceof Intent) {
                            return mediaRouteProviderService.onRouteControlRequest(messenger, i2, i3, (Intent) obj);
                        }
                        break;
                    case 10:
                        if (obj == null || (obj instanceof Bundle)) {
                            MediaRouteDiscoveryRequest fromBundle = MediaRouteDiscoveryRequest.fromBundle((Bundle) obj);
                            return mediaRouteProviderService.onSetDiscoveryRequest(messenger, i2, (fromBundle == null || !fromBundle.isValid()) ? null : null);
                        }
                        break;
                    case 11:
                        String string3 = bundle.getString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID);
                        if (string3 != null) {
                            return mediaRouteProviderService.onCreateDynamicGroupRouteController(messenger, i2, i3, string3);
                        }
                        break;
                    case 12:
                        String string4 = bundle.getString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID);
                        if (string4 != null) {
                            return mediaRouteProviderService.onAddMemberRoute(messenger, i2, i3, string4);
                        }
                        break;
                    case 13:
                        String string5 = bundle.getString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID);
                        if (string5 != null) {
                            return mediaRouteProviderService.onRemoveMemberRoute(messenger, i2, i3, string5);
                        }
                        break;
                    case 14:
                        ArrayList<String> stringArrayList = bundle.getStringArrayList(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_IDS);
                        if (stringArrayList != null) {
                            return mediaRouteProviderService.onUpdateMemberRoutes(messenger, i2, i3, stringArrayList);
                        }
                        break;
                }
            }
            return false;
        }
    }
}
