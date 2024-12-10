package eu.pintergabor.philosophersstone.mixin;

import eu.pintergabor.philosophersstone.Global;
import eu.pintergabor.philosophersstone.item.ModItems;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.BlockStatesLoader;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    /**
     * Inject the 3D model of {@link ModItems#PHILOSPHER_STONE_ITEM} to make it available when needed
     */
    @Inject(method = "<init>", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V",
            ordinal = 1, shift = At.Shift.AFTER))
    private void addModel(
            BlockColors blockColors,
            Profiler profiler,
            Map<Identifier, JsonUnbakedModel> jsonUnbakedModels,
            Map<Identifier, List<BlockStatesLoader.SourceTrackedData>> blockStates,
            CallbackInfo ci) {
        loadItemModel(ModelIdentifier.ofInventoryVariant(Global.ModIdentifier("philosophers_stone_3d")));
    }
}
