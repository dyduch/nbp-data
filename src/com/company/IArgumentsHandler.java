package com.company;

import org.apache.commons.cli.*;
import java.io.IOException;


public interface IArgumentsHandler {

    /**
     * this method sets up options used in application
     *
     * @return - Instance of Options class containing all possible app options
     */
    Options setUpOptions();

    /**
     *
     * This method converts possible options and given arguments to an instance of CommandLine
     * @param options - possible options
     * @param args - arguments with which app is started
     * @return - instance of CommandLine
     * @throws ParseException - if parsing goes wrong
     */
    CommandLine parseOptions(Options options, String [] args) throws ParseException;

    /**
     * This method looks in CommandLine for options and executes them if has to
     *
     * @param cmd - instance of CommandLine
     * @throws IOException - v
     * @throws java.text.ParseException - if parsing goes wrong
     */
    void ParseCommandLine(CommandLine cmd) throws IOException, java.text.ParseException;

    /**
     * This method handles option P if it is in CommandLine
     *
     * @param cmd - instance of CommandLine
     * @throws IOException - if getting data from API goes wrong
     */

    void handleOptionP(CommandLine cmd) throws IOException;

    /**
     * This method handles option AV if it is in CommandLine
     *
     * @param cmd - instance of CommandLine
     * @throws IOException - if getting data from API goes wrong
     */

    void handleOptionAV(CommandLine cmd) throws IOException;

    /**
     * This method handles option BL if it is in CommandLine
     *
     * @param cmd - instance of CommandLine
     * @throws IOException - if getting data from API goes wrong
     */


    void handleOptionBL(CommandLine cmd) throws IOException;

    /**
     * This method handles option LP if it is in CommandLine
     *
     * @param cmd - instance of CommandLine
     * @throws IOException - if getting data from API goes wrong
     */

    void handleOptionLP(CommandLine cmd) throws IOException;

    /**
     * This method handles option AL if it is in CommandLine
     *
     * @param cmd - instance of CommandLine
     * @throws IOException - if getting data from API goes wrong
     */

    void handleOptionAL(CommandLine cmd) throws IOException;

    /**
     * This method handles option PSC if it is in CommandLine
     *
     * @param cmd - instance of CommandLine
     * @throws IOException - if getting data from API goes wrong
     */

    void handleOptionPSC(CommandLine cmd) throws IOException;

    /**
     * This method handles option G if it is in CommandLine
     *
     * @param cmd - instance of CommandLine
     * @throws IOException - if getting data from API goes wrong
     */

    void handleOptionG(CommandLine cmd) throws IOException, java.text.ParseException;

    /**
     *
     * This method gets whole json file from API NBP from its url
     *
     * @param urlString - url address of website we are connecting to
     * @return - whole json file in String
     * @throws IOException - if getting data from API goes wrong
     */
    String getJsonFromURL(String urlString) throws IOException;

    /**
     * This method checks if data given from API NBP is valid
     *
     * @param jsonFromUrl - whole json file in String
     * @return - whether or not returning data is valid
     */
    boolean checkCorrectness(String jsonFromUrl);

    /**
     * Prints tips on using the application
     */
    void printHelp();
}
