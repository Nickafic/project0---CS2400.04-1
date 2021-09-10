public class BagTest{

    public static void main(String[] args){
        ArrayBag<Integer> bag1 = new ArrayBag(9);
        ArrayBag<Integer> bag2 = new ArrayBag();
        ArrayBag<Integer> bag3 = bag1.Union(bag2);
        for(int i=0; i<11; i++){
            bag1.add(i);
        }
        bag1.print();

        
    }
}