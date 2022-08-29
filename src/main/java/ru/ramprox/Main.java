package ru.ramprox;

import ru.ramprox.symbolStatistics.NumberGenerator;
import ru.ramprox.symbolStatistics.SymbolStatistics;
import ru.ramprox.symbolStatistics.SymbolStatisticsApp;
import ru.ramprox.symbolStatistics.TextFromNetReceiver;

import java.net.http.HttpClient;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        NumberGenerator numberGenerator = new NumberGenerator(new Random());
        HttpClient httpClient = HttpClient.newBuilder().build();
        TextFromNetReceiver textReceiver = new TextFromNetReceiver(httpClient);
        SymbolStatistics symbolStatistics = new SymbolStatistics();
        SymbolStatisticsApp symbolStatisticsApp = new SymbolStatisticsApp(numberGenerator, textReceiver, symbolStatistics);
        symbolStatisticsApp.run();
    }

}
