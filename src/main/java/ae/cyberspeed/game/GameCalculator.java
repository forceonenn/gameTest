package ae.cyberspeed.game;

import ae.cyberspeed.config.GameConfig;
import com.google.gson.Gson;

import java.util.*;

public class GameCalculator {

    LinkedHashSet<String> standardSymbolSet = new LinkedHashSet<>();
    LinkedList<String> bonusSymbolList = new LinkedList<>();
    LinkedList<String> extraBonusSymbolList = new LinkedList<>();
    LinkedList<String> multiplyBonusSymbolList = new LinkedList<>();


    public String getGameCalculator(MatrixField matrixField, GameConfig gameConfig){

        LinkedHashSet<String> symbolSet = matrixField.getFieldSymbolList();
        symbolSet.forEach(a -> addSymbolsToLists(a, gameConfig));
        TreeMap<String, Integer> sameSymbolsList = getSameSymbolMap(matrixField.getMatrix(), standardSymbolSet);

        LinkedHashMap<String, LinkedList<String>> appliedWinningCombinationList = new LinkedHashMap();
        sameSymbolsList
                .forEach((k,v) -> {
                    LinkedList<String> res = setSameSymbolListReward(k,v, gameConfig, matrixField);
                    if(res.size() > 0) {
                        appliedWinningCombinationList.put(k, res);
                    }
                });


        LinkedList<Double> rewardModifier = new LinkedList<>();
        if(appliedWinningCombinationList.size() > 0) {

            appliedWinningCombinationList
                    .forEach((a, b) -> {
                        if (b.size() > 0) {
                            rewardModifier.add(getReward(gameConfig.getSymbolsConfig().getRewardMultiplier(a), b, gameConfig));
                        }
                    });
        }
        double rewardSum = 0d;
        if(rewardModifier.size() > 0) {
            rewardSum = rewardModifier
                    .stream()
                    .reduce(Double::sum)
                    .get();
        }
        double finalReward = 0d;
        if(rewardSum > 0) {
            double bonusMultiplier = getBonusMultiplier(gameConfig);

            double extraReward = getExtraBonus(gameConfig);

            finalReward = rewardSum * bonusMultiplier * gameConfig.getBettingAmount() + extraReward;
        } else {
            finalReward = finalReward * gameConfig.getBettingAmount();
        }

        Gson gson = new Gson();


        String matrixJson = gson.toJson(matrixField.getMatrix());
        String appliedWinningCombinationListJson = gson.toJson(appliedWinningCombinationList);
        String bonusJson = gson.toJson(bonusSymbolList)
                .replace("{","")
                .replace("}","")
                .replace("[","")
                .replace("]","");

        String rewardOutput;
        int finalRewardInt = (int) Math.round(finalReward);
        if(finalRewardInt == finalReward) {
            rewardOutput = String.valueOf(finalRewardInt);
        } else {
            rewardOutput = String.valueOf(finalReward);
        }

        return getResultJson(matrixJson, rewardOutput, appliedWinningCombinationListJson, bonusJson) ;
    }



    private String getResultJson(String matrixJson, String reward, String appliedWinningCombinationListJson, String bonusJson) {
        String result = "{\n\"matrix\":" + matrixJson + ",\n";
        result += "\"reward\":" + reward;
        if(Double.parseDouble(reward) > 0) {
            result += ",\n\"applied_winning_combinations\":" + appliedWinningCombinationListJson;
        }
        if(bonusJson.length() > 0 && Double.parseDouble(reward) > 0) {
            result += ",\n\"applied_bonus_symbol\":" + bonusJson;
        }
        result += "\n}";
        return result;
    }

    private void addSymbolsToLists(String symbol, GameConfig gameConfig) {

        if(gameConfig.getSymbolsConfig().isBonusSymbol(symbol)){
            this.bonusSymbolList.add(symbol);
            if(gameConfig.getSymbolsConfig().isMultiplyBonusSymbol(symbol)){
                this.multiplyBonusSymbolList.add(symbol);
            }
            if(gameConfig.getSymbolsConfig().isExtraBonusSymbol(symbol)){
                extraBonusSymbolList.add(symbol);
            }

        } else {
            this.standardSymbolSet.add(symbol);
        }
    }

    public TreeMap<String, Integer> getSameSymbolMap(LinkedList<LinkedList<String>> matrixField, LinkedHashSet<String> standardSymbolSet){

        TreeMap<String, Integer> result = new TreeMap<>();

        matrixField
                .forEach(arr -> arr.forEach(el -> {
                    if(standardSymbolSet.contains(el)) {
                        Integer count = result.get(el);
                        if (count != null && count > 0) {
                            count++;
                            result.remove(el);
                            result.put(el, count);
                        } else {
                            result.put(el, 1);
                        }
                    }

                }));

        return result;
    }

    private LinkedList<String> setSameSymbolListReward(String symbol, Integer counts, GameConfig gameConfig, MatrixField matrixField) {

        return gameConfig.getWinCombinations().getWinComboListBySymbol(symbol, counts, matrixField);
    }

    public double getReward(Double symbolMultiplier, LinkedList<String> winCombination, GameConfig gameConfig){
        double reward;
        ArrayList<Double> list = new ArrayList<>();
        list.add(symbolMultiplier);
        winCombination
                .forEach(a -> list.add(gameConfig.getWinCombinations().getReward(a)));

        reward = list
                .stream()
                .reduce((a,b) -> a * b)
                .get();
        return reward;

    }

    private double getBonusMultiplier(GameConfig gameConfig) {

        if(multiplyBonusSymbolList.size() == 0) {
            return 1d;
        }

        ArrayList<Double> list = new ArrayList<>();
        multiplyBonusSymbolList
                .forEach(a -> list.add(gameConfig.getSymbolsConfig().getRewardMultiplier(a)));

        return list.stream().reduce((a,b) -> a*b).get();
    }

    private double getExtraBonus(GameConfig gameConfig) {

        if(extraBonusSymbolList.size() == 0){
            return 0d;
        }

        ArrayList<Double> list = new ArrayList<>();
        extraBonusSymbolList
                .forEach(a -> list.add(gameConfig.getSymbolsConfig().getExtraBonus(a)));

        return list.stream().reduce((a,b) -> a + b).get();
    }

}
