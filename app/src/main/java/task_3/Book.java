package task_3;

import java.util.Objects;

public class Book implements Publication {
    double price;
    double wordCount;

    Book(double price, double wordCount) {
        this.price = price;
        this.wordCount = wordCount;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getWordCount() {
        return wordCount;
    }

    @Override
    public String getType() {
        if (wordCount <= 1000) {
            return "Flash Fiction";
        } else if (wordCount <= 7500) {
            return "Short Story";
        } else {
            return "Novel";
        }
    }

    @Override
    public String toString() {
        return "Book{" +"Type"+this.getType()+
                "price=" + price +
                ", wordCount=" + wordCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Double.compare(book.price, price) == 0 && Double.compare(book.wordCount, wordCount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, wordCount);
    }
}