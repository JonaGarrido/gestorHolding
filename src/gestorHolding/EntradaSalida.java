package gestorHolding;

import java.util.Scanner;

public class EntradaSalida {

    
    /** 
     * @param texto
     * @return double
     */
    public static double leerDouble(String texto) {
        Scanner sc = new Scanner(System.in);
        System.out.println(texto);
        double s = sc.nextInt();
        return s;
    }

    public static int leerInt(String texto) {
        Scanner sc = new Scanner(System.in);
        System.out.println(texto);
        int s = sc.nextInt();
        return s;
    }

    public static char leerChar(String texto) {
        String s;
        Scanner sc = new Scanner(System.in);
        System.out.println(texto);
        s = sc.nextLine();
        return (s == null || s.length() == 0 ? '0' : s.charAt(0));
    }

    public static String leerString(String texto) {
        String s;
        Scanner sc = new Scanner(System.in);
        System.out.println(texto);
        s = sc.nextLine();
        return (s == null ? "" : s);
    }

    public static boolean leerBoolean(String texto) {
        System.out.println(texto + " (s o n): ");
        String s;
        Scanner sc = new Scanner(System.in);
        s = sc.nextLine();
        return (s.equals("si") || s.equals("s"));
    }

    public static void mostrarString(String s) {
        System.out.println(s);
    }
}
