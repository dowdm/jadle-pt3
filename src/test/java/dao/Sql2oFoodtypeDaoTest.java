package dao;

import models.Foodtype;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oFoodtypeDaoTest {
    private Connection conn;
    private Sql2oFoodtypeDao foodtypeDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingFoodtypeSetsId() throws Exception {
        Foodtype testFoodtype = setupFoodtype();
        assertEquals(1, testFoodtype.getId());
    }

    @Test
    public void getAll() throws Exception {
        Foodtype foodtype1 = setupFoodtype();
        Foodtype foodtype2 = setupFoodtype();
        assertEquals(2, foodtypeDao.getAll().size());
    }



    @Test
    public void deleteById() throws Exception {
        Foodtype testFoodtype = setupFoodtype();
        Foodtype otherFoodtype = setupFoodtype();
        assertEquals(2, foodtypeDao.getAll().size());
        foodtypeDao.deleteById(testFoodtype.getId());
        assertEquals(1, foodtypeDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Foodtype testFoodtype = setupFoodtype();
        Foodtype otherFoodtype = setupFoodtype();
        foodtypeDao.clearAll();
        assertEquals(0, foodtypeDao.getAll().size());
    }

    public Foodtype setupFoodtype(){
        Foodtype foodtype = new Foodtype("dessert");
        foodtypeDao.add(foodtype);
        return foodtype;
    }
}