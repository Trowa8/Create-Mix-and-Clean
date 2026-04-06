package net.mcreator.createmixandclean.client;

import net.mcreator.createmixandclean.block.entity.ElectrolyzerBlockEntity;
import net.mcreator.createmixandclean.client.renderer.ElectrolyzerRenderer;
import net.mcreator.createmixandclean.init.CreateMixAndCleanModBlockEntities;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mcreator.createmixandclean.CreateMixAndCleanMod;

@Mod.EventBusSubscriber(modid = CreateMixAndCleanMod.MODID,
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