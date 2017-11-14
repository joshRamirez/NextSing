package common;

import model.Singer;

import java.util.HashMap;
import java.util.Map;

public class SingerCache {
    boolean isCached = false;
    private Map singerCache = new HashMap<Integer, Singer>();

    public SingerCache() {
    }

    public Map<Integer, Singer> getSingerCache() {
        return singerCache;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setCached(boolean cached) {
        isCached = cached;
    }
}
