package ae.cyberspeed.config.subconfig;

import ae.cyberspeed.game.MatrixField;

import java.util.*;

public class WinCombinations {
    private HashMap<String, HashMap<String, Object>> win_combinations;


    public Double getReward(String winCombination){
        return (Double) this.win_combinations.get(winCombination).get("reward_multiplier");
    }

    public LinkedList<String> getWinComboListBySymbol(String symbol, Integer counts, MatrixField matrixField) {

        LinkedList<String> list = new LinkedList<>();

        win_combinations
                .forEach((winCombinationName, value) -> {
                    if(value.get("when").toString().equals("same_symbols")){
                        double dbl = Double.parseDouble(value.get("count").toString());
                        if(dbl == counts) {
                            list.add(winCombinationName);
                        }
                    }
                });

        win_combinations
                .forEach((winCombinationName, value) -> {
                    if (value.get("when").toString().equals("linear_symbols")) {

                        ArrayList<ArrayList<String>> arrayList = (ArrayList<ArrayList<String>>) value.get("covered_areas");

                        arrayList
                                .forEach(rows -> {
                                    final boolean[] flag = {true};
                                    rows.forEach(columns -> {
                                        if(flag[0]) {
                                            int x, y;
                                            x = Integer.parseInt(columns.substring(0, columns.indexOf(":")));
                                            y = Integer.parseInt(columns.substring(columns.indexOf(":") + 1));
                                            LinkedList<LinkedList<String>> matrix = matrixField.getMatrix();
                                            LinkedList<String> line = matrix.get(y);
                                            String matrixSymbol = line.get(x);
                                            if (!matrixSymbol.equals(symbol)) {
                                                flag[0] = false;
                                            }
                                        }
                                    });
                                    if(flag[0]) {
                                        list.add(winCombinationName);
                                    }
                                });
                    }

                });

        return list;
    }

}

