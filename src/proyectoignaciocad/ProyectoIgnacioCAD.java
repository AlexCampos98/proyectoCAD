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
    public int insertarUsuario(usuario usuario) throws excepcionProyecto
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
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
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

    public int eliminarUsuario(Integer idUsuario) throws excepcionProyecto
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
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
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

    public int modificarUsuario(usuario usuario) throws excepcionProyecto
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
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
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

    public usuario leerUsuario(Integer idUsuario) throws excepcionProyecto
    {
        String dql = "SELECT * FROM usuario WHERE id_usuario = " + idUsuario;
        usuario usuario = new usuario();
        try
        {
            //----- Acciones Previas: Cargar Driver en Memoria + Crear Conexión
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.69:1521:test", "proyecto", "kk");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.125:1521:test", "proyecto", "kk");

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

        } catch (ClassNotFoundException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());

            if (ex.getErrorCode() == 17008)
            {
                e.setMensajeErrorUsuario("Se ha perdido la conexion. Vuelva a intentarlo mas tarde");
            } else
            {
                e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            }

            throw e;
        }
        return usuario;
    }

    public ArrayList<usuario> leerUsuarios() throws excepcionProyecto
    {
        String dql = "SELECT * FROM usuario";
        ArrayList<usuario> usuarios = new ArrayList<>();
        try
        {
            //----- Acciones Previas: Cargar Driver en Memoria + Crear Conexión
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.69:1521:test", "proyecto", "kk");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.125:1521:test", "proyecto", "kk");

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

        } catch (ClassNotFoundException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());

            if (ex.getErrorCode() == 17008)
            {
                e.setMensajeErrorUsuario("Se ha perdido la conexion. Vuelva a intentarlo mas tarde");
            } else
            {
                e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            }
            throw e;
        }
        return usuarios;
    }

    //----------Entrenamiento-----------//
    public int insertarEntrenamiento(entrenamiento entrenamiento) throws excepcionProyecto
    {
        String dml = "INSERT INTO entrenamiento (ID_ENTRENAMIENTO, NOMBRE, FECHA, PLAZAS, ID_USUARIO_ENTRENADOR, ID_USUARIO_DEPORTISTA) "
                + "VALUES (sequence_entrenamiento.NEXTVAL," + entrenamiento.nombre + "," + entrenamiento.fecha + "," + entrenamiento.plazas + ","
                + entrenamiento.idUsuarioEntrenador + "," + entrenamiento.idUsuarioDeportista + ")";
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
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
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

                case 17008:
                    e.setMensajeErrorUsuario("Se ha perdido la conexion. Vuelva a intentarlo mas tarde");
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
        String dml = "DELETE FROM entrenamiento WHERE id_entrenamiento = " + idEntrenamiento;
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
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorAdministrador(ex.getMessage());
            switch (ex.getErrorCode())
            {
                case 17008:
                    e.setMensajeErrorUsuario("Se ha perdido la conexion. Vuelva a intentarlo mas tarde");
                    break;

                default:
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
                    break;
            }
            e.setSentenciaSQL(dml);
            throw e;
        }
        return resultado;
    }

    public int modificarEntrenamiento(entrenamiento entrenamiento) throws excepcionProyecto
    {
        String dml = "UPDATE entrenamiento SET nombre='" + entrenamiento.nombre + "', fecha='" + entrenamiento.fecha + "', ID_USUARIO_DEPORTISTA='" + entrenamiento.idUsuarioDeportista + "', "
                + "ID_USUARIO_ENTRENADOR='" + entrenamiento.idUsuarioEntrenador + "', plazas='" + entrenamiento.plazas + "' WHERE id_entrenamiento = " + entrenamiento.idEntrenamiento;
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
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
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

                case 17008:
                    e.setMensajeErrorUsuario("Se ha perdido la conexion. Vuelva a intentarlo mas tarde");
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
        String dql = "SELECT * FROM entrenamiento WHERE id_entrenamiento = " + idEntrenamiento;
        entrenamiento entrenamiento = new entrenamiento();
        try
        {
            //----- Acciones Previas: Cargar Driver en Memoria + Crear Conexión
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.69:1521:test", "proyecto", "kk");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.125:1521:test", "proyecto", "kk");

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

        } catch (ClassNotFoundException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());

            if (ex.getErrorCode() == 17008)
            {
                e.setMensajeErrorUsuario("Se ha perdido la conexion. Vuelva a intentarlo mas tarde");
            } else
            {
                e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            }

            throw e;
        }
        return entrenamiento;
    }

    public ArrayList<entrenamiento> leerEntrenamientos() throws excepcionProyecto
    {
        String dql = "SELECT * FROM entrenamiento";
        ArrayList<entrenamiento> entrenamientos = new ArrayList<>();
        try
        {
            //----- Acciones Previas: Cargar Driver en Memoria + Crear Conexión
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.209.69:1521:test", "proyecto", "kk");
            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.125:1521:test", "proyecto", "kk");

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

        } catch (ClassNotFoundException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());

            if (ex.getErrorCode() == 17008)
            {
                e.setMensajeErrorUsuario("Se ha perdido la conexion. Vuelva a intentarlo mas tarde");
            } else
            {
                e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            }
            throw e;
        }
        return entrenamientos;
    }
}
