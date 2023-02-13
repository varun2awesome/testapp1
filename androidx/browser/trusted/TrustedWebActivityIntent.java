package androidx.browser.trusted;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import java.util.List;
/* loaded from: classes.dex */
public final class TrustedWebActivityIntent {
    @NonNull
    private final Intent mIntent;
    @NonNull
    private final List<Uri> mSharedFileUris;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TrustedWebActivityIntent(@NonNull Intent intent, @NonNull List<Uri> sharedFileUris) {
        this.mIntent = intent;
        this.mSharedFileUris = sharedFileUris;
    }

    public void launchTrustedWebActivity(@NonNull Context context) {
        grantUriPermissionToProvider(context);
        ContextCompat.startActivity(context, this.mIntent, null);
    }

    private void grantUriPermissionToProvider(Context context) {
        for (Uri uri : this.mSharedFileUris) {
            context.grantUriPermission(this.mIntent.getPackage(), uri, 1);
        }
    }

    @NonNull
    public Intent getIntent() {
        return this.mIntent;
    }
}
