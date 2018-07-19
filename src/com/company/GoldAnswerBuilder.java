package com.company;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class GoldAnswerBuilder implements IGoldAnswerBuilder{

    @Override
    public String prepareGoldRates(String rawGoldRates){
        String fullGoldRates = rawGoldRates;
        fullGoldRates = fullGoldRates.replaceAll("data", "date");
        fullGoldRates = fullGoldRates.replaceAll("cena", "price");

        return fullGoldRates;
    }

    @Override
    public Gold createSingleGoldObject(String singleGoldInfo) throws IOException {
        singleGoldInfo = prepareGoldRates(singleGoldInfo);
        singleGoldInfo = singleGoldInfo.replaceAll("[\\[\\]]","");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(singleGoldInfo, Gold.class);
    }

    @Override
    public ArrayList<Gold> createGoldRatesList(String fullGoldRates) throws IOException {

        fullGoldRates = prepareGoldRates(fullGoldRates);
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(fullGoldRates,  new TypeReference<ArrayList<Gold>>(){});
    }

    @Override
    public double countAverageGoldPrice(ArrayList<Gold> goldPricesInfo){

        double avg = 0;
        for (Gold g : goldPricesInfo){
            avg += g.getPrice();
        }
        avg /= goldPricesInfo.size();
        avg = Math.round(avg * 100d) / 100d;
        return avg;
    }

    public double getPriceOnDay(String singleGoldInfo) throws IOException {
        Gold gold = createSingleGoldObject(singleGoldInfo);
        return gold.getPrice();
    }
}

