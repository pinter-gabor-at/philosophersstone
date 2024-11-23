package eu.pintergabor.philosophersstone.recipe;

import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.util.PotionUtil;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class PhilosophersStoneRecipe extends SpecialCraftingRecipe {
    public static String ID = "crafting_special";
    public static final RecipeSerializer<PhilosophersStoneRecipe> SERIALIZER =
            new SpecialRecipeSerializer<>(PhilosophersStoneRecipe::new);
    /**
     * The one and only crafting result
     */
    private static final ItemStack result = new ItemStack(ModItems.PHILOSPHER_STONE_ITEM);

    public PhilosophersStoneRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    /**
     * There is only one recipe, and the result is always the {@link ModItems#PHILOSPHER_STONE_ITEM}
     */
    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        final int w = inventory.getWidth();
        final int h = inventory.getHeight();
        return w == 3 && h == 3 &&
                inventory.getStack(1).isOf(Items.DIAMOND_BLOCK) &&
                inventory.getStack(7).isOf(Items.DIAMOND_BLOCK) &&
                inventory.getStack(3).isOf(Items.GOLD_BLOCK) &&
                inventory.getStack(5).isOf(Items.GOLD_BLOCK) &&
                PotionUtil.matchPotion(inventory.getStack(4), Potions.HEALING);
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, RegistryWrapper.WrapperLookup lookup) {
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
