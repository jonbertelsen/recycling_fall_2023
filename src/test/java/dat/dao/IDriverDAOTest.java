package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Driver;
import dat.model.WasteTruck;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IDriverDAOTest
{
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("recycling");
    private static IDriverDAO driverDAO = DriverDAOImpl.getInstance(emf);
    private static IWasteTruckDAO wasteTruckDAO = WasteTruckDAOImpl.getInstance(emf);

    @BeforeAll
    static void beforeAll()
    {

    }

    @BeforeEach
    void setUp()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Driver").executeUpdate();
            em.createQuery("DELETE FROM WasteTruck").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE wastetruck_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("INSERT INTO public.wastetruck (id, brand, capacity, isavailable, registrationnumber)\n" +
                    "VALUES\n" +
                    "    (1, 'Volvo', 8000, true, 'A123-45-B6'),\n" +
                    "    (2, 'Ford', 12000, false, 'C678-90-D1'),\n" +
                    "    (3, 'Mercedes', 15000, true, 'E234-56-F7'),\n" +
                    "    (4, 'BMW', 10000, false, 'G789-01-H2'),\n" +
                    "    (5, 'Chevrolet', 17000, false, 'I345-67-J8');").executeUpdate();

            em.createNativeQuery("INSERT INTO public.driver (id, employmentdate, name, surname, salary, wastetruck_id)\n" +
                    "VALUES\n" +
                    "    ('230826-BD-398E', '2023-08-26', 'John', 'Doe', 12550, 1),\n" +
                    "    ('200512-AD-786M', '1995-05-12', 'Alice', 'Smith', 18550, 2),\n" +
                    "    ('211104-CE-572S', '1996-11-04', 'Charlie', 'Evans', 21800, 3),\n" +
                    "    ('201217-GK-123D', '1997-12-17', 'Grace', 'Kelly', 52000, 4),\n" +
                    "    ('220609-MP-456H', '1998-06-09', 'Michael', 'Parker', 31200, 5),\n" +
                    "    ('200821-FR-892L', '1999-08-21', 'Frank', 'Roberts', 28500, 4),\n" +
                    "    ('230531-WT-642B', '2023-05-31', 'William', 'Turner', 65800, 3),\n" +
                    "    ('230304-LH-718R', '2023-03-04', 'Laura', 'Harris', 8500, 3),\n" +
                    "    ('230925-SJ-599M', '2023-09-25', 'Sarah', 'Johnson', 16500, 5),\n" +
                    "    ('211128-PT-347S', '2000-11-28', 'Peter', 'Taylor', 33000, 1);").executeUpdate();

            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDown()
    {
        emf.close();
    }

    @Test
    void saveDriver()
    {
        String dId = driverDAO.saveDriver("Egon", "Olsen",  new BigDecimal(25000));
        Driver d = driverDAO.getDriverById(dId);
        assertEquals("Egon", d.getName());
        System.out.println(d.getId());
    }

    @Test
    void getDriverById()
    {
        Driver d = driverDAO.getDriverById("211128-PT-347S");
        assertEquals("Peter", d.getName());
    }

    @Test
    void fetchDriverWithHighestSalary()
    {
        Driver d = driverDAO.fetchDriverWithHighestSalary();
        assertEquals("William", d.getName());
    }

    @Test
    void setWasteTruckAvailable()
    {
        WasteTruck wasteTruck = wasteTruckDAO.getWasteTruckById(1); // Volvo
        wasteTruckDAO.setWasteTruckAvailable(wasteTruck, false);
        wasteTruck = wasteTruckDAO.getWasteTruckById(1);
        assertEquals(false, wasteTruck.isAvailable());
    }

    @Test
    void removeDriverFromWasteTruck()
    {
        WasteTruck wasteTruck = wasteTruckDAO.getWasteTruckById(3); // Mercedes
        wasteTruckDAO.removeDriverFromWasteTruck(wasteTruck, "211104-CE-572S");  // Charlie
        Driver driver = driverDAO.getDriverById("211104-CE-572S");
        assertNull(driver.getWasteTruck());
    }
}