package net.kronus.mod.item;

public interface IItem {
    String getName();
    String getDescription();
    int getMaxStackSize();
    void onUse(); // gọi khi player dùng item
}
