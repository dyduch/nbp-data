package com.company;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface ICurrencyAnswerBuilder {

    /**
     * This method creates ArrayList of Rate from whole json file
     *
     * @param rawCurrencyExchangeRates - whole json file from API
     * @return ArrayList of Rates
     * @throws IOException - if getting data from API goes wrong
     */
    ArrayList<Rate> createRatesList(String rawCurrencyExchangeRates) throws IOException;

    /**
     * This method creates single ExchangeRate object but without Rates
     *
     * @param rawCurrencyExchangeRates - whole json file from API
     * @return - Single ExchangeRate instance without Rates
     * @throws IOException - if getting data from API goes wrong
     */
    ExchangeRate createSingleExchangeRateWORates(String rawCurrencyExchangeRates) throws IOException;

    /**
     * This method creates single ExchangeRate object
     *
     * @param rawCurrencyExchangeRates - whole json file from API
     * @return - Single ExchangeRate instance
     * @throws IOException - if getting data from API goes wrong
     */
    ExchangeRate createSingleExchangeRateRates(String rawCurrencyExchangeRates) throws IOException;

    /**
     * This method gets what is in field 'mid' from a whole json file from API
     *
     * @param rawCurrencyExchangeRates - whole json file from API
     * @return - 'mid' field
     * @throws IOException - if getting data from API goes wrong
     */
    double getPriceOnDay(String rawCurrencyExchangeRates) throws IOException;

    /**
     * This method finds lowest bid price in ArrayList of Rates
     *
     * @param rates - ArrayList of Rates
     * @return - lowest bid
     */
    Rate findLowestBid(ArrayList<Rate> rates);

    /**
     * This method finds lowest mid price in ArrayList of Rates
     *
     * @param rates - ArrayList of Rates
     * @return - lowest mid
     */
    Rate findLowestMid(ArrayList<Rate> rates);

    /**
     * This method finds highest mid price in ArrayList of Rates
     *
     * @param rates - ArrayList of Rates
     * @return - highest mid
     */
    Rate findHighestMid(ArrayList<Rate> rates);

    /**
     * This method returns rate with biggest amplitude (sets amplitude as mid)
     *
     * @param exchangeRatesList - ArrayList of Rates
     * @return - Rate with biggest amplitude of prices
     */
    Rate getRateWithBiggestAmplitude(ArrayList<ExchangeRate> exchangeRatesList);

    /**
     * This method gets Map of lowest prices of one currency
     * Key is a String code and Value is Rate
     *
     * @param exchangeRatesList - ArrayList of Rates
     * @return - HashMap
     */
    HashMap<String, Rate> getMapOfLowestPricedRates(ArrayList<ExchangeRate> exchangeRatesList);

    /**
     * This method gets Map of highest prices of one currency
     * Key is a String code and Value is Rate
     *
     * @param exchangeRatesList - ArrayList of Rates
     * @return - HashMap
     */
    HashMap<String, Rate> getMapOfHighestPricedRates(ArrayList<ExchangeRate> exchangeRatesList);

    /**
     * This method sorts rates by difference between bid and ask
     *
     * @param ratesList - ArrayList of Rates
     * @return ArrayList of sorted Rates
     */
    ArrayList<Rate> sortRatesByDiff(ArrayList<Rate> ratesList);

    /**
     * This method sets the difference in bid and ask as mid in each Rate
     *
     * @param ratesList - ArrayList of Rates
     * @return ArrayList of Rates
     */
    ArrayList<Rate> setMidAsDiff(ArrayList<Rate> ratesList);

    String getDataGraph(ArrayList<Rate> ratesList) throws ParseException;

    /**
     * This method prints equivalent number of squares for a price of Rate
     *
     * @param price - mid price in Rate
     * @return - given number of filled squares
     */
    String printSquares(double price);

}
