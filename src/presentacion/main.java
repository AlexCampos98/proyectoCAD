/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.util.logging.Level;
import java.util.logging.Logger;
import proyectoignaciocad.ProyectoIgnacioCAD;
import proyectoignaciocad.excepcionUsuario;

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
            a.eliminarUsuario(10);
            System.out.println("Se ha eliminado correctamente.");
        } catch (excepcionUsuario ex)
        {
            System.out.println(ex.toString());
        }
        
    }
}
