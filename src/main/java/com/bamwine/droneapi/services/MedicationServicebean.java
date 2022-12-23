package com.bamwine.droneapi.services;
import com.bamwine.droneapi.entity.Medication;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class MedicationServicebean {

    @PersistenceContext(name = "default")
    private EntityManager em;



    public Medication findMedication(String code) {
        TypedQuery<Medication> query = em.createQuery("SELECT e FROM Medication e WHERE e.code = :code", Medication.class);
        query.setParameter("code", code);
        return query.getSingleResult();
    }






    public List<Medication> getallMedication() {
        return em.createQuery("SELECT c FROM Medication c", Medication.class).getResultList();
    }


    public Medication addMedication(Medication medication) {
         em.persist(medication);
        return medication;
    }

}
