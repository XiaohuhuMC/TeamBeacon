package com.xiaohuhu.beacon.client.screen;

import com.xiaohuhu.beacon.TeamBeacon;
import com.xiaohuhu.beacon.client.component.DropdownList;
import com.xiaohuhu.beacon.common.menu.TeamBeaconMenu;
import com.xiaohuhu.beacon.common.network.SetBeamColorPayload;
import com.xiaohuhu.beacon.common.network.SetTeamPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.scores.PlayerTeam;

import java.util.ArrayList;
import java.util.List;

public class TeamBeaconScreen extends AbstractContainerScreen<TeamBeaconMenu> {
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(TeamBeacon.MOD_ID, "textures/gui/team_beacon_gui.png");

    private DropdownList colorDropdown;
    private DropdownList teamDropdown;

    public TeamBeaconScreen(TeamBeaconMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        int dropdownWidth = 120;
        int dropdownHeight = 20;
        int startX = this.leftPos + (this.imageWidth - dropdownWidth) / 2;
        int colorY = this.topPos + 80;
        int teamY = this.topPos + 40;

        // 颜色列表
        List<Component> colorOptions = getColorOptions();
        colorDropdown = new DropdownList(startX, colorY, dropdownWidth, dropdownHeight,
                Component.translatable("gui.team_beacon.choose_color"),
                colorOptions,
                selected -> {
                    int color = switch (selected.getString()) {
                        case "color.minecraft.red" -> 0xFF0000;
                        case "color.minecraft.blue" -> 0x0000FF;
                        case "color.minecraft.green" -> 0x00FF00;
                        case "color.minecraft.purple" -> 0x800080;
                        default -> 0xFFFFFF;
                    };
                    sendColor(color);
                });
        this.addRenderableWidget(colorDropdown);

        // 队伍列表
        List<Component> teamOptions = getTeamOptions();
        teamDropdown = new DropdownList(startX, teamY, dropdownWidth, dropdownHeight,
                Component.translatable("gui.team_beacon.choose_team"),
                teamOptions,
                selected -> {
                    String teamName = selected.getString();
                    sendTeam(teamName);
                });
        this.addRenderableWidget(teamDropdown);

        // 互斥（可选）
        teamDropdown.setOtherDropdown(colorDropdown);
        colorDropdown.setOtherDropdown(teamDropdown);

        // 加载已保存的颜色（从方块实体）
        int currentColor = this.menu.getBlockEntity().getBeamColor();
        Component savedColor = switch (currentColor) {
            case 0xFF0000 -> Component.translatable("color.minecraft.red");
            case 0x0000FF -> Component.translatable("color.minecraft.blue");
            case 0x00FF00 -> Component.translatable("color.minecraft.green");
            case 0x800080 -> Component.translatable("color.minecraft.purple");
            default -> null;
        };
        if (savedColor != null) {
            colorDropdown.setSelectedOption(savedColor);
        }

        // 加载已保存的队伍
        String savedTeam = this.menu.getBlockEntity().getSelectedTeam();
        if (savedTeam != null && !savedTeam.isEmpty()) {
            for (Component opt : teamOptions) {
                if (opt.getString().equals(savedTeam)) {
                    teamDropdown.setSelectedOption(opt);
                    break;
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // 收集所有命中的下拉列表
        List<DropdownList> hitList = new ArrayList<>();
        for (GuiEventListener child : this.children()) {
            if (child instanceof DropdownList dropdown && child.isMouseOver(mouseX, mouseY)) {
                hitList.add(dropdown);
            }
        }

        // 如果有重叠（命中数量 > 1）
        if (hitList.size() > 1) {
            // 如果队伍列表在命中列表中，则只处理队伍列表，忽略颜色列表
            if (hitList.contains(teamDropdown)) {
                teamDropdown.handleClick(mouseX, mouseY);
                return true;
            } else {
                // 理论上不会发生，因为如果有重叠且队伍列表不在其中，可能颜色列表自己重叠？但只有两个列表，所以忽略
                return false;
            }
        } else if (hitList.size() == 1) {
            // 只有一个命中，正常处理
            hitList.get(0).handleClick(mouseX, mouseY);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private List<Component> getTeamOptions() {
        List<Component> teams = new ArrayList<>();
        if (this.minecraft != null && this.minecraft.level != null) {
            var scoreboard = this.minecraft.level.getScoreboard();
            for (PlayerTeam team : scoreboard.getPlayerTeams()) {
                Component display = team.getDisplayName();
                if (display == null || display.getString().isEmpty()) {
                    display = Component.literal(team.getName());
                }
                teams.add(display);
            }
        }
        if (teams.isEmpty()) {
            teams.add(Component.translatable("gui.team_beacon.no_teams"));
        }
        return teams;
    }

    private List<Component> getColorOptions() {
        return List.of(
                Component.translatable("color.minecraft.red"),
                Component.translatable("color.minecraft.blue"),
                Component.translatable("color.minecraft.green"),
                Component.translatable("color.minecraft.purple")
        );
    }

    private void sendColor(int color) {
        if (this.minecraft != null && this.minecraft.player != null) {
            var pos = this.menu.getBlockEntity().getBlockPos();
            var packet = new SetBeamColorPayload(pos, color);
            this.minecraft.player.connection.send(packet);
        }
    }

    private void sendTeam(String teamName) {
        if (this.minecraft != null && this.minecraft.player != null) {
            var pos = this.menu.getBlockEntity().getBlockPos();
            var packet = new SetTeamPayload(pos, teamName);
            this.minecraft.player.connection.send(packet);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // 不绘制默认标签
    }
}