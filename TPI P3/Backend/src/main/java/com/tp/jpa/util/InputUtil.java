package com.tp.jpa.util;

import com.tp.jpa.model.enums.Rol;

import java.util.Scanner;

public class InputUtil {

    private static final Scanner input = new Scanner(System.in);

    // Evita crear objetos InputUtil
    private InputUtil() {
    }


    // =========================
    // STRING
    // =========================

    public static String leerString(String mensaje) {

        String valor;

        do {
            System.out.print(mensaje);
            valor = input.nextLine().trim();

            if (valor.isEmpty()) {
                System.out.println("El valor no puede estar vacío.");
            }

        } while (valor.isEmpty());

        return valor;
    }


    // =========================
    // LONG
    // =========================

    public static long leerLong(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);
                return Long.parseLong(input.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Ingrese un número válido.");
            }
        }
    }


    // =========================
    // ENTERO POSITIVO
    // =========================

    public static int leerEnteroPositivo(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                int valor = Integer.parseInt(input.nextLine());

                if (valor >= 0) {
                    return valor;
                }

                System.out.println("El número no puede ser negativo.");

            } catch (NumberFormatException e) {

                System.out.println("Ingrese un número válido.");
            }
        }
    }


    // =========================
    // DOUBLE POSITIVO
    // =========================

    public static double leerDoublePositivo(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                double valor = Double.parseDouble(input.nextLine());

                if (valor >= 0) {
                    return valor;
                }

                System.out.println("El número no puede ser negativo.");

            } catch (NumberFormatException e) {

                System.out.println("Ingrese un número válido.");
            }
        }
    }


    // ==========================================
    // STRING OPCIONAL
    // Enter = mantener valor actual
    // ==========================================

    public static String leerStringOption(
            String mensaje,
            String valorActual) {

        System.out.print(mensaje);

        String valor = input.nextLine().trim();

        if (valor.isEmpty()) {
            return valorActual;
        }

        return valor;
    }


    // ==========================================
    // ENTERO OPCIONAL
    // Enter = mantener valor actual
    // ==========================================

    public static int leerEnteroOption(
            String mensaje,
            int valorActual) {

        while (true) {

            try {

                System.out.print(mensaje);

                String entrada = input.nextLine().trim();

                if (entrada.isEmpty()) {
                    return valorActual;
                }

                int valor = Integer.parseInt(entrada);

                if (valor >= 0) {
                    return valor;
                }

                System.out.println("El número no puede ser negativo.");

            } catch (NumberFormatException e) {

                System.out.println("Ingrese un número válido.");
            }
        }
    }


    // ==========================================
    // DOUBLE OPCIONAL
    // Enter = mantener valor actual
    // ==========================================

    public static double leerDoubleOption(
            String mensaje,
            double valorActual) {

        while (true) {

            try {

                System.out.print(mensaje);

                String entrada = input.nextLine().trim();

                if (entrada.isEmpty()) {
                    return valorActual;
                }

                double valor = Double.parseDouble(entrada);

                if (valor >= 0) {
                    return valor;
                }

                System.out.println("El número no puede ser negativo.");

            } catch (NumberFormatException e) {

                System.out.println("Ingrese un número válido.");
            }
        }
    }


    // ==========================================
    // BOOLEAN OPCIONAL
    // Enter = mantener valor actual
    // ==========================================

    public static boolean leerBooleanOption(
            String mensaje,
            boolean valorActual) {

        while (true) {

            System.out.print(mensaje + " (s/n): ");

            String entrada = input.nextLine()
                    .trim()
                    .toLowerCase();

            if (entrada.isEmpty()) {
                return valorActual;
            }

            if (entrada.equals("s")) {
                return true;
            }

            if (entrada.equals("n")) {
                return false;
            }

            System.out.println("Ingrese 's' o 'n'.");
        }
    }

    public static Rol leerRol(String mensaje) {

        while (true) {

            System.out.println(mensaje);
            System.out.println("1) ADMIN");
            System.out.println("2) CLIENTE");

            String opcion = input.nextLine().trim();

            switch (opcion) {
                case "1":
                    return Rol.ADMIN;

                case "2":
                    return Rol.USUARIO;

                default:
                    System.out.println("Rol inválido. Intente nuevamente.");
            }
        }
    }
}
