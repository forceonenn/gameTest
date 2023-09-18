package ae.cyberspeed.config.subconfig;

import java.util.*;

public class SymbolsConfig {

    private HashMap<String, HashMap<String, Object>> symbols;

    private HashMap<String, HashMap<String, Object>> getSymbols() {
        return symbols;
    }

    public boolean isBonusSymbol(String symbol) {
        return this.symbols
                .get(symbol)
                .get("type")
                .equals("bonus");
    }

    public boolean isMultiplyBonusSymbol(String symbol){
        return this.symbols
                .get(symbol)
                .get("impact")
                .equals("multiply_reward");
    }

    public boolean isExtraBonusSymbol(String symbol){
        return this.symbols
                .get(symbol)
                .get("impact")
                .equals("extra_bonus");
    }

    public Double getRewardMultiplier(String symbol) {
        return (Double) this.getSymbols().get(symbol).get("reward_multiplier");
    }

    public Double getExtraBonus(String symbol) {
        return (Double) this.getSymbols().get(symbol).get("extra");
    }
}
