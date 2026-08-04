package com.alnrflo.chaos;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;

public class ModPortalActivation {

    // Cadre en losange (relatif au centre), toujours à plat au sol (plan X-Z)
    //     F
    //    F F
    //   F   F
    //  F     F
    //   F   F
    //    F F
    //     F
    private static final int[][] FRAME_OFFSETS = {
        {0, 3},
        {-1, 2}, {1, 2},
        {-2, 1}, {2, 1},
        {-3, 0}, {3, 0},
        {-2, -1}, {2, -1},
        {-1, -2}, {1, -2},
        {0, -3}
    };

    // Intérieur du losange (là où le portail se place)
    private static final int[][] INTERIOR_OFFSETS = {
        {0, 2},
        {-1, 1}, {0, 1}, {1, 1},
        {-2, 0}, {-1, 0}, {0, 0}, {1, 0}, {2, 0},
        {-1, -1}, {0, -1}, {1, -1},
        {0, -2}
    };

    public static void register() {
        // L'activation se fait maintenant via briquet sur chaos_portal_frame
    }

    /**
     * Appelé depuis ChaosPortalFrameBlock quand un briquet est utilisé sur un bloc du cadre.
     * Le cadre n'est reconnu qu'à plat au sol.
     */
    public static boolean tryActivateFromFrame(ServerLevel level, BlockPos framePos) {
        for (int[] frameOffset : FRAME_OFFSETS) {
            BlockPos center = framePos.offset(-frameOffset[0], 0, -frameOffset[1]);
            if (checkFrameHorizontal(level, center)) {
                fillPortalHorizontal(level, center);
                playSound(level, center);
                return true;
            }
        }
        return false;
    }

    /**
     * Garantit qu'un portail (cadre + intérieur) existe à cet endroit, en le construisant
     * de force si besoin. Utilisé à l'arrivée pour permettre de repartir tranquillement.
     */
    public static void ensurePortalAt(ServerLevel level, BlockPos center) {
        if (level.getBlockState(center).is(ModBlocks.CHAOS_PORTAL)) {
            return;
        }
        for (int[] offset : FRAME_OFFSETS) {
            BlockPos framePos = center.offset(offset[0], 0, offset[1]);
            level.setBlock(framePos, ModBlocks.CHAOS_PORTAL_FRAME.defaultBlockState(), 3);
            clearHeadroom(level, framePos);
        }
        for (int[] offset : INTERIOR_OFFSETS) {
            BlockPos portalPos = center.offset(offset[0], 0, offset[1]);
            level.setBlock(portalPos, ModBlocks.CHAOS_PORTAL.defaultBlockState(), 3);
            clearHeadroom(level, portalPos);
        }
    }

    // Le cadre est posé à une hauteur unique (celle du centre) ; sur un terrain avec du relief,
    // le sol environnant peut être plus haut et ensevelir une partie du portail. On dégage
    // donc l'air au-dessus pour garantir qu'il reste visible et praticable.
    private static void clearHeadroom(ServerLevel level, BlockPos pos) {
        for (int dy = 1; dy <= 3; dy++) {
            BlockPos above = pos.above(dy);
            if (!level.getBlockState(above).isAir()) {
                level.setBlock(above, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    private static boolean checkFrameHorizontal(ServerLevel level, BlockPos center) {
        for (int[] offset : FRAME_OFFSETS) {
            BlockPos framePos = center.offset(offset[0], 0, offset[1]);
            if (!level.getBlockState(framePos).is(ModBlocks.CHAOS_PORTAL_FRAME)) {
                return false;
            }
        }
        return true;
    }

    private static void fillPortalHorizontal(ServerLevel level, BlockPos center) {
        for (int[] offset : INTERIOR_OFFSETS) {
            BlockPos portalPos = center.offset(offset[0], 0, offset[1]);
            if (level.getBlockState(portalPos).isAir()) {
                level.setBlock(portalPos, ModBlocks.CHAOS_PORTAL.defaultBlockState(), 3);
            }
        }
    }

    private static void playSound(ServerLevel level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0f, 0.8f);
    }
}
