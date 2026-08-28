package net.mcreator.createmixandclean.gas.block.entity;

import net.mcreator.createmixandclean.gas.GasType;
import net.mcreator.createmixandclean.gas.init.GasBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumMap;
import java.util.Map;

public class GasCellBlockEntity extends BlockEntity {

    private final Map<GasType, Float> concentrations = new EnumMap<>(GasType.class);

    public GasCellBlockEntity(BlockPos pos, BlockState state) {
        super(GasBlockEntities.MIXTURE_GAS_CELL.get(), pos, state);
    }

    public float get(GasType type) {
        return concentrations.getOrDefault(type, 0f);
    }

    public void set(GasType type, float value) {
        if (value <= 0f) {
            concentrations.remove(type);
        } else {
            concentrations.put(type, Math.min(15f, value));
        }
        setChanged();
    }

    public Map<GasType, Float> getAll() {
        return concentrations;
    }

    public boolean isEmpty() {
        return concentrations.isEmpty();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag gases = new CompoundTag();
        concentrations.forEach((type, value) -> gases.putFloat(type.name(), value));
        tag.put("Gases", gases);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        concentrations.clear();
        if (tag.contains("Gases")) {
            CompoundTag gases = tag.getCompound("Gases");
            for (String key : gases.getAllKeys()) {
                GasType type = GasType.byName(key);
                if (type != null) concentrations.put(type, gases.getFloat(key));
            }
        }
    }
}