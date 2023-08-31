package dat;

import dat.config.HibernateConfig;
import dat.dao.DriverDAOImpl;
import dat.dao.IDriverDAO;
import dat.dao.IWasteTruckDAO;
import dat.dao.WasteTruckDAOImpl;
import dat.dtos.SimpleDTO;
import dat.dtos.TruckDriverDTO;
import dat.model.Driver;
import dat.model.WasteTruck;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("recycling");

    public static void main(String[] args) {

        IDriverDAO driverDAO = DriverDAOImpl.getInstance(emf);
        IWasteTruckDAO wasteTruckDAO = WasteTruckDAOImpl.getInstance(emf);

        String dId = driverDAO.saveDriver("Egon", "Olsen",  new BigDecimal(25000));
        Driver d = driverDAO.getDriverById(dId);
        System.out.println(d);

        int wId = wasteTruckDAO.saveWasteTruck("Volvo", "A123-45-B6", 1000);

        WasteTruck wasteTruck = wasteTruckDAO.getWasteTruckById(wId);

        wasteTruckDAO.addDriverToWasteTruck(wasteTruck, d);

        List<WasteTruck> wasteTruckList = wasteTruckDAO.getAllAvailableTrucks();
        for (WasteTruck truck : wasteTruckList)
        {
            System.out.println(truck);
        }

        wasteTruck = wasteTruckDAO.getWasteTruckById(wId);

        TruckDriverDTO truckDriverDTO = new TruckDriverDTO(wasteTruck);
        System.out.println(truckDriverDTO.toString());

        List<SimpleDTO> simpleDTOS = wasteTruckDAO.getSimpleTruckDriverList();
        for (SimpleDTO simpleDTO : simpleDTOS)
        {
            System.out.println(simpleDTO);
        }

        emf.close();



    }
}