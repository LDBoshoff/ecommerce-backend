package com.ldb.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ldb.backend.model.Product;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    // @Test
    // public void testGetProductById() {
    //     // Product 1 = (1, 'Iphone', 'Latest model of the popular smartphone', 999.99, 50)
    //     // Verify the status code for product 1
    //     ResponseEntity<Product> response1 = restTemplate.getForEntity("/api/products/1", Product.class);
    //     assertEquals(HttpStatus.OK, response1.getStatusCode());

    //     Product product1 = response1.getBody();
       
    //     // Verify the properties of the product
    //     assertEquals(1L, product1.getId());
    //     assertEquals("Iphone", product1.getName());
    //     assertEquals("Latest model of the popular smartphone", product1.getDescription());
    //     assertEquals(999.99, product1.getPrice());
    //     assertEquals(50, product1.getQuantity());

    //     // Product 4 = (4, 'Playstation 5', 'Next-gen gaming console with 4K graphics support', 499.99, 40)
    //     // Verify the status code for product 4
    //     ResponseEntity<Product> response4 = restTemplate.getForEntity("/api/products/4", Product.class);
    //     assertEquals(HttpStatus.OK, response4.getStatusCode());
    
    //     Product product4 = response4.getBody();
    
    //     // Verify the properties of product 4
    //     assertEquals(4L, product4.getId());
    //     assertEquals("Playstation 5", product4.getName());
    //     assertEquals("Next-gen gaming console with 4K graphics support", product4.getDescription());
    //     assertEquals(499.99, product4.getPrice());
    //     assertEquals(40, product4.getQuantity());

    //     // Non-existant product with id 999
    //     ResponseEntity<String> responseNonExistent = restTemplate.getForEntity("/api/products/999", String.class);
    //     assertEquals(HttpStatus.NOT_FOUND, responseNonExistent.getStatusCode()); // Verify NOT FOUND status code

    // }

}

