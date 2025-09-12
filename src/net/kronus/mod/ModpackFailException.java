package net.kronus.mod;

public class ModpackFailException extends RuntimeException {
    private final String modId;

    public ModpackFailException(String modId, String message, Throwable cause) {
        super(message, cause);
        this.modId = modId;
    }

    public String getModId() {
        return modId;
    }
}
