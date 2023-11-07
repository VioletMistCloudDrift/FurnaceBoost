package neko.violetmist.furnaceboost.mixin;

import neko.violetmist.furnaceboost.FurnaceBoost;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class MixinAbstractFurnaceBlockEntity {
    @Inject(method = "serverTick", at = @At("HEAD"))
    @SuppressWarnings("unchecked")
    private static void furnaceboost$inject$serverTick(Level level, BlockPos blockPos, BlockState blockState, AbstractFurnaceBlockEntity entity, CallbackInfo ci) {
        ((ExtendedAFBE) entity).getQuickCheck()
                .getRecipeFor(entity, level).ifPresent(r -> ((ExtendedAFBE) entity)
                        .setCookingTotalTime(Math.max((int) (level.getRecipeManager()
                                .getRecipeFor((RecipeType<AbstractCookingRecipe>) r.getType(), entity, level)
                                .map(AbstractCookingRecipe::getCookingTime).orElse(200) /
                                level.getGameRules().getRule(FurnaceBoost.getFurnaceSpeedRuleKey()).get()), 1)));
    }

    @Redirect(method = "serverTick", at = @At(value = "FIELD",
            target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;litTime:I", opcode = Opcodes.PUTFIELD))
    private static void furnaceboost$redirect$serverTick$1(AbstractFurnaceBlockEntity instance, int litTime) {
        if (instance.getLevel() == null) return;
        final ExtendedAFBE extended = (ExtendedAFBE) instance;
        extended.setLitTime(extended.getLitTime() <= 0 ? Math.max((int) (litTime / instance.getLevel().getGameRules()
                .getRule(FurnaceBoost.getFurnaceSpeedRuleKey()).get()), 1) : litTime);
    }

    @Redirect(method = "serverTick", at = @At(value = "FIELD",
            target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;cookingTotalTime:I", opcode = Opcodes.PUTFIELD))
    private static void furnaceboost$redirect$serverTick$2(AbstractFurnaceBlockEntity instance, int cookTime) {
        if (instance.getLevel() == null) return;
        ((ExtendedAFBE) instance).setCookingTotalTime(Math.max((int) (cookTime / instance.getLevel().getGameRules()
                .getRule(FurnaceBoost.getFurnaceSpeedRuleKey()).get()), 1));
    }
}
