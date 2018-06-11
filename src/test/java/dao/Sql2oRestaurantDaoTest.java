package dao;

import models.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oRestaurantDaoTest {
    private Connection conn;
    private Sql2oRestaurantDao restaurantDao;
    
    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingRestaurantSetsId() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        assertEquals(1, testRestaurant.getId());
    }

    @Test
    public void getAll() throws Exception {
        Restaurant restaurant1 = setupRestaurant();
        Restaurant restaurant2 = setupRestaurant();
        assertEquals(2, restaurantDao.getAll().size());
    }

    @Test
    public void findByIdReturnsRestaurant() {
        Restaurant restaurant1 = setupRestaurant();
        Restaurant restaurant2 = setupRestaurant();

        assertEquals(restaurant1.getName(), restaurantDao.findById(1).getName());
    }

    @Test
    public void deleteById() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        Restaurant otherRestaurant = setupRestaurant();
        assertEquals(2, restaurantDao.getAll().size());
        restaurantDao.deleteById(testRestaurant.getId());
        assertEquals(1, restaurantDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        Restaurant otherRestaurant = setupRestaurant();
        restaurantDao.clearAll();
        assertEquals(0, restaurantDao.getAll().size());
    }

    public Restaurant setupRestaurant (){
        Restaurant restaurant = new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        restaurantDao.add(restaurant);
        return restaurant;

    }

}