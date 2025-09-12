package net.kronus.mod;

import net.minecraft.util.ResourceLocation;
import com.google.gson.Gson;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModLoader {

    public final ModList modList = new ModList();
    private static final List<String> failedMods = new ArrayList<>();

    public void loadMods() {
        addDefaultMods();

        String modsPath = System.getProperty("user.home") + "/Appdata/Roaming/.minecraft/mods/";
        File modsFolder = new File(modsPath);

        if (!modsFolder.exists() || modsFolder.listFiles() == null) {
            System.out.println("No mods folder found at: " + modsPath);
            return;
        }

        File[] jars = modsFolder.listFiles((dir, name) -> name.endsWith(".jar"));
        if (jars == null || jars.length == 0) {
            System.out.println("No jar mods found in: " + modsPath);
            return;
        }

        Gson gson = new Gson();

        for (File jar : jars) {
            System.out.println("Checking jar: " + jar.getName());
            try (JarFile jarFile = new JarFile(jar)) {
                JarEntry manifestEntry = jarFile.getJarEntry("kronus_mod.json");
                if (manifestEntry == null) {
                    System.out.println("No kronus_mod.json found, skipping: " + jar.getName());
                    continue;
                }

                KronusManifest manifest;
                try (InputStreamReader reader = new InputStreamReader(jarFile.getInputStream(manifestEntry))) {
                    manifest = gson.fromJson(reader, KronusManifest.class);
                }

                URLClassLoader loader = new URLClassLoader(
                        new URL[]{jar.toURI().toURL()},
                        this.getClass().getClassLoader()
                );

                String mainClassName = manifest.mainClass != null ? manifest.mainClass : "ModMain";
                Class<?> clazz = loader.loadClass(mainClassName);

                Object modInstance = clazz.getDeclaredConstructor().newInstance();
                if (!(modInstance instanceof IMod)) {
                    throw new RuntimeException("Main class does not implement IMod");
                }

                IMod mod = (IMod) modInstance;
                mod.onEnable();

                ResourceLocation logo = manifest.logo != null ?
                        new ResourceLocation(manifest.logo) :
                        new ResourceLocation("KClient/ModLogo/default.png");

                ModInfo info = new ModInfo(
                        manifest.name,
                        manifest.version,
                        manifest.author,
                        manifest.description,
                        mod,
                        logo
                );

                modList.add(info);
                System.out.println("Loaded mod: " + manifest.name + " v" + manifest.version);

            } catch (Exception e) {
                System.out.println("Failed to load mod: " + jar.getName());
                e.printStackTrace();
                failedMods.add(jar.getName() + ": " + e.toString());

                // Ném exception để Minecraft show GUI lỗi
                //throw new ModpackFailException(jar.getName(), "Cannot load mod", e);
            }
        }

        System.out.println("=== Mod loading complete ===");
        modList.printAll();
    }

    private void addDefaultMods() {
        ResourceLocation optifineLogo = new ResourceLocation("Kronus/Icons/optifine.jpg");
        modList.add(new ModInfo(
                "OptiFine 1.8.9 (KClient)",
                "1.8.9",
                "OptiFine, PmgTeam",
                "OptiFine integrated for 1.8.9",
                new IMod() {
                    @Override public void onEnable() { System.out.println("OptiFine loaded."); }
                    @Override public void onTick() {}
                    @Override public void onRender() {}
                },
                optifineLogo
        ));

        ResourceLocation kronusLogo = new ResourceLocation("KClient/ModLogo/kronus.png");
        modList.add(new ModInfo(
                "Kronus Next-Generation",
                "1.0.0",
                "PmgTeam",
                "Core mod for KClient features",
                new IMod() {
                    @Override public void onEnable() { System.out.println("Kronus NG loaded."); }
                    @Override public void onTick() {}
                    @Override public void onRender() {}
                },
                kronusLogo
        ));
    }

    public void tickAll() { modList.tickAll(); }
    public void renderAll() { modList.renderAll(); }

    public static boolean hasFailedMods() {
        return !failedMods.isEmpty();
    }

    public static List<String> getFailedMods() {
        return failedMods;
    }

    private static class KronusManifest {
        public String name;
        public String version;
        public String author;
        public String description;
        public String mainClass;
        public String logo;
    }
}
