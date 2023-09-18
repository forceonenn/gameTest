package ae.cyberspeed.game;

import ae.cyberspeed.config.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GameRunner extends Thread{

    public GameRunner(String file, Double bettingAmount){

        Path filePath = Path.of(file);
        String content;
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(String.format("File %s not found", file));
        }

        GameConfig gameConfig;
        try {
            gameConfig = new GameConfig(content, bettingAmount);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Impossible to deserialize %s file", file));
        }


        MatrixField matrixField = new MatrixField(gameConfig);

        String gameResultJson = new GameCalculator().getGameCalculator(matrixField, gameConfig);

        System.out.println(gameResultJson);

    }

}
