package org.example;

import org.example.service.CategoriaService;
import org.example.service.MenuABM;
import org.example.service.ProductoService;

import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try{
            MenuABM menu = new MenuABM();
            menu.MenuABM();
        }catch (Exception e){
            System.out.println("Error al iniciar la aplicacion : " + e.getMessage());
        }



    }
}                 