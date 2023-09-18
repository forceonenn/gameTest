package ae.cyberspeed.config;

import ae.cyberspeed.config.subconfig.*;
import com.google.gson.Gson;

public class GameConfig {
    private final MatrixConfig matrixConfig;
    private final SymbolsConfig symbolsConfig;
    private final ProbabilitiesConfig probabilitiesConfig;
    private final WinCombinations winCombinations;
    private final Double bettingAmount;




    public GameConfig(String content, double bettingAmount) {


        this.bettingAmount = bettingAmount;

        Gson gson = new Gson();
        this.matrixConfig = gson.fromJson(content, MatrixConfig.class);
        this.symbolsConfig = gson.fromJson(content, SymbolsConfig.class);
        this.probabilitiesConfig = new ProbabilitiesConfig(content);
        this.winCombinations = gson.fromJson(content, WinCombinations.class);

    }

    public MatrixConfig getMatrixConfig() {
        return matrixConfig;
    }

    public SymbolsConfig getSymbolsConfig() {
        return symbolsConfig;
    }

    public ProbabilitiesConfig getProbabilitiesConfig() {
        return probabilitiesConfig;
    }

    public WinCombinations getWinCombinations() {
        return winCombinations;
    }

    public Double getBettingAmount(){
        return this.bettingAmount;
    }

}
