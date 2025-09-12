package net.kronus.mod.item;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {
    private static final Map<String, IItem> ITEMS = new HashMap<>();

    public static void register(IItem item) {
        ITEMS.put(item.getName(), item);
        System.out.println("Registered item: " + item.getName());
    }

    public static IItem getItem(String name) { return ITEMS.get(name); }

    public static Map<String, IItem> getAllItems() { return ITEMS; }
}
