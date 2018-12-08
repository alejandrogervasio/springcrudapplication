package com.classbox.crud.application.repositories;

import com.classbox.crud.application.entities.Persona;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Persona, Long> {
    
    List<Persona> findByDni(int dni);
    
    List<Persona> findByNombre(String nombre);
    
    List<Persona> findByEdad(int edad);
}
