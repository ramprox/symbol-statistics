package ru.ramprox.symbolStatistics;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.joining;

public class SymbolStatisticsApp {

    private final NumberGenerator numberGenerator;

    private final TextFromNetReceiver textFromNetReceiver;

    private final SymbolStatistics symbolStatistics;

    private static final String upperBound = Integer.toString(Integer.MAX_VALUE);

    public SymbolStatisticsApp(NumberGenerator numberGenerator,
                               TextFromNetReceiver textFromNetReceiver,
                               SymbolStatistics symbolStatistics) {
        this.numberGenerator = numberGenerator;
        this.textFromNetReceiver = textFromNetReceiver;
        this.symbolStatistics = symbolStatistics;
    }

    public void run() {
        try {
            process();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void process() {
        String number = numberGenerator.generate(upperBound);
        String text = textFromNetReceiver.receive(number);
        System.out.println(text);
        Map<Integer, Long> symbolFrequencies = symbolStatistics.getSymbolsFrequencies(text);
        printSymbolFrequencies(symbolFrequencies);

        long sum = symbolFrequencies.values().stream().mapToLong(Long::longValue).sum();
        double average = (double) sum / symbolFrequencies.size();
        System.out.printf("Среднее значение частоты %d/%s = %f%n", sum, symbolFrequencies.size(), average);
        Set<Integer> symbolsNearestToAverageFrequency = symbolStatistics.getSymbolsNearestToAverageFrequency(symbolFrequencies, average);
        printNearestSymbols(symbolsNearestToAverageFrequency);
    }

    private void printSymbolFrequencies(Map<Integer, Long> symbolFrequencies) {
        System.out.println("Частоты:");
        symbolFrequencies.forEach((key, value) ->
                System.out.printf("%s - %d раза%n", Character.toString(key), value));
    }

    private void printNearestSymbols(Set<Integer> nearestSymbolCodes) {
        String nearestSymbols = nearestSymbolCodes.stream()
                .map(code -> String.format("%s(%d)", Character.toString(code), code))
                .collect(joining(", "));
        System.out.printf("Символы, которые соответствуют условию наиболее близкого значения частоты к среднему значению:%n %s%n", nearestSymbols);
    }
}
