package net.mcreator.createmixandclean.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.mcreator.createmixandclean.CreateMixAndCleanMod;

public class CausticSodaPlayerFinishesUsingItemProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		CreateMixAndCleanMod.queueServerWork(400, () -> {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))), 2);
			CreateMixAndCleanMod.queueServerWork(60, () -> {
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))), 2);
				CreateMixAndCleanMod.queueServerWork(60, () -> {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))), 2);
					CreateMixAndCleanMod.queueServerWork(60, () -> {
						entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))), 2);
						CreateMixAndCleanMod.queueServerWork(60, () -> {
							entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))), 2);
							CreateMixAndCleanMod.queueServerWork(60, () -> {
								entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))), 2);
								CreateMixAndCleanMod.queueServerWork(60, () -> {
									entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))),
											2);
									CreateMixAndCleanMod.queueServerWork(60, () -> {
										entity.hurt(
												new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))),
												2);
										CreateMixAndCleanMod.queueServerWork(60, () -> {
											entity.hurt(new DamageSource(
													world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))), 2);
											CreateMixAndCleanMod.queueServerWork(60, () -> {
												entity.hurt(
														new DamageSource(
																world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))),
														2);
												CreateMixAndCleanMod.queueServerWork(60, () -> {
													entity.hurt(new DamageSource(
															world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))), 2);
													CreateMixAndCleanMod.queueServerWork(60, () -> {
														entity.hurt(new DamageSource(
																world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("create_mix_and_clean:corrosion")))),
																2);
													});
												});
											});
										});
									});
								});
							});
						});
					});
				});
			});
		});
	}
}