package net.mcreator.createmixandclean.block.entity;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlockEntities;
import net.mcreator.createmixandclean.recipe.ElectrolyzerRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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

    private static final int   BUFFER_SIZE    = 10_000;
    private static final int   FE_PER_RECIPE  = 100;
    private static final float FE_PER_RPM     = 0.5f;

    private static final int   PLUNGE_TICKS   = 8;
    private static final int   RETURN_TICKS   = 8;

    private final EnergyStorage energyStorage  = new EnergyStorage(BUFFER_SIZE);
    private LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.empty();
    private float feAccumulator = 0f;

    private boolean processing   = false;
    private boolean returning    = false;
    private int  processingTick  = 0;
    private int  processingTime  = 200;
    private int  returnTick      = 0;
    private ElectrolyzerRecipe currentRecipe = null;

    public float headOffset     = 0f;
    public float prevHeadOffset = 0f;

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

    private void tickProcessing() {
        processingTick++;

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

        if (energyStorage.getEnergyStored() >= FE_PER_RECIPE
                && Math.abs(getSpeed()) > 0) {
            Optional<ElectrolyzerRecipe> next = findRecipe();
            if (next.isPresent()) {
                currentRecipe  = next.get();
                processingTime = next.get().getProcessingTime();
                processingTick = 0;
                sendData();
                return;
            }
        }

        startReturn();
    }

    private void startReturn() {
        processing    = false;
        currentRecipe = null;
        returning     = true;
        returnTick    = 0;
        processingTick = 0;
        sendData();
    }

    private void tickReturn() {
        returnTick++;
        if (returnTick >= RETURN_TICKS) {
            returning = false;
            returnTick = 0;
            sendData();
        }
    }

    private void clientTick() {
        prevHeadOffset = headOffset;

        if (processing) {
            if (processingTick < PLUNGE_TICKS) {
                float t = (float) processingTick / PLUNGE_TICKS;
                headOffset = easeIn(t);
            } else {
                headOffset = 1.0f;
            }
            processingTick++;

        } else if (returning) {
            float t = (float) returnTick / RETURN_TICKS;
            headOffset = 1.0f - easeIn(t);
            returnTick++;

        } else {
            headOffset = 0f;
        }
    }

    private float easeIn(float t) {
        return (float) Math.sin(t * Math.PI / 2f);
    }

    private Optional<IItemHandler> getBasinInventory() {
        if (level == null) return Optional.empty();
        var be = level.getBlockEntity(worldPosition.below(2));
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

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        processing     = tag.getBoolean("Processing");
        returning      = tag.getBoolean("Returning");
        processingTick = tag.getInt("ProcessingTick");
        processingTime = tag.getInt("ProcessingTime");
        returnTick     = tag.getInt("ReturnTick");
        headOffset     = tag.getFloat("HeadOffset");
        if (!clientPacket && tag.contains("Energy"))
            energyStorage.deserializeNBT(tag.get("Energy"));
    }

    @Override
    public void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putBoolean("Processing",    processing);
        tag.putBoolean("Returning",     returning);
        tag.putInt("ProcessingTick",    processingTick);
        tag.putInt("ProcessingTime",    processingTime);
        tag.putInt("ReturnTick",        returnTick);
        tag.putFloat("HeadOffset",      headOffset);
        if (!clientPacket)
            tag.put("Energy", energyStorage.serializeNBT());
    }

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

    public boolean isProcessing() { return processing; }
    public int getEnergyStored()  { return energyStorage.getEnergyStored(); }
}