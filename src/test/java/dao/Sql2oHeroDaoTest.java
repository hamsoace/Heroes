package dao;

import models.Hero;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class Sql2oHeroDaoTest {

    private static Sql2oHeroDao heroDao;
    private static Sql2oSquadDao squadDao;
    private static Connection conn;


    @Test
    public void addingHeroSetsId() throws Exception {
        Hero hero = setUpNewHero();
        int originalHeroId = hero.getId();
        heroDao.add(hero);
        assertNotEquals(originalHeroId, hero.getId());
    }

    @Test
    public void aHeroIsSuccessfullyAdded() throws Exception {
        Hero hero = setUpNewHero();
        heroDao.add(hero);
        assertTrue(heroDao.getAll().contains(hero));
    }

    @Test
    public void existingHeroesCanBeFoundById() throws Exception {
        Hero hero = setUpNewHero();
        heroDao.add(hero);
        Hero foundHero = heroDao.findById(hero.getId());
        assertEquals(hero, foundHero);
    }

    @Test
    public void existingHeroCanBeUpdated() throws Exception {
        Hero hero = setUpNewHero();
        heroDao.add(hero);
        heroDao.update(hero.getId(),"Superman",33,"Invincibility","Kryptonite",2);
        Hero foundHero = heroDao.findById(hero.getId());
        assertNotEquals(hero, foundHero);
    }

    @Test
    public void existingHeroCanBeDeleted() throws Exception {
        Hero hero = setUpNewHero();
        heroDao.add(hero);
        heroDao.deleteById(hero.getId());
        assertFalse(heroDao.getAll().contains(hero));
    }

    @Test
    public void AllExistingHeroesCanBeDeleted() throws Exception {
        Hero hero = setUpNewHero();
        Hero otherHero = setUpNewHero();
        heroDao.add(hero);
        heroDao.add(otherHero);
        heroDao.clearAllHeroes();
        assertFalse(heroDao.getAll().contains(hero));
        assertFalse(heroDao.getAll().contains(otherHero));
        assertEquals(0,heroDao.getAll().size());
    }

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/heroes_test";
        Sql2o sql2o = new Sql2o(connectionString, "baraka", "fRankline");
        squadDao = new Sql2oSquadDao(sql2o);
        heroDao = new Sql2oHeroDao(sql2o);
        conn = sql2o.open();
    }


    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Hero setUpNewHero(){
        return new Hero("Wolverine", 78, "Metal Claws", "Magnets",1);
    }
}
