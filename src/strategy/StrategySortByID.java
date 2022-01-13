package strategy;

import dataset.Child;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StrategySortByID implements StrategySort {

    @Override
    public final List<Child> sortBy(final List<Child> children) {
        List<Child> sortedList = new ArrayList<>();
        sortedList.addAll(children);
        sortedList.sort(new Comparator<Child>() {
            @Override
            public int compare(final Child o1, final Child o2) {
                return o1.getId() - o2.getId();
            }
        });
        return sortedList;
    }
}
