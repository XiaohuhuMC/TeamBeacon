package com.xiaohuhu.beacon.client;

import com.xiaohuhu.beacon.TeamBeacon;
import com.xiaohuhu.beacon.client.screen.TeamBeaconScreen;
import com.xiaohuhu.beacon.common.RegistryObjects.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = TeamBeacon.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.TEAM_BEACON_MENU.get(), TeamBeaconScreen::new);
    }
}