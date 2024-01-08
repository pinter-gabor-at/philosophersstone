package eu.pintergabor.philosophersstone.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import static eu.pintergabor.philosophersstone.recipe.RecipeUtils.filterEmptyIngredients;

public class GemEmpoweringRecipe implements Recipe<SimpleInventory> {
	private final ItemStack output;
	private final DefaultedList<Ingredient> recipeItems;

	public GemEmpoweringRecipe(ItemStack output, DefaultedList<Ingredient> recipeItems) {
		this.output = output;
		this.recipeItems = recipeItems;
	}

	@Override
	public boolean matches(SimpleInventory inventory, World world) {
		if (world.isClient()) {
			return false;
		}
		return recipeItems.get(0).test(inventory.getStack(0));
	}

	@Override
	public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
		return output.copy();
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResult(DynamicRegistryManager registryManager) {
		return output.copy();
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	@Override
	public DefaultedList<Ingredient> getIngredients() {
		return this.recipeItems;
	}

	public static class Type implements RecipeType<GemEmpoweringRecipe> {
		public static final Type INSTANCE = new Type();
		public static final String ID = "gem_empowering";

		private Type() {
		}
	}

	public static class Serializer implements RecipeSerializer<GemEmpoweringRecipe> {
		public static final Serializer INSTANCE = new Serializer();
		public static final String ID = "gem_empowering";
		private static Codec<GemEmpoweringRecipe> CODEC;

		private Serializer() {
			final RecordCodecBuilder<GemEmpoweringRecipe, ItemStack> rcbOutput =
				ItemStack.RECIPE_RESULT_CODEC.fieldOf("output")
					.forGetter((recipe) -> recipe.output);
			final RecordCodecBuilder<GemEmpoweringRecipe, DefaultedList<Ingredient>> rcbInput =
				Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").flatXmap(
						(ingredients) -> {
							Ingredient[] filtered = filterEmptyIngredients(ingredients);
							return 1 <= filtered.length && filtered.length <= 3 ?
								DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, filtered)) :
								DataResult.error(() ->
									"The number of ingredients for a gem empowering recipe " +
										"must be between 1 and 3, inclusive.");
						}, DataResult::success)
					.forGetter((recipe) -> recipe.recipeItems);
			CODEC = RecordCodecBuilder.create((instance) ->
				instance.group(rcbOutput, rcbInput).apply(instance, GemEmpoweringRecipe::new));
		}

		@Override
		public Codec<GemEmpoweringRecipe> codec() {
			return CODEC;
		}

		@Override
		public GemEmpoweringRecipe read(PacketByteBuf buf) {
			DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
			inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
			ItemStack output = buf.readItemStack();
			return new GemEmpoweringRecipe(output, inputs);
		}

		@Override
		public void write(PacketByteBuf buf, GemEmpoweringRecipe recipe) {
			buf.writeInt(recipe.getIngredients().size());
			for (Ingredient ing : recipe.getIngredients()) {
				ing.write(buf);
			}
			buf.writeItemStack(recipe.getResult(null));
		}
	}
}
