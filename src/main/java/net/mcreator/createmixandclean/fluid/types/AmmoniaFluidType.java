package net.mcreator.createmixandclean.fluid.types;

import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class AmmoniaFluidType extends FluidType {
    public AmmoniaFluidType() {
        super(FluidType.Properties.create()
                .fallDistanceModifier(1F)
                .canExtinguish(false)
                .supportsBoating(false)
                .canHydrate(false)
                .motionScale(0D)
                .density(-1) 
                .viscosity(0)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                .canSwim(false)
                .canDrown(false));
    }

    @Override
	public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
    	Vec3 horizontalMovement = new Vec3(movementVector.x, 0.0D, movementVector.z);
    	float speedFactor = entity.onGround() ? 0.025F : 0.005F;
    	entity.moveRelative(speedFactor, horizontalMovement);

    	Vec3 vel = entity.getDeltaMovement();
    	if (vel.y > 0.0D && vel.y < 0.3D && !entity.onGround()) {
    	    entity.setDeltaMovement(vel.x, 0.0D, vel.z);
    	}

    	return false;
	}

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_TEXTURE = ResourceLocation.parse("create_mix_and_clean:block/ammonia");
            private static final ResourceLocation FLOWING_TEXTURE = ResourceLocation.parse("create_mix_and_clean:block/ammonia");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }
        });
    }
}