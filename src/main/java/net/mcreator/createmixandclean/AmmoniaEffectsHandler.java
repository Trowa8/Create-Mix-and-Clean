package net.mcreator.createmixandclean;

import net.mcreator.createmixandclean.fluid.types.AmmoniaFluidType;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModSounds;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = "create_mix_and_clean")
public class AmmoniaEffectsHandler {

    private static final Map<UUID, Integer> exposure = new HashMap<>();
    private static final Map<UUID, Integer> nextSoundTick = new HashMap<>();
    private static final Set<UUID> messageShown = new HashSet<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        UUID id = player.getUUID();
        FluidState eyeFluid = player.level().getFluidState(BlockPos.containing(player.getEyePosition()));
        boolean headInFluid = eyeFluid.getFluidType() instanceof AmmoniaFluidType;

        if (headInFluid && !HazardHelmetHandler.isProtected(player)) {
            int ticks = exposure.getOrDefault(id, 0) + 1;
            exposure.put(id, ticks);

            if (!messageShown.contains(id)) {
                player.displayClientMessage(Component.literal("A pungent smell fills the air"), true);
                messageShown.add(id);
                nextSoundTick.put(id, ticks + 60);
            }

            if (ticks >= nextSoundTick.getOrDefault(id, Integer.MAX_VALUE)) {
                player.level().playSound(null, player, CreateMixAndCleanModSounds.COUGHING.get(), SoundSource.PLAYERS, 0.5f, 0.8f);
                int interval = (3 + player.getRandom().nextInt(6)) * 20;
                nextSoundTick.put(id, ticks + interval);
            }

            applyEffects(player, ticks);

        } else if (!headInFluid) {
            int ticks = exposure.getOrDefault(id, 0);
            if (ticks > 0) {
                ticks--;
                exposure.put(id, ticks);
                applyEffects(player, ticks);
                if (ticks == 0) {
                    exposure.remove(id);
                    nextSoundTick.remove(id);
                    messageShown.remove(id);
                }
            }
        }
    }

    private static void applyEffects(Player player, int ticks) {
        if (ticks > 360) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 0, false, false));
        }
        if (ticks > 520) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0, false, false));
        }
        if (ticks > 760) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 1, false, false));
        }
        if (ticks > 1000) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 2, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 2, false, false));
        }
        if (ticks > 1200 && ticks % 20 == 0) {
            player.hurt(player.damageSources().magic(), 1.0f);
        }
    }
}