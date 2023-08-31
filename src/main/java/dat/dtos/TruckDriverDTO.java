package dat.dtos;

import dat.model.Driver;
import dat.model.WasteTruck;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class TruckDriverDTO
{
    private int id;
    private String brand;
    private String registrationNumber;
    private List<String> drivers = new ArrayList<>();

    public TruckDriverDTO(WasteTruck wasteTruck)
    {
        this.id = wasteTruck.getId();
        this.brand = wasteTruck.getBrand();
        this.registrationNumber = wasteTruck.getRegistrationNumber();
        wasteTruck.getDrivers().forEach(driver -> drivers.add(driver.getName()));
    }

}
