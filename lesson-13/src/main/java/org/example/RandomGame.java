package org.example;

import java.util.Scanner;

import java.util.Scanner;

public class RandomGame {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int a = (int) (Math.random() * 100);

        System.out.println("Oyun başladı! 0-100 arası rəqəmi tap:");

        int b = sc.nextInt();

        while (b != a) {

            if (b < a) {
                System.out.println("Daxil etdiyiniz rəqəm randomdan kiçikdir, yenidən cəhd edin:");
            } else {
                System.out.println("Daxil etdiyiniz rəqəm randomdan böyükdür, yenidən cəhd edin:");
            }

            b = sc.nextInt();
        }

        System.out.println("Təbriklər! Rəqəmi tapdınız!");
    }
}
