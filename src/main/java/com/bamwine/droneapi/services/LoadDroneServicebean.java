package com.bamwine.droneapi.services;

import com.bamwine.droneapi.entity.LoadDrone;
import com.bamwine.droneapi.entity.Medication;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class LoadDroneServicebean {

    @PersistenceContext(name = "default")
    private EntityManager em;


    public List<LoadDrone> get_allloadedMedication() {
        return em.createQuery("SELECT c FROM LoadDrone c", LoadDrone.class).getResultList();
    }



    public List<LoadDrone> getdroneMedication(String serialno) {
        TypedQuery<LoadDrone> query = em.createQuery("SELECT e FROM LoadDrone e WHERE e.serial = :serial", LoadDrone.class);
        query.setParameter("serial", serialno);
        return query.getResultList();
    }


    public LoadDrone Loadmedication(LoadDrone medication) {
         em.persist(medication);
        return medication;
    }

}
