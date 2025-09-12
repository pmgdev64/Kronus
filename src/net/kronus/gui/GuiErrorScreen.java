package net.kronus.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;

import java.util.List;

public class GuiErrorScreen extends GuiScreen {
    private final List<String> failedMods;
    private final GuiScreen parentScreen;
    private ModpackErrorList errorList;

    public GuiErrorScreen(GuiScreen parent, List<String> failedMods) {
        this.parentScreen = parent;
        this.failedMods = failedMods;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        this.errorList = new ModpackErrorList(this.mc, this.width, this.height, 32, this.height - 64, 36);

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 52, "Back to Main Menu"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 28, "Quit Game"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen(parentScreen);
        } else if (button.id == 1) {
            this.mc.shutdown();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.errorList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, "Modpack Load Failed", this.width / 2, 15, 0xFF5555);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    // ===============================
    // Inner class ModpackErrorList
    // ===============================
    class ModpackErrorList extends GuiSlot {
        public ModpackErrorList(Minecraft mc, int width, int height, int top, int bottom, int slotHeight) {
            super(mc, width, height, top, bottom, slotHeight);
        }

        @Override
        protected int getSize() {
            return failedMods.size();
        }

        @Override
        protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {}

        @Override
        protected boolean isSelected(int index) {
            return false;
        }

        @Override
        protected void drawBackground() {}

        @Override
        protected void drawSlot(int entryID, int x, int y, int slotHeight, int mouseX, int mouseY) {
            if (entryID < 0 || entryID >= failedMods.size()) {
                return;
            }
            String errorText = failedMods.get(entryID);
            mc.fontRendererObj.drawString(errorText, x + 2, y + 2, 0xFFFFFF);
        }
    }
}
