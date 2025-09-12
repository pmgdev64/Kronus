package net.kronus.mod;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

public class ModPackReader {

    public static class Manifest {
        public String name;
        public String version;
        public String author;
        public String description;
        public List<String> mods;
    }

    private final Gson gson = new Gson();

    public Manifest readManifest(File modpackDir) {
        File manifestFile = new File(modpackDir, "manifest.json");
        if (!manifestFile.exists()) {
            System.out.println("Manifest not found in " + modpackDir.getPath());
            return null;
        }

        try (FileReader reader = new FileReader(manifestFile)) {
            Manifest manifest = gson.fromJson(reader, Manifest.class);
            return manifest;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
