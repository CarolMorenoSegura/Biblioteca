package com.ciclo3.BibliotecaR.Service;

import com.ciclo3.BibliotecaR.Model.Client;
import com.ciclo3.BibliotecaR.Repository.ClientRepository;
import com.ciclo3.BibliotecaR.Repository.crudRepository.ClientCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    private ClientCrudRepository clientCrudRepository;

    public List<Client> getAll(){
        return clientRepository.getAll();
    }

    public Optional<Client> getClient (int idClient){
        return clientRepository.getClient(idClient);
    }

    public List<Client>listaClient(){
        return (List<Client>) clientCrudRepository.findAll(); //Consultar todos los libros
    }

    public Client save(Client client){
        if(client.getIdClient()==null){
            return clientRepository.save(client);
        }else {
            Optional<Client> clientEncontrada = getClient(client.getIdClient());
            if(clientEncontrada.isEmpty()){
                return clientRepository.save(client);
            }else{
                return client;
            }
        }
    }

    public Client update(Client client){
        if(client.getIdClient()!=null){
            Optional<Client> clientEncontrada = getClient(client.getIdClient());
            if(!clientEncontrada.isEmpty()){
                if(client.getName()!=null){
                    clientEncontrada.get().setName(client.getName());
                }
                if (client.getAge()!=null){
                    clientEncontrada.get().setAge(client.getAge());
                }
                if (client.getPassword()!=null){
                    clientEncontrada.get().setPassword(client.getPassword());
                }
                return clientRepository.save(clientEncontrada.get());
            }
        }
        return client;
    }

    public boolean delete(int id){
        Boolean respuesta = getClient(id).map(elemento -> {
            clientRepository.delete(elemento);
            return true;
        }).orElse(false);

        return respuesta;
    }
}
