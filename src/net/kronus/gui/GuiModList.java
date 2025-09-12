package net.kronus.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSlot;
import net.kronus.mod.ModInfo;
import net.kronus.mod.ModList;

import java.io.IOException;

public class GuiModList extends GuiScreen {

    private final GuiScreen parent;
    private final ModList modList;
    private ModSlot modSlot;

    private GuiButton btnEnable;
    private GuiButton btnInfo;
    private GuiButton btnBack;

    private int selectedIndex = -1;

    public GuiModList(GuiScreen parent, ModList modList) {
        this.parent = parent;
        this.modList = modList;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        modSlot = new ModSlot(mc, this.width, this.height, 32, this.height - 64, 36);

        btnEnable = new GuiButton(1, this.width / 2 - 154, this.height - 28, 100, 20, "Enable/Disable");
        btnInfo   = new GuiButton(2, this.width / 2 - 50, this.height - 28, 100, 20, "Info");
        btnBack   = new GuiButton(0, this.width / 2 + 54, this.height - 28, 100, 20, "Back");

        this.buttonList.add(btnEnable);
        this.buttonList.add(btnInfo);
        this.buttonList.add(btnBack);

        updateButtonState();
    }

    private void updateButtonState() {
        boolean hasSelection = selectedIndex >= 0 && selectedIndex < modList.size();
        btnEnable.enabled = hasSelection;
        btnInfo.enabled = hasSelection;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(parent);
        } else if (button.id == 1 && selectedIndex >= 0) {
            ModInfo info = modList.get(selectedIndex);
            info.toggle();
        } else if (button.id == 2 && selectedIndex >= 0) {
            ModInfo info = modList.get(selectedIndex);
            mc.displayGuiScreen(new GuiModInfo(this, info));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.modSlot.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, "Mod List", this.width / 2, 12, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    class ModSlot extends GuiSlot {

        public ModSlot(Minecraft mcIn, int width, int height, int top, int bottom, int slotHeight) {
            super(mcIn, width, height, top, bottom, slotHeight);
        }

        @Override
        protected int getSize() {
            return modList.size();
        }

        @Override
        protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
            selectedIndex = index;
            updateButtonState();

            if (doubleClick) {
                ModInfo info = modList.get(index);
                info.toggle();
            }
        }

        @Override
        protected boolean isSelected(int index) {
            return index == selectedIndex;
        }

        @Override
        protected void drawBackground() {
            GuiModList.this.drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int index, int x, int y, int height, int mouseX, int mouseY) {
            ModInfo info = modList.get(index);
            if (info == null) return;

            // Vẽ logo mod bên trái
            if (info.logo != null) {
                mc.getTextureManager().bindTexture(info.logo);
                drawModalRectWithCustomSizedTexture(x, y, 0, 0, 32, 32, 32, 32);
            }

            // Tên mod (trạng thái ON/OFF màu xanh/đỏ)
            int color = info.enabled ? 0x00FF00 : 0xFF5555;
            GuiModList.this.fontRendererObj.drawString(
                    info.name + " v" + info.version, x + 36, y + 4, color
            );

            // Tác giả
            GuiModList.this.fontRendererObj.drawString(
                    "by " + info.author, x + 36, y + 14, 0xAAAAAA
            );

            // Mô tả (một dòng ngắn)
            GuiModList.this.fontRendererObj.drawString(
                    info.description.length() > 40 ? info.description.substring(0, 40) + "..." : info.description,
                    x + 36, y + 24, 0xCCCCCC
            );
        }
    }
}
