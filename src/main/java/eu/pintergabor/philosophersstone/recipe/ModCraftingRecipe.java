package eu.pintergabor.philosophersstone.recipe;

import java.util.List;

import com.mojang.serialization.MapCodec;
import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.util.ModUtil;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;


/**
 * Craftiong recipes of a new Philosopher's stone.
 */
public class ModCraftingRecipe extends CustomRecipe {
	public static final String PATH = "crafting_recipe";
	public static ModCraftingRecipe INSTANCE;
	public static MapCodec<ModCraftingRecipe> MAP_CODEC;
	public static StreamCodec<RegistryFriendlyByteBuf, ModCraftingRecipe> STREAM_CODEC;
	public static RecipeSerializer<ModCraftingRecipe> SERIALIZER;

	/**
	 * Used in {@link #matchesPotion(CraftingInput)}.
	 */
	private static final List<Holder<Potion>> potions = List.of(
		Potions.HEALING,
		Potions.STRONG_HEALING,
		Potions.REGENERATION,
		Potions.LONG_REGENERATION);

	public ModCraftingRecipe() {
		super();
	}

	/**
	 * There must be exactly two gold and two diamond blocks in cross shape.
	 * <pre>
	 *
	 *   G             D
	 * D + D   or    G + G
	 *   G             D
	 *
	 * G = Gold block
	 * D = Diamond block
	 * + = Potion
	 */
	private static boolean matchesGoldDiamond(
		final @NonNull CraftingInput input
	) {
		final ItemStack i1 = input.getItem(1);
		final ItemStack i3 = input.getItem(3);
		final ItemStack i5 = input.getItem(5);
		final ItemStack i7 = input.getItem(7);
		final Item G = Items.GOLD_BLOCK;
		final Item D = Items.DIAMOND_BLOCK;
		return (ModUtil.sameItem(i1, i7) && ModUtil.sameItem(i3, i5)) &&
			((i1.is(G) && i3.is(D)) || (i1.is(D) && i3.is(G)));
	}

	/**
	 * There must be one healing or regeneration potion in the middle.
	 * <p>
	 * See {@link #matchesGoldDiamond(CraftingInput)}.
	 */
	private static boolean matchesPotion(
		final @NonNull CraftingInput input
	) {
		ItemStack center = input.getItem(4);
		for (Holder<Potion> p : potions) {
			if (ModUtil.isPotion(center, p)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * There is only one recipe, and the result is always the {@link ModItems#PHILOSPHER_STONE_ITEM}.
	 */
	@Override
	public boolean matches(
		final @NonNull CraftingInput input,
		final @NonNull Level level
	) {
		final int w = input.width();
		final int h = input.height();
		return w == 3 && h == 3 &&
			matchesGoldDiamond(input) && matchesPotion(input);
	}

	/**
	 * @return the created Philosopher's stone.
	 */
	@Override
	public @NonNull ItemStack assemble(final @NonNull CraftingInput input) {
		return new ItemStack(ModItems.PHILOSPHER_STONE_ITEM.get());
	}

	@Override
	public @NonNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return SERIALIZER;
	}

	public static void init() {
		INSTANCE = new ModCraftingRecipe();
		MAP_CODEC = MapCodec.unit(INSTANCE);
		STREAM_CODEC = StreamCodec.unit(INSTANCE);
		SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
	}
}
