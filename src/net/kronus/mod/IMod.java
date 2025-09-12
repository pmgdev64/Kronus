package net.kronus.mod;

public interface IMod {
    void onEnable();
    void onTick();
    void onRender();
    default void handleEvent(Object event) {}
    default boolean isEnabled() { return true; }
}
