package com.xiaohuhu.beacon.common.block.entity;

import com.xiaohuhu.beacon.common.RegistryObjects.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,"team_beacon");

    public static final Supplier<BlockEntityType<ModBlockEntity>> TEAM_BEACON_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("team_beacon_block_entity", ()->
                    BlockEntityType.Builder.of(ModBlockEntity::new, ModBlocks.TEAM_BEACON_BLOCK.get()).build(null));
}
