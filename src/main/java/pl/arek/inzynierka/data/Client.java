package pl.arek.inzynierka.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "client")
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String surname;
    private int phoneNumber;
    @OneToMany
    private List<Address> addresses;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserInternal userInternal;

    public Client(String name, String surname, int phoneNumber, List<Address> addresses, UserInternal userInternal) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.addresses = addresses;
        this.userInternal = userInternal;
    }
}
