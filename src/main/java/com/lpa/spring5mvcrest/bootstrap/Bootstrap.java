package com.lpa.spring5mvcrest.bootstrap;

import com.lpa.spring5mvcrest.domain.Category;
import com.lpa.spring5mvcrest.domain.Customer;
import com.lpa.spring5mvcrest.domain.Vendor;
import com.lpa.spring5mvcrest.repositories.CategoryRepository;
import com.lpa.spring5mvcrest.repositories.CustomerRepository;
import com.lpa.spring5mvcrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();

        loadCustomers();

        loadVendors();
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

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");
        vendorRepository.save(vendor2);
    }
}
