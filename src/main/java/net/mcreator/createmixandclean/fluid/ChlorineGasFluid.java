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

public abstract class ChlorineGasFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> CreateMixAndCleanModFluidTypes.CHLORINE_GAS_TYPE.get(), () -> CreateMixAndCleanModFluids.CHLORINE_GAS.get(),
			() -> CreateMixAndCleanModFluids.FLOWING_CHLORINE_GAS.get()).explosionResistance(100f).slopeFindDistance(1).bucket(() -> CreateMixAndCleanModItems.CHLORINE_GAS_BUCKET.get())
			.block(() -> (LiquidBlock) CreateMixAndCleanModBlocks.CHLORINE_GAS.get());

	private ChlorineGasFluid() {
		super(PROPERTIES);
	}

	public static class Source extends ChlorineGasFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends ChlorineGasFluid {
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