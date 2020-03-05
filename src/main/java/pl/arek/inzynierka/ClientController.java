package pl.arek.inzynierka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.arek.inzynierka.data.Client;
import pl.arek.inzynierka.data.User;
import pl.arek.inzynierka.repository.ClientRepository;
import pl.arek.inzynierka.repository.RoleRepository;
import pl.arek.inzynierka.repository.UserRepository;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Optional;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(method = RequestMethod.GET,value = "clients/{id}")
    public ResponseEntity getClient(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(client -> ResponseEntity.ok(client))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST,value = "clients")
    public ResponseEntity saveClient(@RequestBody Client client) {
         return ResponseEntity.ok(clientRepository.save(client));
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "clients/{id}" )
    public ResponseEntity deleteClient(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            clientRepository.delete(client.get());
            return ResponseEntity.ok(client.get());
        }

        return ResponseEntity.notFound().build();
    }
}
