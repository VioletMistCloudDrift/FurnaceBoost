package neko.violetmist.furnaceboost.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface ExtendedAFBE {
    @Accessor
    void setCookingTotalTime(int totalTime);

    @Accessor
    void setLitTime(int litTime);

    @Accessor
    RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> getQuickCheck();

    @Accessor
    int getLitTime();
}
