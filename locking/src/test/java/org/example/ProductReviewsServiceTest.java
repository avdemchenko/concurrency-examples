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
        service.addProduct(1);
        List<String> reviews = service.getAllProductReviews(1);
        assertTrue(reviews.isEmpty());
    }

    @Test
    void testRemoveProduct() {
        service.addProduct(1);
        service.addProductReview(1, "Great product!");
        service.removeProduct(1);
        List<String> reviews = service.getAllProductReviews(1);
        assertTrue(reviews.isEmpty());
    }

    @Test
    void testAddProductReview() {
        service.addProduct(1);
        service.addProductReview(1, "Great product!");
        List<String> reviews = service.getAllProductReviews(1);
        assertEquals(1, reviews.size());
        assertEquals("Great product!", reviews.get(0));
    }

    @Test
    void testGetAllProductReviews() {
        service.addProduct(1);
        service.addProductReview(1, "Great product!");
        service.addProductReview(1, "Could be better.");
        List<String> reviews = service.getAllProductReviews(1);
        assertEquals(2, reviews.size());
        assertEquals("Great product!", reviews.get(0));
        assertEquals("Could be better.", reviews.get(1));
    }

    @Test
    void testGetLatestReview() {
        service.addProduct(1);
        service.addProductReview(1, "Great product!");
        service.addProductReview(1, "Could be better.");
        Optional<String> latestReview = service.getLatestReview(1);
        assertTrue(latestReview.isPresent());
        assertEquals("Could be better.", latestReview.get());
    }

    @Test
    void testGetAllProductIdsWithReviews() {
        service.addProduct(1);
        service.addProductReview(1, "Great product!");
        service.addProduct(2);
        service.addProductReview(2, "Not bad.");
        Set<Integer> productIdsWithReviews = service.getAllProductIdsWithReviews();
        assertEquals(2, productIdsWithReviews.size());
        assertTrue(productIdsWithReviews.contains(1));
        assertTrue(productIdsWithReviews.contains(2));
    }
}
