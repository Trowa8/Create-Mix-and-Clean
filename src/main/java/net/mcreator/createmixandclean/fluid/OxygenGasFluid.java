package net.mcreator.createmixandclean.fluid;

import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModItems;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluidTypes;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlocks;

public abstract class OxygenGasFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> CreateMixAndCleanModFluidTypes.OXYGEN_GAS_TYPE.get(), () -> CreateMixAndCleanModFluids.OXYGEN_GAS.get(),
			() -> CreateMixAndCleanModFluids.FLOWING_OXYGEN_GAS.get()).explosionResistance(99f).tickRate(2).slopeFindDistance(1).bucket(() -> CreateMixAndCleanModItems.OXYGEN_GAS_BUCKET.get())
			.block(() -> (LiquidBlock) CreateMixAndCleanModBlocks.OXYGEN_GAS.get());

	private OxygenGasFluid() {
		super(PROPERTIES);
	}

	public static class Source extends OxygenGasFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends OxygenGasFluid {
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		public boolean isSource(FluidState state) {
			return false;
		}
	}
}