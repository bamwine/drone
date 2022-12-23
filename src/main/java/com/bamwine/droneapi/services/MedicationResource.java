package com.bamwine.droneapi.services;
import com.bamwine.droneapi.entity.Drone;
import com.bamwine.droneapi.entity.LoadDrone;
import com.bamwine.droneapi.entity.Medication;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;

import static com.bamwine.droneapi.enums.STATE.LOADING;

@RequestScoped
@Path("/medication")
public class MedicationResource {
    @Inject
    MedicationServicebean medicationservicebean;

    @Inject
    LoadDroneServicebean Loaddroneservicebean;
    @Inject
    DroneServicebean droneServicebean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/{code}")
    public Medication findMedication(@PathParam("code" )String code) {

    return  medicationservicebean.findMedication(code);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Medication> getallMedication() {
        return  medicationservicebean.getallMedication();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
//    public Medication addMedication( Medication medication) {
    public JsonArray addMedication( Medication medication) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        Medication newmedi =  medicationservicebean.addMedication(medication);
        builder.add("Result", "Success")
                .add("Message", "Medication Successfully Registered")
                .add("Code Number", newmedi.getCode())
                .add("name", newmedi.getName().toString())
                .add("Weight", newmedi.getWeight());


        finalArray.add(builder.build());
        return finalArray.build();

//        return  medicationservicebean.addMedication(medication);
    }

    //  loading medicine functions

    @GET
    @Path("/loadedmedicine")
    @Produces(MediaType.APPLICATION_JSON)
//    public List<LoadDrone> get_allloadedMedication() {
        public JsonArray get_allloadedMedication() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        for (LoadDrone data : Loaddroneservicebean.get_allloadedMedication()) {
            builder.add("Result","Success")
                    .add("Message","Drone and its Medication")
                    .add("name", data.getName()).add("Drone serial", data.getSerial())
                    .add("Medicine code", data.getCode()).add("id", data.getId());
            finalArray.add(builder.build());
        }
        return finalArray.build();

//        return  Loaddroneservicebean.get_allloadedMedication();
    }

    @POST
    @Path("/loadmedicine")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray addmedication(LoadDrone medication) {
//     public LoadDrone addmedication(LoadDrone medication) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();

        Medication medicadata = medicationservicebean.findMedication(medication.getCode());
        Drone dronedata = droneServicebean.findDrone(medication.getSerial());

        if (medicadata!=null && dronedata!=null  ) {

            if( dronedata.getBattery().compareTo(new BigDecimal(0.25)) < 0 || dronedata.getWeightLimit() < medicadata.getWeight()  ){

                builder.add("Result", "Failed").add("Battery Message", "This Drone cannot be loaded, its battery below 25%")
                        .add("Weight Message", "This Drone cannot be loaded, its weight below that of Medication");
                finalArray.add(builder.build());
            } else{
                LoadDrone loadrone = new LoadDrone();
                loadrone.setCode(medicadata.getCode());
                loadrone.setName(medicadata.getName());
                loadrone.setSerial(dronedata.getSerial());
                loadrone.setSource(medication.getSource());
                loadrone.setDestination(medication.getDestination());

                Loaddroneservicebean.Loadmedication(loadrone);
                dronedata.setState(LOADING);
                droneServicebean.updateDrone(dronedata);

                    builder.add("Result", "Success").add("Message", "Drone Loaded Successfully !")
                            .add("Drone serial", loadrone.getSerial())
                            .add("Medicine code", loadrone.getCode())
                            .add("Medicine", loadrone.getName())
                            .add("source", loadrone.getSource())
                            .add("destination",loadrone.getDestination());


                    finalArray.add(builder.build());


            }

        } else if (medicadata==null || dronedata==null ){

            builder.add("Result", "Failed").add("Message", "Drone or Medication doesnt Exist");
            finalArray.add(builder.build());
        }

        return finalArray.build();

//        return  Loaddroneservicebean.Loadmedication(medication);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dronemedicine/{serial}")
//    public List<LoadDrone> getdroneMedication(@PathParam("serial" )String serial) {
    public JsonArray getdroneMedication(@PathParam("serial" )String serial) {

        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        if (!Loaddroneservicebean.getdroneMedication(serial).isEmpty()) {

            for (LoadDrone data : Loaddroneservicebean.get_allloadedMedication()) {
                builder.add("Result", "Success").add("Message", "Drone Medicine Details !")
                        .add("medicine name", data.getName())
                        .add("drone serial ", data.getSerial())
                        .add("Medicine code", data.getCode());

                finalArray.add(builder.build());
            }
        } else{

            builder.add("Result","Failed").add("Message", "NO Data About this Drone");
            finalArray.add(builder.build());
        }

        return finalArray.build();
//        return  Loaddroneservicebean.getdroneMedication(serial);
    }


}