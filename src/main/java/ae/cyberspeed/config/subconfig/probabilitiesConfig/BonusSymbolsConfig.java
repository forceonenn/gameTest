package ae.cyberspeed.config.subconfig.probabilitiesConfig;

import java.util.*;
public class BonusSymbolsConfig {

    private HashMap<String, HashMap<String, Double>> bonus_symbols;

    public List<Map.Entry<String, Double>> getSortedSymbolProbabilitiesList(){

        return this.bonus_symbols
                .get("symbols")
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

    }

}
