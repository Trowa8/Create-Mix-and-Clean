package net.mcreator.createmixandclean.client;

import net.mcreator.createmixandclean.client.renderer.ElectrolyzerRenderer;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

@EventBusSubscriber(modid = CreateMixAndCleanMod.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                CreateMixAndCleanModBlockEntities.ELECTROLYZER.get(),
                ElectrolyzerRenderer::new);
    }
}