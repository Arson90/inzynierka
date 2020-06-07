package pl.arek.inzynierka.dto;

import lombok.Data;

@Data
public class ClientUpdateDTO {

    private String name;
    private String surname;
    private int phoneNumber;

}
