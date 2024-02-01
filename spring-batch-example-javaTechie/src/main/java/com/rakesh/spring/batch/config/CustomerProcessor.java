package com.rakesh.spring.batch.config;


import com.rakesh.spring.batch.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) {
   /*     Here we can implement out business logic to transform the data, like in this case, my csv has 1000 rows
        but i have put the logic to only filter the data that has country as US and parse them to the DB*/
        if (customer.getCountry().equals("United States")) {
            return customer;
        } else {
            return null;
        }
    }
}
