package net.mcreator.createmixandclean.gas.block;

import net.mcreator.createmixandclean.gas.GasType;
import net.mcreator.createmixandclean.gas.block.entity.GasCellBlockEntity;
import net.mcreator.createmixandclean.gas.client.GasTextureColorSampler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

import java.util.Map.Entry;

import org.jetbrains.annotations.Nullable;

public class MixtureGasBlock extends Block implements EntityBlock {

    public MixtureGasBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.NONE)
                .noCollission()
                .noOcclusion()
                .noLootTable()
                .replaceable()
                .randomTicks()
                .sound(SoundType.EMPTY)
                .pushReaction(PushReaction.DESTROY)
                .strength(-1f, 3600000f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GasCellBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!(level.getBlockEntity(pos) instanceof GasCellBlockEntity be) || be.isEmpty()) return;
    
        GasType dominant = null;
        float best = -1f;
        for (Entry<GasType, Float> entry : be.getAll().entrySet()) {
            if (entry.getValue() > best) {
                best = entry.getValue();
                dominant = entry.getKey();
            }
        }
        if (dominant == null) return;
        if (random.nextFloat() > (best / 15f) * 0.5f) return;
    
        double x = pos.getX() + random.nextDouble();
        double y = pos.getY() + random.nextDouble();
        double z = pos.getZ() + random.nextDouble();
    
        float[] color = GasTextureColorSampler.sampleColor(dominant, random);
        if (color == null || color.length < 3) {
            color = dominant.getFallbackColor();
        }

        level.addParticle(new DustParticleOptions(new Vector3f(color[0], color[1], color[2]), 1.0f),
                x, y, z, 0.0, 0.01, 0.0);
        level.addParticle(ParticleTypes.CLOUD, x, y, z, 0.0, 0.0, 0.0);
    }
}
