package com.company;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CurrencyAnswerBuilder implements ICurrencyAnswerBuilder {

    @Override
    public ArrayList<Rate> createRatesList(String rawCurrencyExchangeRates) throws IOException {
        String [] elems = rawCurrencyExchangeRates.split(",\"rates\":");
        elems[0] += "}";
        elems[1] = elems[1].replace("]}", "]");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(elems[1],  new TypeReference<ArrayList<Rate>>(){});
    }

    @Override
    public ExchangeRate createSingleExchangeRateWORates(String rawCurrencyExchangeRates) throws IOException {
        String [] elems = rawCurrencyExchangeRates.split(",\"rates\":");
        elems[0] += "}";
        if(elems[0].startsWith("[")) elems[0] = elems[0].substring(1);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(elems[0], ExchangeRate.class);
    }

    @Override
    public ExchangeRate createSingleExchangeRateRates(String rawCurrencyExchangeRates) throws IOException {
        ExchangeRate exchangeRate = createSingleExchangeRateWORates(rawCurrencyExchangeRates);
        exchangeRate.setRates(createRatesList(rawCurrencyExchangeRates));
        return exchangeRate;
    }

    ArrayList<ExchangeRate> createExchangeRatesList(String rawCurrencyExchangeRates) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(rawCurrencyExchangeRates,  new TypeReference<ArrayList<ExchangeRate>>(){});
    }

    @Override
    public double getPriceOnDay(String rawCurrencyExchangeRates) throws IOException {
        ExchangeRate exchangeRate = createSingleExchangeRateRates(rawCurrencyExchangeRates);
        return exchangeRate.getRates().get(0).getMid();
    }


    @Override
    public Rate findLowestBid(ArrayList<Rate> rates){
        Rate lowestRate = rates.get(0);
        for(Rate rate : rates){
            if(rate.getBid()<lowestRate.getBid()) lowestRate = rate;
        }

        return lowestRate;
    }

    @Override
    public Rate findLowestMid(ArrayList<Rate> rates){
        Rate lowestRate = rates.get(0);
        for(Rate rate : rates){
            if(rate.getMid()<lowestRate.getMid()) lowestRate = rate;
        }
        return lowestRate;
    }

    @Override
    public Rate findHighestMid(ArrayList<Rate> rates){
        Rate highestRate = rates.get(0);
        for(Rate rate : rates){
            if(rate.getMid()>highestRate.getMid()) highestRate = rate;
        }
        return highestRate;
    }

    public Rate getRateWithBiggestAmplitude(ArrayList<ExchangeRate> exchangeRatesList) {
        HashMap<String, Rate> lowestPricedRates = getMapOfLowestPricedRates(exchangeRatesList);
        HashMap<String, Rate> highestPricedRates = getMapOfHighestPricedRates(exchangeRatesList);
        HashMap<String, Double> amplitudes = new HashMap<>();
        for(String code: highestPricedRates.keySet()) {
            amplitudes.put(code, highestPricedRates.get(code).getMid() - lowestPricedRates.get(code).getMid());
        }
        double biggestAmplitude = 0;
        String codeOfBiggestAmplitude = null;
        for(String code : amplitudes.keySet()) {
            if (amplitudes.get(code) > biggestAmplitude) {
                biggestAmplitude = amplitudes.get(code);
                codeOfBiggestAmplitude = code;
            }
        }

        Rate biggestAmplitudeRate = highestPricedRates.get(codeOfBiggestAmplitude);
        biggestAmplitudeRate.setMid(Math.round(biggestAmplitude * 100d)/100d);
        return biggestAmplitudeRate;
    }

    public HashMap<String, Rate> getMapOfLowestPricedRates(ArrayList<ExchangeRate> exchangeRatesList){

        HashMap<String, Rate> lowestPricedRates= new HashMap<>();
        for(ExchangeRate exchangeRate : exchangeRatesList){
            for(Rate rate : exchangeRate.getRates()){
                if(!lowestPricedRates.containsKey(rate.getCode())){
                    lowestPricedRates.put(rate.getCode(), rate);
                }
                else{
                    if(lowestPricedRates.get(rate.getCode()).getMid() > rate.getMid()){
                        lowestPricedRates.remove(rate.getCode());
                        lowestPricedRates.put(rate.getCode(), rate);
                    }
                }
            }
        }
        return lowestPricedRates;

    }

    public HashMap<String, Rate> getMapOfHighestPricedRates(ArrayList<ExchangeRate> exchangeRatesList){
        HashMap<String, Rate> highestPricedRates= new HashMap<>();
        for(ExchangeRate exchangeRate : exchangeRatesList){
            for(Rate rate : exchangeRate.getRates()){
                if(!highestPricedRates.containsKey(rate.getCode())){
                    highestPricedRates.put(rate.getCode(), rate);
                }
                else{
                    if(highestPricedRates.get(rate.getCode()).getMid() < rate.getMid()){
                        highestPricedRates.remove(rate.getCode());
                        highestPricedRates.put(rate.getCode(), rate);
                    }
                }
            }
        }
        return highestPricedRates;
    }

    public ArrayList<Rate> sortRatesByDiff(ArrayList<Rate> ratesList){
        ratesList = setMidAsDiff(ratesList);
        ratesList.sort(Collections.reverseOrder());
        return ratesList;

    }


    public ArrayList<Rate> setMidAsDiff(ArrayList<Rate> ratesList){
        for(Rate rate : ratesList){
            rate.setMid(Math.round((rate.getAsk() - rate.getBid()) * 10000d) / 10000d);
        }
        return ratesList;
    }




    public String getDataGraph(ArrayList<Rate> ratesList) throws ParseException {
        StringBuilder graph = new StringBuilder();
        int baseWeek = DateParser.getWeekFromDate(ratesList.get(0).getEffectiveDate()) - 1;
        int baseYear = DateParser.getYearFromDate(ratesList.get(0).getEffectiveDate());
        for(Rate rate : ratesList){
            if(baseYear < DateParser.getYearFromDate(rate.getEffectiveDate())){
                baseWeek = baseWeek - 52;
                baseYear = DateParser.getYearFromDate(rate.getEffectiveDate());
            }
            graph.append(String.format(" %-2s", DateParser.stringDateToWeekday(rate.getEffectiveDate())));
            graph.append(String.format(" %-2s", (DateParser.getWeekFromDate(rate.getEffectiveDate()) - baseWeek)));
            graph.append(" ");
            graph.append(rate.getEffectiveDate());
            graph.append(" ");
            graph.append(printSquares(rate.getMid()));
            graph.append(" ");
            graph.append(rate.getMid());
            graph.append("\n");

        }
        return graph.toString();
    }

    public String printSquares(double price){
        StringBuilder squares = new StringBuilder();
        double roundedPrice = price;
        while(roundedPrice<1){
            roundedPrice = roundedPrice*10;
        }
        roundedPrice = roundedPrice *10;
        roundedPrice = (int) roundedPrice;
        for(int i = 0; i< roundedPrice ; i++ ) squares.append("\u25A0");
        return squares.toString();
    }

}

