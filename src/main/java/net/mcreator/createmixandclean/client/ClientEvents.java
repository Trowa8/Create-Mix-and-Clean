package net.mcreator.createmixandclean.client;

import net.mcreator.createmixandclean.client.renderer.ElectrolyzerRenderer;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlockEntities;
import net.neoforged.neoforge.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.eventbus.api.SubscribeEvent;
import net.neoforged.neoforge.fml.common.Mod;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

@EventBusSubscriber(modid = CreateMixAndCleanMod.MODID,
                        bus = Mod.EventBusSubscriber.Bus.MOD,
                        value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                CreateMixAndCleanModBlockEntities.ELECTROLYZER.get(),
                ElectrolyzerRenderer::new);
    }
}