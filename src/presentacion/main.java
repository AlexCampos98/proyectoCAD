/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectoignaciocad.ProyectoIgnacioCAD;
import proyectoignaciocad.excepcionProyecto;
import proyectoignaciocad.usuario;

/**
 *
 * @author Alejandro Campos Maestre
 */
public class main
{
    public static void main(String[] args)
    {
        ProyectoIgnacioCAD a = new ProyectoIgnacioCAD();
        try
        {
            ArrayList<usuario> b = a.leerUsuarios();
            System.out.println(b.toString());
            System.out.println("Se ha eliminado correctamente.");
        } catch (excepcionProyecto ex)
        {
            System.out.println(ex.toString());
        }
        
    }
}
