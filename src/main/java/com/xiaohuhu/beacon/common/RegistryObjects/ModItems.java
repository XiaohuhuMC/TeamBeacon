package com.xiaohuhu.beacon.common.RegistryObjects;

import com.xiaohuhu.beacon.TeamBeacon;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    //延迟注册器
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(TeamBeacon.MOD_ID);

    //注册物品
    //信标核心
    public static final DeferredItem<Item> BEACON_CORE =
            ITEMS.register("material/beacon_core", () -> new Item(new Item.Properties().fireResistant()));

    //初始化类
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
