package com.xiaohuhu.beacon.common.block.entity;

import com.xiaohuhu.beacon.common.RegistryObjects.ModBlockEntities;
import com.xiaohuhu.beacon.common.menu.TeamBeaconMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ModBlockEntity extends BlockEntity implements MenuProvider {

    private int beamColor = 0xFFFFFF;
    private String selectedTeam = ""; // 存储队伍名称

    public ModBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TEAM_BEACON_BLOCK_ENTITY.get(), pos, state);
    }

    // ========== 颜色 ==========
    public int getBeamColor() {
        return beamColor;
    }

    public void setBeamColor(int color) {
        this.beamColor = color;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    // ========== 队伍 ==========
    public String getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(String team) {
        this.selectedTeam = team == null ? "" : team;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    // ========== MenuProvider ==========
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("gui.team_beacon.team_beacon_block.title");
    }

    @Override
    public @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
        return new TeamBeaconMenu(containerId, inventory, this.getBlockPos());
    }

    // ========== NBT ==========
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("BeamColor", beamColor);
        tag.putString("SelectedTeam", selectedTeam);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.beamColor = tag.getInt("BeamColor");
        this.selectedTeam = tag.getString("SelectedTeam");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag, registries);
    }
}