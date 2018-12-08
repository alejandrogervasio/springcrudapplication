package com.classbox.crud.application.controllers;

import com.classbox.crud.application.entities.Persona;
import com.classbox.crud.application.repositories.PersonRepository;
import com.classbox.crud.application.services.FileUploadService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PersonController {
    
    private final PersonRepository personRepository;
    private final FileUploadService fileUploadService;
    
    @Autowired
    public PersonController(PersonRepository personRepository, 
            @Qualifier("SimpleFileUploadService")FileUploadService fileUploadService) {
        this.personRepository = personRepository;
        this.fileUploadService = fileUploadService;
    }
    
    @GetMapping("/searchpersons")
    public String searchPersonsByQuery(
            @RequestParam(name = "criterio", required = false) String criterio,
            @RequestParam(name = "valor", required = false) String value, Model model) {
        List<Persona> persons;
        
        if (criterio != null && !criterio.isEmpty() && "dni".equalsIgnoreCase(criterio)) {
            persons = searchPersonsByDni(stringToInt(value));
        } else if (criterio != null && !criterio.isEmpty() && "nombre".equalsIgnoreCase(criterio)) {
            persons = searchPersonsByName(value);
        } else if (criterio != null && !criterio.isEmpty() && "edad".equalsIgnoreCase(criterio)) {
            persons = searchPersonsByAge(stringToInt(value));
        } else {
            persons = (List<Persona>) personRepository.findAll();
        }
        
        model.addAttribute("persons", persons);
        return "index";
    }

    private List<Persona> searchPersonsByDni(int dni) {
        return personRepository.findByDni(dni);
    }

    private List<Persona> searchPersonsByName(String name) {
        return personRepository.findByNombre(name);
    }
    
    private List<Persona> searchPersonsByAge(int age) {
        return personRepository.findByEdad(age);
    }
    
    @GetMapping("/addperson")
    public String showPersonForm(Model model) {
        Persona person = new Persona();
        model.addAttribute("person", person);
        return "addperson";
    }
    
    @PostMapping("/addperson")
    public String addPerson(
            @RequestParam("dni") int dni,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("edad") int edad,
            @RequestParam("imagen") MultipartFile imagen,
            Model model) throws IOException {
        
        String photo = imagen.getOriginalFilename();
        Persona person = getPersonFromRequestParams(dni, nombre, apellido, edad, photo);
        fileUploadService.uploadFile(imagen);
        personRepository.save(person);
        model.addAttribute("persons", personRepository.findAll());
        return "index";
    }
    
    @GetMapping("/images/{imageName}")
    @ResponseBody
    public byte[] getPersonImage(@PathVariable("imageName") String imageName) throws IOException {
        return fileUploadService.getFileAsByteArray(imageName);
    }
    
    private Persona getPersonFromRequestParams(int dni, String nombre, String apellido, int edad, String imagen) {
        return new Persona(dni, nombre, apellido, edad, imagen);
    }
    
    private int stringToInt(String value) {
        int intValue;
        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            intValue = 0;
        }
        return intValue;
    }
}
