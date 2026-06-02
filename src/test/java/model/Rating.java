package model;

import java.util.Objects;

public class Rating {
    private double rate;
    private int count;

    public Rating(int count, double rate) {
        this.count = count;
        this.rate = rate;
    }
    public Rating() {}
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Double.compare(rate, rating.rate) == 0 && count == rating.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, count);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rate=" + rate +
                ", count=" + count +
                '}';
    }
}
