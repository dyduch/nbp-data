package com.company;

import java.io.IOException;
import java.util.ArrayList;


public interface IGoldAnswerBuilder {

    /**
     * This method prepares raw String from API and coverts it to parsable form.
     *
     * @param rawGoldRates - String containing whole JSON file downloaded from NBP API
     * @return - String prepared for parser to parse and use
     */
    String prepareGoldRates(String rawGoldRates);

    /**
     * This method prepares one single Gold Object
     *
     * @param singleGoldInfo - String containing one single information about gold
     * @return - Instance of class Gold
     * @throws IOException - if getting data from API goes wrong
     */
    Gold createSingleGoldObject(String singleGoldInfo) throws IOException;

    /**
     * This method creates Array List of Gold Objects
     *
     * @param fullGoldRates - String containing whole JSON file downloaded from NBP API and already parsed
     * @return - Array List of single Gold Objects
     * @throws IOException - if getting data from API goes wrong
     */
    ArrayList<Gold> createGoldRatesList(String fullGoldRates) throws IOException;

    /**
     * this method counts average price in Array List of Gold Objects
     *
     * @param goldPricesInfo - String containing whole JSON file downloaded from NBP API and already parsed
     * @return - average of fields 'price' from Array List
     */
    double countAverageGoldPrice(ArrayList<Gold> goldPricesInfo);

    /**
     * @param singleGoldInfo - Instance of class Gold
     * @return - gets field 'price' from Gold Object
     * @throws IOException - if getting data from API goes wrong
     */
    double getPriceOnDay(String singleGoldInfo) throws IOException;

    }
