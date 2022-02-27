package task_3;

import java.util.function.BiConsumer;

public class Task3 {
    Book aBriefHistoryOfTime = new Book(99.89, 15780);
    Book foundation = new Book(50.4, 7500);
    Magazine time = new Magazine(10.2, 889);

   public void mainFun() {
        comparison(aBriefHistoryOfTime, foundation);
        buy(time);
       sum.accept(aBriefHistoryOfTime.getPrice(), time.getPrice());
    }

    void comparison(Book a, Book b) {
        System.out.println("Сравнение");
        System.out.println(a == b);
        System.out.println(a.equals(b));
    }

    void buy(Publication arg) {
        System.out.println("The purchase is complete. The purchase amount was €" + arg.getPrice());
    }
    BiConsumer<Double, Double> sum = (first, second) -> System.out.println( "Sum: " + (first + second));

}
