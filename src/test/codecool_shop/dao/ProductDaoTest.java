package codecool_shop.dao;

import codecool_shop.Application;
import codecool_shop.model.Product;
import codecool_shop.model.ProductSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ProductDaoTest {
    private SgliteJDSCConnector connector;
    Connection connection;
    ProductDao productDao;

    @BeforeEach
    public void createApp(){
        Application.runApp();
    }

    @BeforeEach
    public void establishConnection() throws SQLException {
        connector = mock(SgliteJDSCConnector.class);
        when(connector.connection()).thenReturn(DriverManager.getConnection("jdbc:sqlite:src/main/resources/sample-database.db"));
        connection = connector.connection();
    }

    @BeforeEach
    public void createTestProductDao(){
        productDao = new ProductDao(connection);
    }

    @Test
    public void testRemoveRecordWhenThereIsNoRequestedIdThrowException(){
        assertThrows(NullPointerException.class, () -> {
        productDao.remove(2);
        });
    }

    @Test
    public void testAddWhenNullIsPassedThrowException(){
        assertThrows(NullPointerException.class, () ->{
            productDao.add(null);
        });
    }

    @Test
    public void testUpdateWhenNullIsPassedThrowException(){
        assertThrows(NullPointerException.class, () ->{
            productDao.update(null);
        });
    }

    @Test
    public void testGetByIdRetrieveAccurateObjectFromDatabase() throws SQLException {
        assertEquals("product1", productDao.getById(1).getName());
    }

    @Test
    public void testGetIdByProductNameRetrievesAccurateObjectFromDatabase() throws SQLException {
        assertEquals(1, (int)productDao.getByName("product1"));
    }

    @Test
    public void testGetByIdThrowsExceptionWhenPassedANull(){
        assertThrows(NullPointerException.class, () -> {
            productDao.getById(null);
        });
    }

    @Test
    public void testGetAll() throws SQLException {
        List<Product> firstTry = productDao.getAll();
        ProductSupplier supplier = new ProductSupplier();
        supplier.setId(2);
        productDao.add(new Product("product2", "description2", new Date(), new ArrayList<>(), "url", supplier, 4));
        List<Product> secondTry = productDao.getAll();
        assertEquals(firstTry.size()+1, secondTry.size());
    }

    @Test
    public void testGetByAllCategoryThrowExceptionWhenNullIsPassed(){
        assertThrows(NullPointerException.class, () -> {
            productDao.getByAllCategory(null);
        });
    }

    @Test
    public void testGetBySupplierThrowExceptionWhenNullIsPassed(){
        assertThrows(NullPointerException.class, () -> {
            productDao.getBySupplier(null);
        });
    }
}
