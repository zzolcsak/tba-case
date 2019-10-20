package com.zzolcsak.vehicle.presenter;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		String nextMove = null;
		try (Scanner scanner = new Scanner(System.in)) {
			while (!"exit".equals(nextMove)) {
				System.out.print("Next move:");
				nextMove = scanner.nextLine();
				if ("help".equals(nextMove)) {
					System.out.println("Available moves: help, exit");
				}
			}
		}
	}

}
