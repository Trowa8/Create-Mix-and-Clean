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

public abstract class IronSlurryFluid extends BaseFlowingFluid {
	public static final BaseFlowingFluid.Properties PROPERTIES = new BaseFlowingFluid.Properties(() -> CreateMixAndCleanModFluidTypes.IRON_SLURRY_TYPE.get(), () -> CreateMixAndCleanModFluids.IRON_SLURRY.get(),
			() -> CreateMixAndCleanModFluids.FLOWING_IRON_SLURRY.get()).explosionResistance(100f).bucket(() -> CreateMixAndCleanModItems.IRON_SLURRY_BUCKET.get()).block(() -> (LiquidBlock) CreateMixAndCleanModBlocks.IRON_SLURRY.get());

	private IronSlurryFluid() {
		super(PROPERTIES);
	}

	public static class Source extends IronSlurryFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends IronSlurryFluid {
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