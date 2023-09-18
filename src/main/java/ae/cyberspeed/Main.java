package ae.cyberspeed;

import ae.cyberspeed.game.GameRunner;
import org.apache.commons.cli.*;

public class Main {

    private static String configFile;
    private static Double bettingAmount;

    public static void main(String[] args) {

        if(parseConsoleArguments(args)) {
   /*
            String output = String.format("\nConfig is %s\nBetting amount is %s", configFile, bettingAmount.toString());
            System.out.println(output);
  */
        } else {
            System.out.println("Arguments parsing error, shutting down");
            System.exit(0);
        }


        new Thread(() -> {
            try {
                new GameRunner(configFile, bettingAmount);
            } catch (RuntimeException e) {
                System.out.println("ERROR: " + e.getMessage());
                System.out.println("Shutdown");
            }
        }).start();

    }

    private static boolean parseConsoleArguments(String[] args) {

        Options options = new Options();

        Option config = getOption("c", "config", "config.json");
        options.addOption(config);

        Option bettingAmountArgument = getOption("b", "betting-amount", "your_bet(example:100)");
        options.addOption(bettingAmountArgument);

        return areArgumentsValid(options, args);

    }

    private static Option getOption(String shortArgName, String longArgName, String description) {

        return Option.builder(shortArgName).longOpt(longArgName)
                .argName(longArgName)
                .hasArg()
                .required(true)
                .desc(description).build();

    }


    private static boolean areArgumentsValid(Options options, String[] args){

        CommandLine cmd;
        CommandLineParser parser = new BasicParser();
        HelpFormatter helper = new HelpFormatter();

        try{
            cmd = parser.parse(options, args);

            if (cmd.hasOption("c")) {
                String configFileArg = cmd.getOptionValue("config");
                if(configFileArg.length() > 0) {
                    configFile = configFileArg;
                } else {
                    throw new ParseException("config filename is empty");
                }
            }
            if (cmd.hasOption("b")) {
                String bettingAmountArgumentLine = cmd.getOptionValue("betting-amount");
                if(configFile.length() > 0) {
                    try {
                        bettingAmount = Double.parseDouble(bettingAmountArgumentLine);
                    } catch (NumberFormatException e) {
                        throw new ParseException(String.format("can't parse a value of betting-amount (%s)", bettingAmountArgumentLine));
                    }
                } else {
                    throw new ParseException("betting-amount is empty");
                }
            }
        } catch (ParseException e) {
            System.out.println("Argument error: " + e.getMessage());
            helper.printHelp("Usage:", options);
            return false;
        }

        return true;
    }

}