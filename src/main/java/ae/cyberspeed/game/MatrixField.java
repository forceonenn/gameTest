package ae.cyberspeed.game;

import ae.cyberspeed.config.GameConfig;
import ae.cyberspeed.config.subconfig.probabilitiesConfig.BonusSymbolsConfig;

import java.util.*;
import java.util.stream.Collectors;

public class MatrixField {

    /*
        it was better to read it from config,
        but there's no any information about it in description.

        I hope, it will have no effect on my chances
        on getting this job :)
     */
    private final double REDUCE_BONUS_SYMBOL_PROBABILITY = 10;
    private boolean haveBonusSymbol = false;

    LinkedList<LinkedList<String>> matrix = new LinkedList<>();
    public MatrixField (GameConfig config){

        int columns = config.getMatrixConfig().getColumns();
        int rows = config.getMatrixConfig().getRows();

        for(int i = 0; i < columns; i++){
            LinkedList<String> matrixLine = new LinkedList<>();
            for(int j = 0; j < rows; j++){
                matrixLine.add(generateSymbol(i,j,config));
            }
            matrix.add(matrixLine);
        }

    }

    public LinkedList<LinkedList<String>> getMatrix() {
        return this.matrix;
    }

    public LinkedHashSet<String> getFieldSymbolList() {
        LinkedHashSet<String> symbolsSet = new LinkedHashSet<>();
        this.matrix.forEach(symbolsSet::addAll);
        return symbolsSet;
    }


    private String generateSymbol(int column, int row, GameConfig config){
        /*
            no description of probability of getting a bonus symbol
            so let's do it this way for a while, why not
         */
        if(Math.random() < Math.random()/REDUCE_BONUS_SYMBOL_PROBABILITY && !haveBonusSymbol){
            haveBonusSymbol = true;
            return generateBonusSymbol(config.getProbabilitiesConfig().getBonusSymbolsConfig());
        }

        List<Map.Entry<String, Double>> sortedList = config.getProbabilitiesConfig().getStandardSymbolsConfig().getSortedSymbolProbabilitiesList(column,row);

        return getSymbolFromProbabilityList(sortedList);

    }



    private String generateBonusSymbol(BonusSymbolsConfig config) {

        List<Map.Entry<String, Double>> sortedList = config.getSortedSymbolProbabilitiesList();

        return getSymbolFromProbabilityList(sortedList);
    }

    private String getSymbolFromProbabilityList(List<Map.Entry<String, Double>> sortedList) {
        LinkedHashMap<String, Double> finalProbabilities = new LinkedHashMap<>();
        double lastProbability = 0d;
        double maxProbability = sortedList.get(sortedList.size()-1).getValue();
        finalProbabilities.put(sortedList.get(0).getKey(), sortedList.get(0).getValue() / maxProbability);

        for (Map.Entry<String, Double> stringDoubleEntry : sortedList) {
            lastProbability += stringDoubleEntry.getValue() / maxProbability;
            finalProbabilities.put(stringDoubleEntry.getKey(), lastProbability);
        }

        double random = Math.random() * lastProbability;

        return finalProbabilities
                .entrySet()
                .stream()
                .filter(a -> a.getValue() >= random)
                .findFirst()
                .get()
                .getKey();
    }


}
