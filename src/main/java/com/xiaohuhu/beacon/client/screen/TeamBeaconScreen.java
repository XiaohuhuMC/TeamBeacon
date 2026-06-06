package com.xiaohuhu.beacon.client.screen;

import com.xiaohuhu.beacon.common.menu.TeamBeaconMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class TeamBeaconScreen extends AbstractContainerScreen<TeamBeaconMenu> {

    public TeamBeaconScreen(TeamBeaconMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 800;
        this.imageHeight = 500;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.fill(0, 0, this.width, this.height, 0xAA000000);
        int panelX = this.leftPos;
        int panelY = this.topPos;
        guiGraphics.fill(panelX, panelY, panelX + imageWidth, panelY + imageHeight, 0xFFCCCCCC);
        guiGraphics.fill(panelX, panelY, panelX + imageWidth, panelY + 1, 0xFF000000);
        guiGraphics.fill(panelX, panelY + imageHeight - 1, panelX + imageWidth, panelY + imageHeight, 0xFF000000);
        guiGraphics.fill(panelX, panelY, panelX + 1, panelY + imageHeight, 0xFF000000);
        guiGraphics.fill(panelX + imageWidth - 1, panelY, panelX + imageWidth, panelY + imageHeight, 0xFF000000);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        Component text = Component.translatable("gui.team_beacon.custom_text");
        int textWidth = Minecraft.getInstance().font.width(text);
        int textX = this.leftPos + (this.imageWidth - textWidth) / 2;
        int textY = this.topPos + 70;
        guiGraphics.drawString(Minecraft.getInstance().font, text, textX, textY, 0x000000, false);

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

    }

    @Override
    protected void init() {
        super.init();
    }
}