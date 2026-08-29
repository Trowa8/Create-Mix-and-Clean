package net.mcreator.createmixandclean.gas.block;

import org.joml.Vector3f;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;
import net.mcreator.createmixandclean.gas.GasType;
import net.mcreator.createmixandclean.gas.client.GasTextureColorSampler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DiffusingGasBlock extends Block {

    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 15);

    private final GasType gasType;

    public DiffusingGasBlock(GasType gasType) {
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
        this.gasType = gasType;
        registerDefaultState(stateDefinition.any().setValue(LEVEL, 15));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public GasType getGasType() {
        return gasType;
    }

    public static int levelOf(BlockState state) {
        return state.getValue(LEVEL);
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
        int amount = levelOf(state);
        if (amount <= 0) return;
        if (random.nextFloat() > (amount / 15f) * 0.5f) return;

        CreateMixAndCleanMod.LOGGER.info("Diffusing gas tick at {} amount={} gas={}", pos, amount, gasType);

        double x = pos.getX() + random.nextDouble();
        double y = pos.getY() + random.nextDouble();
        double z = pos.getZ() + random.nextDouble();

        float[] color = GasTextureColorSampler.sampleColor(gasType, random);
        if (color == null || color.length < 3) {
            CreateMixAndCleanMod.LOGGER.info("Diffusing gas fallback color used at {} for {}", pos, gasType);
            color = gasType.getFallbackColor();
        }

        level.addParticle(new DustParticleOptions(new Vector3f(color[0], color[1], color[2]), 1.0f),
                x, y, z, 0.0, 0.01, 0.0);
        level.addParticle(ParticleTypes.CLOUD, x, y, z, 0.0, 0.0, 0.0);
        CreateMixAndCleanMod.LOGGER.info("Particle emitted at {} color={}", pos, java.util.Arrays.toString(color));
    }
}