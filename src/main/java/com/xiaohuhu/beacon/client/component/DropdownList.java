package com.xiaohuhu.beacon.client.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class DropdownList extends AbstractWidget {
    private final List<Component> options;
    private final Consumer<Component> onSelect;
    private boolean expanded = false;
    private Component selectedOption = null;   // 存储选中的可翻译组件
    @Nullable
    private DropdownList otherDropdown;

    public DropdownList(int x, int y, int width, int height, Component message,
                        List<Component> options, Consumer<Component> onSelect) {
        super(x, y, width, height, message);
        this.options = options;
        this.onSelect = onSelect;
    }

    public void setOtherDropdown(DropdownList other) {
        this.otherDropdown = other;
    }

    public void setSelectedOption(Component option) {
        this.selectedOption = option;
    }

    public Component getSelectedOption() {
        return selectedOption;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }

    /**
     * 判断鼠标是否在组件的可点击区域内（包括展开后的选项）
     */
    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        // 标题栏
        if (mouseX >= getX() && mouseX <= getX() + width && mouseY >= getY() && mouseY <= getY() + height) {
            return true;
        }
        // 展开的选项区域
        if (expanded) {
            int listTop = getY() + height;
            int listBottom = listTop + options.size() * 20;
            if (mouseX >= getX() && mouseX <= getX() + width && mouseY >= listTop && mouseY <= listBottom) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理点击事件（由 Screen 调用）
     */
    public void handleClick(double mouseX, double mouseY) {
        if (expanded) {
            int listTop = getY() + height;
            int clickedIndex = (int) ((mouseY - listTop) / 20);
            if (clickedIndex >= 0 && clickedIndex < options.size()) {
                selectedOption = options.get(clickedIndex);
                onSelect.accept(selectedOption);
                expanded = false;
                if (otherDropdown != null && otherDropdown.isExpanded()) {
                    otherDropdown.setExpanded(false);
                }
                return;
            }
            expanded = false;
        } else {
            if (otherDropdown != null && otherDropdown.isExpanded()) {
                otherDropdown.setExpanded(false);
            }
            expanded = true;
        }
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Font font = Minecraft.getInstance().font;
        boolean hovered = this.isHovered();

        int bgColor = hovered ? 0xFF555555 : 0xFF333333;
        guiGraphics.fill(getX(), getY(), getX() + width, getY() + height, bgColor);
        guiGraphics.fill(getX(), getY(), getX() + width, getY() + 1, 0xFFFFFFFF);
        guiGraphics.fill(getX(), getY() + height - 1, getX() + width, getY() + height, 0xFFFFFFFF);
        guiGraphics.fill(getX(), getY(), getX() + 1, getY() + height, 0xFFFFFFFF);
        guiGraphics.fill(getX() + width - 1, getY(), getX() + width, getY() + height, 0xFFFFFFFF);

        // 标题：优先显示选中的选项，否则显示默认消息
        Component display = (selectedOption != null) ? selectedOption : getMessage();
        guiGraphics.drawString(font, display, getX() + 4, getY() + (height - 8) / 2, 0xFFFFFF, false);
        guiGraphics.drawString(font, "▼", getX() + width - 12, getY() + (height - 8) / 2, 0xFFFFFF, false);

        if (expanded) {
            int listHeight = options.size() * 20;
            int listY = getY() + height;
            guiGraphics.fill(getX(), listY, getX() + width, listY + listHeight, 0xCC000000);
            for (int i = 0; i < options.size(); i++) {
                int itemY = listY + i * 20;
                boolean itemHovered = mouseX >= getX() && mouseX < getX() + width && mouseY >= itemY && mouseY < itemY + 20;
                if (itemHovered) {
                    guiGraphics.fill(getX(), itemY, getX() + width, itemY + 20, 0x44FFFFFF);
                }
                guiGraphics.drawString(font, options.get(i), getX() + 4, itemY + 6, 0xFFFFFF, false);
            }
        }
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {}
}