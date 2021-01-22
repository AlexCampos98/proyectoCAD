/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoignaciocad;

import java.sql.CallableStatement;
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
     * Constructor con la carga del Driver en Memoria
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
     * @throws excepcionProyecto Cuando se produzca cualquier error
     */
    private void conectar() throws excepcionProyecto
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
        conectar();
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
        conectar();
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

    /**
     * Modifica un registro de la tabla usuario. No se modifica el
     * identificador.
     *
     * @param usuario Objeto de tipo usuario con los datos para proceder a la
     * modficacion
     * @return 0. No devuelve la cantidad de registros modificados.
     * @throws excepcionProyecto Cuando se produzca cualquier error en la
     * conexio o sentencia SQL.
     */
    public int modificarUsuario(usuario usuario) throws excepcionProyecto
    {
        conectar();

        String llamada = "call modificar_usuario(?,?,?,?,?,?,?,?)";

        try
        {
            CallableStatement sentenciaLlamable = conexion.prepareCall(llamada);
            sentenciaLlamable.setObject(1, usuario.getIdUsuario(), Types.INTEGER);
            sentenciaLlamable.setString(2, usuario.getCorreo());
            sentenciaLlamable.setString(3, usuario.getNombre());
            sentenciaLlamable.setString(4, usuario.getApellido1());
            sentenciaLlamable.setString(5, usuario.getApellido2());
            sentenciaLlamable.setString(6, usuario.getTelefono());
            sentenciaLlamable.setString(7, usuario.getTelefonoEmergencia());
            sentenciaLlamable.setString(8, usuario.getNombreUsuario());

            //----- Lanzamiento del procedimiento
            sentenciaLlamable.executeUpdate();

            //----- Cerrar la Conexión a la BD
            sentenciaLlamable.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(llamada);

            switch (ex.getErrorCode())
            {
                case 1:
                    e.setMensajeErrorUsuario("El correo, el nombre de usuario o el telefono no pueden ser iguales que los de otros usuarios.");
                    break;

                case 1407:
                    e.setMensajeErrorUsuario("Es obligatorio rellenar todos los datos.");
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

        return 0;
    }

    /**
     * Lectura de los datos de la tabla usuario, buscando por la id del usuario.
     *
     * @param idUsuario Numero de busqueda por ID. Tipo INTEGER.
     * @return Devuelve un objeto de tipo usuario con los datos del usaurio
     * selecionado.
     * @throws excepcionProyecto Devuelve cualquier error.
     */
    public usuario leerUsuario(Integer idUsuario) throws excepcionProyecto
    {
        conectar();
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

    /**
     * Lectura de todos los registros de la tabla usuario.
     *
     * @return Devuelve un arrayList con objetos usuario con todos los registros
     * de la tabla usuario.
     * @throws excepcionProyecto Devuelve cualquier tipo de error.
     */
    public ArrayList<usuario> leerUsuarios() throws excepcionProyecto
    {
        conectar();
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
    /**
     * Inserta un registro en la tabla de entrenamiento
     *
     * @param entrenamiento Objeto entrenamiento con los datos necesarios para
     * la insercion de los mismos en la BD.
     * @return El numero de registros afectados.
     * @throws excepcionProyecto Devuelve cualquier tipo de error.
     */
    public int insertarEntrenamiento(entrenamiento entrenamiento) throws excepcionProyecto
    {
        conectar();
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

    /**
     * Eliminacion de un registro de la tabla usuario, por el identificador del
     * entrenamiento
     *
     * @param idEntrenamiento Numero de identificador de la tabla entrenamiento.
     * @return Devuelve el numero de registros afectados.
     * @throws excepcionProyecto Devuelve cualquier error.
     */
    public int eliminarEntrenamiento(Integer idEntrenamiento) throws excepcionProyecto
    {
        conectar();
        String dml = "DELETE FROM entrenamiento WHERE id_entrenamiento = ?";
        int resultado = 0;
        try
        {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1, idEntrenamiento, Types.INTEGER);

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
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            e.setSentenciaSQL(dml);
            throw e;
        }
        return resultado;
    }

    /**
     * Modificacion de un registro de la tabla entrenamiento
     *
     * @param entrenamiento Objeto entrenamiento con los datos necesarios para
     * la modificacion del registro.
     * @return 0. No devuelve el numero de registros modificados.
     * @throws excepcionProyecto Devuelve cualquer errror producido.
     */
    public int modificarEntrenamiento(entrenamiento entrenamiento) throws excepcionProyecto
    {
        conectar();
        String llamada = "call modificar_entrenamiento(?,?,?,?,?,?)";

        try
        {
            CallableStatement sentenciaLlamada = conexion.prepareCall(llamada);

            sentenciaLlamada.setObject(1, entrenamiento.getIdEntrenamiento(), Types.INTEGER);
            sentenciaLlamada.setString(2, entrenamiento.getNombre());
            sentenciaLlamada.setObject(3, entrenamiento.fecha, Types.DATE);
            sentenciaLlamada.setObject(4, entrenamiento.getPlazas(), Types.INTEGER);
            sentenciaLlamada.setObject(5, entrenamiento.getIdUsuarioEntrenador().getIdUsuario(), Types.INTEGER);
            sentenciaLlamada.setObject(6, entrenamiento.getIdUsuarioDeportista().getIdUsuario(), Types.INTEGER);

            //----- Lanzamiento de una sentencia DQL
            sentenciaLlamada.executeUpdate();

            //----- Cerrar la Conexión a la BD
            sentenciaLlamada.close();
            conexion.close();

        } catch (SQLException ex)
        {
            excepcionProyecto e = new excepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(llamada);

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

        return 0;
    }

    /**
     * Lectura de un registro de la tabla entrenamiento, buscado por su
     * identificador.
     *
     * @param idEntrenamiento Identificador del registro de la tabla de
     * entrenamiento.
     * @return Devuelve un objeto de tipo entrenamiento, con los datos del
     * registro.
     * @throws excepcionProyecto Devuelve cualquier error.
     */
    public entrenamiento leerEntrenamiento(Integer idEntrenamiento) throws excepcionProyecto
    {
        conectar();
        String dql = "SELECT * FROM entrenamiento WHERE id_entrenamiento = " + idEntrenamiento;

        /*
        *   Posible solucion
        *   SELECT a.*, e.nombre AS nombreEntrenador, e.apellido1 AS apellido1Entrenador, e.apellido2 AS apellido2Entrenador, e.correo AS correoEntrenador, e.id_usuario AS id_usuarioEntrenador
            , e.nombreusuario AS nombreUsuarioEntrenador, e.telefono AS telefonoEntrenador, e.telefonoemergencia AS telefonoEmergenciaEntrenador, d.nombre AS nombreDeportista, d.apellido1 AS apellido1Deportista, 
            d.apellido2 AS apellido2Deportista, d.correo AS correoDeportista, d.id_usuario AS id_usuarioDeportista, d.nombreusuario AS nombreUsuarioDeportista, d.telefono AS telefonoDeportista, d.telefonoemergencia AS telefonoDeportista
            FROM entrenamiento a, usuario e, usuario d 
            WHERE id_entrenamiento = 1 AND e.id_usuario = a.id_usuario_entrenador AND a.id_usuario_deportista = d.id_usuario;
         */
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

    /**
     * Lectura de todos los datos de la tabla entrenamiento.
     *
     * @return Devuelve un arrayList con objetos de tipo entrenamiento, con los
     * datos de cada registro.
     * @throws excepcionProyecto Devuelve cualquier error.
     */
    public ArrayList<entrenamiento> leerEntrenamientos() throws excepcionProyecto
    {
        conectar();
        String dql = "SELECT * FROM entrenamiento e, usuario u, usuario u2 WHERE e.id_usuario_deportista = u.id_usuario AND e.id_usuario_entrenador = u2.id_usuario";
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

//                usuario entrenador = new usuario(resultado.getInt("id_usuario"), dql, dql, dql, dql, dql, dql, dql);
//                usuario deportista = new usuario(Integer.MIN_VALUE, dql, dql, dql, dql, dql, dql, dql);
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
