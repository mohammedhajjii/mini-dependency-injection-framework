package ma.enset.repo;

import ma.enset.Main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Context {
    INSTANCE;
    private final Map<String, Object> context = new ConcurrentHashMap<>();

    public Map<String, Object> getContext() {
        return context;
    }
}
