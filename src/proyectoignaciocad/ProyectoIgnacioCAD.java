/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoignaciocad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alejandro Campos Maestre
 */
public class ProyectoIgnacioCAD
{

    //----------Usuario-----------//
    public int insertarUsuario(usuario usuario) throws excepcionUsuario
    {
        String dml = "INSERT INTO usuario (id_usuario, correo, nombre, apellido1, apellido2, telefono, telefonoEmergencia, nombreUsuario) "
                + "VALUES (sequence_usuario.NEXTVAL," + usuario.correo + "," + usuario.nombre + "," + usuario.apellido1 + "," + usuario.apellido2 + ","
                + usuario.telefono + "," + usuario.telefonoEmergencia + "," + usuario.nombreUsuario + ")";
        int resultado = 0;

        try
        {
            //----- Acciones Previas: Cargar Driver en Memoria + Crear Conexión
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.69:1521:test", "proyecto", "kk");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.125:1521:test", "proyecto", "kk");

            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            resultado = sentencia.executeUpdate(dml);

            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException ex)
        {
            excepcionUsuario e = new excepcionUsuario();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        } catch (SQLException ex)
        {
            excepcionUsuario e = new excepcionUsuario();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(dml);

            switch (ex.getErrorCode())
            {
                case 1:
                    e.setMensajeErrorUsuario("El correo, el nombre de usuario o el telefono no pueden ser iguales que los de otros usuarios.");
                    break;

                case 2290: //Preguntar, la check como dividirlas, aunque habria que solucionarlas antes de llegar aqui.
                    e.setMensajeErrorUsuario("La check, preguntar a Ignacio.");
                    break;

                case 17008:
                    e.setMensajeErrorUsuario("Se ha perdido la conexion. Vuelva a intentarlo mas tarde");
                    break;

                case 20003:
                    e.setMensajeErrorUsuario("No puede haber mas vocales que consonantes en la union del nombre y los apellidos.");
                    break;

                default:
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
                    break;
            }

            throw e;
        }

        return resultado;
    }

    public int eliminarUsuario(Integer idUsuario) throws excepcionUsuario
    {
        String dml = "DELETE FROM usuario WHERE id_usuario = " + idUsuario;
        int resultado = 0;
        try
        {
            //----- Acciones Previas: Cargar Driver en Memoria + Crear Conexión
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.69:1521:test", "proyecto", "kk");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.125:1521:test", "proyecto", "kk");

            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            resultado = sentencia.executeUpdate(dml);

            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException ex)
        {
            excepcionUsuario e = new excepcionUsuario();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        } catch (SQLException ex)
        {
            excepcionUsuario e = new excepcionUsuario();
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorAdministrador(ex.getMessage());
            switch (ex.getErrorCode())
            {
                case 2292:
                    e.setMensajeErrorUsuario("No se puede eliminar al usuario, debido a que esta relacionado con algun entrenamiento.");
                    break;

                case 17008:
                    e.setMensajeErrorUsuario("Se ha perdido la conexion. Vuelva a intentarlo mas tarde");
                    break;

                default:
                    e.setMensajeErrorUsuario("Error al realizar la eliminacion. Compruebe los datos.");
                    break;
            }
            e.setSentenciaSQL(dml);
            throw e;
        }
        return resultado;
    }

    public int modificarUsuario(usuario usuario) throws excepcionUsuario
    {
        String dml = "UPDATE usuario SET correo='" + usuario.correo + "', nombre='" + usuario.nombre + "', apellido1='" + usuario.apellido1 + "', "
                + "apellido2='" + usuario.apellido2 + "', telefono='" + usuario.telefono + "', telefonoEmergencia='" + usuario.telefonoEmergencia + "', "
                + "nombreUsuario='" + usuario.nombreUsuario + "' WHERE id_usuario = " + usuario.getIdUsuario();
        int resultado = 0;

        try
        {
            //----- Acciones Previas: Cargar Driver en Memoria + Crear Conexión
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.69:1521:test", "proyecto", "kk");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.125:1521:test", "proyecto", "kk");

            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            resultado = sentencia.executeUpdate(dml);

            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException ex)
        {
            excepcionUsuario e = new excepcionUsuario();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        } catch (SQLException ex)
        {
            excepcionUsuario e = new excepcionUsuario();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(dml);

            switch (ex.getErrorCode())
            {
                case 1:
                    e.setMensajeErrorUsuario("El correo, el nombre de usuario o el telefono no pueden ser iguales que los de otros usuarios.");
                    break;
                case 2290: //Preguntar, la check como dividirlas, aunque habria que solucionarlas antes de llegar aqui.
                    e.setMensajeErrorUsuario("La check, preguntar a Ignacio.");
                    break;

                case 17008:
                    e.setMensajeErrorUsuario("Se ha perdido la conexion. Vuelva a intentarlo mas tarde");
                    break;

                case 20003:
                    e.setMensajeErrorUsuario("No puede haber mas vocales que consonantes en la union del nombre y los apellidos.");
                    break;

                default:
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
                    break;
            }

            throw e;
        }

        return resultado;
    }

    public usuario leerUsuario(Integer idUsuario)
    {
//        String dql = "SELECT * FROM usuario WHERE id_usuario = " + idUsuario;
//        usuario resultado;
//        try
//        {
//            //----- Acciones Previas: Cargar Driver en Memoria + Crear Conexión
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            //Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.69:1521:test", "proyecto", "kk");
//            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.125:1521:test", "proyecto", "kk");
//
//            Statement sentencia = conexion.createStatement();
//
//            //----- Lanzamiento de una sentencia DQL
//            resultado = (usuario) sentencia.executeQuery(dql);
//
//            //----- Cerrar la Conexión a la BD
//            sentencia.close();
//            conexion.close();
//
//        } catch (ClassNotFoundException ex)
//        {
//            Logger.getLogger(ProyectoIgnacioCAD.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex)
//        {
//            Logger.getLogger(ProyectoIgnacioCAD.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return null;
    }

    public ArrayList<usuario> leerUsuarios()
    {
        return null;
    }

    //----------Entrenamiento-----------//
    public int insertarEntrenamiento(entrenamiento entrenamiento)
    {
        return 0;
    }

    public int eliminarEntrenamiento(Integer idEntrenamiento)
    {
        return 0;
    }

    public int modificarEntrenamiento(entrenamiento entrenamiento)
    {
        return 0;
    }

    public entrenamiento leerEntrenamiento(Integer idEntrenamiento)
    {
        return null;
    }

    public ArrayList<entrenamiento> leerEntrenamientos()
    {
        return null;
    }
}
