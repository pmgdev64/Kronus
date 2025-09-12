package net.kronus.mod;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private final List<IMod> listeners = new ArrayList<>();

    public void register(IMod mod) {
        listeners.add(mod);
    }

    public void post(Object event) {
        for (IMod mod : listeners) {
            mod.handleEvent(event);
        }
    }
}
