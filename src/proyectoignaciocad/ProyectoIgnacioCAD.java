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

/**
 *
 * @author Alejandro Campos Maestre
 */
public class ProyectoIgnacioCAD
{

    //----------Usuario-----------//
    public int insertarUsuario(usuario usuario)
    {
        return 0;
    }

    public int eliminarUsuario(Integer idUsuario) throws excepcionUsuario
    {
        String dml = "DELETE FROM usuario WHERE id_usuario = " + idUsuario;
        int resultado = 0;
        try
        {
            //----- Acciones Previas: Cargar Driver en Memoria + Crear Conexión
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.69:1521:test", "proyecto", "kk");

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
            if (ex.getErrorCode() == 2292)
            {
                e.setMensajeErrorUsuario("El usuario esta relacionado con algo 'En mi caso es impsible que de'");
            } else
            {
                e.setMensajeErrorUsuario("Error al realizar la eliminacion. Compruebe los datos.");
            }
            e.setSentenciaSQL(dml);
            throw e;
        }
        return resultado;
    }

    public int modificarUsuario(usuario usuario)
    {
        return 0;
    }

    public usuario leerUsuario(Integer idUsuario)
    {
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
