package eu.pintergabor.philosophersstone.mixin;

import java.util.List;
import java.util.Map;

import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.util.ModModelIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
	@Shadow
	protected abstract void addModel(ModelIdentifier modelId);

	/**
	 * Inject the 3D model of {@link ModItems#PHILOSPHER_STONE_ITEM} to make it available when needed
	 */
	@Inject(method = "<init>", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V",
		ordinal = 3, shift = At.Shift.AFTER))
	private void addModel(BlockColors blockColors, Profiler profiler,
		Map<Identifier, JsonUnbakedModel> jsonUnbakedModels,
		Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates,
		CallbackInfo ci) {
		addModel(new ModModelIdentifier("philosophers_stone_3d", "inventory"));
	}
}
