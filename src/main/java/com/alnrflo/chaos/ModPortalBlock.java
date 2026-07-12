package com.alnrflo.chaos;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTarget;
import net.minecraft.world.phys.Vec3;

public class ModPortalBlock extends Block {

    public static final ResourceKey<Level> CHAOS_DIMENSION_KEY = ResourceKey.create(
            Registries.DIMENSION, Chaos.id("chaos_dimension")
    );

    public ModPortalBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier applier, boolean bl) {
        if (level.isClientSide()) return;
        if (!(entity instanceof ServerPlayer player)) return;

        ServerLevel currentLevel = (ServerLevel) level;
        MinecraftServer server = currentLevel.getServer();

        ServerLevel targetLevel;
        if (currentLevel.dimension().equals(CHAOS_DIMENSION_KEY)) {
            targetLevel = server.getLevel(Level.OVERWORLD);
        } else {
            targetLevel = server.getLevel(CHAOS_DIMENSION_KEY);
        }

        if (targetLevel == null) return;

        Vec3 destination = new Vec3(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);

        FabricDimensions.teleport(player, targetLevel, new TeleportTarget(
                destination,
                Vec3.ZERO,
                player.getYRot(),
                player.getXRot(),
                TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET
        ));
    }
}