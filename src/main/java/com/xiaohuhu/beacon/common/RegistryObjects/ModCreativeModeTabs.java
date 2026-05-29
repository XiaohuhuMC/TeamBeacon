package com.xiaohuhu.beacon.common.RegistryObjects;

import com.xiaohuhu.beacon.TeamBeacon;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TeamBeacon.MOD_ID);

    public static final Supplier<CreativeModeTab> TEAM_BEACON_TAB =
            CREATIVE_MODE_TABS.register("team_beacon_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.BEACON_CORE.get()))
                    .title(Component.translatable("itemGroup.team_beacon_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.BEACON_CORE);
                    }).build());
    public static final Supplier<CreativeModeTab> TEAM_BEACON_MATERIAL_TAB =
            CREATIVE_MODE_TABS.register("team_beacon_material_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.BEACON_CORE.get()))
                    .title(Component.translatable("itemGroup.team_beacon_material_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.BEACON_CORE);
                    }).withTabsBefore(ResourceLocation.fromNamespaceAndPath(TeamBeacon.MOD_ID, "team_beacon_tab"))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
