package org.example;

public class Car {
    public void changeGear(int gear){
        if(gear==1){
            System.out.println("Car is in first gear");
        }
        if (gear==2) {
            System.out.println("Car is in second gear");
        }
        if (gear==3 || gear==4 || gear==5) {
          throw new IllegalArgumentException("High gear not supported");
        }


    }



}
