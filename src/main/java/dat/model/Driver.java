package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Driver
{
    @Id()
    @Column(length = 255)
    private String id;
    @Temporal(TemporalType.DATE)
    private Date employmentDate;
    @Column(length = 255)
    private String name;
    private BigDecimal salary;
    @Column(length = 255)
    private String surname;
    @ManyToOne
    private WasteTruck wasteTruck;

    public Driver(String name, BigDecimal salary, String surname)
    {
        this.name = name;
        this.salary = salary;
        this.surname = surname;
    }

    @PrePersist
    private void generateId() throws Exception
    {
        LocalDate today = LocalDate.now();
        String dateOfEmployment = String.format("%02d%02d%02d",
                today.getDayOfMonth(),
                today.getMonthValue(),
                today.getYear() % 1000);
        String newId = generateIdString(dateOfEmployment, name, surname);
        if (validateDriverId(newId))
        {
            this.id = newId;
        } else {
            throw new Exception("Id not correct: " + newId);
        }
        this.employmentDate = new Date();
    }

    public Boolean validateDriverId(String driverId) {
        return driverId.matches("[0-9][0-9][0-9][0-9][0-9][0-9]-[A-Z][A-Z]-[0-9][0-9][0-9][A-Z]");
    }

    private String generateIdString(String date, String name, String surname)
    {
        Random rnd = new Random();
        int number = rnd.nextInt(100, 1000);

        return String.format("%s-%s%s-%03d%s",
                date,
                name.substring(0,1).toUpperCase(),
                surname.substring(0,1).toUpperCase(),
                number,
                surname.substring(surname.length() - 1).toUpperCase());
    }

    @Override
    public String toString()
    {
        return "Driver{" +
                "id='" + id + '\'' +
                ", employmentDate=" + employmentDate +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", surname='" + surname + '\'' +
                '}';
    }
}
