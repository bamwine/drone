package com.bamwine.droneapi.entity;
import com.bamwine.droneapi.enums.MODEL;
import com.bamwine.droneapi.enums.STATE;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "drone")
public class Drone implements Serializable {

    @Id
    @TableGenerator(name = "DroneDRN", table = "ID_DRN", pkColumnName = "DRN_NAME", valueColumnName = "DRN_VAL", pkColumnValue = "DroneDRN", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "DroneDRN")
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @NotNull
    @Size(max = 255, message = "Max 255 characters!")
    @Column(name = "serial",unique = true )
    private String  serial;

//
    @Max(500)
    @NotNull
    @Column(name = "weightLimit")
    private double weightLimit;
    @NotNull
    @Column(name = "battery",precision = 10, scale = 2)
    private BigDecimal battery;
    @NotNull
    @Column(name = "model")
    @Enumerated(EnumType.STRING)
    private MODEL model;
    @NotNull
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private STATE state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public BigDecimal getBattery() {
        return battery;
    }

    public void setBattery(BigDecimal battery) {
        this.battery = battery;
    }

    public MODEL getModel() {
        return model;
    }

    public void setModel(MODEL model) {
        this.model = model;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }
}