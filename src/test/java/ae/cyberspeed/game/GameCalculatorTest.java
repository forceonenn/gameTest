package ae.cyberspeed.game;

import ae.cyberspeed.config.GameConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class GameCalculatorTest {

    @Test
    void getSameSymbolMap() {

        GameCalculator gameCalculator = new GameCalculator();
        LinkedList<LinkedList<String>> matrixField = new LinkedList<>();
        LinkedList<String> matrixLine1 = new LinkedList<>();
        LinkedList<String> matrixLine2 = new LinkedList<>();
        LinkedList<String> matrixLine3 = new LinkedList<>();

        matrixLine1.add("A");
        matrixLine1.add("D");
        matrixLine1.add("C");

        matrixLine2.add("D");
        matrixLine2.add("A");
        matrixLine2.add("D");

        matrixLine3.add("10x");
        matrixLine3.add("D");
        matrixLine3.add("A");

        matrixField.add(matrixLine1);
        matrixField.add(matrixLine2);
        matrixField.add(matrixLine3);

        LinkedHashSet<String> symbolSet = new LinkedHashSet<>();
        symbolSet.add("A");
        symbolSet.add("B");
        symbolSet.add("C");
        symbolSet.add("D");
        symbolSet.add("E");

        TreeMap<String, Integer> sameSymbolMap = gameCalculator.getSameSymbolMap(matrixField, symbolSet);

        Integer a = sameSymbolMap.get("A");
        Integer b = sameSymbolMap.get("B");
        Integer c = sameSymbolMap.get("C");
        Integer d = sameSymbolMap.get("D");
        Integer e = sameSymbolMap.get("E");
        Integer x10 = sameSymbolMap.get("10x");

        assertEquals(3, a);
        assertNull(b);
        assertEquals(1, c);
        assertEquals(4, d);
        assertNull(e);
        assertNull(x10);
    }

    @Test
    void getReward(){

        String file = "configTest.json";
        Path testFile = Path.of(file);
        String content;
        try {
            content = Files.readString(testFile);
        } catch (IOException e) {
            throw new RuntimeException(String.format("File %s not found", file));
        }

        Double bettingAmount = 100d;
        Double symbolMultiplier = 2d;

        LinkedList<String> winCombination = new LinkedList<>();

        GameConfig gameConfig = new GameConfig(content, bettingAmount);

        winCombination.add("same_symbol_5_times");
        winCombination.add("same_symbols_diagonally_left_to_right");

        Double reward = new GameCalculator().getReward(symbolMultiplier, winCombination, gameConfig);

        Double resultAmount = symbolMultiplier * 2d * 5d;

        assertEquals(resultAmount, reward);
    }

}