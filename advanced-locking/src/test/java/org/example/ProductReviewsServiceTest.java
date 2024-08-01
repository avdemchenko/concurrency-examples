package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductReviewsServiceTest {

    private ProductReviewsService service;

    @BeforeEach
    void setUp() {
        service = new ProductReviewsService();
    }

    @Test
    void testAddProduct() {
        // when
        service.addProduct(1);

        // then
        List<String> reviews = service.getAllProductReviews(1);
        assertTrue(reviews.isEmpty());
    }

    @Test
    void testRemoveProduct() {
        // when
        service.addProduct(1);
        service.addProductReview(1, "Great product!");
        service.removeProduct(1);

        // and
        List<String> reviews = service.getAllProductReviews(1);

        // then
        assertTrue(reviews.isEmpty());
    }

    @Test
    void testAddProductReview() {
        // when
        service.addProduct(1);
        service.addProductReview(1, "Great product!");

        // and
        List<String> reviews = service.getAllProductReviews(1);

        // then
        assertEquals(1, reviews.size());
        assertEquals("Great product!", reviews.get(0));
    }

    @Test
    void testGetAllProductReviews() {
        // when
        service.addProduct(1);
        service.addProductReview(1, "Great product!");
        service.addProductReview(1, "Could be better.");

        // and
        List<String> reviews = service.getAllProductReviews(1);

        // then
        assertEquals(2, reviews.size());
        assertEquals("Great product!", reviews.get(0));
        assertEquals("Could be better.", reviews.get(1));
    }

    @Test
    void testGetLatestReview() {
        // when
        service.addProduct(1);
        service.addProductReview(1, "Great product!");
        service.addProductReview(1, "Could be better.");

        // and
        Optional<String> latestReview = service.getLatestReview(1);

        // then
        assertTrue(latestReview.isPresent());
        assertEquals("Could be better.", latestReview.get());
    }

    @Test
    void testGetAllProductIdsWithReviews() {
        // when
        service.addProduct(1);
        service.addProductReview(1, "Great product!");
        service.addProduct(2);
        service.addProductReview(2, "Not bad.");

        // and
        Set<Integer> productIdsWithReviews = service.getAllProductIdsWithReviews();

        // then
        assertEquals(2, productIdsWithReviews.size());
        assertTrue(productIdsWithReviews.contains(1));
        assertTrue(productIdsWithReviews.contains(2));
    }

    @Test
    void testConcurrentAddProductReviews() throws InterruptedException {
        // given
        Runnable task1 = () -> {
            for (int i = 0; i < 1000; i++) {
                service.addProductReview(1, "Review " + i);
            }
        };

        Runnable task2 = () -> {
            for (int i = 1000; i < 2000; i++) {
                service.addProductReview(1, "Review " + i);
            }
        };

        // and
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        // when
        thread1.start();
        thread2.start();

        // and
        thread1.join();
        thread2.join();

        // then
        List<String> reviews = service.getAllProductReviews(1);
        assertEquals(2000, reviews.size());
    }
}
