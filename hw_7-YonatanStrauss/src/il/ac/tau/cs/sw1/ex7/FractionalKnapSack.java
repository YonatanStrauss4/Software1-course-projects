package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;

    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value;
        Item(double w, double v) {
            weight = w;
            value = v;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
    }

    @Override
    public Iterator<Item> selection() {
        Collections.sort(lst, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                double ratio1 = item1.value / item1.weight;
                double ratio2 = item2.value / item2.weight;
                if (ratio1 < ratio2)
                    return 1;
                else if (ratio1 > ratio2)
                    return -1;
                else
                    return 0;
            }
        });
        return lst.iterator();
    }
    
    public double sumOfWeights(List<Item> elements) {
        double sum = 0.0;
        for (Item item : elements) {
            sum += item.weight;
        }
        return sum;
    }
    
    
    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
    	if(this.capacity > sumOfWeights(candidates_lst))
    		return true;
        return false;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
    	double sumW = sumOfWeights(candidates_lst);
    	if(this.capacity - sumW >= element.weight)
    		candidates_lst.add(element);
    	else {
    		double leftovers = this.capacity - sumW;
    		double ratio = leftovers/element.weight;
    		element.value = ratio*element.value;
    		element.weight = leftovers;
    		candidates_lst.add(element);
    		
    	}

    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
    	double sumW = sumOfWeights(candidates_lst);
    	if(this.capacity - sumW == 0 || candidates_lst.size() == this.lst.size())
    		return true;
        return false;
    }
}
