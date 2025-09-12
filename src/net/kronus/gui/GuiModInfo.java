package net.kronus.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.kronus.mod.ModInfo;

import java.io.IOException;
import java.util.List;

/**
 * Màn hình hiển thị chi tiết của một ModInfo
 */
public class GuiModInfo extends GuiScreen {
    private final GuiScreen parentScreen;
    private final ModInfo mod;

    private GuiButton buttonBack;

    public GuiModInfo(GuiScreen parentScreen, ModInfo mod) {
        this.parentScreen = parentScreen;
        this.mod = mod;
    }

    @Override
    public void initGui() {
        int centerX = this.width / 2;
        int bottomY = this.height - 28;
        this.buttonList.clear();

        this.buttonBack = new GuiButton(0, centerX - 50, bottomY, 100, 20, I18n.format("gui.back"));
        this.buttonList.add(this.buttonBack);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        int y = 40;
        int centerX = this.width / 2;

        // Tên mod
        String title = "§b" + mod.name;
        this.drawCenteredString(this.fontRendererObj, title, centerX, y, 0xFFFFFF);
        y += 20;

        // Version
        this.drawCenteredString(this.fontRendererObj, "Version: §a" + mod.version, centerX, y, 0xCCCCCC);
        y += 15;

        // Tác giả
        this.drawCenteredString(this.fontRendererObj, "Author: §e" + mod.author, centerX, y, 0xCCCCCC);
        y += 15;

        // Enabled/Disabled
        this.drawCenteredString(this.fontRendererObj,
                "Status: " + (mod.enabled ? "§aEnabled" : "§cDisabled"),
                centerX, y, 0xCCCCCC);
        y += 20;

        // Description (multi-line wrap)
        if (mod.description != null && !mod.description.isEmpty()) {
            List<String> descLines = this.fontRendererObj.listFormattedStringToWidth(mod.description, this.width - 40);
            for (String line : descLines) {
                this.drawCenteredString(this.fontRendererObj, line, centerX, y, 0xAAAAAA);
                y += this.fontRendererObj.FONT_HEIGHT + 2;
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
