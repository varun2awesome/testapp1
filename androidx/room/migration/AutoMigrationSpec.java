package androidx.room.migration;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteDatabase;
/* loaded from: classes.dex */
public interface AutoMigrationSpec {

    /* renamed from: androidx.room.migration.AutoMigrationSpec$-CC  reason: invalid class name */
    /* loaded from: classes.dex */
    public final /* synthetic */ class CC {
        public static void $default$onPostMigrate(AutoMigrationSpec autoMigrationSpec, @NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        }
    }

    void onPostMigrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase);
}
