package com.example.developerslife;

import com.example.developerslife.network.CacheArrayList;
import com.example.developerslife.network.Post;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class CacheArrayListTest {

    @Test
    public void testEmptyCacheArrayListHasNoNextItem() {
        CacheArrayList<Post> cacheArrayList = new CacheArrayList<>();
        assertEquals(cacheArrayList.hasNext(), false);
    }

    @Test
    public void testEmptyCacheArrayListHasNoPrevItem() {
        CacheArrayList<Post> cacheArrayList = new CacheArrayList<>();
        assertEquals(cacheArrayList.hasPrevious(), false);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testEmptyCacheArrayNextCallThrowIOBExp() {
        CacheArrayList<Post> cacheArrayList = new CacheArrayList<>();
        cacheArrayList.next();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testEmptyCacheArrayPrevCallThrowIOBExp() {
        CacheArrayList<Post> cacheArrayList = new CacheArrayList<>();
        cacheArrayList.previous();
    }

    @Test
    public void testCacheArrayListReturnCorrectSingleItem() {
        CacheArrayList<Integer> cacheArrayList = new CacheArrayList<>();
        cacheArrayList.addAll(Collections.singleton(1));
        assertEquals(cacheArrayList.next(), (Integer) 1);
    }

    @Test
    public void testCacheArrayListHasNaturalOrder() {
        CacheArrayList<Integer> cacheArrayList = new CacheArrayList<>();
        cacheArrayList.addAll(Arrays.asList(1, 2));
        cacheArrayList.addAll(Collections.singletonList(3));
        assertArrayEquals(
                new int[]{cacheArrayList.next(), cacheArrayList.next(), cacheArrayList.next()},
                new int[]{1, 2, 3}
        );
    }
}
