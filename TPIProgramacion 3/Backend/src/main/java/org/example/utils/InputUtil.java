package org.example.utils;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner input  = new Scanner(System.in);

    public InputUtil() {
    }

    public static String leerString(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = input.nextLine().trim();

            if (!valor.isEmpty()) {
                return valor;
            }

            System.out.println("El campo no puede estar vacío.");
        }
    }

    public static long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(input.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    public static int leerEnteroPositivo(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = Integer.parseInt(input.nextLine().trim());

                if (valor >= 0) {
                    return valor;
                }

                System.out.println("El número no puede ser negativo.");

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    public static double leerDoublePositivo(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(input.nextLine().trim());

                if (valor >= 0) {
                    return valor;
                }

                System.out.println("El número no puede ser negativo.");

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    public static String leerStringOption(String mensaje, String valorActual) {
        System.out.print(mensaje);
        String valor =input.nextLine().trim();

        return valor.isEmpty() ? valorActual : valor;
    }

    public static int leerEnteroOption(String mensaje, int valorActual) {
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
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    public static double leerDoubleOption(String mensaje, double valorActual) {
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

                System.out.println("Ingrese un número válido.");

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    public static boolean leerBooleanOption(String mensaje, boolean valorActual) {
        while (true) {
            System.out.print(mensaje);
            String entrada = input.nextLine().trim();

            if (entrada.isEmpty()) {
                return valorActual;
            }

            if (entrada.equalsIgnoreCase("true")) {
                return true;
            }

            if (entrada.equalsIgnoreCase("false")) {
                return false;
            }

            System.out.println("Ingrese true o false.");
        }
    }

    public void Pausa(){
        input.nextLine();
    }

}
