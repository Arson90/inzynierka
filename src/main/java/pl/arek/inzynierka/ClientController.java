package pl.arek.inzynierka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.arek.inzynierka.data.Address;
import pl.arek.inzynierka.data.Client;
import pl.arek.inzynierka.data.UserInternal;
import pl.arek.inzynierka.dto.AddressDTO;
import pl.arek.inzynierka.dto.ClientDTO;
import pl.arek.inzynierka.dto.ClientUpdateDTO;
import pl.arek.inzynierka.repository.AddressRepository;
import pl.arek.inzynierka.repository.ClientRepository;
import pl.arek.inzynierka.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    //szukanie klienta po kluczu ID
    @RequestMapping(method = RequestMethod.GET,value = "clients/{id}")
    public ResponseEntity getClient(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(client -> ResponseEntity.ok(client))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //tworzenie klienta
    @RequestMapping(method = RequestMethod.POST,value = "clients")
    public ResponseEntity saveClient(@RequestBody ClientDTO clientDTO) {
        UserInternal userInternal = new UserInternal(clientDTO.getUsername(),encoder.encode(clientDTO.getPassword()));
        userRepository.save(userInternal);
        Client client = new Client(clientDTO.getName(),clientDTO.getSurname(),clientDTO.getPhoneNumber(),
                                    new ArrayList<>(),userInternal);
        return ResponseEntity.ok(clientRepository.save(client));
    }

    //modyfikacja klienta
    @RequestMapping(method = RequestMethod.PUT,value = "clients/{id}")
    public ResponseEntity saveClient(@PathVariable Long id, @RequestBody ClientUpdateDTO clientUpdateDTO) {
        return clientRepository.findById(id)
                .map(client -> ResponseEntity.ok(saveClient(client, clientUpdateDTO)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Client saveClient(Client client, ClientUpdateDTO clientUpdateDTO){
        client.setName(clientUpdateDTO.getName());
        client.setSurname(clientUpdateDTO.getSurname());
        client.setPhoneNumber(clientUpdateDTO.getPhoneNumber());

        return clientRepository.save(client);
    }

    //dodawanie adresu do klienta
    @RequestMapping(method = RequestMethod.POST,value = "clients/{id}/addresses")
    public ResponseEntity addAddressToClient(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        return clientRepository.findById(id)
                .map(client -> ResponseEntity.ok(addAddressToClient(client, addressDTO)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Client addAddressToClient(Client client, AddressDTO addressDTO){
        Address address = new Address(addressDTO.getName(), addressDTO.getNumber(), addressDTO.getHouseNumber());
        addressRepository.save(address);
        client.getAddresses().add(address);

        return clientRepository.save(client);
    }

    //usuwanie adresu
    @RequestMapping(method = RequestMethod.DELETE,value = "clients/{id}/addresses/{idAddress}")
    public ResponseEntity deleteAddress(@PathVariable Long id, @PathVariable Long idAddress) {
        return clientRepository.findById(id)
                .map(client -> ResponseEntity.ok(deleteAddress(client, idAddress)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Optional<Client> deleteAddress(Client client, Long idAddress){
        return client.getAddresses().stream()
                .filter(address -> idAddress.equals(address.getId()))
                .findFirst().map(address -> {
                    client.getAddresses().remove(address);
                    addressRepository.delete(address);
                    return client;
        });
    }

    //listowanie klientów
    @RequestMapping(method = RequestMethod.GET, value = "clients")
    public ResponseEntity getClients(){
        return ResponseEntity.ok(clientRepository.findAll());
    }

    //usuwnia klienta po kluczu ID
    @RequestMapping(method = RequestMethod.DELETE,value = "clients/{id}" )
    public ResponseEntity deleteClient(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            clientRepository.delete(client.get());
            userRepository.delete(client.get().getUserInternal());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
