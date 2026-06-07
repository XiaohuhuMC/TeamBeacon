package com.xiaohuhu.beacon.common.network;

import com.xiaohuhu.beacon.TeamBeacon;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetTeamPayload(BlockPos pos, String teamName) implements CustomPacketPayload {
    public static final Type<SetTeamPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TeamBeacon.MOD_ID, "set_team"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SetTeamPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, SetTeamPayload::pos,
            ByteBufCodecs.STRING_UTF8, SetTeamPayload::teamName,
            SetTeamPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}