package eu.pintergabor.philosophersstone.mixin;

import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.util.ModModelIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin implements ItemRendererAccessor {
	@ModifyVariable(method =
		"renderItem(Lnet/minecraft/item/ItemStack;" +
			"Lnet/minecraft/client/render/model/json/ModelTransformationMode;" +
			"Z" +
			"Lnet/minecraft/client/util/math/MatrixStack;" +
			"Lnet/minecraft/client/render/VertexConsumerProvider;" +
			"I" +
			"I" +
			"Lnet/minecraft/client/render/model/BakedModel;)V",
		at = @At(value = "HEAD"), argsOnly = true)
	private BakedModel useModel(BakedModel value,
		ItemStack stack,
		ModelTransformationMode renderMode,
		boolean leftHanded,
		MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		int light,
		int overlay) {
		if (stack.isOf(ModItems.PHILOSPHER_STONE_ITEM) && renderMode != ModelTransformationMode.GUI) {
			return this.getModels().getModelManager()
				.getModel(new ModModelIdentifier("philosophers_stone_3d", "inventory"));
		}
		return value;
	}
}
