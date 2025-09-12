package net.kronus.mod;

import net.minecraft.util.ResourceLocation;

public class ModInfo {
    public String name;
    public String version;
    public String author;
    public String description;
    public boolean enabled;
    public IMod instance;
    public ResourceLocation logo; // logo của mod

    public ModInfo(String name, String version, String author, String description, IMod instance, ResourceLocation logo) {
        this.name = name;
        this.version = version;
        this.author = author;
        this.description = description;
        this.instance = instance;
        this.enabled = true;
        this.logo = logo;
    }

    public void toggle() {
        enabled = !enabled;
        System.out.println(name + " is now " + (enabled ? "enabled" : "disabled"));
    }

    public void onTick() {
        if (enabled && instance != null) instance.onTick();
    }

    public void onRender() {
        if (enabled && instance != null) instance.onRender();
    }
}
