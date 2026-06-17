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

public abstract class AmmoniaFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> CreateMixAndCleanModFluidTypes.AMMONIA_TYPE.get(), () -> CreateMixAndCleanModFluids.AMMONIA.get(),
			() -> CreateMixAndCleanModFluids.FLOWING_AMMONIA.get()).explosionResistance(99f).tickRate(2).slopeFindDistance(1).bucket(() -> CreateMixAndCleanModItems.AMMONIA_BUCKET.get())
			.block(() -> (LiquidBlock) CreateMixAndCleanModBlocks.AMMONIA.get());

	private AmmoniaFluid() {
		super(PROPERTIES);
	}

	public static class Source extends AmmoniaFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends AmmoniaFluid {
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