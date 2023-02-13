package androidx.mediarouter.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class RegisteredMediaRouteProvider extends MediaRouteProvider implements ServiceConnection {
    private Connection mActiveConnection;
    private boolean mBound;
    private final ComponentName mComponentName;
    private boolean mConnectionReady;
    private final ArrayList<ControllerConnection> mControllerConnections;
    final PrivateHandler mPrivateHandler;
    private boolean mStarted;
    static final String TAG = "MediaRouteProviderProxy";
    static final boolean DEBUG = Log.isLoggable(TAG, 3);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface ControllerConnection {
        void attachConnection(Connection connection);

        void detachConnection();

        int getControllerId();
    }

    public RegisteredMediaRouteProvider(Context context, ComponentName componentName) {
        super(context, new MediaRouteProvider.ProviderMetadata(componentName));
        this.mControllerConnections = new ArrayList<>();
        this.mComponentName = componentName;
        this.mPrivateHandler = new PrivateHandler();
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public MediaRouteProvider.RouteController onCreateRouteController(@NonNull String str) {
        if (str == null) {
            throw new IllegalArgumentException("routeId cannot be null");
        }
        return createRouteController(str, null);
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public MediaRouteProvider.RouteController onCreateRouteController(@NonNull String str, @NonNull String str2) {
        if (str != null) {
            if (str2 == null) {
                throw new IllegalArgumentException("routeGroupId cannot be null");
            }
            return createRouteController(str, str2);
        }
        throw new IllegalArgumentException("routeId cannot be null");
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public MediaRouteProvider.DynamicGroupRouteController onCreateDynamicGroupRouteController(@NonNull String str) {
        if (str == null) {
            throw new IllegalArgumentException("initialMemberRouteId cannot be null.");
        }
        return createDynamicGroupRouteController(str);
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
        if (this.mConnectionReady) {
            this.mActiveConnection.setDiscoveryRequest(mediaRouteDiscoveryRequest);
        }
        updateBinding();
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        boolean z = DEBUG;
        if (z) {
            Log.d(TAG, this + ": Connected");
        }
        if (this.mBound) {
            disconnect();
            Messenger messenger = iBinder != null ? new Messenger(iBinder) : null;
            if (MediaRouteProviderProtocol.isValidRemoteMessenger(messenger)) {
                Connection connection = new Connection(messenger);
                if (connection.register()) {
                    this.mActiveConnection = connection;
                    return;
                } else if (z) {
                    Log.d(TAG, this + ": Registration failed");
                    return;
                } else {
                    return;
                }
            }
            Log.e(TAG, this + ": Service returned invalid messenger binder");
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        if (DEBUG) {
            Log.d(TAG, this + ": Service disconnected");
        }
        disconnect();
    }

    public String toString() {
        return "Service connection " + this.mComponentName.flattenToShortString();
    }

    public boolean hasComponentName(String str, String str2) {
        return this.mComponentName.getPackageName().equals(str) && this.mComponentName.getClassName().equals(str2);
    }

    public void start() {
        if (this.mStarted) {
            return;
        }
        if (DEBUG) {
            Log.d(TAG, this + ": Starting");
        }
        this.mStarted = true;
        updateBinding();
    }

    public void stop() {
        if (this.mStarted) {
            if (DEBUG) {
                Log.d(TAG, this + ": Stopping");
            }
            this.mStarted = false;
            updateBinding();
        }
    }

    public void rebindIfDisconnected() {
        if (this.mActiveConnection == null && shouldBind()) {
            unbind();
            bind();
        }
    }

    private void updateBinding() {
        if (shouldBind()) {
            bind();
        } else {
            unbind();
        }
    }

    private boolean shouldBind() {
        if (this.mStarted) {
            return (getDiscoveryRequest() == null && this.mControllerConnections.isEmpty()) ? false : true;
        }
        return false;
    }

    private void bind() {
        if (this.mBound) {
            return;
        }
        boolean z = DEBUG;
        if (z) {
            Log.d(TAG, this + ": Binding");
        }
        Intent intent = new Intent("android.media.MediaRouteProviderService");
        intent.setComponent(this.mComponentName);
        try {
            boolean bindService = getContext().bindService(intent, this, 1);
            this.mBound = bindService;
            if (bindService || !z) {
                return;
            }
            Log.d(TAG, this + ": Bind failed");
        } catch (SecurityException e) {
            if (DEBUG) {
                Log.d(TAG, this + ": Bind failed", e);
            }
        }
    }

    private void unbind() {
        if (this.mBound) {
            if (DEBUG) {
                Log.d(TAG, this + ": Unbinding");
            }
            this.mBound = false;
            disconnect();
            try {
                getContext().unbindService(this);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, this + ": unbindService failed", e);
            }
        }
    }

    private MediaRouteProvider.RouteController createRouteController(String str, String str2) {
        MediaRouteProviderDescriptor descriptor = getDescriptor();
        if (descriptor != null) {
            List<MediaRouteDescriptor> routes = descriptor.getRoutes();
            int size = routes.size();
            for (int i = 0; i < size; i++) {
                if (routes.get(i).getId().equals(str)) {
                    RegisteredRouteController registeredRouteController = new RegisteredRouteController(str, str2);
                    this.mControllerConnections.add(registeredRouteController);
                    if (this.mConnectionReady) {
                        registeredRouteController.attachConnection(this.mActiveConnection);
                    }
                    updateBinding();
                    return registeredRouteController;
                }
            }
            return null;
        }
        return null;
    }

    private MediaRouteProvider.DynamicGroupRouteController createDynamicGroupRouteController(String str) {
        MediaRouteProviderDescriptor descriptor = getDescriptor();
        if (descriptor != null) {
            List<MediaRouteDescriptor> routes = descriptor.getRoutes();
            int size = routes.size();
            for (int i = 0; i < size; i++) {
                if (routes.get(i).getId().equals(str)) {
                    RegisteredDynamicController registeredDynamicController = new RegisteredDynamicController(str);
                    this.mControllerConnections.add(registeredDynamicController);
                    if (this.mConnectionReady) {
                        registeredDynamicController.attachConnection(this.mActiveConnection);
                    }
                    updateBinding();
                    return registeredDynamicController;
                }
            }
            return null;
        }
        return null;
    }

    void onConnectionReady(Connection connection) {
        if (this.mActiveConnection == connection) {
            this.mConnectionReady = true;
            attachControllersToConnection();
            MediaRouteDiscoveryRequest discoveryRequest = getDiscoveryRequest();
            if (discoveryRequest != null) {
                this.mActiveConnection.setDiscoveryRequest(discoveryRequest);
            }
        }
    }

    void onConnectionDied(Connection connection) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": Service connection died");
            }
            disconnect();
        }
    }

    void onConnectionError(Connection connection, String str) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": Service connection error - " + str);
            }
            unbind();
        }
    }

    void onConnectionDescriptorChanged(Connection connection, MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": Descriptor changed, descriptor=" + mediaRouteProviderDescriptor);
            }
            setDescriptor(mediaRouteProviderDescriptor);
        }
    }

    void onDynamicRouteDescriptorChanged(Connection connection, int i, List<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> list) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": DynamicRouteDescriptors changed, descriptors=" + list);
            }
            ControllerConnection findControllerById = findControllerById(i);
            if (findControllerById instanceof RegisteredDynamicController) {
                ((RegisteredDynamicController) findControllerById).onDynamicRoutesChanged(list);
            }
        }
    }

    private ControllerConnection findControllerById(int i) {
        Iterator<ControllerConnection> it = this.mControllerConnections.iterator();
        while (it.hasNext()) {
            ControllerConnection next = it.next();
            if (next.getControllerId() == i) {
                return next;
            }
        }
        return null;
    }

    private void disconnect() {
        if (this.mActiveConnection != null) {
            setDescriptor(null);
            this.mConnectionReady = false;
            detachControllersFromConnection();
            this.mActiveConnection.dispose();
            this.mActiveConnection = null;
        }
    }

    void onControllerReleased(ControllerConnection controllerConnection) {
        this.mControllerConnections.remove(controllerConnection);
        controllerConnection.detachConnection();
        updateBinding();
    }

    private void attachControllersToConnection() {
        int size = this.mControllerConnections.size();
        for (int i = 0; i < size; i++) {
            this.mControllerConnections.get(i).attachConnection(this.mActiveConnection);
        }
    }

    private void detachControllersFromConnection() {
        int size = this.mControllerConnections.size();
        for (int i = 0; i < size; i++) {
            this.mControllerConnections.get(i).detachConnection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class RegisteredDynamicController extends MediaRouteProvider.DynamicGroupRouteController implements ControllerConnection {
        private Connection mConnection;
        MediaRouteProvider.DynamicGroupRouteController.OnDynamicRoutesChangedListener mDynamicRoutesChangedListener;
        String mGroupableSectionTitle;
        private final String mInitialMemberRouteId;
        private Executor mListenerExecutor;
        private int mPendingUpdateVolumeDelta;
        private boolean mSelected;
        String mTransferableSectionTitle;
        private int mPendingSetVolume = -1;
        private int mControllerId = -1;

        RegisteredDynamicController(String str) {
            this.mInitialMemberRouteId = str;
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public int getControllerId() {
            return this.mControllerId;
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public void attachConnection(Connection connection) {
            MediaRouter.ControlRequestCallback controlRequestCallback = new MediaRouter.ControlRequestCallback() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProvider.RegisteredDynamicController.1
                @Override // androidx.mediarouter.media.MediaRouter.ControlRequestCallback
                public void onResult(Bundle bundle) {
                    RegisteredDynamicController.this.mGroupableSectionTitle = bundle.getString(MediaRouteProviderProtocol.DATA_KEY_GROUPABLE_SECION_TITLE);
                    RegisteredDynamicController.this.mTransferableSectionTitle = bundle.getString(MediaRouteProviderProtocol.DATA_KEY_TRANSFERABLE_SECTION_TITLE);
                }

                @Override // androidx.mediarouter.media.MediaRouter.ControlRequestCallback
                public void onError(String str, Bundle bundle) {
                    Log.d(RegisteredMediaRouteProvider.TAG, "Error: " + str + ", data: " + bundle);
                }
            };
            this.mConnection = connection;
            int createDynamicGroupRouteController = connection.createDynamicGroupRouteController(this.mInitialMemberRouteId, controlRequestCallback);
            this.mControllerId = createDynamicGroupRouteController;
            if (this.mSelected) {
                connection.selectRoute(createDynamicGroupRouteController);
                int i = this.mPendingSetVolume;
                if (i >= 0) {
                    connection.setVolume(this.mControllerId, i);
                    this.mPendingSetVolume = -1;
                }
                int i2 = this.mPendingUpdateVolumeDelta;
                if (i2 != 0) {
                    connection.updateVolume(this.mControllerId, i2);
                    this.mPendingUpdateVolumeDelta = 0;
                }
            }
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public void detachConnection() {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.releaseRouteController(this.mControllerId);
                this.mConnection = null;
                this.mControllerId = 0;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onRelease() {
            RegisteredMediaRouteProvider.this.onControllerReleased(this);
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onSelect() {
            this.mSelected = true;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.selectRoute(this.mControllerId);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onUnselect() {
            onUnselect(0);
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onUnselect(int i) {
            this.mSelected = false;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.unselectRoute(this.mControllerId, i);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onSetVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.setVolume(this.mControllerId, i);
                return;
            }
            this.mPendingSetVolume = i;
            this.mPendingUpdateVolumeDelta = 0;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onUpdateVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateVolume(this.mControllerId, i);
            } else {
                this.mPendingUpdateVolumeDelta += i;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public boolean onControlRequest(Intent intent, MediaRouter.ControlRequestCallback controlRequestCallback) {
            Connection connection = this.mConnection;
            if (connection != null) {
                return connection.sendControlRequest(this.mControllerId, intent, controlRequestCallback);
            }
            return false;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public String getGroupableSelectionTitle() {
            return this.mGroupableSectionTitle;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public String getTransferableSectionTitle() {
            return this.mTransferableSectionTitle;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public void onUpdateMemberRoutes(@Nullable List<String> list) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateMemberRoutes(this.mControllerId, list);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public void onAddMemberRoute(@NonNull String str) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.addMemberRoute(this.mControllerId, str);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public void onRemoveMemberRoute(String str) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.removeMemberRoute(this.mControllerId, str);
            }
        }

        void onDynamicRoutesChanged(List<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> list) {
            notifyDynamicRoutesChanged(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class RegisteredRouteController extends MediaRouteProvider.RouteController implements ControllerConnection {
        private Connection mConnection;
        private int mControllerId;
        private int mPendingSetVolume = -1;
        private int mPendingUpdateVolumeDelta;
        private final String mRouteGroupId;
        private final String mRouteId;
        private boolean mSelected;

        RegisteredRouteController(String str, String str2) {
            this.mRouteId = str;
            this.mRouteGroupId = str2;
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public int getControllerId() {
            return this.mControllerId;
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public void attachConnection(Connection connection) {
            this.mConnection = connection;
            int createRouteController = connection.createRouteController(this.mRouteId, this.mRouteGroupId);
            this.mControllerId = createRouteController;
            if (this.mSelected) {
                connection.selectRoute(createRouteController);
                int i = this.mPendingSetVolume;
                if (i >= 0) {
                    connection.setVolume(this.mControllerId, i);
                    this.mPendingSetVolume = -1;
                }
                int i2 = this.mPendingUpdateVolumeDelta;
                if (i2 != 0) {
                    connection.updateVolume(this.mControllerId, i2);
                    this.mPendingUpdateVolumeDelta = 0;
                }
            }
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public void detachConnection() {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.releaseRouteController(this.mControllerId);
                this.mConnection = null;
                this.mControllerId = 0;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onRelease() {
            RegisteredMediaRouteProvider.this.onControllerReleased(this);
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onSelect() {
            this.mSelected = true;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.selectRoute(this.mControllerId);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onUnselect() {
            onUnselect(0);
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onUnselect(int i) {
            this.mSelected = false;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.unselectRoute(this.mControllerId, i);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onSetVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.setVolume(this.mControllerId, i);
                return;
            }
            this.mPendingSetVolume = i;
            this.mPendingUpdateVolumeDelta = 0;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public void onUpdateVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateVolume(this.mControllerId, i);
            } else {
                this.mPendingUpdateVolumeDelta += i;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public boolean onControlRequest(Intent intent, MediaRouter.ControlRequestCallback controlRequestCallback) {
            Connection connection = this.mConnection;
            if (connection != null) {
                return connection.sendControlRequest(this.mControllerId, intent, controlRequestCallback);
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class Connection implements IBinder.DeathRecipient {
        private int mPendingRegisterRequestId;
        private final ReceiveHandler mReceiveHandler;
        private final Messenger mReceiveMessenger;
        private final Messenger mServiceMessenger;
        private int mServiceVersion;
        private int mNextRequestId = 1;
        private int mNextControllerId = 1;
        private final SparseArray<MediaRouter.ControlRequestCallback> mPendingCallbacks = new SparseArray<>();

        public boolean onGenericSuccess(int i) {
            return true;
        }

        public Connection(Messenger messenger) {
            this.mServiceMessenger = messenger;
            ReceiveHandler receiveHandler = new ReceiveHandler(this);
            this.mReceiveHandler = receiveHandler;
            this.mReceiveMessenger = new Messenger(receiveHandler);
        }

        public boolean register() {
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            this.mPendingRegisterRequestId = i;
            if (sendRequest(1, i, 3, null, null)) {
                try {
                    this.mServiceMessenger.getBinder().linkToDeath(this, 0);
                    return true;
                } catch (RemoteException unused) {
                    binderDied();
                    return false;
                }
            }
            return false;
        }

        public void dispose() {
            sendRequest(2, 0, 0, null, null);
            this.mReceiveHandler.dispose();
            this.mServiceMessenger.getBinder().unlinkToDeath(this, 0);
            RegisteredMediaRouteProvider.this.mPrivateHandler.post(new Runnable() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProvider.Connection.1
                @Override // java.lang.Runnable
                public void run() {
                    Connection.this.failPendingCallbacks();
                }
            });
        }

        void failPendingCallbacks() {
            int size = this.mPendingCallbacks.size();
            for (int i = 0; i < size; i++) {
                this.mPendingCallbacks.valueAt(i).onError(null, null);
            }
            this.mPendingCallbacks.clear();
        }

        public boolean onGenericFailure(int i) {
            if (i == this.mPendingRegisterRequestId) {
                this.mPendingRegisterRequestId = 0;
                RegisteredMediaRouteProvider.this.onConnectionError(this, "Registration failed");
            }
            MediaRouter.ControlRequestCallback controlRequestCallback = this.mPendingCallbacks.get(i);
            if (controlRequestCallback != null) {
                this.mPendingCallbacks.remove(i);
                controlRequestCallback.onError(null, null);
                return true;
            }
            return true;
        }

        public boolean onRegistered(int i, int i2, Bundle bundle) {
            if (this.mServiceVersion == 0 && i == this.mPendingRegisterRequestId && i2 >= 1) {
                this.mPendingRegisterRequestId = 0;
                this.mServiceVersion = i2;
                RegisteredMediaRouteProvider.this.onConnectionDescriptorChanged(this, MediaRouteProviderDescriptor.fromBundle(bundle));
                RegisteredMediaRouteProvider.this.onConnectionReady(this);
                return true;
            }
            return false;
        }

        public boolean onDescriptorChanged(Bundle bundle) {
            if (this.mServiceVersion != 0) {
                RegisteredMediaRouteProvider.this.onConnectionDescriptorChanged(this, MediaRouteProviderDescriptor.fromBundle(bundle));
                return true;
            }
            return false;
        }

        public boolean onDynamicRouteDescriptorsChanged(int i, Bundle bundle) {
            if (this.mServiceVersion != 0) {
                ArrayList parcelableArrayList = bundle.getParcelableArrayList(MediaRouteProviderProtocol.DATA_KEY_DYNAMIC_ROUTE_DESCRIPTORS);
                ArrayList arrayList = new ArrayList();
                Iterator it = parcelableArrayList.iterator();
                while (it.hasNext()) {
                    arrayList.add(MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor.fromBundle((Bundle) it.next()));
                }
                RegisteredMediaRouteProvider.this.onDynamicRouteDescriptorChanged(this, i, arrayList);
                return true;
            }
            return false;
        }

        public boolean onControlRequestSucceeded(int i, Bundle bundle) {
            MediaRouter.ControlRequestCallback controlRequestCallback = this.mPendingCallbacks.get(i);
            if (controlRequestCallback != null) {
                this.mPendingCallbacks.remove(i);
                controlRequestCallback.onResult(bundle);
                return true;
            }
            return false;
        }

        public boolean onControlRequestFailed(int i, String str, Bundle bundle) {
            MediaRouter.ControlRequestCallback controlRequestCallback = this.mPendingCallbacks.get(i);
            if (controlRequestCallback != null) {
                this.mPendingCallbacks.remove(i);
                controlRequestCallback.onError(str, bundle);
                return true;
            }
            return false;
        }

        public void onDynamicGroupRouteControllerCreated(int i, Bundle bundle) {
            MediaRouter.ControlRequestCallback controlRequestCallback = this.mPendingCallbacks.get(i);
            if (bundle != null && bundle.containsKey(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_ID)) {
                this.mPendingCallbacks.remove(i);
                controlRequestCallback.onResult(bundle);
                return;
            }
            controlRequestCallback.onError("DynamicGroupRouteController is created without valid route id.", bundle);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            RegisteredMediaRouteProvider.this.mPrivateHandler.post(new Runnable() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProvider.Connection.2
                @Override // java.lang.Runnable
                public void run() {
                    Connection connection = Connection.this;
                    RegisteredMediaRouteProvider.this.onConnectionDied(connection);
                }
            });
        }

        public int createRouteController(String str, String str2) {
            int i = this.mNextControllerId;
            this.mNextControllerId = i + 1;
            Bundle bundle = new Bundle();
            bundle.putString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_ID, str);
            bundle.putString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_LIBRARY_GROUP, str2);
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(3, i2, i, null, bundle);
            return i;
        }

        public int createDynamicGroupRouteController(String str, MediaRouter.ControlRequestCallback controlRequestCallback) {
            int i = this.mNextControllerId;
            this.mNextControllerId = i + 1;
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            Bundle bundle = new Bundle();
            bundle.putString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID, str);
            sendRequest(11, i2, i, null, bundle);
            this.mPendingCallbacks.put(i2, controlRequestCallback);
            return i;
        }

        public void releaseRouteController(int i) {
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(4, i2, i, null, null);
        }

        public void selectRoute(int i) {
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(5, i2, i, null, null);
        }

        public void unselectRoute(int i, int i2) {
            Bundle bundle = new Bundle();
            bundle.putInt(MediaRouteProviderProtocol.CLIENT_DATA_UNSELECT_REASON, i2);
            int i3 = this.mNextRequestId;
            this.mNextRequestId = i3 + 1;
            sendRequest(6, i3, i, null, bundle);
        }

        public void setVolume(int i, int i2) {
            Bundle bundle = new Bundle();
            bundle.putInt(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, i2);
            int i3 = this.mNextRequestId;
            this.mNextRequestId = i3 + 1;
            sendRequest(7, i3, i, null, bundle);
        }

        public void updateVolume(int i, int i2) {
            Bundle bundle = new Bundle();
            bundle.putInt(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, i2);
            int i3 = this.mNextRequestId;
            this.mNextRequestId = i3 + 1;
            sendRequest(8, i3, i, null, bundle);
        }

        public boolean sendControlRequest(int i, Intent intent, MediaRouter.ControlRequestCallback controlRequestCallback) {
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            if (sendRequest(9, i2, i, intent, null)) {
                if (controlRequestCallback != null) {
                    this.mPendingCallbacks.put(i2, controlRequestCallback);
                    return true;
                }
                return true;
            }
            return false;
        }

        public void updateMemberRoutes(int i, List<String> list) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_IDS, new ArrayList<>(list));
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(14, i2, i, null, bundle);
        }

        public void addMemberRoute(int i, String str) {
            Bundle bundle = new Bundle();
            bundle.putString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID, str);
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(12, i2, i, null, bundle);
        }

        public void removeMemberRoute(int i, String str) {
            Bundle bundle = new Bundle();
            bundle.putString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID, str);
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(13, i2, i, null, bundle);
        }

        public void setDiscoveryRequest(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(10, i, 0, mediaRouteDiscoveryRequest != null ? mediaRouteDiscoveryRequest.asBundle() : null, null);
        }

        private boolean sendRequest(int i, int i2, int i3, Object obj, Bundle bundle) {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = i2;
            obtain.arg2 = i3;
            obtain.obj = obj;
            obtain.setData(bundle);
            obtain.replyTo = this.mReceiveMessenger;
            try {
                this.mServiceMessenger.send(obtain);
                return true;
            } catch (DeadObjectException unused) {
                return false;
            } catch (RemoteException e) {
                if (i != 2) {
                    Log.e(RegisteredMediaRouteProvider.TAG, "Could not send message to service.", e);
                    return false;
                }
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class PrivateHandler extends Handler {
        PrivateHandler() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class ReceiveHandler extends Handler {
        private final WeakReference<Connection> mConnectionRef;

        public ReceiveHandler(Connection connection) {
            this.mConnectionRef = new WeakReference<>(connection);
        }

        public void dispose() {
            this.mConnectionRef.clear();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Connection connection = this.mConnectionRef.get();
            if (connection == null || processMessage(connection, message.what, message.arg1, message.arg2, message.obj, message.peekData()) || !RegisteredMediaRouteProvider.DEBUG) {
                return;
            }
            Log.d(RegisteredMediaRouteProvider.TAG, "Unhandled message from server: " + message);
        }

        private boolean processMessage(Connection connection, int i, int i2, int i3, Object obj, Bundle bundle) {
            switch (i) {
                case 0:
                    connection.onGenericFailure(i2);
                    return true;
                case 1:
                    connection.onGenericSuccess(i2);
                    return true;
                case 2:
                    if (obj == null || (obj instanceof Bundle)) {
                        return connection.onRegistered(i2, i3, (Bundle) obj);
                    }
                    return false;
                case 3:
                    if (obj == null || (obj instanceof Bundle)) {
                        return connection.onControlRequestSucceeded(i2, (Bundle) obj);
                    }
                    return false;
                case 4:
                    if (obj == null || (obj instanceof Bundle)) {
                        return connection.onControlRequestFailed(i2, bundle == null ? null : bundle.getString(MediaRouteProviderProtocol.SERVICE_DATA_ERROR), (Bundle) obj);
                    }
                    return false;
                case 5:
                    if (obj == null || (obj instanceof Bundle)) {
                        return connection.onDescriptorChanged((Bundle) obj);
                    }
                    return false;
                case 6:
                    if (obj instanceof Bundle) {
                        connection.onDynamicGroupRouteControllerCreated(i2, (Bundle) obj);
                        return false;
                    }
                    Log.w(RegisteredMediaRouteProvider.TAG, "No further information on the dynamic group controller");
                    return false;
                case 7:
                    if (obj == null || (obj instanceof Bundle)) {
                        return connection.onDynamicRouteDescriptorsChanged(i3, (Bundle) obj);
                    }
                    return false;
                default:
                    return false;
            }
        }
    }
}
