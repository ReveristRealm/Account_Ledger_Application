
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Transactions {
private LocalDate date;
private LocalTime time;
private String description;
private String vendor;
private double price;

    public Transactions(LocalDate date, LocalTime time, String description, String vendor, double price){
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.price = price;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Transaction:" +
                " " + date +
                " | " + time +
                " | " + description +
                " | " + vendor +
                " | " + price ;
    }
}