package com.example.finalJava;

import com.example.finalJava.Repository.ProductRepository;
import com.example.finalJava.dto.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Test
    public void testListAll(){
        Iterable<Product> products = productRepository.findAll();
        Assertions.assertThat(products).hasSizeGreaterThan(0);

        for(Product prod : products){
            System.out.println(prod);
        }
    }

    @Test
    public void testGet(){
        Integer productId = 2;
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Assertions.assertThat(optionalProduct).isPresent();
        System.out.println(optionalProduct.get());
    }
}
