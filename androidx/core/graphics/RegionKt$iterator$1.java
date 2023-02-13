package androidx.core.graphics;

import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
/* compiled from: Region.kt */
/* loaded from: classes.dex */
public final class RegionKt$iterator$1 implements Iterator<Rect>, KMappedMarker {
    private boolean hasMore;
    @NotNull
    private final RegionIterator iterator;
    @NotNull
    private final Rect rect;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RegionKt$iterator$1(Region region) {
        RegionIterator regionIterator = new RegionIterator(region);
        this.iterator = regionIterator;
        Rect rect = new Rect();
        this.rect = rect;
        this.hasMore = regionIterator.next(rect);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.hasMore;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    @NotNull
    public Rect next() {
        if (this.hasMore) {
            Rect rect = new Rect(this.rect);
            this.hasMore = this.iterator.next(this.rect);
            return rect;
        }
        throw new IndexOutOfBoundsException();
    }
}
