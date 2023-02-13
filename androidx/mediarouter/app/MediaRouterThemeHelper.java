package androidx.mediarouter.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ProgressBar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.mediarouter.R;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class MediaRouterThemeHelper {
    static final int COLOR_DARK_ON_LIGHT_BACKGROUND = -570425344;
    private static final int COLOR_DARK_ON_LIGHT_BACKGROUND_RES_ID = R.color.mr_dynamic_dialog_icon_light;
    static final int COLOR_WHITE_ON_DARK_BACKGROUND = -1;
    private static final float MIN_CONTRAST = 3.0f;

    private MediaRouterThemeHelper() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Drawable getMuteButtonDrawableIcon(Context context) {
        return getIconByDrawableId(context, R.drawable.mr_cast_mute_button);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Drawable getCheckBoxDrawableIcon(Context context) {
        return getIconByDrawableId(context, R.drawable.mr_cast_checkbox);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Drawable getDefaultDrawableIcon(Context context) {
        return getIconByAttrId(context, R.attr.mediaRouteDefaultIconDrawable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Drawable getTvDrawableIcon(Context context) {
        return getIconByAttrId(context, R.attr.mediaRouteTvIconDrawable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Drawable getSpeakerDrawableIcon(Context context) {
        return getIconByAttrId(context, R.attr.mediaRouteSpeakerIconDrawable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Drawable getSpeakerGroupDrawableIcon(Context context) {
        return getIconByAttrId(context, R.attr.mediaRouteSpeakerGroupIconDrawable);
    }

    private static Drawable getIconByDrawableId(Context context, int i) {
        Drawable wrap = DrawableCompat.wrap(ContextCompat.getDrawable(context, i));
        if (isLightTheme(context)) {
            DrawableCompat.setTint(wrap, ContextCompat.getColor(context, COLOR_DARK_ON_LIGHT_BACKGROUND_RES_ID));
        }
        return wrap;
    }

    private static Drawable getIconByAttrId(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        Drawable wrap = DrawableCompat.wrap(obtainStyledAttributes.getDrawable(0));
        if (isLightTheme(context)) {
            DrawableCompat.setTint(wrap, ContextCompat.getColor(context, COLOR_DARK_ON_LIGHT_BACKGROUND_RES_ID));
        }
        obtainStyledAttributes.recycle();
        return wrap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Context createThemedButtonContext(Context context) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, getRouterThemeId(context));
        int themeResource = getThemeResource(contextThemeWrapper, R.attr.mediaRouteTheme);
        return themeResource != 0 ? new ContextThemeWrapper(contextThemeWrapper, themeResource) : contextThemeWrapper;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Context createThemedDialogContext(Context context, int i, boolean z) {
        if (i == 0) {
            i = getThemeResource(context, !z ? androidx.appcompat.R.attr.dialogTheme : androidx.appcompat.R.attr.alertDialogTheme);
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i);
        return getThemeResource(contextThemeWrapper, R.attr.mediaRouteTheme) != 0 ? new ContextThemeWrapper(contextThemeWrapper, getRouterThemeId(contextThemeWrapper)) : contextThemeWrapper;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int createThemedDialogStyle(Context context) {
        int themeResource = getThemeResource(context, R.attr.mediaRouteTheme);
        return themeResource == 0 ? getRouterThemeId(context) : themeResource;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getThemeResource(Context context, int i) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(i, typedValue, true)) {
            return typedValue.resourceId;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static float getDisabledAlpha(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(16842803, typedValue, true)) {
            return typedValue.getFloat();
        }
        return 0.5f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getControllerColor(Context context, int i) {
        if (ColorUtils.calculateContrast(-1, getThemeColor(context, i, androidx.appcompat.R.attr.colorPrimary)) >= 3.0d) {
            return -1;
        }
        return COLOR_DARK_ON_LIGHT_BACKGROUND;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getButtonTextColor(Context context) {
        int themeColor = getThemeColor(context, 0, androidx.appcompat.R.attr.colorPrimary);
        return ColorUtils.calculateContrast(themeColor, getThemeColor(context, 0, 16842801)) < 3.0d ? getThemeColor(context, 0, androidx.appcompat.R.attr.colorAccent) : themeColor;
    }

    static TypedArray getStyledAttributes(Context context) {
        return context.obtainStyledAttributes(new int[]{R.attr.mediaRouteDefaultIconDrawable, R.attr.mediaRouteTvIconDrawable, R.attr.mediaRouteSpeakerIconDrawable, R.attr.mediaRouteSpeakerGroupIconDrawable});
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setDialogBackgroundColor(Context context, Dialog dialog) {
        dialog.getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(context, isLightTheme(context) ? R.color.mr_dynamic_dialog_background_light : R.color.mr_dynamic_dialog_background_dark));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setMediaControlsBackgroundColor(Context context, View view, View view2, boolean z) {
        int themeColor = getThemeColor(context, 0, androidx.appcompat.R.attr.colorPrimary);
        int themeColor2 = getThemeColor(context, 0, androidx.appcompat.R.attr.colorPrimaryDark);
        if (z && getControllerColor(context, 0) == COLOR_DARK_ON_LIGHT_BACKGROUND) {
            themeColor2 = themeColor;
            themeColor = -1;
        }
        view.setBackgroundColor(themeColor);
        view2.setBackgroundColor(themeColor2);
        view.setTag(Integer.valueOf(themeColor));
        view2.setTag(Integer.valueOf(themeColor2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setVolumeSliderColor(Context context, MediaRouteVolumeSlider mediaRouteVolumeSlider, View view) {
        int controllerColor = getControllerColor(context, 0);
        if (Color.alpha(controllerColor) != 255) {
            controllerColor = ColorUtils.compositeColors(controllerColor, ((Integer) view.getTag()).intValue());
        }
        mediaRouteVolumeSlider.setColor(controllerColor);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setVolumeSliderColor(Context context, MediaRouteVolumeSlider mediaRouteVolumeSlider) {
        int color;
        int color2;
        if (isLightTheme(context)) {
            color = ContextCompat.getColor(context, R.color.mr_cast_progressbar_progress_and_thumb_light);
            color2 = ContextCompat.getColor(context, R.color.mr_cast_progressbar_background_light);
        } else {
            color = ContextCompat.getColor(context, R.color.mr_cast_progressbar_progress_and_thumb_dark);
            color2 = ContextCompat.getColor(context, R.color.mr_cast_progressbar_background_dark);
        }
        mediaRouteVolumeSlider.setColor(color, color2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setIndeterminateProgressBarColor(Context context, ProgressBar progressBar) {
        if (progressBar.isIndeterminate()) {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, isLightTheme(context) ? R.color.mr_cast_progressbar_progress_and_thumb_light : R.color.mr_cast_progressbar_progress_and_thumb_dark), PorterDuff.Mode.SRC_IN);
        }
    }

    private static boolean isLightTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        return context.getTheme().resolveAttribute(androidx.appcompat.R.attr.isLightTheme, typedValue, true) && typedValue.data != 0;
    }

    private static int getThemeColor(Context context, int i, int i2) {
        if (i != 0) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(i, new int[]{i2});
            int color = obtainStyledAttributes.getColor(0, 0);
            obtainStyledAttributes.recycle();
            if (color != 0) {
                return color;
            }
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i2, typedValue, true);
        if (typedValue.resourceId != 0) {
            return context.getResources().getColor(typedValue.resourceId);
        }
        return typedValue.data;
    }

    private static int getRouterThemeId(Context context) {
        if (isLightTheme(context)) {
            if (getControllerColor(context, 0) == COLOR_DARK_ON_LIGHT_BACKGROUND) {
                return R.style.Theme_MediaRouter_Light;
            }
            return R.style.Theme_MediaRouter_Light_DarkControlPanel;
        } else if (getControllerColor(context, 0) == COLOR_DARK_ON_LIGHT_BACKGROUND) {
            return R.style.Theme_MediaRouter_LightControlPanel;
        } else {
            return R.style.Theme_MediaRouter;
        }
    }
}
