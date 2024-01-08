package eu.pintergabor.philosophersstone.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.philosophersstone.mixin.ShapedRecipeAccessor;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

public class ExtraShapedRecipe extends ShapedRecipe {

	public ExtraShapedRecipe(String group, CraftingRecipeCategory category,
		RawShapedRecipe raw, ItemStack result) {
		super(group, category, raw, result);
	}

	@Override
	public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
		return super.matches(recipeInputInventory, world);
	}

	/**
	 * Special type
	 */
	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	/**
	 * Special type
	 */
	public static class Type implements RecipeType<ExtraShapedRecipe> {
		public static final ExtraShapedRecipe.Type INSTANCE = new Type();
		public static final String ID = "crafting_extra_shaped";

		private Type() {
		}
	}

	/**
	 * Extra serializer
	 */
	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	/**
	 * Extra serializer
	 * <p>
	 * which is almost the same as in {@link ShapedRecipe}.
	 */
	public static class Serializer implements RecipeSerializer<ExtraShapedRecipe> {
		public static final ExtraShapedRecipe.Serializer INSTANCE = new Serializer();
		public static final String ID = "crafting_extra_shaped";
		private static Codec<ExtraShapedRecipe> CODEC;

		private Serializer() {
			final RecordCodecBuilder<ExtraShapedRecipe, String> rcbGroup =
				Codecs.createStrictOptionalFieldCodec(Codec.STRING, "group", "")
					.forGetter((recipe) ->
						((ShapedRecipeAccessor) recipe).getGroup());
			final RecordCodecBuilder<ExtraShapedRecipe, CraftingRecipeCategory> rcbCategory =
				CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC)
					.forGetter((recipe) ->
						((ShapedRecipeAccessor) recipe).getCategory());
			final RecordCodecBuilder<ExtraShapedRecipe, RawShapedRecipe> rcbRaw =
				RawShapedRecipe.CODEC
					.forGetter((recipe) ->
						((ShapedRecipeAccessor) recipe).getRaw());
			final RecordCodecBuilder<ExtraShapedRecipe, ItemStack> rcbResult =
				ItemStack.RECIPE_RESULT_CODEC.fieldOf("result")
					.forGetter((recipe) ->
						((ShapedRecipeAccessor) recipe).getResult());
			CODEC = RecordCodecBuilder.create((instance) ->
				instance.group(rcbGroup, rcbCategory, rcbRaw, rcbResult)
					.apply(instance, ExtraShapedRecipe::new));
		}

		public Codec<ExtraShapedRecipe> codec() {
			return CODEC;
		}

		public ExtraShapedRecipe read(PacketByteBuf packetByteBuf) {
			String string = packetByteBuf.readString();
			CraftingRecipeCategory craftingRecipeCategory =
				packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
			RawShapedRecipe rawShapedRecipe =
				RawShapedRecipe.readFromBuf(packetByteBuf);
			ItemStack itemStack =
				packetByteBuf.readItemStack();
			return new ExtraShapedRecipe(string, craftingRecipeCategory, rawShapedRecipe, itemStack);
		}

		public void write(PacketByteBuf packetByteBuf, ExtraShapedRecipe recipe) {
			var r = (ShapedRecipeAccessor) recipe;
			packetByteBuf.writeString(r.getGroup());
			packetByteBuf.writeEnumConstant(r.getCategory());
			r.getRaw().writeToBuf(packetByteBuf);
			packetByteBuf.writeItemStack(r.getResult());
		}
	}


}
