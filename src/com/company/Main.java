package com.company;


import org.apache.commons.cli.*;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {

        ArgumentsHandler ah = new ArgumentsHandler();
        Options options = ah.setUpOptions();
        CommandLine cmd = ah.parseOptions(options, args);
        if(cmd != null) ah.ParseCommandLine(cmd);

    }



}

