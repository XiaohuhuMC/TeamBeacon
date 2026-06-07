package com.xiaohuhu.beacon.common.RegistryObjects;

import com.xiaohuhu.beacon.TeamBeacon;
import com.xiaohuhu.beacon.common.menu.TeamBeaconMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, TeamBeacon.MOD_ID);

    public static final Supplier<MenuType<TeamBeaconMenu>> TEAM_BEACON_MENU =
            MENUS.register("team_beacon_menu", () ->
                    IMenuTypeExtension.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        return new TeamBeaconMenu(windowId, inv, pos);
                    }));
}
