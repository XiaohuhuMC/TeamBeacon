package com.xiaohuhu.beacon.common.block.entity;

import com.xiaohuhu.beacon.common.menu.TeamBeaconMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModBlockEntity extends BlockEntity implements MenuProvider {
    public ModBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TEAM_BEACON_BLOCK_ENTITY.get(), pos, state);

    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("gui.team_beacon.team_beacon_block.title");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
        return new TeamBeaconMenu(containerId, inventory, this.getBlockPos());
    }


}
