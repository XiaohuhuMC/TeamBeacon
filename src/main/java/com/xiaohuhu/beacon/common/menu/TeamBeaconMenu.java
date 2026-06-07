package com.xiaohuhu.beacon.common.menu;

import com.xiaohuhu.beacon.common.RegistryObjects.ModBlocks;
import com.xiaohuhu.beacon.common.RegistryObjects.ModMenuTypes;
import com.xiaohuhu.beacon.common.block.entity.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;


public class TeamBeaconMenu extends AbstractContainerMenu {
    private final ModBlockEntity blockEntity;
    private final Level level;
    private final BlockPos pos;

    public TeamBeaconMenu(int containerId, Inventory inv, BlockPos pos) {
        super(ModMenuTypes.TEAM_BEACON_MENU.get(), containerId);
        this.pos = pos;
        this.level = inv.player.level();
        BlockEntity entity = level.getBlockEntity(pos);
        this.blockEntity = (ModBlockEntity) entity;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, pos), player, ModBlocks.TEAM_BEACON_BLOCK.get());
    }

    public ModBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
