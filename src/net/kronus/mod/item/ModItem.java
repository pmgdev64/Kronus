package net.kronus.mod.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ModItem extends Item {
    private final ResourceLocation icon;

    public ModItem(String name, ResourceLocation icon) {
        super();
        this.setUnlocalizedName(name); // tên dùng trong game
        this.icon = icon;
        // Minecraft tự add vào itemList trong constructor Item
    }

    public ResourceLocation getIcon() { return icon; }
}
