/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoignaciocad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author Alejandro Campos Maestre
 */
public class ProyectoIgnacioCAD
{

    Connection conexion;

    /**
     * Carga el Driver en Memoria
     *
     * @throws proyectoignaciocad.excepcionProyecto Cuando se produzca cualquier
     * error
     */
    public ProyectoIgnacioCAD() throws excepcionProyecto
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        }
    }

    /**
     * Metodo usado para conectar con la BD
     *
     * @throws excepcionProyecto
     */
    private void conexion() throws excepcionProyecto
    {
        try
        {
            //conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.69:1521:test", "proyecto", "kk");
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.125:1521:test", "proyecto", "kk");
        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        }
    }

    //----------Usuario-----------//
    /**
     * Inserta un registro de usuario a la base de datos, tabla usuario.
     * Devuelve el numero de registros insertados.
     *
     * @param usuario Clase solicitada con los datos necesarios del usuario para
     * inserta en la BD.
     * @return Cantidad de registros añadidos
     * @throws excepcionProyecto Cuando se produzca cualquier error con la
     * conexion o con excepciones SQL.
     */
    public int insertarUsuario(usuario usuario) throws excepcionProyecto
    {
        conexion();
        String dml = "INSERT INTO usuario (id_usuario, correo, nombre, apellido1, apellido2, telefono, telefonoEmergencia, nombreUsuario) "
                + "VALUES (sequence_usuario.NEXTVAL,?,?,?,?,?,?,?)";
        int resultado = 0;
        try
        {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, usuario.getCorreo());
            sentenciaPreparada.setString(2, usuario.getNombre());
            sentenciaPreparada.setString(3, usuario.getApellido1());
            sentenciaPreparada.setString(4, usuario.getApellido2());
            sentenciaPreparada.setString(5, usuario.getTelefono());
            sentenciaPreparada.setString(6, usuario.getTelefonoEmergencia());
            sentenciaPreparada.setString(7, usuario.getNombreUsuario());

            //----- Lanzamiento de una sentencia DQL
            resultado = sentenciaPreparada.executeUpdate();

            //----- Cerrar la Conexión a la BD
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(dml);

            switch (ex.getErrorCode())
            {
                case 1:
                    e.setMensajeErrorUsuario("El correo, el nombre de usuario o el telefono no pueden ser iguales que los de otros usuarios.");
                    break;

                case 1400:
                    e.setMensajeErrorUsuario("No puede haber ningun campo vacio.");
                    break;

                case 2290: //Preguntar, la check como dividirlas, aunque habria que solucionarlas antes de llegar aqui.
                    e.setMensajeErrorUsuario("La check, preguntar a Ignacio.");
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

    /**
     * Elimina el registro de la tabla usuario. Necesita el identificador del
     * registro.
     *
     * @param idUsuario indica el valor del campo usuario.id_usuario del
     * registro a eliminar
     * @return Cantidad de registros eliminados
     * @throws excepcionProyecto Cuando se produzca cualquier excepcion en la
     * conexion a la BD
     */
    public int eliminarUsuario(Integer idUsuario) throws excepcionProyecto
    {
        conexion();
        String dml = "DELETE FROM usuario WHERE id_usuario = ?";
        int resultado = 0;
        try
        {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1, idUsuario, Types.INTEGER);

            //----- Lanzamiento de una sentencia DQL
            resultado = sentenciaPreparada.executeUpdate();

            //----- Cerrar la Conexión a la BD
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setSentenciaSQL(dml + " -> ? = " + idUsuario);
            switch (ex.getErrorCode())
            {
                case 2292:
                    e.setMensajeErrorUsuario("No se puede eliminar al usuario, debido a que esta relacionado con algun entrenamiento.");
                    break;

                default:
                    e.setMensajeErrorUsuario("Error al realizar la eliminacion. Compruebe los datos.");
                    break;
            }
            throw e;
        }
        return resultado;
    }

    public int modificarUsuario(usuario usuario) throws excepcionProyecto
    {
        conexion();
        String dml = "UPDATE usuario SET correo='" + usuario.correo + "', nombre='" + usuario.nombre + "', apellido1='" + usuario.apellido1 + "', "
                + "apellido2='" + usuario.apellido2 + "', telefono='" + usuario.telefono + "', telefonoEmergencia='" + usuario.telefonoEmergencia + "', "
                + "nombreUsuario='" + usuario.nombreUsuario + "' WHERE id_usuario = " + usuario.getIdUsuario();
        int resultado = 0;

        try
        {
            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            resultado = sentencia.executeUpdate(dml);

            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
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

    public usuario leerUsuario(Integer idUsuario) throws excepcionProyecto
    {
        conexion();
        String dql = "SELECT * FROM usuario WHERE id_usuario = " + idUsuario;
        usuario usuario = new usuario();
        try
        {
            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            ResultSet resultado = sentencia.executeQuery(dql);

            if (resultado.next())
            {
                usuario.setApellido1(resultado.getString("apellido1"));
                usuario.setApellido2(resultado.getString("apellido2"));
                usuario.setCorreo(resultado.getString("correo"));
                usuario.setIdUsuario(resultado.getInt("id_usuario"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setNombreUsuario(resultado.getString("nombreUsuario"));
                usuario.setTelefono(resultado.getString("telefono"));
                usuario.setTelefonoEmergencia(resultado.getString("telefonoEmergencia"));
            }
            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        }
        return usuario;
    }

    public ArrayList<usuario> leerUsuarios() throws excepcionProyecto
    {
        conexion();
        String dql = "SELECT * FROM usuario";
        ArrayList<usuario> usuarios = new ArrayList<>();
        try
        {
            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            ResultSet resultado = sentencia.executeQuery(dql);

            while (resultado.next())
            {
                usuario usu = new usuario();
                usu.setApellido1(resultado.getString("apellido1"));
                usu.setApellido2(resultado.getString("apellido2"));
                usu.setCorreo(resultado.getString("correo"));
                usu.setIdUsuario(resultado.getInt("id_usuario"));
                usu.setNombre(resultado.getString("nombre"));
                usu.setNombreUsuario(resultado.getString("nombreUsuario"));
                usu.setTelefono(resultado.getString("telefono"));
                usu.setTelefonoEmergencia(resultado.getString("telefonoEmergencia"));
                usuarios.add(usu);
            }

            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");

            throw e;
        }
        return usuarios;
    }

    //----------Entrenamiento-----------//
    public int insertarEntrenamiento(entrenamiento entrenamiento) throws excepcionProyecto
    {
        conexion();
        String dml = "INSERT INTO entrenamiento (ID_ENTRENAMIENTO, NOMBRE, FECHA, PLAZAS, ID_USUARIO_ENTRENADOR, ID_USUARIO_DEPORTISTA) "
                + "VALUES (sequence_entrenamiento.NEXTVAL,?,?,?,?,?)";
        int resultado = 0;

        try
        {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, entrenamiento.getNombre());
            sentenciaPreparada.setObject(2, entrenamiento.getFecha(), Types.DATE);
            sentenciaPreparada.setObject(3, entrenamiento.getPlazas(), Types.INTEGER);
            sentenciaPreparada.setObject(4, entrenamiento.getIdUsuarioEntrenador().getIdUsuario(), Types.INTEGER);
            sentenciaPreparada.setObject(5, entrenamiento.getIdUsuarioDeportista().getIdUsuario(), Types.INTEGER);

            //----- Lanzamiento de una sentencia DQL
            resultado = sentenciaPreparada.executeUpdate();

            //----- Cerrar la Conexión a la BD
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(dml);

            switch (ex.getErrorCode())
            {
                case 1400:
                    e.setMensajeErrorUsuario("No puede haber ningun campo vacio.");
                    break;

                case 2291:
                    e.setMensajeErrorUsuario("El entrenador o del deportista no existe.");
                    break;

                case 2290:
                    e.setMensajeErrorUsuario("No puede haber un numero negativo de plazas.");
                    break;

                case 20001:
                    e.setMensajeErrorUsuario("El deportistas no puede tener mas de dos entrenamientos el mismo dia.");
                    break;

                case 20002:
                    e.setMensajeErrorUsuario("Se ha alcanzado el maximo de entrenamientos en el dia " + entrenamiento.fecha + " (Max 8).");
                    break;

                default:
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
                    break;
            }

            throw e;
        }

        return resultado;
    }

    public int eliminarEntrenamiento(Integer idEntrenamiento) throws excepcionProyecto
    {
        conexion();
        String dml = "DELETE FROM entrenamiento WHERE id_entrenamiento = " + idEntrenamiento;
        int resultado = 0;
        try
        {
            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            resultado = sentencia.executeUpdate(dml);

            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            e.setSentenciaSQL(dml);
            throw e;
        }
        return resultado;
    }

    public int modificarEntrenamiento(entrenamiento entrenamiento) throws excepcionProyecto
    {
        conexion();
        String dml = "UPDATE entrenamiento SET nombre='" + entrenamiento.nombre + "', fecha='" + entrenamiento.fecha + "', ID_USUARIO_DEPORTISTA='" + entrenamiento.idUsuarioDeportista + "', "
                + "ID_USUARIO_ENTRENADOR='" + entrenamiento.idUsuarioEntrenador + "', plazas='" + entrenamiento.plazas + "' WHERE id_entrenamiento = " + entrenamiento.idEntrenamiento;
        int resultado = 0;

        try
        {
            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            resultado = sentencia.executeUpdate(dml);

            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(dml);

            switch (ex.getErrorCode())
            {
                case 2291:
                    e.setMensajeErrorUsuario("El identificador del entrenador o del deportista no existe.");
                    break;

                case 2290:
                    e.setMensajeErrorUsuario("No puede haber un numero negativo de plazas.");
                    break;

                case 20001:
                    e.setMensajeErrorUsuario("El deportistas no puede tener mas de dos entrenamientos el mismo dia.");
                    break;

                case 20002:
                    e.setMensajeErrorUsuario("Se ha alcanzado el maximo de entrenamientos en el dia " + entrenamiento.fecha + " (Max 8).");
                    break;

                default:
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
                    break;
            }

            throw e;
        }

        return resultado;
    }

    public entrenamiento leerEntrenamiento(Integer idEntrenamiento) throws excepcionProyecto
    {
        conexion();
        String dql = "SELECT * FROM entrenamiento WHERE id_entrenamiento = " + idEntrenamiento;
        entrenamiento entrenamiento = new entrenamiento();
        try
        {
            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            ResultSet resultado = sentencia.executeQuery(dql);

            if (resultado.next())
            {
                entrenamiento.setFecha(resultado.getDate("fecha"));
                entrenamiento.setIdEntrenamiento(resultado.getInt("id_entrenamiento"));
                //Tengo que recibir un usuario, preguntar.
//                entrenamiento.setIdUsuarioDeportista(idUsuarioDeportista);
//                entrenamiento.setIdUsuarioEntrenador(resultado.getInt("id_usuario"));
                entrenamiento.setNombre(resultado.getString("nombre"));
                entrenamiento.setPlazas(resultado.getInt("plazas"));
            }
            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");

            throw e;
        }
        return entrenamiento;
    }

    public ArrayList<entrenamiento> leerEntrenamientos() throws excepcionProyecto
    {
        conexion();
        String dql = "SELECT * FROM entrenamiento";
        ArrayList<entrenamiento> entrenamientos = new ArrayList<>();
        try
        {
            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            ResultSet resultado = sentencia.executeQuery(dql);

            while (resultado.next())
            {
                entrenamiento entre = new entrenamiento();
                entre.setFecha(resultado.getDate("fecha"));
                entre.setIdEntrenamiento(resultado.getInt("id_entrenamiento"));
                //Tengo que recibir un usuario, preguntar.
//                entrenamiento.setIdUsuarioDeportista(idUsuarioDeportista);
//                entrenamiento.setIdUsuarioEntrenador(resultado.getInt("id_usuario"));
                entre.setNombre(resultado.getString("nombre"));
                entre.setPlazas(resultado.getInt("plazas"));
                entrenamientos.add(entre);
            }

            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");

            throw e;
        }
        return entrenamientos;
    }
}
