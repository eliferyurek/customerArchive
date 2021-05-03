package com.examples.customerArchive.controller;

import java.util.List;

import com.examples.customerArchive.ResourceNotFoundException;
import com.examples.customerArchive.model.Customer;
import com.examples.customerArchive.repository.CustomerRepository;
import com.examples.customerArchive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<Customer> GetCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer GetCustomer(@PathVariable Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Customer PostCustomer(@RequestBody Customer customer) {
        return userRepository.findById(1).map(newCustomer -> {
            customer.setUser(newCustomer);
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found."));

    }

    @PutMapping("/{id}")
    public Customer PutUser(@PathVariable (value = "id") Integer customerId,
                            @RequestBody Customer customer) {

        return customerRepository.findById(customerId).map(newCustomer -> {
            newCustomer.setFirstName(customer.getFirstName());
            newCustomer.setLastName(customer.getLastName());
            newCustomer.setEmail(customer.getEmail());
            return customerRepository.save(newCustomer);
        }).orElseThrow(() -> new ResourceNotFoundException("CustomerId " + customerId + "not found"));

    }

    @DeleteMapping("/{id}")
    public Integer DeleteUser(@PathVariable Integer id) {
        customerRepository.deleteById(id);
        return id;
    }
}