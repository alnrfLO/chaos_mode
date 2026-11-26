package com.alnrflo.chaos;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.TeleportTransition;
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
        if (player.isOnPortalCooldown()) return;

        ServerLevel currentLevel = (ServerLevel) level;
        MinecraftServer server = currentLevel.getServer();

        ServerLevel targetLevel;
        boolean returningToOverworld = currentLevel.dimension().equals(CHAOS_DIMENSION_KEY);
        if (returningToOverworld) {
            targetLevel = server.getLevel(Level.OVERWORLD);
        } else {
            targetLevel = server.getLevel(CHAOS_DIMENSION_KEY);
        }

        if (targetLevel == null) return;

        int portalSurfaceY = surfaceY(targetLevel, pos.getX(), pos.getZ());
        BlockPos arrivalCenter = new BlockPos(pos.getX(), portalSurfaceY, pos.getZ());
        ModPortalActivation.ensurePortalAt(targetLevel, arrivalCenter);

        double yawRad = Math.toRadians(player.getYRot());
        int landingX = pos.getX() + (int) Math.round(Math.cos(yawRad) * 10);
        int landingZ = pos.getZ() + (int) Math.round(Math.sin(yawRad) * 10);
        int landingSurfaceY = surfaceY(targetLevel, landingX, landingZ);
        BlockPos landingPos = new BlockPos(landingX, landingSurfaceY, landingZ);
        targetLevel.setBlock(landingPos, Blocks.AIR.defaultBlockState(), 3);
        targetLevel.setBlock(landingPos.above(), Blocks.AIR.defaultBlockState(), 3);
        Vec3 destination = new Vec3(landingX + 0.5, landingSurfaceY, landingZ + 0.5);

        float landingYaw = player.getYRot() + 180.0F;

        TeleportTransition transition = new TeleportTransition(
                targetLevel,
                destination,
                Vec3.ZERO,
                landingYaw,
                player.getXRot(),
                TeleportTransition.DO_NOTHING
        );

        player.teleport(transition);
        player.setPortalCooldown();
    }

    private static int surfaceY(ServerLevel level, int x, int z) {
        LevelChunk chunk = level.getChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
        return chunk.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z) + 1;
    }
}