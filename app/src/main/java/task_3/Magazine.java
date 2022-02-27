package task_3;

public class Magazine implements Publication {
    double price;
    double wordCount;

    Magazine(double price, double wordCount ) {
        this.price = price;
        this.wordCount=wordCount;

    }

    @Override
    public String getType() {
        return "Magazine";
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getWordCount() {
        return wordCount;
    }
}