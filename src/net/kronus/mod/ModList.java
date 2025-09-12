package net.kronus.mod;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModList {
    public final List<ModInfo> mods = new ArrayList<>();
    
    private static final Logger logger = LogManager.getLogger();

    public void add(ModInfo mod) {
        mods.add(mod);
    }

    public void tickAll() {
        for (ModInfo mod : mods) mod.onTick();
    }

    public void renderAll() {
        for (ModInfo mod : mods) mod.onRender();
    }

    public void printAll() {
        logger.info("[Kronus Next-Generation] Displaying Mod Name");
        for (ModInfo mod : mods) {
        	logger.info("[Kronus Next-Generation] Mod Name: " + mod.name + " v" + mod.version + " by " + mod.author + " [" + (mod.enabled ? "Enabled" : "Disabled") + "]");
        }
    }

    public int size() {
        return mods.size();
    }

    public ModInfo get(int index) {
        if (index < 0 || index >= mods.size()) return null;
        return mods.get(index);
    }
}
