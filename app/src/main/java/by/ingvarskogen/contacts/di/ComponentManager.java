package by.ingvarskogen.contacts.di;

import android.os.Bundle;

import net.tribe7.common.cache.Cache;
import net.tribe7.common.cache.CacheBuilder;

import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import by.ingvarskogen.contacts.di.component.FragmentComponent;

/**
 * Helper for saving fragment components on recreating fragments.
 * ComponentManager use Guava Cache for saving components on destroy and restoring on create.
 */
public class ComponentManager {
    private static final String KEY_COMPONENT_ID = "KEY_COMPONENT_ID";

    private final AtomicLong currentId;

    private final Cache<Long, FragmentComponent> fragmentComponentCache;

    @Inject public ComponentManager() {
        currentId = new AtomicLong();
        fragmentComponentCache = CacheBuilder.newBuilder()
                .build();
    }

    /**
     * Restore saved component after fragment recreating
     *
     * @param savedInstanceState fragment's state bundle, used for saving component id
     * @param <C>                component class
     * @return restored component
     */
    @SuppressWarnings("unchecked")
    public <C extends FragmentComponent> C restoreComponent(Bundle savedInstanceState) {
        Long componentId = savedInstanceState.getLong(KEY_COMPONENT_ID);
        C component = (C) fragmentComponentCache.getIfPresent(componentId);
        fragmentComponentCache.invalidate(componentId);
        return component;
    }

    /**
     * Save component to cache
     *
     * @param component fragment component to save
     * @param outState  fragment's state bundle, used for saving component id
     */
    public void saveComponent(FragmentComponent component, Bundle outState) {
        long componentId = currentId.incrementAndGet();
        fragmentComponentCache.put(componentId, component);
        outState.putLong(KEY_COMPONENT_ID, componentId);
    }
}