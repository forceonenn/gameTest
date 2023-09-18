package ae.cyberspeed.config.subconfig.probabilitiesConfig;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StandardSymbolsConfig {
    private ArrayList<LinkedTreeMap> standard_symbols;

    public List<Map.Entry<String, Double>> getSortedSymbolProbabilitiesList(int column, int row){

        LinkedTreeMap<String, Double> o = (LinkedTreeMap<String, Double>) this.standard_symbols
                .stream()
                .filter(a -> Double.parseDouble(a.get("column").toString()) == column
                        && Double.parseDouble(a.get("row").toString()) == row)
                .findFirst()
                .get()
                .get("symbols");

        return o.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();
    }

}
