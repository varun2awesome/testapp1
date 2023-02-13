package androidx.navigation;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Navigator.Name("activity")
/* loaded from: classes.dex */
public class ActivityNavigator extends Navigator<Destination> {
    private static final String EXTRA_NAV_CURRENT = "android-support-navigation:ActivityNavigator:current";
    private static final String EXTRA_NAV_SOURCE = "android-support-navigation:ActivityNavigator:source";
    private static final String EXTRA_POP_ENTER_ANIM = "android-support-navigation:ActivityNavigator:popEnterAnim";
    private static final String EXTRA_POP_EXIT_ANIM = "android-support-navigation:ActivityNavigator:popExitAnim";
    private static final String LOG_TAG = "ActivityNavigator";
    private Context mContext;
    private Activity mHostActivity;

    public ActivityNavigator(@NonNull Context context) {
        this.mContext = context;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                this.mHostActivity = (Activity) context;
                return;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
    }

    public static void applyPopAnimationsToPendingTransition(@NonNull Activity activity) {
        Intent intent = activity.getIntent();
        if (intent == null) {
            return;
        }
        int intExtra = intent.getIntExtra(EXTRA_POP_ENTER_ANIM, -1);
        int intExtra2 = intent.getIntExtra(EXTRA_POP_EXIT_ANIM, -1);
        if (intExtra == -1 && intExtra2 == -1) {
            return;
        }
        if (intExtra == -1) {
            intExtra = 0;
        }
        if (intExtra2 == -1) {
            intExtra2 = 0;
        }
        activity.overridePendingTransition(intExtra, intExtra2);
    }

    @NonNull
    final Context getContext() {
        return this.mContext;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.navigation.Navigator
    @NonNull
    public Destination createDestination() {
        return new Destination(this);
    }

    @Override // androidx.navigation.Navigator
    public boolean popBackStack() {
        Activity activity = this.mHostActivity;
        if (activity != null) {
            activity.finish();
            return true;
        }
        return false;
    }

    @Override // androidx.navigation.Navigator
    @Nullable
    public NavDestination navigate(@NonNull Destination destination, @Nullable Bundle bundle, @Nullable NavOptions navOptions, @Nullable Navigator.Extras extras) {
        Intent intent;
        int intExtra;
        if (destination.getIntent() == null) {
            throw new IllegalStateException("Destination " + destination.getId() + " does not have an Intent set.");
        }
        Intent intent2 = new Intent(destination.getIntent());
        if (bundle != null) {
            intent2.putExtras(bundle);
            String dataPattern = destination.getDataPattern();
            if (!TextUtils.isEmpty(dataPattern)) {
                StringBuffer stringBuffer = new StringBuffer();
                Matcher matcher = Pattern.compile("\\{(.+?)\\}").matcher(dataPattern);
                while (matcher.find()) {
                    String group = matcher.group(1);
                    if (bundle.containsKey(group)) {
                        matcher.appendReplacement(stringBuffer, "");
                        stringBuffer.append(Uri.encode(bundle.get(group).toString()));
                    } else {
                        throw new IllegalArgumentException("Could not find " + group + " in " + bundle + " to fill data pattern " + dataPattern);
                    }
                }
                matcher.appendTail(stringBuffer);
                intent2.setData(Uri.parse(stringBuffer.toString()));
            }
        }
        boolean z = extras instanceof Extras;
        if (z) {
            intent2.addFlags(((Extras) extras).getFlags());
        }
        if (!(this.mContext instanceof Activity)) {
            intent2.addFlags(268435456);
        }
        if (navOptions != null && navOptions.shouldLaunchSingleTop()) {
            intent2.addFlags(536870912);
        }
        Activity activity = this.mHostActivity;
        if (activity != null && (intent = activity.getIntent()) != null && (intExtra = intent.getIntExtra(EXTRA_NAV_CURRENT, 0)) != 0) {
            intent2.putExtra(EXTRA_NAV_SOURCE, intExtra);
        }
        intent2.putExtra(EXTRA_NAV_CURRENT, destination.getId());
        Resources resources = getContext().getResources();
        if (navOptions != null) {
            int popEnterAnim = navOptions.getPopEnterAnim();
            int popExitAnim = navOptions.getPopExitAnim();
            if ((popEnterAnim != -1 && resources.getResourceTypeName(popEnterAnim).equals("animator")) || (popExitAnim != -1 && resources.getResourceTypeName(popExitAnim).equals("animator"))) {
                Log.w(LOG_TAG, "Activity destinations do not support Animator resource. Ignoring popEnter resource " + resources.getResourceName(popEnterAnim) + " and popExit resource " + resources.getResourceName(popExitAnim) + "when launching " + destination);
            } else {
                intent2.putExtra(EXTRA_POP_ENTER_ANIM, popEnterAnim);
                intent2.putExtra(EXTRA_POP_EXIT_ANIM, popExitAnim);
            }
        }
        if (z) {
            ActivityOptionsCompat activityOptions = ((Extras) extras).getActivityOptions();
            if (activityOptions != null) {
                ContextCompat.startActivity(this.mContext, intent2, activityOptions.toBundle());
            } else {
                this.mContext.startActivity(intent2);
            }
        } else {
            this.mContext.startActivity(intent2);
        }
        if (navOptions == null || this.mHostActivity == null) {
            return null;
        }
        int enterAnim = navOptions.getEnterAnim();
        int exitAnim = navOptions.getExitAnim();
        if (enterAnim == -1 && exitAnim == -1) {
            return null;
        }
        if (resources.getResourceTypeName(enterAnim).equals("animator") || resources.getResourceTypeName(exitAnim).equals("animator")) {
            Log.w(LOG_TAG, "Activity destinations do not support Animator resource. Ignoring enter resource " + resources.getResourceName(enterAnim) + " and exit resource " + resources.getResourceName(exitAnim) + "when launching " + destination);
            return null;
        }
        if (enterAnim == -1) {
            enterAnim = 0;
        }
        this.mHostActivity.overridePendingTransition(enterAnim, exitAnim != -1 ? exitAnim : 0);
        return null;
    }

    @NavDestination.ClassType(Activity.class)
    /* loaded from: classes.dex */
    public static class Destination extends NavDestination {
        private String mDataPattern;
        private Intent mIntent;

        @Override // androidx.navigation.NavDestination
        boolean supportsActions() {
            return false;
        }

        public Destination(@NonNull NavigatorProvider navigatorProvider) {
            this(navigatorProvider.getNavigator(ActivityNavigator.class));
        }

        public Destination(@NonNull Navigator<? extends Destination> navigator) {
            super(navigator);
        }

        @Override // androidx.navigation.NavDestination
        @CallSuper
        public void onInflate(@NonNull Context context, @NonNull AttributeSet attributeSet) {
            super.onInflate(context, attributeSet);
            TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.ActivityNavigator);
            String string = obtainAttributes.getString(R.styleable.ActivityNavigator_targetPackage);
            if (string != null) {
                string = string.replace(NavInflater.APPLICATION_ID_PLACEHOLDER, context.getPackageName());
            }
            setTargetPackage(string);
            String string2 = obtainAttributes.getString(R.styleable.ActivityNavigator_android_name);
            if (string2 != null) {
                if (string2.charAt(0) == '.') {
                    string2 = context.getPackageName() + string2;
                }
                setComponentName(new ComponentName(context, string2));
            }
            setAction(obtainAttributes.getString(R.styleable.ActivityNavigator_action));
            String string3 = obtainAttributes.getString(R.styleable.ActivityNavigator_data);
            if (string3 != null) {
                setData(Uri.parse(string3));
            }
            setDataPattern(obtainAttributes.getString(R.styleable.ActivityNavigator_dataPattern));
            obtainAttributes.recycle();
        }

        @NonNull
        public final Destination setIntent(@Nullable Intent intent) {
            this.mIntent = intent;
            return this;
        }

        @Nullable
        public final Intent getIntent() {
            return this.mIntent;
        }

        @NonNull
        public final Destination setTargetPackage(@Nullable String str) {
            if (this.mIntent == null) {
                this.mIntent = new Intent();
            }
            this.mIntent.setPackage(str);
            return this;
        }

        @Nullable
        public final String getTargetPackage() {
            Intent intent = this.mIntent;
            if (intent == null) {
                return null;
            }
            return intent.getPackage();
        }

        @NonNull
        public final Destination setComponentName(@Nullable ComponentName componentName) {
            if (this.mIntent == null) {
                this.mIntent = new Intent();
            }
            this.mIntent.setComponent(componentName);
            return this;
        }

        @Nullable
        public final ComponentName getComponent() {
            Intent intent = this.mIntent;
            if (intent == null) {
                return null;
            }
            return intent.getComponent();
        }

        @NonNull
        public final Destination setAction(@Nullable String str) {
            if (this.mIntent == null) {
                this.mIntent = new Intent();
            }
            this.mIntent.setAction(str);
            return this;
        }

        @Nullable
        public final String getAction() {
            Intent intent = this.mIntent;
            if (intent == null) {
                return null;
            }
            return intent.getAction();
        }

        @NonNull
        public final Destination setData(@Nullable Uri uri) {
            if (this.mIntent == null) {
                this.mIntent = new Intent();
            }
            this.mIntent.setData(uri);
            return this;
        }

        @Nullable
        public final Uri getData() {
            Intent intent = this.mIntent;
            if (intent == null) {
                return null;
            }
            return intent.getData();
        }

        @NonNull
        public final Destination setDataPattern(@Nullable String str) {
            this.mDataPattern = str;
            return this;
        }

        @Nullable
        public final String getDataPattern() {
            return this.mDataPattern;
        }

        @Override // androidx.navigation.NavDestination
        @NonNull
        public String toString() {
            ComponentName component = getComponent();
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            if (component != null) {
                sb.append(" class=");
                sb.append(component.getClassName());
            } else {
                String action = getAction();
                if (action != null) {
                    sb.append(" action=");
                    sb.append(action);
                }
            }
            return sb.toString();
        }
    }

    /* loaded from: classes.dex */
    public static final class Extras implements Navigator.Extras {
        private final ActivityOptionsCompat mActivityOptions;
        private final int mFlags;

        Extras(int i, @Nullable ActivityOptionsCompat activityOptionsCompat) {
            this.mFlags = i;
            this.mActivityOptions = activityOptionsCompat;
        }

        public int getFlags() {
            return this.mFlags;
        }

        @Nullable
        public ActivityOptionsCompat getActivityOptions() {
            return this.mActivityOptions;
        }

        /* loaded from: classes.dex */
        public static final class Builder {
            private ActivityOptionsCompat mActivityOptions;
            private int mFlags;

            @NonNull
            public Builder addFlags(int i) {
                this.mFlags = i | this.mFlags;
                return this;
            }

            @NonNull
            public Builder setActivityOptions(@NonNull ActivityOptionsCompat activityOptionsCompat) {
                this.mActivityOptions = activityOptionsCompat;
                return this;
            }

            @NonNull
            public Extras build() {
                return new Extras(this.mFlags, this.mActivityOptions);
            }
        }
    }
}
