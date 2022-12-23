package com.bamwine.droneapi.services;
import com.bamwine.droneapi.entity.Drone;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.math.BigDecimal;
import java.util.List;

@RequestScoped
@Path("/drone")
public class DroneResource {
    @Inject
    DroneServicebean droneServicebean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/{serial}")
    public Drone getDronedetails(@PathParam("serial" )String serial) {

    return  droneServicebean.findDrone(serial);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/batterylevel/{serial}")
//    public BigDecimal getDroneBatterylevel(@PathParam("serial" )String serial) {
    public JsonArray getDroneBatterylevel(@PathParam("serial" )String serial) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        Drone nowdrone =droneServicebean.Dronebatterylevel(serial);
        if(nowdrone!=null){
            builder.add("Result", "Success")
                    .add("Message", "Drone Battery Results")
                    .add("Serial Number", nowdrone.getSerial())
                    .add("Batterylevel", nowdrone.getBattery());

            finalArray.add(builder.build());
        } else{
            builder.add("Result", "Failed")
                    .add("Message", "Drone Not found with ")
                    .add("Serial Number", serial);

            finalArray.add(builder.build());
        }


        return finalArray.build();

//        return  droneServicebean.Dronebatterylevel(serial);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Drone> getDrones() {

        return  droneServicebean.getallDrones();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/available")
    public List<Drone> getDroneforloading() {

        return  droneServicebean.Dronesforloading();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray addDrones(Drone drone) {
//    public Drone addDrones( Drone drone) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();

        Drone nowdrone =droneServicebean.addDrone(drone);

        builder.add("Result", "Success")
                .add("Message", "Drone Successfully Registered")
                .add("Serial Number", nowdrone.getSerial())
                .add("WeightLimit", nowdrone.getWeightLimit())
                .add("state", nowdrone.getState().toString());

        finalArray.add(builder.build());
        return finalArray.build();
//        return  droneServicebean.addDrone(drone);
    }


}