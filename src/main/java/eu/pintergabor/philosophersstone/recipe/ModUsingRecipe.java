package eu.pintergabor.philosophersstone.recipe;

import java.util.HashMap;

import com.mojang.serialization.MapCodec;
import eu.pintergabor.philosophersstone.item.ModItems;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;


/**
 * Recipes for items that can be crafted using the Philosopher's stone as an ingredient.
 */
public class ModUsingRecipe extends CustomRecipe {
	public static final String PATH = "using_recipe";
	public static ModUsingRecipe INSTANCE;
	public static MapCodec<ModUsingRecipe> MAP_CODEC;
	public static StreamCodec<RegistryFriendlyByteBuf, ModUsingRecipe> STREAM_CODEC;
	public static RecipeSerializer<ModUsingRecipe> SERIALIZER;

	/**
	 * Input -> Output map.
	 */
	private static final HashMap<Item, Result> RESULTMAP = new HashMap<>();
	static {
		// Gold generating.
		RESULTMAP.put(Items.RAW_COPPER_BLOCK, new Result(Items.GOLD_BLOCK, 2));
		RESULTMAP.put(Items.COPPER_INGOT, new Result(Items.GOLD_INGOT, 2));
		Items.COPPER_BLOCK.asList().forEach(item ->
			RESULTMAP.put(item, new Result(Items.GOLD_BLOCK, 2)));
		RESULTMAP.put(Items.RAW_IRON_BLOCK, new Result(Items.GOLD_BLOCK, 4));
		RESULTMAP.put(Items.IRON_BLOCK, new Result(Items.GOLD_BLOCK, 4));
		RESULTMAP.put(Items.IRON_INGOT, new Result(Items.GOLD_INGOT, 4));
		// Diamond generating.
		RESULTMAP.put(Items.COAL_BLOCK, new Result(Items.DIAMOND_BLOCK, 1));
		RESULTMAP.put(Items.COAL, new Result(Items.DIAMOND, 1));
		RESULTMAP.put(Items.CHARCOAL, new Result(Items.DIAMOND, 1));
		// Misc.
		RESULTMAP.put(Items.REDSTONE_TORCH, new Result(Items.REDSTONE_BLOCK, 1));
		RESULTMAP.put(Items.STICK, new Result(Items.OAK_LOG, 1));
	}

	/**
	 * Similar to {@link ItemStack}, but lighter.
	 */
	private record Result(Item item, int count) {
	}

	/**
	 * The crafted result.
	 */
	private ItemStack result;

	public ModUsingRecipe() {
		super();
	}

	/**
	 * Check if there is a {@link ModItems#PHILOSPHER_STONE_ITEM} at the center.
	 *
	 * @return the {@link ItemStack} of {@link ModItems#PHILOSPHER_STONE_ITEM} on success.
	 */
	private @Nullable ItemStack testCenter(@NonNull CraftingInput input) {
		final ItemStack center = input.getItem(4);
		return center.is(ModItems.PHILOSPHER_STONE_ITEM) ? center : null;
	}

	/**
	 * Check if there are 8 identical items around the center and try to craft the result.
	 *
	 * @return the crafted result.
	 */
	private @Nullable Result tryCraft(final @NonNull CraftingInput input) {
		final Item key = input.getItem(0).getItem();
		for (int i = 1; i < 9; i++) {
			if (i != 4) {
				ItemStack itemStack = input.getItem(i);
				if (!itemStack.is(key)) {
					return null;
				}
			}
		}
		return RESULTMAP.get(key);
	}

	/**
	 * Match recipe and craft {@link #result}.
	 * <p>
	 * All recipes consist of 8 identical input items, and the {@link ModItems#PHILOSPHER_STONE_ITEM} at the center.
	 *
	 * @return true on match.
	 */
	@Override
	public boolean matches(final @NonNull CraftingInput input, final @NonNull Level level) {
		final int w = input.width();
		final int h = input.height();
		if (w == 3 && h == 3 && testCenter(input) != null) {
			Result r = tryCraft(input);
			if (r != null) {
				result = new ItemStack(r.item, r.count);
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the already crafted {@link #result}.
	 */
	@Override
	public @NonNull ItemStack assemble(final @NonNull CraftingInput input) {
		return result;
	}

	/**
	 * Leave the damaged {@link ModItems#PHILOSPHER_STONE_ITEM} as remainder.
	 */
	@Override
	public @NonNull NonNullList<ItemStack> getRemainingItems(final @NonNull CraftingInput input) {
		NonNullList<ItemStack> remainder = NonNullList.withSize(input.size(), ItemStack.EMPTY);
		final int w = input.width();
		final int h = input.height();
		if (w == 3 && h == 3) {
			ItemStack center = testCenter(input);
			if (center != null) {
				final int damage = center.getDamageValue();
				if (damage < center.getMaxDamage()) {
					// Have to create a new one
					center = new ItemStack(ModItems.PHILOSPHER_STONE_ITEM);
					center.setDamageValue(damage + 1);
					remainder.set(4, center);
				}
			}
		}
		return remainder;
	}

	@Override
	public @NonNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return SERIALIZER;
	}

	public static void init() {
		INSTANCE = new ModUsingRecipe();
		MAP_CODEC = MapCodec.unit(INSTANCE);
		STREAM_CODEC = StreamCodec.unit(INSTANCE);
		SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
	}
}
