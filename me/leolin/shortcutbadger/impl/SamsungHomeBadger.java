package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.util.CloseHelper;
@Deprecated
/* loaded from: classes2.dex */
public class SamsungHomeBadger implements Badger {
    private static final String[] CONTENT_PROJECTION = {"_id", "class"};
    private static final String CONTENT_URI = "content://com.sec.badge/apps?notify=true";

    @Override // me.leolin.shortcutbadger.Badger
    public void executeBadge(Context context, ComponentName componentName, int i) throws ShortcutBadgeException {
        Uri parse = Uri.parse(CONTENT_URI);
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(parse, CONTENT_PROJECTION, "package=?", new String[]{componentName.getPackageName()}, null);
            if (cursor != null) {
                String className = componentName.getClassName();
                boolean z = false;
                while (cursor.moveToNext()) {
                    contentResolver.update(parse, getContentValues(componentName, i, false), "_id=?", new String[]{String.valueOf(cursor.getInt(0))});
                    if (className.equals(cursor.getString(cursor.getColumnIndex("class")))) {
                        z = true;
                    }
                }
                if (!z) {
                    contentResolver.insert(parse, getContentValues(componentName, i, true));
                }
            }
        } finally {
            CloseHelper.close(cursor);
        }
    }

    private ContentValues getContentValues(ComponentName componentName, int i, boolean z) {
        ContentValues contentValues = new ContentValues();
        if (z) {
            contentValues.put("package", componentName.getPackageName());
            contentValues.put("class", componentName.getClassName());
        }
        contentValues.put("badgecount", Integer.valueOf(i));
        return contentValues;
    }

    @Override // me.leolin.shortcutbadger.Badger
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.sec.android.app.launcher", "com.sec.android.app.twlauncher");
    }
}
