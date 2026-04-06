package net.mcreator.createmixandclean.block.entity;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlockEntities;
import net.mcreator.createmixandclean.recipe.ElectrolyzerRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ElectrolyzerBlockEntity extends KineticBlockEntity {

    private static final int   BUFFER_SIZE   = 10_000;
    private static final int   FE_PER_RECIPE = 100;
    private static final float FE_PER_RPM    = 0.5f;

    private final EnergyStorage energyStorage = new EnergyStorage(BUFFER_SIZE);
    private LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.empty();
    private float feAccumulator = 0f;

    private int    processingTick = 0;
    private int    processingTime = 200;
    private boolean processing    = false;
    private ElectrolyzerRecipe currentRecipe = null;

    // Synced to client for head animation interpolation
    public float headOffset     = 0f;
    public float prevHeadOffset = 0f;

    // Two-arg constructor — matches BlockEntityType.BlockEntitySupplier<T>
    public ElectrolyzerBlockEntity(BlockPos pos, BlockState state) {
        super(CreateMixAndCleanModBlockEntities.ELECTROLYZER.get(), pos, state);
    }

    // ── Tick ─────────────────────────────────────────────────────────────────

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) return;

        generateFE();

        if (!processing) {
            tryStartProcessing();
        } else {
            advanceProcessing();
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

    private void advanceProcessing() {
        processingTick++;
        prevHeadOffset = headOffset;
        float t = (float) processingTick / processingTime;
        headOffset = (float) Math.sin(t * Math.PI); // smooth 0 → 1 → 0 arc
        if (processingTick >= processingTime) finishProcessing();
    }

    private void finishProcessing() {
        if (currentRecipe == null || level == null) { resetProcessing(); return; }
        Optional<IItemHandler> basinInv = getBasinInventory();
        if (basinInv.isEmpty() || !currentRecipe.matchesInventory(basinInv.get())) {
            resetProcessing(); return;
        }
        currentRecipe.consumeIngredients(basinInv.get());
        currentRecipe.depositResults(basinInv.get());
        energyStorage.extractEnergy(FE_PER_RECIPE, false);
        setChanged();
        resetProcessing();
    }

    private void resetProcessing() {
        processing     = false;
        processingTick = 0;
        currentRecipe  = null;
        headOffset     = 0f;
        prevHeadOffset = 0f;
        sendData();
    }

    // ── Basin helpers ─────────────────────────────────────────────────────────

    private Optional<IItemHandler> getBasinInventory() {
        if (level == null) return Optional.empty();
        var be = level.getBlockEntity(worldPosition.below());
        if (be == null) return Optional.empty();
        return be.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).resolve();
    }

    private Optional<ElectrolyzerRecipe> findRecipe() {
        Optional<IItemHandler> inv = getBasinInventory();
        if (inv.isEmpty() || level == null) return Optional.empty();
        return level.getRecipeManager()
                    .getAllRecipesFor(ElectrolyzerRecipe.TYPE)
                    .stream()
                    .filter(r -> r.matchesInventory(inv.get()))
                    .findFirst();
    }

    // ── NBT ──────────────────────────────────────────────────────────────────

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        processing     = tag.getBoolean("Processing");
        processingTick = tag.getInt("ProcessingTick");
        processingTime = tag.getInt("ProcessingTime");
        headOffset     = tag.getFloat("HeadOffset");
        if (tag.contains("Energy"))
            energyStorage.deserializeNBT(tag.get("Energy"));
    }

    @Override
    public void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putBoolean("Processing",    processing);
        tag.putInt("ProcessingTick",    processingTick);
        tag.putInt("ProcessingTime",    processingTime);
        tag.putFloat("HeadOffset",      headOffset);
        tag.put("Energy", energyStorage.serializeNBT());
    }

    // ── Capabilities ──────────────────────────────────────────────────────────

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergy = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergy.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
                                              @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) return lazyEnergy.cast();
        return super.getCapability(cap, side);
    }

    // ── Getters for block and renderer ────────────────────────────────────────

    public boolean isProcessing()  { return processing; }
    public int getEnergyStored()   { return energyStorage.getEnergyStored(); }
}