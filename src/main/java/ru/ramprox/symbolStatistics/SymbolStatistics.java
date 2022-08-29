package ru.ramprox.symbolStatistics;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class SymbolStatistics {

    public Map<Integer, Long> getSymbolsFrequencies(String text) {
        return text
                .replaceAll("[^a-zA-Z0-9]", "")
                .codePoints()
                .boxed()
                .collect(groupingBy(Function.identity(), TreeMap::new, counting()));
    }

    public Set<Integer> getSymbolsNearestToAverageFrequency(Map<Integer, Long> frequencyTable, double averageFrequency) {
        Set<Integer> result = new TreeSet<>();
        double min = Double.MAX_VALUE;
        for(Map.Entry<Integer, Long> entry : frequencyTable.entrySet()) {
            double currentMin = Math.abs(entry.getValue() - averageFrequency);
            if(Double.compare(currentMin, min) < 0) {
                result.clear();
                min = currentMin;
                result.add(entry.getKey());
            } else if(Double.compare(currentMin, min) == 0) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

}
