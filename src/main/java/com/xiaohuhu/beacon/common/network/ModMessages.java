package com.xiaohuhu.beacon.common.network;

import com.xiaohuhu.beacon.TeamBeacon;
import com.xiaohuhu.beacon.common.block.entity.ModBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = TeamBeacon.MOD_ID)
public class ModMessages {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        // 颜色数据包
        registrar.playToServer(
                SetBeamColorPayload.TYPE,
                SetBeamColorPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        (payload, context) -> {
                            context.enqueueWork(() -> {
                                var level = context.player().level();
                                if (level.getBlockEntity(payload.pos()) instanceof ModBlockEntity be) {
                                    be.setBeamColor(payload.color());
                                }
                            });
                        },
                        (payload, context) -> {}
                )
        );

        // 队伍数据包
        registrar.playToServer(
                SetTeamPayload.TYPE,
                SetTeamPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        (payload, context) -> {
                            context.enqueueWork(() -> {
                                var level = context.player().level();
                                if (level.getBlockEntity(payload.pos()) instanceof ModBlockEntity be) {
                                    be.setSelectedTeam(payload.teamName());
                                }
                            });
                        },
                        (payload, context) -> {}
                )
        );
    }
}