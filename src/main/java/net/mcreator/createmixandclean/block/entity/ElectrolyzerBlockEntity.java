package net.mcreator.createmixandclean.block.entity;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlockEntities;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModSounds;
import net.mcreator.createmixandclean.recipe.ElectrolyzerRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Optional;

public class ElectrolyzerBlockEntity extends KineticBlockEntity {

    private static final int   BUFFER_SIZE   = 2_000;
    private static final int   FE_PER_RECIPE = 300;
    private static final float FE_PER_RPM    = 0.02f;
    private static final int   PLUNGE_TICKS  = 32;
    private static final int   RETURN_TICKS  = 32;

    private final EnergyStorage energyStorage = new EnergyStorage(BUFFER_SIZE);
    private float feAccumulator = 0f;

    private boolean processing    = false;
    private boolean returning     = false;
    private int     processingTick = 0;
    private int     processingTime = 200;
    private int     returnTick     = 0;
    private ElectrolyzerRecipe currentRecipe = null;

    public  float headOffset      = 0f;
    public  float prevHeadOffset  = 0f;
    private int   clientPlungeTick = 0;
    private boolean clientWasProcessing = false;

    public ElectrolyzerBlockEntity(BlockPos pos, BlockState state) {
        super(CreateMixAndCleanModBlockEntities.ELECTROLYZER.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition).inflate(1, 0, 1).expandTowards(0, -3, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null) return;

        if (level.isClientSide()) {
            clientTick();
            return;
        }

        generateFE();

        if (returning) {
            tickReturn();
        } else if (processing) {
            tickProcessing();
        } else {
            tryStartProcessing();
        }
    }

    private void generateFE() {
        float rpm = Math.abs(getSpeed());
        if (rpm <= 0) return;
        feAccumulator += rpm * FE_PER_RPM;
        int toStore = (int) feAccumulator;
        if (toStore > 0) {
            energyStorage.receiveEnergy(toStore, false);
            feAccumulator -= toStore;
            setChanged();
        }
    }

    private void tryStartProcessing() {
        if (energyStorage.getEnergyStored() < FE_PER_RECIPE) return;
        if (Math.abs(getSpeed()) == 0) return;
        Optional<ElectrolyzerRecipe> match = findRecipe();
        if (match.isEmpty()) return;
        currentRecipe  = match.get();
        processingTime = currentRecipe.getProcessingTime();
        processingTick = 0;
        processing     = true;
        sendData();
    }

    private void playProcessingSound() {
        if (level == null) return;
        level.playSound(null,
                worldPosition,
                CreateMixAndCleanModSounds.ELECTROLYZER_PROCESSING.get(),
                net.minecraft.sounds.SoundSource.BLOCKS,
                0.8f,
                0.9f + level.getRandom().nextFloat() * 0.2f);
    }

    private void tickProcessing() {
        processingTick++;
        if (processingTick > PLUNGE_TICKS) {
            if (processingTick % 4 == 0) spawnProcessingParticles();
            if (processingTick % 20 == 0) playProcessingSound();
        }
        if (processingTick >= processingTime + PLUNGE_TICKS) {
            finishProcessing();
        }
    }

    private void finishProcessing() {
        if (currentRecipe == null || level == null) {
            startReturn();
            return;
        }
        Optional<IItemHandler> basinInv = getBasinInventory();
        if (basinInv.isEmpty() || !currentRecipe.matchesInventory(basinInv.get())) {
            startReturn();
            return;
        }
        currentRecipe.consumeIngredients(basinInv.get());
        currentRecipe.depositResults(basinInv.get());
        energyStorage.extractEnergy(FE_PER_RECIPE, false);
        setChanged();

        BlockPos basinPos = worldPosition.below(2);
        IFluidHandler tank = level.getCapability(Capabilities.FluidHandler.BLOCK, basinPos, Direction.UP);
        if (tank != null) {
            if (!currentRecipe.getFluidIngredients().isEmpty()) {
                for (FluidStack fs : currentRecipe.getFluidIngredients())
                    tank.drain(fs, IFluidHandler.FluidAction.EXECUTE);
            }
            if (!currentRecipe.getFluidResults().isEmpty()) {
                currentRecipe.depositFluidResults(tank);
            }
        }

        startReturn();
    }

    private void startReturn() {
        processing     = false;
        currentRecipe  = null;
        returning      = true;
        returnTick     = 0;
        processingTick = 0;
        sendData();
    }

    private void tickReturn() {
        returnTick++;
        if (returnTick >= RETURN_TICKS) {
            returning  = false;
            returnTick = 0;
            sendData();
        }
    }

    private void spawnProcessingParticles() {
        if (!(level instanceof net.minecraft.server.level.ServerLevel serverLevel)) return;
        BlockPos basinPos = worldPosition.below(2);
        RandomSource rand = serverLevel.getRandom();

        for (int i = 0; i < 3; i++) {
            double x = basinPos.getX() + 0.2 + rand.nextDouble() * 0.6;
            double y = basinPos.getY() + 0.9 + rand.nextDouble() * 0.2;
            double z = basinPos.getZ() + 0.2 + rand.nextDouble() * 0.6;
            double vx = (rand.nextDouble() - 0.5) * 0.1;
            double vy = rand.nextDouble() * 0.05;
            double vz = (rand.nextDouble() - 0.5) * 0.1;
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    x, y, z, 1, vx, vy, vz, 0.01);
            if (i == 0) {
                serverLevel.sendParticles(ParticleTypes.BUBBLE,
                        x, basinPos.getY() + 0.5, z,
                        1, vx, vy * 2, vz, 0.01);
            }
        }
    }

    private void clientTick() {
        prevHeadOffset = headOffset;

        if (processing) {
            if (!clientWasProcessing) clientPlungeTick = 0;
            clientWasProcessing = true;
            if (clientPlungeTick < PLUNGE_TICKS) {
                headOffset = easeIn((float) clientPlungeTick / PLUNGE_TICKS);
                clientPlungeTick++;
            } else {
                headOffset = 1.0f;
            }
        } else if (returning) {
            clientWasProcessing = false;
            float t = (float) returnTick / Math.max(RETURN_TICKS, 1);
            headOffset = 1.0f - easeIn(t);
            returnTick = Math.min(returnTick + 1, RETURN_TICKS);
        } else {
            clientWasProcessing = false;
            clientPlungeTick = 0;
            headOffset = 0f;
        }
    }

    private float easeIn(float t) {
        return (float) Math.sin(t * Math.PI / 2f);
    }

    private Optional<IItemHandler> getBasinInventory() {
        if (level == null) return Optional.empty();
        BlockPos basinPos = worldPosition.below(2);
        return Optional.ofNullable(level.getCapability(Capabilities.ItemHandler.BLOCK, basinPos, Direction.UP));
    }

    private Optional<ElectrolyzerRecipe> findRecipe() {
        Optional<IItemHandler> inv = getBasinInventory();
        if (inv.isEmpty() || level == null) return Optional.empty();

        BlockPos basinPos = worldPosition.below(2);
        IFluidHandler fluidTank = level.getCapability(Capabilities.FluidHandler.BLOCK, basinPos, Direction.UP);

        return level.getRecipeManager()
                    .getAllRecipesFor(ElectrolyzerRecipe.TYPE)
                    .stream()
                    .map(net.minecraft.world.item.crafting.RecipeHolder::value)
                    .filter(r -> r.matchesInventory(inv.get()) && r.matchesFluids(fluidTank))
                    .findFirst();
    }

    @Override
    protected void read(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        processing     = tag.getBoolean("Processing");
        returning      = tag.getBoolean("Returning");
        processingTick = tag.getInt("ProcessingTick");
        processingTime = tag.getInt("ProcessingTime");
        returnTick     = tag.getInt("ReturnTick");
        if (!clientPacket && tag.contains("Energy"))
            energyStorage.deserializeNBT(registries, tag.get("Energy"));
    }

    @Override
    public void write(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putBoolean("Processing",    processing);
        tag.putBoolean("Returning",     returning);
        tag.putInt("ProcessingTick",    processingTick);
        tag.putInt("ProcessingTime",    processingTime);
        tag.putInt("ReturnTick",        returnTick);
        if (!clientPacket)
            tag.put("Energy", energyStorage.serializeNBT(registries));
    }

    public IEnergyStorage getEnergyStorage() { return energyStorage; }

    public boolean isProcessing() { return processing; }
    public int getEnergyStored()  { return energyStorage.getEnergyStored(); }
}