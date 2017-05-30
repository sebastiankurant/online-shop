package codecool_shop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product product;

    @BeforeEach
    public void setNewProduct(){
        product = new Product();
    }

    @Test
    public void testDoesSetNameThrowExceptionWhenNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            product.setName(null);
        });
    }

    @Test
    public void testDoesSetDescriptionThrowExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            product.setDescription(null);
        });
    }

    @Test
    public void testDoesSetDateThrowExceptionWhenNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            product.setDate(null);
        });
    }

    @Test
    public void testDoesSetUrlThrowExceptionWhenNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            product.setUrl(null);
        });
    }

    @Test
    public void testDoesSetPriceThrowExceptionWhenNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            product.setPrice(null);
        });
    }

    @Test
    public void testDoesSetCategoriesThrowExceptionWhenNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            product.setCategories(null);
        });
    }

    @Test
    public void testSetPriceBelowZeroThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            product.setPrice(-1);});
    }

    @Test
    public void testDoesSetSupplierThrowExceptionWhenNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            product.setSupplier(null);
        });
    }

    @Test
    public void testGetNameResultEqualsSetName() {
        product.setName("ŻÓŁĆ");
        assertEquals("ŻÓŁĆ", product.getName());
    }

    @Test
    public void testGetDescriptionResultEqualsSetDescription() {
        product.setDescription("Description");
        assertEquals("Description", product.getDescription());
    }

    @Test
    public void testDoesConstructorThrowErrorIfIdBellowZero(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(-1, "Name", "Description", new Date(), new ArrayList<>(), "url", new ProductSupplier(), 12 );
        });
    }

    @Test
    public void testDoesConstructorThrowErrorIfPriceBellowZero(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(1, "Name", "Description", new Date(), new ArrayList<>(), "url", new ProductSupplier(), -12 );
        });
    }

}