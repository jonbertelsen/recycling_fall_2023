package dat.dao;

import dat.dtos.SimpleDTO;
import dat.model.Driver;
import dat.model.WasteTruck;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class WasteTruckDAOImpl implements IWasteTruckDAO
{
    private static EntityManagerFactory emf;
    private static WasteTruckDAOImpl wasteTruckDAO;

    private WasteTruckDAOImpl(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public static WasteTruckDAOImpl getInstance(EntityManagerFactory emf)
    {
        if (wasteTruckDAO == null)
        {
            wasteTruckDAO = new WasteTruckDAOImpl(emf);
        }
        return wasteTruckDAO;
    }

    @Override
    public int saveWasteTruck(String brand, String registrationNumber, int capacity)
    {
        int newId;
        WasteTruck wasteTruck = new WasteTruck(brand, capacity, registrationNumber);

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
                em.persist(wasteTruck);
            em.getTransaction().commit();
        }
        return wasteTruck.getId();
    }

    @Override
    public WasteTruck getWasteTruckById(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(WasteTruck.class, id);
        }
    }

    @Override
    public void setWasteTruckAvailable(WasteTruck wasteTruck, boolean available)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
                WasteTruck wasteTruckToUpdate = em.find(WasteTruck.class, wasteTruck.getId());
                wasteTruckToUpdate.setAvailable(available);
            em.getTransaction().commit();
        }
    }

    @Override
    public void deleteWasteTruck(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            WasteTruck wasteTruck = em.find(WasteTruck.class, id);
            em.remove(wasteTruck);
            em.getTransaction().commit();
        }
    }

    @Override
    public void addDriverToWasteTruck(WasteTruck wasteTruck, Driver driver)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
                wasteTruck = em.find(WasteTruck.class, wasteTruck.getId());
                driver = em.find(Driver.class, driver.getId());
                wasteTruck.addDriver(driver);
            em.getTransaction().commit();
        }
    }

    @Override
    public void removeDriverFromWasteTruck(WasteTruck wasteTruck, String driverId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
                wasteTruck = em.find(WasteTruck.class, wasteTruck.getId());
                Driver driver = em.find(Driver.class, driverId);
                wasteTruck.removeDriver(driver);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<WasteTruck> getAllAvailableTrucks()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<WasteTruck> query = em.createQuery(
                    "SELECT w FROM WasteTruck w WHERE w.isAvailable",
                    WasteTruck.class);
            return query.getResultList();
        }
    }

    public List<SimpleDTO> getSimpleTruckDriverList()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<SimpleDTO> query = em.createQuery(
                    "SELECT NEW dat.dtos.SimpleDTO(w.registrationNumber, d.name) FROM WasteTruck w JOIN w.drivers d",
                    SimpleDTO.class);
            return query.getResultList();
        }
    }

}
