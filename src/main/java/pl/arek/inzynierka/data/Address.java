package pl.arek.inzynierka.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int number;
    private int houseNumber;

    public Address(String name, int number, int houseNumber) {
        this.name = name;
        this.number = number;
        this.houseNumber = houseNumber;
    }

}
