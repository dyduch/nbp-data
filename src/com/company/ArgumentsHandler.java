package com.company;


import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ArgumentsHandler implements IArgumentsHandler {


    @Override
    public Options setUpOptions(){
        Options options = new Options();
        options.addOption("help", false, "Prints instructions how to use this app.");
        options.addOption("av", false, "Average price of gold for some time.");
        options.addOption("p",false,"Print prices of currency and gold on a specified date.");
        options.addOption("al", false, "Print name and code of a currency with highest amplitude between dates");
        options.addOption("bl", false, "Print name of currency with lowest bid on a specified date");
        options.addOption("psc", false, "Print currencies sorted by difference between ask and bid on a specified date");
        options.addOption("lp", false, "Prints a date, when specified currency was at lowest price");
        options.addOption("g", false, "Prints a graph of currency price changing.");
        options.addOption("d", true, "Specified date.");
        options.addOption("c", true, "Currency code.");
        options.addOption("s", true, "Starting date.");
        options.addOption("e", true, "Ending date.");
        return options;
    }

    @Override
    public CommandLine parseOptions(Options options, String [] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        }
        catch(UnrecognizedOptionException e){
            System.out.println("You passed an option that doesn't exist!");
            return null;
        }
        return cmd;
    }

    @Override
    public void ParseCommandLine(CommandLine cmd) throws IOException, java.text.ParseException {

        if(cmd.getOptions().length == 0 || cmd.hasOption("help")) printHelp();
        if(cmd.hasOption("p")) handleOptionP(cmd);
        if(cmd.hasOption("av")) handleOptionAV(cmd);
        if(cmd.hasOption("bl")) handleOptionBL(cmd);
        if(cmd.hasOption("lp")) handleOptionLP(cmd);
        if(cmd.hasOption("al")) handleOptionAL(cmd);
        if(cmd.hasOption("psc")) handleOptionPSC(cmd);
        if(cmd.hasOption("g")) handleOptionG(cmd);
    }

    @Override
    public void handleOptionP(CommandLine cmd) throws IOException {
        String code;
        String date;
        if(cmd.hasOption("d")) {
            date = cmd.getOptionValue("d");
        }
        else throw new IllegalArgumentException("Option p requires specified date");
        if(cmd.hasOption("c")){
            code = cmd.getOptionValue("c");
        }
        else throw new IllegalArgumentException("Option p requires specified currency code");


        String currencyUrl = "http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + date + "/?format=json";
        String goldUrl = "http://api.nbp.pl/api/cenyzlota/" + date + "/?format=json";

        CurrencyAnswerBuilder cab = new CurrencyAnswerBuilder();
        GoldAnswerBuilder gab = new GoldAnswerBuilder();


        if(getJsonFromURL(currencyUrl) == null) return;
        String currencyJsonString = getJsonFromURL(currencyUrl);
        if(checkCorrectness(currencyJsonString)) return;
        System.out.println("Price mid of currency " + code + " on a date: " + date + " is: " + cab.getPriceOnDay(currencyJsonString) + "\n");

        if(getJsonFromURL(goldUrl) == null) return;
        String goldJsonString = getJsonFromURL(goldUrl);
        if(checkCorrectness(goldJsonString)) return;
        System.out.println("Price of gold on a date: " + date + " is: " + gab.getPriceOnDay(goldJsonString) + "\n");

    }

    @Override
    public void handleOptionAV(CommandLine cmd) throws IOException {
        String startingDate;
        String endingDate;

        if(cmd.hasOption("s")) {
            startingDate = cmd.getOptionValue("s");
        }
        else throw new IllegalArgumentException("Option av requires specified starting date");
        if(cmd.hasOption("e")){
            endingDate = cmd.getOptionValue("e");
        }
        else throw new IllegalArgumentException("Option av requires specified ending date");

        String goldUrl = "http://api.nbp.pl/api/cenyzlota/" + startingDate + "/" + endingDate + "/?format=json";
        if(getJsonFromURL(goldUrl) == null) return;
        String goldJsonString = getJsonFromURL(goldUrl);
        if(checkCorrectness(goldJsonString)) return;
        GoldAnswerBuilder gab = new GoldAnswerBuilder();
        System.out.println("Average gold price between " + startingDate +
                " and " + endingDate + " equals: " +  gab.countAverageGoldPrice(gab.createGoldRatesList(goldJsonString)) +"\n");

    }

    @Override
    public void handleOptionAL(CommandLine cmd) throws IOException {
        String startingDate;
        String endingDate;

        if(cmd.hasOption("s")) {
            startingDate = cmd.getOptionValue("s");
        }
        else throw new IllegalArgumentException("Option av reluires specified starting date");
        if(cmd.hasOption("e")){
            endingDate = cmd.getOptionValue("e");
        }
        else throw new IllegalArgumentException("Option al requires specified ending date");

        String currencyUrl = "http://api.nbp.pl/api/exchangerates/tables/a/" + startingDate + "/" + endingDate + "/?format=json";
        if(getJsonFromURL(currencyUrl) == null) return;
        String currencyJsonString = getJsonFromURL(currencyUrl);
        if(checkCorrectness(currencyJsonString)) return;
        CurrencyAnswerBuilder cab = new CurrencyAnswerBuilder();
        ArrayList<ExchangeRate> exchangeRatesList = cab.createExchangeRatesList(currencyJsonString);
        Rate bigAmplitudeRate = cab.getRateWithBiggestAmplitude(exchangeRatesList);
        System.out.println("Currency with biggest amplitude between: " + startingDate +
                " and " + endingDate + " is: " + bigAmplitudeRate.getCurrency()
                + "\ncode: " + bigAmplitudeRate.getCode()
                + "\namplitude = " + bigAmplitudeRate.getMid() + "\n");
    }

    @Override
    public void handleOptionBL(CommandLine cmd) throws IOException {
        String date;
        if(cmd.hasOption("d")) {
            date = cmd.getOptionValue("d");
        }
        else throw new IllegalArgumentException("Option bl requires specified date");

        String currencyUrl = "http://api.nbp.pl/api/exchangerates/tables/c/" + date + "/?format=json";

        if(getJsonFromURL(currencyUrl) == null) return;
        String currencyJsonString = getJsonFromURL(currencyUrl);
        if(checkCorrectness(currencyJsonString)) return;
        CurrencyAnswerBuilder cab = new CurrencyAnswerBuilder();

        ExchangeRate exchangeRate = cab.createSingleExchangeRateRates(currencyJsonString);
        Rate rate = cab.findLowestBid(exchangeRate.getRates());

        System.out.println("Lowest bid on a date: " + date + " is: " + rate.getBid() + "; \ncode: " + rate.getCode() + "\ncurrency: " + rate.getCurrency() + "\n");
    }

    @Override
    public void handleOptionLP(CommandLine cmd) throws IOException {
        String code;
        String startingDate;
        String endingDate;

        if(cmd.hasOption("c")){
            code = cmd.getOptionValue("c");
        }
        else throw new IllegalArgumentException("Option lp requires specified currency code");

        if(cmd.hasOption("s")) {
            startingDate = cmd.getOptionValue("s");
        }
        else throw new IllegalArgumentException("Option lp requires specified starting date");

        if(cmd.hasOption("e")){
            endingDate = cmd.getOptionValue("e");
        }
        else throw new IllegalArgumentException("Option lp requires specified ending date");

        String currencyUrl = "http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + startingDate + "/" + endingDate + "/?format=json";
        if(getJsonFromURL(currencyUrl) == null) return;
        String currencyJsonString = getJsonFromURL(currencyUrl);
        if(checkCorrectness(currencyJsonString)) return;
        CurrencyAnswerBuilder cab = new CurrencyAnswerBuilder();
        ExchangeRate exchangeRate = cab.createSingleExchangeRateRates(currencyJsonString);
        Rate rateLowest = cab.findLowestMid(exchangeRate.getRates());
        Rate rateHighest = cab.findHighestMid(exchangeRate.getRates());

        System.out.println("Lowest mid price for the currency: " + exchangeRate.getCurrency() + "\ncode: " +
                exchangeRate.getCode() + "\nis on date: " + rateLowest.getEffectiveDate() +
                "\nit's value is: " + rateLowest.getMid() + "\n");

        System.out.println("\nHighest mid price for the currency: " + exchangeRate.getCurrency() + "\ncode: " +
                exchangeRate.getCode() + "\nis on date: " + rateHighest.getEffectiveDate() +
                "\nit's value is: " + rateHighest.getMid() + "\n");

    }

    @Override
    public void handleOptionPSC(CommandLine cmd) throws IOException {
        String date;
        if(cmd.hasOption("d")) {
            date = cmd.getOptionValue("d");
        }
        else throw new IllegalArgumentException("Option psc requires specified date");

        String currencyUrl = "http://api.nbp.pl/api/exchangerates/tables/c/" + date + "/?format=json";
        if(getJsonFromURL(currencyUrl) == null) return;
        String currencyJsonString = getJsonFromURL(currencyUrl);
        if(checkCorrectness(currencyJsonString)) return;
        CurrencyAnswerBuilder cab = new CurrencyAnswerBuilder();
        ExchangeRate exchangeRate = cab.createSingleExchangeRateRates(currencyJsonString);
        ArrayList<Rate> ratesList = cab.sortRatesByDiff(exchangeRate.getRates());
        System.out.println("Date: " + date + "\nSorted currencies: \n");
        for(Rate rate : ratesList){
            System.out.println("Currency: " + rate.getCurrency()
                    + "\nCode: " + rate.getCode()
                    + "\nDifference between ask and bid: " + rate.getMid() + "\n");
        }

    }

    @Override
    public void handleOptionG(CommandLine cmd) throws IOException, java.text.ParseException {

        String code;
        String startingDate;
        String endingDate;

        if(cmd.hasOption("c")){
            code = cmd.getOptionValue("c");
        }
        else throw new IllegalArgumentException("Option g requires specified currency code");

        if(cmd.hasOption("s")) {
            startingDate = cmd.getOptionValue("s");
        }
        else throw new IllegalArgumentException("Option g requires specified starting date");

        if(cmd.hasOption("e")){
            endingDate = cmd.getOptionValue("e");
        }
        else throw new IllegalArgumentException("Option g requires specified ending date");

        CurrencyAnswerBuilder cab = new CurrencyAnswerBuilder();
        startingDate = DateParser.getDateOfPreviousWeekday(startingDate, "Pn");
        endingDate = DateParser.getDateOfPreviousWeekday(endingDate, "Pt");

        String currencyUrl = "http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + startingDate + "/" + endingDate + "/?format=json";
        if(getJsonFromURL(currencyUrl) == null) return;
        String currencyJsonString = getJsonFromURL(currencyUrl);
        if(checkCorrectness(currencyJsonString)) return;
        ExchangeRate exchangeRate = cab.createSingleExchangeRateRates(currencyJsonString);
        System.out.println(cab.getDataGraph(exchangeRate.getRates()));
    }

    @Override
    public String getJsonFromURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        InputStream is;
        try {
            is = con.getInputStream();
        }
        catch (IOException e){
            System.out.println("Wrong input - perhaps you typed arguments wrongly or typed date of a day in the future.\n" +
                    "For options including starting and ending date, check if time interval is shorter than 93 days as it should be.\n" +
                    "If you typed everything right it means that there is no data for specified parameters. \n" + urlString);
            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder fullJSON = new StringBuilder();
        while ( (line = br.readLine()) != null) {
            fullJSON.append(line);
        }

        br.close();
        is.close();

        return fullJSON.toString();
    }

    public boolean checkCorrectness(String jsonFromUrl){
        if (jsonFromUrl.startsWith("<HTML>")){
            System.out.println("Problem with connection to NBP API occurred, try again.");
            return true;
        }
        else if(jsonFromUrl.startsWith("4")){
            System.out.println(jsonFromUrl);
            return true;
        }
        else return false;
    }

    @Override
    public void printHelp(){
        System.out.println("List of possible options: \n" +
                "-help      prints information on how to use this application;\n\n" +
                "argument options:\n" +
                "-d         used to specify a date for an option;\n" +
                "-s         used to specify a starting date for an option;\n" +
                "-e         used to specify a ending date for an option;\n" +
                "-c         used to specify a code of currency for an option;\n\n" +
                "application options:\n" +
                "-p         prints prices of a specified currency and gold on a specified date.\n" +
                "requires: -d -c\n" +
                "-av        prints average price of gold for a specified time interval.\n" +
                "requires: -s -e\n" +
                "-al        prints name and code of a currency with highest amplitude between two dates\n" +
                "requires: -s -e -c\n" +
                "-bl        prints name of two currencies: one with lowest and one with highest bid price on a specified date\n" +
                "requires: -d\n" +
                "-psc       prints currencies sorted by difference between ask price and bid price on a specified date\n" +
                "requires: -d\n" +
                "-lp        prints a date, when specified currency was at lowest mid price\n" +
                "requires: -s -e -c\n" +
                "-g         prints a graph showing weekly changes of mid price of specified currency\n" +
                "requires: -s -e -c\n\n" +
                "You can use more than one app option at once, but you can't use the same app option more than one time at once.\n" +
                "You can't use argument option more than once at one time.\n\n" +
                "Passing argument to app example:\n" +
                "-p -c usd -d 2017-08-08\n" +
                "will print prices of american dollar and gold on 8th August of 2017.\n\n" +
                "Date has to be passed in following format: yyyy-mm-dd\n\n" +
                "Starting app without arguments will print help message.");
    }
}
