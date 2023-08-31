package dat.dao;

import dat.model.Driver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class DriverDAOImpl implements IDriverDAO
{
    private static EntityManagerFactory emf;
    private static DriverDAOImpl driverDAO;

    private DriverDAOImpl(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public static DriverDAOImpl getInstance(EntityManagerFactory emf)
    {
        if (driverDAO == null)
        {
            driverDAO = new DriverDAOImpl(emf);
        }
        return driverDAO;
    }

    @Override
    public String saveDriver(String name, String surname, BigDecimal salary)
    {
        Driver driver = new Driver(name, salary, surname);
        try (EntityManager em = emf.createEntityManager())
        {

            em.getTransaction().begin();
                em.persist(driver);
            em.getTransaction().commit();

        }
        return driver.getId();
    }

    @Override
    public Driver getDriverById(String id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Driver.class, id);
        }
    }

    @Override
    public Driver updateDriver(Driver driver)
    {
        return null;
    }

    @Override
    public void deleteDriver(String id)
    {

    }

    @Override
    public List<Driver> findAllDriversEmployedAtTheSameYear(String year)
    {
        return null;
    }

    @Override
    public List<Driver> fetchAllDriversWithSalaryGreaterThan10000()
    {
        return null;
    }

    @Override
    public double fetchHighestSalary()
    {
        return 0;
    }

    @Override
    public List<String> fetchFirstNameOfAllDrivers()
    {
        return null;
    }

    @Override
    public long calculateNumberOfDrivers()
    {
        return 0;
    }

    @Override
    public Driver fetchDriverWithHighestSalary()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<Driver> query = em.createQuery("SELECT d FROM Driver d ORDER BY d.salary DESC", Driver.class);
            return query.setMaxResults(1).getSingleResult();
        }
    }
}
