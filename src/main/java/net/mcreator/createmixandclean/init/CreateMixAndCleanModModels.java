/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createmixandclean.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.createmixandclean.client.model.Modelpeacock_tail;
import net.mcreator.createmixandclean.client.model.ModelOGI_Goggles;
import net.mcreator.createmixandclean.client.model.ModelGasMask;

@EventBusSubscriber(Dist.CLIENT)
public class CreateMixAndCleanModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelpeacock_tail.LAYER_LOCATION, Modelpeacock_tail::createBodyLayer);
		event.registerLayerDefinition(ModelGasMask.LAYER_LOCATION, ModelGasMask::createBodyLayer);
		event.registerLayerDefinition(ModelOGI_Goggles.LAYER_LOCATION, ModelOGI_Goggles::createBodyLayer);
	}
}