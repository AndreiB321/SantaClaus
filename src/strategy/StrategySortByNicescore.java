package strategy;

import dataset.Child;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StrategySortByNicescore implements StrategySort {
    @Override
    public List<Child> sortBy(List<Child> children) {
        List <Child> sortedList = new ArrayList<>();
        sortedList.addAll(children);
        sortedList.sort(new Comparator<Child>() {
            @Override
            public int compare(Child o1, Child o2) {
                if(o1.getAverageScore() ==  o2.getAverageScore())
                    return o1.getId() - o2.getId();
                return (int) (o2.getAverageScore() -  o1.getAverageScore());
            }
        });
        return sortedList;
    }
}
