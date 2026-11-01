package com.livesports.observer.source;

import com.livesports.observer.Event;
import com.livesports.observer.EventType;
import com.livesports.observer.Observer;
import com.livesports.observer.Subject;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class AbstractSubject<E extends Event>
        implements Subject<E> {
    private final ConcurrentHashMap<EventType, Set<Observer<E>>> observers =
            new ConcurrentHashMap<>();

    @Override
    public void subscribe(EventType type, Observer<E> observer) {
        observers.computeIfAbsent(
                type,
                key -> new CopyOnWriteArraySet<>()
        ).add(observer);
    }

    @Override
    public void unsubscribe(EventType type, Observer<E> observer) {
        Set<Observer<E>> listeners = observers.get(type);
        if (listeners != null) {
            listeners.remove(observer);
        }
    }

    @Override
    public void publish(E event) {
        Set<Observer<E>> listeners = observers.get(event.getType());
        if (listeners != null) {
            for (Observer<E> observer : listeners) {
                observer.onEvent(event);
            }
        }
    }
}
