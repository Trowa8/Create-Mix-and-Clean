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

public abstract class NitrogenGasFluid extends BaseFlowingFluid {
	public static final BaseFlowingFluid.Properties PROPERTIES = new BaseFlowingFluid.Properties(() -> CreateMixAndCleanModFluidTypes.NITROGEN_GAS_TYPE.get(), () -> CreateMixAndCleanModFluids.NITROGEN_GAS.get(),
			() -> CreateMixAndCleanModFluids.FLOWING_NITROGEN_GAS.get()).explosionResistance(99f).tickRate(2).slopeFindDistance(1).bucket(() -> CreateMixAndCleanModItems.NITROGEN_GAS_BUCKET.get())
			.block(() -> (LiquidBlock) CreateMixAndCleanModBlocks.NITROGEN_GAS.get());

	private NitrogenGasFluid() {
		super(PROPERTIES);
	}

	public static class Source extends NitrogenGasFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends NitrogenGasFluid {
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