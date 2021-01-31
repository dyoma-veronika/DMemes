package com.example.developerslife.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CacheArrayList<T> {

    List<T> localCache;
    int position;

    public CacheArrayList() {
        localCache = new ArrayList<>();
        position = -1;
    }

    public boolean hasNext() {
        return position + 1 < localCache.size();
    }

    public boolean hasPrevious() {
        return position - 1 >= 0;
    }

    public T next() {
        position++;
        return localCache.get(position);
    }

    public T previous() {
        position--;
        return localCache.get(position);
    }

    public void addAll(Collection<T> collection) {
        localCache.addAll(collection);
    }
}
