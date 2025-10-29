
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

public abstract class HydrochloricAcidFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> CreateMixAndCleanModFluidTypes.HYDROCHLORIC_ACID_TYPE.get(), () -> CreateMixAndCleanModFluids.HYDROCHLORIC_ACID.get(),
			() -> CreateMixAndCleanModFluids.FLOWING_HYDROCHLORIC_ACID.get()).explosionResistance(100f).slopeFindDistance(3).bucket(() -> CreateMixAndCleanModItems.HYDROCHLORIC_ACID_BUCKET.get())
			.block(() -> (LiquidBlock) CreateMixAndCleanModBlocks.HYDROCHLORIC_ACID.get());

	private HydrochloricAcidFluid() {
		super(PROPERTIES);
	}

	public static class Source extends HydrochloricAcidFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends HydrochloricAcidFluid {
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
