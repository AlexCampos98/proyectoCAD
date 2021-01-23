/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectocad.Entrenamiento;
import proyectocad.ProyectoCAD;
import proyectocad.ExcepcionProyecto;
import proyectocad.Usuario;

/**
 *
 * @author Alejandro Campos Maestre
 */
public class main
{
    public static void main(String[] args)
    {
        
        try
        {
            ProyectoCAD a = new ProyectoCAD();
//            ProyectoCAD a = new ProyectoCAD("192.168.1.125", "proyecto", "kk");
//            ArrayList<Usuario> b = a.leerUsuarios();
//            Usuario usuario = new Usuario(null, "alex@hotmail.com", "Alexggggg", "ape1", "ape2", "456789456", "216457984", "Alex69");
//            a.insertarUsuario(usuario);
            
            //Usuario usuarioM = new Usuario(510, "alex@hotmail.com", "Alexggggg1", "ape1", "ape2", "456789456", "216457984", "Alex69");
            
            //a.modificarUsuario(usuarioM);
//            ArrayList<Usuario> usu = a.leerUsuarios();
//            Iterator ite = usu.iterator();
//            while(ite.hasNext())
//            {
//                
//                System.out.println(ite.next().toString());
//            }
            //System.out.println(b);
            
            Usuario idUsuarioEntrenador = new Usuario(1);
            Usuario idUsuarioDeportista = new Usuario(510);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaUtil = sdf.parse("2010-35-55");
            java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
            
            Entrenamiento entrenamiento = new Entrenamiento(null, 5, "aaaa", fechaSql, idUsuarioEntrenador, idUsuarioDeportista);
            //Solo comprobado el campo vacio
            int b = a.insertarEntrenamiento(entrenamiento);
            System.out.println(b);
        } catch (ExcepcionProyecto ex)
        {
            System.out.println(ex.toString());
        } catch (ParseException ex)
        {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
