package bsr.project.checkers.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bsr.project.checkers.logger.Logs;

public class EventDispatcher {
    
    private static EventDispatcher instance = null;
    
    private static EventDispatcher getInstance() {
        if (instance == null) {
            new EventDispatcher();
        }
        return instance;
    }
    
    private Map<Class<? extends IEvent>, List<IEventObserver>> eventObservers;
    
    private List<IEvent> eventsQueue;
    
    private volatile boolean dispatching = false;
    
    private EventDispatcher() {
        eventObservers = new HashMap<>();
        eventsQueue = new ArrayList<>();
        instance = this;
    }
    
    public static void registerEventObserver(Class<? extends IEvent> eventClass, IEventObserver observer) {
        getInstance()._registerEventObserver(eventClass, observer);
    }
    
    public static void unregisterEventObserver(IEventObserver observer) {
        getInstance()._unregisterEventObserver(observer);
    }
    
    public static void sendEvent(IEvent event) {
        getInstance()._sendEvent(event);
    }
    
    private void _registerEventObserver(Class<? extends IEvent> eventClass, IEventObserver observer) {
        List<IEventObserver> observers = eventObservers.get(eventClass);
        if (observers == null) {
            observers = new ArrayList<>();
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
        eventObservers.put(eventClass, observers);
    }
    
    private void _unregisterEventObserver(IEventObserver observer) {
        for (Class<? extends IEvent> clazz : eventObservers.keySet()) {
            List<IEventObserver> observers = eventObservers.get(clazz);
            if (observers != null) {
                observers.removeIf(obs -> obs == observer);
            }
        }
    }
    
    private void _sendEvent(IEvent event) {
        eventsQueue.add(event);
        dispatchEvents();
    }
    
    private void clearEventObservers(Class<? extends IEvent> eventClass) {
        List<IEventObserver> observers = eventObservers.get(eventClass);
        if (observers != null) {
            observers.clear();
        }
        eventObservers.put(eventClass, null);
    }
    
    private void dispatchEvents() {
        if (dispatching) return;
        dispatching = true;
        
        while (!eventsQueue.isEmpty()) {
            dispatch(eventsQueue.get(0));
            eventsQueue.remove(0);
        }
        
        dispatching = false;
    }
    
    private void dispatch(IEvent event) {
        List<IEventObserver> observers = eventObservers.get(event.getClass());
        if (observers == null || observers.isEmpty()) {
            Logs.warn("no observer for event " + event.getClass().getName());
        }
        if (observers != null) {
            for (IEventObserver observer : observers) {
                observer.onEvent(event);
            }
        }
    }
}
