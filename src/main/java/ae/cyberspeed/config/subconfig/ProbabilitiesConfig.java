package ae.cyberspeed.config.subconfig;

import ae.cyberspeed.config.subconfig.probabilitiesConfig.BonusSymbolsConfig;
import ae.cyberspeed.config.subconfig.probabilitiesConfig.StandardSymbolsConfig;
import com.google.gson.Gson;

import java.util.HashMap;

public class ProbabilitiesConfig {

    private final StandardSymbolsConfig standardSymbolsConfig;
    private final BonusSymbolsConfig bonusSymbolsConfig;


    public ProbabilitiesConfig (String content) {

        Gson gson = new Gson();
        HashMap<String, Object> probabilities = gson.fromJson(content, HashMap.class);

        this.standardSymbolsConfig = gson.fromJson(probabilities.get("probabilities").toString(), StandardSymbolsConfig.class);
        this.bonusSymbolsConfig = gson.fromJson(probabilities.get("probabilities").toString(), BonusSymbolsConfig.class);
    }

    public StandardSymbolsConfig getStandardSymbolsConfig() {
        return standardSymbolsConfig;
    }

    public BonusSymbolsConfig getBonusSymbolsConfig() {
        return bonusSymbolsConfig;
    }
}
