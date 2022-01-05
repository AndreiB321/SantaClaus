package strategy;

import dataset.Child;
import enums.Cities;

import java.util.*;

public class StrategySortByNicescoreCity implements StrategySort {

    Map<Cities, Double> cityScore = new HashMap<>();
    Map<Cities, Integer> cityCounterScore = new HashMap<>();

    @Override
    public List<Child> sortBy(List<Child> children) {
        List<Child> roundChildren = new ArrayList<>();
        for (Child child : children) {
            if (cityScore.containsKey(child.getCity())) {
                cityScore.put(child.getCity(), child.getAverageScore() + cityScore.get(child.getCity()));
                cityCounterScore.put(child.getCity(), 1 + cityCounterScore.get(child.getCity()));
            } else {
                cityScore.put(child.getCity(), child.getAverageScore());
                cityCounterScore.put(child.getCity(), 1);
            }
        }
        Map<Cities, Double> unsortedMap = new HashMap<>();
        for (Map.Entry<Cities, Double> entry : cityScore.entrySet()) {
            unsortedMap.put(entry.getKey(), entry.getValue() / cityCounterScore.get(entry.getKey()));
        }

        List<Map.Entry<Cities, Double>> list =
                new LinkedList<>(unsortedMap.entrySet());

        list.sort(((Comparator<Map.Entry<Cities, Double>>)
                (o1, o2) -> ((o1.getValue()).compareTo(o2.getValue()))).reversed());
        for (Map.Entry<Cities, Double> ent : list) {
            for (Child child : children) {
                if (child.getCity().equals(ent.getKey())) {
                    roundChildren.add(child);
                }
            }
        }
        return roundChildren;
    }
}
