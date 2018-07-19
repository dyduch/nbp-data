package com.company;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GoldAnswerBuilderTest {


    @Test
    public void prepareGoldRates() {

        GoldAnswerBuilder ab = new GoldAnswerBuilder();

        String goldRates = "[{\"data\":\"2017-12-19+\",\"cena\":144.85}]";
        String goldRatesCh = "[{\"date\":\"2017-12-19+\",\"price\":144.85}]";

        assertEquals(ab.prepareGoldRates(goldRates), goldRatesCh);

    }

    @Test
    public void createGoldRatesList() {


    }

    @Test
    public void countAverageGoldPrice() {

        ArrayList<Gold> goldPricesInfo = new ArrayList<>();
        goldPricesInfo.add(new Gold("2017-12-19", 140.65));
        goldPricesInfo.add(new Gold("2017-12-20", 141.15));
        goldPricesInfo.add(new Gold("2017-12-21", 154.23));
        goldPricesInfo.add(new Gold("2017-12-22", 123.43));
        goldPricesInfo.add(new Gold("2017-12-23", 124.54));
        goldPricesInfo.add(new Gold("2017-12-24", 112.54));
        goldPricesInfo.add(new Gold("2017-12-25", 123.54));
        goldPricesInfo.add(new Gold("2017-12-26", 145.75));
        goldPricesInfo.add(new Gold("2017-12-27", 148.77));

        GoldAnswerBuilder ab = new GoldAnswerBuilder();
        double avg = ab.countAverageGoldPrice(goldPricesInfo);
        assertEquals(avg, 134.96, 0);


    }

    @Test
    public void createSingleGoldObject() {
    }

    @Test
    public void getPriceOnDay() throws IOException {
        GoldAnswerBuilder gab = new GoldAnswerBuilder();
        assertEquals(165.83, gab.getPriceOnDay("[{\"data\":\"2013-01-02\",\"cena\":165.83}]"), 0);
        assertEquals(143.83, gab.getPriceOnDay("[{\"data\":\"2013-01-02\",\"cena\":143.83}]"), 0);
        assertEquals(115.83, gab.getPriceOnDay("[{\"data\":\"2013-01-02\",\"cena\":115.83}]"), 0);
        assertEquals(117.83, gab.getPriceOnDay("[{\"data\":\"2013-01-02\",\"cena\":117.83}]"), 0);
        assertEquals(165.54, gab.getPriceOnDay("[{\"data\":\"2013-01-02\",\"cena\":165.54}]"), 0);


    }
}
