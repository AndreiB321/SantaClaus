package strategy;

import dataset.Child;

import java.util.List;

public interface StrategySort {
     /**
      * sort strategy by types
      * @param children
      * @return
      */
     List<Child> sortBy(List<Child> children);

}
