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
	public ExtraShapedRecipe(String group, CraftingRecipeCategory category, RawShapedRecipe raw, ItemStack result, boolean showNotification) {
		super(group, category, raw, result, showNotification);
	}

	public ExtraShapedRecipe(String group, CraftingRecipeCategory category, RawShapedRecipe raw, ItemStack result) {
		super(group, category, raw, result);
	}

	@Override
	public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
		return super.matches(recipeInputInventory, world);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	public static class Type implements RecipeType<ExtraShapedRecipe> {
		public static final ExtraShapedRecipe.Type INSTANCE = new Type();
		public static final String ID = "crafting_extra_shaped";

		private Type() {
		}
	}

	public static class Serializer implements RecipeSerializer<ExtraShapedRecipe> {
		public static final ExtraShapedRecipe.Serializer INSTANCE = new Serializer();
		public static final String ID = "crafting_extra_shaped";
		private static Codec<ExtraShapedRecipe> CODEC;

		private Serializer() {
			CODEC = RecordCodecBuilder.create((instance) -> {
				return instance.group(Codecs.createStrictOptionalFieldCodec(Codec.STRING, "group", "").forGetter((recipe) -> {
					return ((ShapedRecipeAccessor) recipe).getGroup();
				}), CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter((recipe) -> {
					return ((ShapedRecipeAccessor) recipe).getCategory();
				}), RawShapedRecipe.CODEC.forGetter((recipe) -> {
					return ((ShapedRecipeAccessor) recipe).getRaw();
				}), ItemStack.RECIPE_RESULT_CODEC.fieldOf("resultx").forGetter((recipe) -> {
					return ((ShapedRecipeAccessor) recipe).getResult();
				}), Codecs.createStrictOptionalFieldCodec(Codec.BOOL, "show_notification", true).forGetter((recipe) -> {
					return true;
				})).apply(instance, ExtraShapedRecipe::new);
			});
		}

		public Codec<ExtraShapedRecipe> codec() {
			return CODEC;
		}

		public ExtraShapedRecipe read(PacketByteBuf packetByteBuf) {
			String string = packetByteBuf.readString();
			CraftingRecipeCategory craftingRecipeCategory = (CraftingRecipeCategory) packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
			RawShapedRecipe rawShapedRecipe = RawShapedRecipe.readFromBuf(packetByteBuf);
			ItemStack itemStack = packetByteBuf.readItemStack();
			boolean bl = packetByteBuf.readBoolean();
			return new ExtraShapedRecipe(string, craftingRecipeCategory, rawShapedRecipe, itemStack, bl);
		}

		public void write(PacketByteBuf packetByteBuf, ExtraShapedRecipe shapedRecipe) {
			var parent = (ShapedRecipeAccessor) shapedRecipe;
			packetByteBuf.writeString(parent.getGroup());
			packetByteBuf.writeEnumConstant(parent.getCategory());
			parent.getRaw().writeToBuf(packetByteBuf);
			packetByteBuf.writeItemStack(parent.getResult());
			packetByteBuf.writeBoolean(true);
		}
	}


}
