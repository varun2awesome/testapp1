package me.leolin.shortcutbadger.util;

import android.database.Cursor;
/* loaded from: classes2.dex */
public class CloseHelper {
    public static void close(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        cursor.close();
    }
}
