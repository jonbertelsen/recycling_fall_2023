package dat.dtos;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SimpleDTO
{
    private String registration;
    private String name;

    public SimpleDTO(String registration, String name)
    {
        this.registration = registration;
        this.name = name;
    }

}
