package net.mcreator.createmixandclean.fluid;

import net.neoforged.neoforge.fluids.BaseFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import net.mcreator.createmixandclean.init.CreateMixAndCleanModItems;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluids;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModFluidTypes;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlocks;

public abstract class HydrogenGasFluid extends BaseFlowingFluid {
	public static final BaseFlowingFluid.Properties PROPERTIES = new BaseFlowingFluid.Properties(() -> CreateMixAndCleanModFluidTypes.HYDROGEN_GAS_TYPE.get(), () -> CreateMixAndCleanModFluids.HYDROGEN_GAS.get(),
			() -> CreateMixAndCleanModFluids.FLOWING_HYDROGEN_GAS.get()).explosionResistance(100f).tickRate(3).slopeFindDistance(1).bucket(() -> CreateMixAndCleanModItems.HYDROGEN_GAS_BUCKET.get())
			.block(() -> (LiquidBlock) CreateMixAndCleanModBlocks.HYDROGEN_GAS.get());

	private HydrogenGasFluid() {
		super(PROPERTIES);
	}

	public static class Source extends HydrogenGasFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends HydrogenGasFluid {
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