package pl.arek.inzynierka.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="users_roles")
@NoArgsConstructor
public class UsersRoles {

    @Id
    private Roles role;
    //roles dostepne dla usera
    public UsersRoles(Roles role){

        this.role = role;
    }

}
