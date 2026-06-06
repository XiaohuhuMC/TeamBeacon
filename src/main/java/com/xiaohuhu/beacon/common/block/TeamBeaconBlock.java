package com.xiaohuhu.beacon.common.block;

import com.xiaohuhu.beacon.TeamBeacon;
import com.xiaohuhu.beacon.common.block.entity.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TeamBeaconBlock extends Block implements EntityBlock {
    public TeamBeaconBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, @NotNull BlockState state) {
        return new ModBlockEntity(pos, state);
    }

//    @Override
//    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
//                                       Player player, InteractionHand hand, BlockHitResult hitResult) {
//        if (!level.isClientSide) {
//            BlockEntity blockEntity = level.getBlockEntity(pos);
//            if (blockEntity instanceof MenuProvider menuProvider) {
//                player.openMenu(menuProvider);
//                return InteractionResult.SUCCESS;
//            }
//        }
//        return InteractionResult.sidedSuccess(level.isClientSide);
//    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            TeamBeacon.LOGGER.info("Server side, blockEntity = {}", blockEntity);
//            if (blockEntity instanceof MenuProvider menuProvider) {
//                TeamBeacon.LOGGER.info("Opening menu...");
//                player.openMenu(menuProvider);
//                return InteractionResult.SUCCESS;
//            }
            if (blockEntity instanceof MenuProvider menuProvider) {
                ((ServerPlayer) player).openMenu(menuProvider, buf -> buf.writeBlockPos(pos));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
