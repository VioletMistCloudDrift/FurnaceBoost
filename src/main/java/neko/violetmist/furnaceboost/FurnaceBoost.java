package neko.violetmist.furnaceboost;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.level.GameRules;

public class FurnaceBoost implements ModInitializer {
    private static GameRules.Key<DoubleRule> furnaceSpeed;

    @Override
    public void onInitialize() {
        furnaceSpeed = GameRuleRegistry.register("furnaceSpeed", GameRules.Category.UPDATES, GameRuleFactory.createDoubleRule(2.0, 0.0));
    }

    public static GameRules.Key<DoubleRule> getFurnaceSpeedRuleKey() {
        return furnaceSpeed;
    }
}
