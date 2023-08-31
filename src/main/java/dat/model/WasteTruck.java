package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class WasteTruck
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 255)
    private String brand;
    private int capacity;
    private boolean isAvailable;
    @Column(length = 255)
    private String registrationNumber;

    @OneToMany(mappedBy = "wasteTruck", fetch = FetchType.EAGER)
    private Set<Driver> drivers = new HashSet<>();

    public WasteTruck(String brand, int capacity, String registrationNumber)
    {
        this.brand = brand;
        this.capacity = capacity;
        this.registrationNumber = registrationNumber;
        this.setAvailable(true);
    }

    public void addDriver(Driver driver)
    {
        this.drivers.add(driver);
        if (driver != null)
        {
            driver.setWasteTruck(this);
        }
    }


}
