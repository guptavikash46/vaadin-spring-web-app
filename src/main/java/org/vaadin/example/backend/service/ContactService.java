package org.vaadin.example.backend.service;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;
import org.vaadin.example.backend.repository.CompanyRepository;
import org.vaadin.example.backend.repository.ContactRepository;

@Service
public class ContactService {
    private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());
    private ContactRepository contactRepository;
    private CompanyRepository companyRepository;

    public ContactService(ContactRepository contactRepository, CompanyRepository companyRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }
    public List<Contact> findAll(String searchTerm) {
        if(searchTerm == null || searchTerm.isEmpty()) {
            return contactRepository.findAll();
        }
        return contactRepository.search(searchTerm);
    }

    public long getTotalCount() {
        return contactRepository.count();
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact) {
        if(contact == null) {
            LOGGER.log(Level.SEVERE, "Contact provided is null, make sure the contact object has the"+
            "actual data");
            return;
        }
        contactRepository.save(contact);
    }
    
    @PostConstruct
    public void populateTestData() {
        if(companyRepository.count() == 0) {
           companyRepository.saveAll(
                Stream.of("Path-Way Electronics", "E-Tech Management", "Path-E-Tech Management")
                    .map(Company::new)
                    .collect(Collectors.toList())
                    );
            
        }
        if(contactRepository.count() == 0) {
            Random random = new Random(0);
            List<Company> companies = companyRepository.findAll();
            contactRepository.saveAll(
              Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
              "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
              "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
              "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
              "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
              "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
              "Jaydan Jackson", "Bernard Nilsen")
              .map(name -> {
                  Contact contact = new Contact();
                  String[] names = name.split(" ");
                  contact.setFirstName(names[0]);
                  contact.setLastName(names[1]);
                  contact.setCompany(companies.get(random.nextInt(companies.size())));
                  contact.setStatus(Contact.Status.values()[random.nextInt(Contact.Status.values().length)]);
                  contact.setEmail((contact.getFirstName()+"."+contact.getLastName()+"@"+
                  contact.getCompany().getName().replaceAll("[\\s-]", "")+".com").toLowerCase());
                  return contact;
              })
              .collect(Collectors.toList())
            );
        }
    }
}
