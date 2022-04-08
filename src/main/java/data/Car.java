package data;

public class Car {
    private String vin;
    private int year;
    private int mileage;

    @Override
    public String toString() {
        return "Car{" +
                "vin='" + vin + '\'' +
                ", year=" + year +
                ", mileage=" + mileage +
                '}';
    }

    public Car(String vin, int year, int mileage) {
        this.vin = vin;
        this.year = year;
        this.mileage = mileage;
    }


    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }



}