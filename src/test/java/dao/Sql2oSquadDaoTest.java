package dao;

import models.Squad;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class Sql2oSquadDaoTest {

    private Sql2oSquadDao squadDao;
    private Connection conn;


    @Test
    public void addingCourseSetsId() throws Exception {
        Squad squad = setUpNewSquad();
        int originalSquadId = squad.getId();
        squadDao.add(squad);
        assertNotEquals(originalSquadId, squad.getId());
    }

    @Test
    public void existingSquadsCanBeFoundById() throws Exception {
        Squad squad = setUpNewSquad();
        squadDao.add(squad); //add to dao (takes care of saving)
        Squad foundSquad = squadDao.findById(squad.getId()); //retrieve
        assertEquals(squad, foundSquad); //should be the same
    }

    @Test
    public void existingSquadCanBeUpdated() throws Exception {
        Squad squad = setUpNewSquad();
        squadDao.add(squad);
        Squad foundSquad = squadDao.findById(squad.getId());
        assertEquals(squad, foundSquad);
    }

    @Test
    public void existingSquadCanBeDeleted() throws Exception {
        Squad squad = setUpNewSquad();
        squadDao.add(squad);
        squadDao.deleteById(squad.getId());
        assertEquals(0, squadDao.getAll().size());
    }

    @Test
    public void AllExistingSquadsCanBeDeleted() throws Exception {
        Squad squad = setUpNewSquad();
        squadDao.add(squad); //add to dao (takes care of saving)
        Squad foundSquad = squadDao.findById(squad.getId()); //retrieve
        assertEquals(squad, foundSquad); //should be the same
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        squadDao = new Sql2oSquadDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Squad setUpNewSquad(){
        return new Squad("X-me",45,"Fighting Mutants");
    }
}
