package eu.pintergabor.philosophersstone.recipe;

import eu.pintergabor.philosophersstone.item.ModItems;
import eu.pintergabor.philosophersstone.util.PotionUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class PhilosophersStoneRecipe extends SpecialCraftingRecipe {
    public static final String ID = "crafting_recipe";
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
    public boolean matches(CraftingRecipeInput input, World world) {
        final int w = input.getWidth();
        final int h = input.getHeight();
        return w == 3 && h == 3 &&
                input.getStackInSlot(1).isOf(Items.DIAMOND_BLOCK) &&
                input.getStackInSlot(7).isOf(Items.DIAMOND_BLOCK) &&
                input.getStackInSlot(3).isOf(Items.GOLD_BLOCK) &&
                input.getStackInSlot(5).isOf(Items.GOLD_BLOCK) &&
                PotionUtil.matchPotion(input.getStackInSlot(4), Potions.HEALING);
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return result;
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return SERIALIZER;
    }
}
