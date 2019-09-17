package cc.catalysts.kafka.livedemo.kafka.stream;

public class AvgTemperature {
    private int count;
    private double sum;

    public AvgTemperature() {
        this.count = 0;
        this.sum = 0;
    }

    AvgTemperature add(double temperature) {
        sum += temperature;
        count++;
        return this;
    }

    double average() {
        return sum / count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
