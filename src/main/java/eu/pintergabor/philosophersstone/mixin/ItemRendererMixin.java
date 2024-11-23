package eu.pintergabor.philosophersstone.mixin;

import eu.pintergabor.philosophersstone.Global;
import eu.pintergabor.philosophersstone.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Final
    @Shadow
    private ItemModels models;

    /**
     * 3D render the {@link ModItems#PHILOSPHER_STONE_ITEM} if not in GUI
     *
     * @param value      2D model
     * @param stack      might be the {@link ModItems#PHILOSPHER_STONE_ITEM}
     * @param renderMode transfrom if not {@link ModelTransformationMode#GUI}
     * @return the 3D model, if transformed, or the original model, if not
     */
    @ModifyVariable(method =
            "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"), argsOnly = true)
    private BakedModel useModel(
            BakedModel value,
            ItemStack stack,
            ModelTransformationMode renderMode,
            boolean leftHanded,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay) {
        if (stack.isOf(ModItems.PHILOSPHER_STONE_ITEM) && renderMode != ModelTransformationMode.GUI) {
            return models.getModelManager()
                    .getModel(ModelIdentifier.ofInventoryVariant(Global.ModIdentifier("philosophers_stone_3d")));
        }
        return value;
    }
}
