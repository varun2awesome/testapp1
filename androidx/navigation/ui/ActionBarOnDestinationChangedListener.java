package androidx.navigation.ui;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
/* JADX INFO: Access modifiers changed from: package-private */
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ActionBarOnDestinationChangedListener extends AbstractAppBarOnDestinationChangedListener {
    private final AppCompatActivity mActivity;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ActionBarOnDestinationChangedListener(@NonNull AppCompatActivity appCompatActivity, @NonNull AppBarConfiguration appBarConfiguration) {
        super(appCompatActivity.getDrawerToggleDelegate().getActionBarThemedContext(), appBarConfiguration);
        this.mActivity = appCompatActivity;
    }

    @Override // androidx.navigation.ui.AbstractAppBarOnDestinationChangedListener
    protected void setTitle(CharSequence charSequence) {
        this.mActivity.getSupportActionBar().setTitle(charSequence);
    }

    @Override // androidx.navigation.ui.AbstractAppBarOnDestinationChangedListener
    protected void setNavigationIcon(Drawable drawable, @StringRes int i) {
        ActionBar supportActionBar = this.mActivity.getSupportActionBar();
        if (drawable == null) {
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            return;
        }
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.mActivity.getDrawerToggleDelegate().setActionBarUpIndicator(drawable, i);
    }
}
