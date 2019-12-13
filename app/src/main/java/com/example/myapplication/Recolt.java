package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.io.Serializable;

@Entity(tableName = MyDatabase.Racolt)
public class Recolt implements Serializable {

 @PrimaryKey
  int a;
    @ColumnInfo
    double laltitude;

    @ColumnInfo
    double longitude;

    @ColumnInfo
    double speed;

    @ColumnInfo
    double direction;

    @ColumnInfo
    double x;

    @ColumnInfo
    double y;

    @ColumnInfo
    double z;


    @ColumnInfo
    double vilocity;


    public Recolt() { }


    public double getLaltitude() {
        return laltitude;
    }

    public  double getLongitude() {
        return longitude;
    }

    public  double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }

    public double getX() { return x; }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getVilocity() {return vilocity; }

    public void setLaltitude(double laltitude) {this.laltitude = laltitude; }

    public  void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public  void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(double direction) { this.direction = direction; }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setVilocity(double vilocity) {this.vilocity = vilocity;}
}

