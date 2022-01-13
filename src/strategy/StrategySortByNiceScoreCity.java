package strategy;

import dataset.Child;
import enums.Cities;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StrategySortByNiceScoreCity implements StrategySort {

    @Override
    public final List<Child> sortBy(final List<Child> children) {
       final Map<Cities, Double> cityScore = new HashMap<>();
       final Map<Cities, Integer> cityCounterScore = new HashMap<>();
       final Map<Cities, Double> unsortedMap = new HashMap<>();

        List<Child> roundChildren = new ArrayList<>();
        for (Child child : children) {
            if (cityScore.containsKey(child.getCity())) {
                cityScore.put(child.getCity(), child.getAverageScore()
                        + cityScore.get(child.getCity()));
                cityCounterScore.put(child.getCity(), 1 + cityCounterScore.get(child.getCity()));
            } else {
                cityScore.put(child.getCity(), child.getAverageScore());
                cityCounterScore.put(child.getCity(), 1);
            }
        }

        for (Map.Entry<Cities, Double> entry : cityScore.entrySet()) {
            unsortedMap.put(entry.getKey(), entry.getValue()
                    / cityCounterScore.get(entry.getKey()));
        }

        List<Map.Entry<Cities, Double>> list =
                new ArrayList<>(unsortedMap.entrySet());

        list.sort(((Comparator<Map.Entry<Cities, Double>>)
                (o1, o2) -> {
                    if (Objects.equals(o1.getValue(), (o2.getValue()))) {
                        return o1.getKey().toString().compareTo(o2.getKey().toString());
                    }
                    return Double.compare(o2.getValue(), (o1.getValue()));
                }));
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
