package net.mcreator.createmixandclean.gas;

import java.util.Set;

public interface GasCellAccess {
    float getConcentration(GasType gasType);
    void setConcentration(GasType gasType, float value);
    Set<GasType> getPresentGases();
    float getTotalLevel();
    boolean isEmpty();
    void clear();
}
