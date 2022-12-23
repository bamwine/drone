package com.bamwine.droneapi.services;


import com.bamwine.droneapi.entity.Drone;
import com.bamwine.droneapi.entity.Medication;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.bamwine.droneapi.enums.STATE.IDLE;

@ApplicationScoped
@Transactional
public class DroneServicebean {

    @PersistenceContext(name = "default")
    private EntityManager em;


    public Drone findDrone(String serial) {
//        return em.find(Drone.class, serial);
        TypedQuery<Drone> query = em.createQuery("SELECT e FROM Drone e WHERE e.serial = :serial", Drone.class);
        query.setParameter("serial", serial);
        return query.getSingleResult();
    }

    public void updateDrone(Drone event) {
        em.merge(event);
    }

    public List<Drone> getallDrones() {
        return em.createQuery("SELECT c FROM Drone c", Drone.class).getResultList();
    }


    public List<Drone> Dronesforloading() {
        TypedQuery<Drone> query = em.createQuery("SELECT e FROM Drone e WHERE e.state = :state", Drone.class);
        query.setParameter("state", IDLE);
        return query.getResultList();
    }

    public Drone Dronebatterylevel(String serial) {
        TypedQuery<Drone> query = em.createQuery("SELECT e FROM Drone e WHERE e.serial = :serial", Drone.class);
        query.setParameter("serial", serial);

        return query.getSingleResult();
    }

    public Drone addDrone(Drone drone) {
         em.persist(drone);
        return drone;
    }

}
