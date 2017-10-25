package com.lpa.spring5mvcrest.bootstrap;

import com.lpa.spring5mvcrest.domain.Category;
import com.lpa.spring5mvcrest.domain.Customer;
import com.lpa.spring5mvcrest.repositories.CategoryRepository;
import com.lpa.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();

        loadCustomers();
    }

    private void loadCustomers() {
        Customer meyers = new Customer();
        meyers.setFirstname("Freddy");
        meyers.setLastname("Meyers");

        Customer buck = new Customer();
        buck.setFirstname("Joe");
        buck.setLastname("Buck");

        Customer weston = new Customer();
        weston.setFirstname("Michael");
        weston.setLastname("Weston");

        Customer doe = new Customer();
        doe.setFirstname("John");
        doe.setLastname("Doe");

        customerRepository.save(meyers);
        customerRepository.save(buck);
        customerRepository.save(weston);
        customerRepository.save(doe);

        System.out.println("Customers loaded = " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories loaded = " + categoryRepository.count());
    }
}
