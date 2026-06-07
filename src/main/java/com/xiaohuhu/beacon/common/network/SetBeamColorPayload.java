package com.xiaohuhu.beacon.common.network;

import com.xiaohuhu.beacon.TeamBeacon;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetBeamColorPayload(BlockPos pos, int color) implements CustomPacketPayload {
    public static final Type<SetBeamColorPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TeamBeacon.MOD_ID, "set_beam_color"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SetBeamColorPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, SetBeamColorPayload::pos,
            ByteBufCodecs.INT, SetBeamColorPayload::color,
            SetBeamColorPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
