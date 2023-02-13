package androidx.navigation;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
public class NavDeepLinkRequest {
    private final String mAction;
    private final String mMimeType;
    private final Uri mUri;

    /* JADX INFO: Access modifiers changed from: package-private */
    public NavDeepLinkRequest(@NonNull Intent intent) {
        this(intent.getData(), intent.getAction(), intent.getType());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public NavDeepLinkRequest(@Nullable Uri uri, @Nullable String str, @Nullable String str2) {
        this.mUri = uri;
        this.mAction = str;
        this.mMimeType = str2;
    }

    @Nullable
    public Uri getUri() {
        return this.mUri;
    }

    @Nullable
    public String getAction() {
        return this.mAction;
    }

    @Nullable
    public String getMimeType() {
        return this.mMimeType;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NavDeepLinkRequest");
        sb.append("{");
        if (this.mUri != null) {
            sb.append(" uri=");
            sb.append(this.mUri.toString());
        }
        if (this.mAction != null) {
            sb.append(" action=");
            sb.append(this.mAction);
        }
        if (this.mMimeType != null) {
            sb.append(" mimetype=");
            sb.append(this.mMimeType);
        }
        sb.append(" }");
        return sb.toString();
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private String mAction;
        private String mMimeType;
        private Uri mUri;

        private Builder() {
        }

        @NonNull
        public static Builder fromUri(@NonNull Uri uri) {
            Builder builder = new Builder();
            builder.setUri(uri);
            return builder;
        }

        @NonNull
        public static Builder fromAction(@NonNull String str) {
            if (str.isEmpty()) {
                throw new IllegalArgumentException("The NavDeepLinkRequest cannot have an empty action.");
            }
            Builder builder = new Builder();
            builder.setAction(str);
            return builder;
        }

        @NonNull
        public static Builder fromMimeType(@NonNull String str) {
            Builder builder = new Builder();
            builder.setMimeType(str);
            return builder;
        }

        @NonNull
        public Builder setUri(@NonNull Uri uri) {
            this.mUri = uri;
            return this;
        }

        @NonNull
        public Builder setAction(@NonNull String str) {
            if (str.isEmpty()) {
                throw new IllegalArgumentException("The NavDeepLinkRequest cannot have an empty action.");
            }
            this.mAction = str;
            return this;
        }

        @NonNull
        public Builder setMimeType(@NonNull String str) {
            if (!Pattern.compile("^[-\\w*.]+/[-\\w+*.]+$").matcher(str).matches()) {
                throw new IllegalArgumentException("The given mimeType " + str + " does not match to required \"type/subtype\" format");
            }
            this.mMimeType = str;
            return this;
        }

        @NonNull
        public NavDeepLinkRequest build() {
            return new NavDeepLinkRequest(this.mUri, this.mAction, this.mMimeType);
        }
    }
}
