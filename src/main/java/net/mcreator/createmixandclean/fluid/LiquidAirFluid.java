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

public abstract class LiquidAirFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> CreateMixAndCleanModFluidTypes.LIQUID_AIR_TYPE.get(), () -> CreateMixAndCleanModFluids.LIQUID_AIR.get(),
			() -> CreateMixAndCleanModFluids.FLOWING_LIQUID_AIR.get()).explosionResistance(100f).bucket(() -> CreateMixAndCleanModItems.LIQUID_AIR_BUCKET.get()).block(() -> (LiquidBlock) CreateMixAndCleanModBlocks.LIQUID_AIR.get());

	private LiquidAirFluid() {
		super(PROPERTIES);
	}

	public static class Source extends LiquidAirFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends LiquidAirFluid {
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