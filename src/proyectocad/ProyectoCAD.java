package proyectocad;

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
 * Clase creada para el manejo de la conexion de una BD Oracle con Java para el proyecto.
 * @author Alejandro Campos Maestre
 */
public class ProyectoCAD
{

    Connection conexion;
    static String IP = null, nombreBD = null, contraseñaBD = null;

    /**
     * Constructor por defecto, con la carga del Driver en Memoria
     *
     * @throws proyectocad.ExcepcionProyecto Devuelve un error al intentar cargar el Driver
     */
    public ProyectoCAD() throws ExcepcionProyecto
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex)
        {
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        }
    }

    /**
     * Constructor con los datos necesarios para proceder a conectarse a una BD Oracle.
     * @param IP Direccion de la interfaz en red del servidor BD
     * @param nombreBD Nombre de la BD
     * @param contraseñaBD Contraseña de la BD
     * @throws proyectocad.ExcepcionProyecto Devuelve un error al intentar cargar el Driver
     */
    public ProyectoCAD(String IP, String nombreBD, String contraseñaBD) throws ExcepcionProyecto
    {
        try
        {
            ProyectoCAD.IP = IP;
            ProyectoCAD.nombreBD = nombreBD;
            ProyectoCAD.contraseñaBD = contraseñaBD;
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex)
        {
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        }
    }

    /**
     * Metodo usado para conectar con la BD, este metodo diferencia entre una 
     * conexcion por defecto o una conexion con parametros de IP, nombreBD y contraseñaBD.
     * @throws ExcepcionProyecto Cuando se produzca cualquier error en la conexion.
     */
    private void conectar() throws ExcepcionProyecto
    {
        try
        {
            if (IP == null)
            {
                conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.125:1521:test", "proyecto", "kk");
            } else
            {
                conexion = DriverManager.getConnection("jdbc:oracle:thin:@" + IP + ":1521:test", nombreBD, contraseñaBD);
            }
        } catch (SQLException ex)
        {
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        }
    }

    //----------Usuario-----------//
    /**
     * Inserta un registro de Usuario a la base de datos, tabla Usuario.
     *
     * @param usuario Clase solicitada con los datos necesarios del Usuario para inserta en la BD, no es necesario el idUsuario.
     * @return Cantidad de registros añadidos
     * @throws ExcepcionProyecto Cuando se produzca cualquier error con la conexion o con excepciones SQL.
     */
    public int insertarUsuario(Usuario usuario) throws ExcepcionProyecto
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

            //----- Lanzamiento de una sentencia DML
            resultado = sentenciaPreparada.executeUpdate();

            //----- Cerrar la Conexión a la BD
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex)
        {
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(dml);

            switch (ex.getErrorCode())
            {
                case 1:
                    e.setMensajeErrorUsuario("El correo, el nombre de usuario o el telefono no pueden ser iguales que los de otros usuarios.");
                    break;

                case 1400:
                    e.setMensajeErrorUsuario("No puedes dejar ningun dato sin rellenar.");
                    break;

                case 2290:
                    e.setMensajeErrorUsuario("El correo debe seguir los parametros normalizados (____@___.__) y el numero de telefono no puede ser el mismo numero que el de emergencia.");
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
     * Elimina un registro de la tabla Usuario. Necesita el identificador del
     * usuario.
     *
     * @param idUsuario Identificador del usuario a elminiar.
     * @return Cantidad de registros eliminados
     * @throws ExcepcionProyecto Cuando se produzca cualquier excepcion en la
     * conexion a la BD o una Foreign Key.
     */
    public int eliminarUsuario(Integer idUsuario) throws ExcepcionProyecto
    {
        conectar();
        String dml = "DELETE FROM usuario WHERE id_usuario = ?";
        int resultado = 0;
        try
        {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1, idUsuario, Types.INTEGER);

            //----- Lanzamiento de una sentencia DML
            resultado = sentenciaPreparada.executeUpdate();

            //----- Cerrar la Conexión a la BD
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex)
        {
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setSentenciaSQL(dml + " --> ? = " + idUsuario);
            switch (ex.getErrorCode())
            {
                case 2292:
                    e.setMensajeErrorUsuario("No se puede eliminar al usuario, ya que esta alistado en al menos un entrenamiento.");
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
     * Modifica un registro de la tabla Usuario. No se modifica el
     * identificador.
     *
     * @param usuario Objeto de tipo Usuario con los datos para proceder a la
     * modficacion, necesaria la idUsuario para modificar.
     * @throws ExcepcionProyecto Cuando se produzca cualquier error en la
     * conexio o sentencia SQL.
     */
    public void modificarUsuario(Usuario usuario) throws ExcepcionProyecto
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
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(llamada);

            switch (ex.getErrorCode())
            {
                case 1:
                    e.setMensajeErrorUsuario("El correo, el nombre de usuario y el telefono no pueden ser iguales que los de otros usuarios.");
                    break;

                case 1407:
                    e.setMensajeErrorUsuario("Es obligatorio rellenar todos los datos.");
                    break;

                case 2290:
                    e.setMensajeErrorUsuario("El correo debe seguir los parametros normalizados (____@___.__) y el numero de telefono no puede ser el mismo numero que el de emergencia.");
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
    }

    /**
     * Lectura de los datos de la tabla Usuario, buscando por la id del Usuario.
     *
     * @param idUsuario Identificador del usuario de la tabla usuario.
     * @return Devuelve un objeto de tipo Usuario con los datos del usuario
     * selecionado, si no existe, se devuelve un objeto vacio.
     * @throws ExcepcionProyecto Devuelve un error en caso de cualquier problema
     * con la conexion de la BD.
     */
    public Usuario leerUsuario(Integer idUsuario) throws ExcepcionProyecto
    {
        conectar();
        String dql = "SELECT * FROM usuario WHERE id_usuario = " + idUsuario;
        Usuario usuario = new Usuario();
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
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(dql);
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            throw e;
        }
        return usuario;
    }

    /**
     * Lectura de todos los registros de la tabla Usuario.
     *
     * @return Devuelve un arrayList con objetos Usuario con todos los registros de la tabla Usuario.
     * @throws ExcepcionProyecto Devuelve cualquier tipo de error de conexion.
     */
    public ArrayList<Usuario> leerUsuarios() throws ExcepcionProyecto
    {
        conectar();
        String dql = "SELECT * FROM usuario";
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try
        {
            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            ResultSet resultado = sentencia.executeQuery(dql);

            while (resultado.next())
            {
                Usuario usu = new Usuario();
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
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setSentenciaSQL(dql);
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");

            throw e;
        }
        return usuarios;
    }

    //----------Entrenamiento-----------//
    /**
     * Inserta un registro en la tabla de Entrenamiento
     *
     * @param entrenamiento Objeto Entrenamiento con los datos necesarios para la insercion de los mismos en la BD.
     * El identificador no se usa.
     * @return El numero de registros afectados.
     * @throws ExcepcionProyecto Devuelve cualquier tipo de error en la conexion a la BD o 
     * cualquier tipo de excepcion en la BD.
     */
    public int insertarEntrenamiento(Entrenamiento entrenamiento) throws ExcepcionProyecto
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
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(dml);

            switch (ex.getErrorCode())
            {
                case 1400:
                    e.setMensajeErrorUsuario("No puede haber ningun campo vacio.");
                    break;

                case 2291:
                    e.setMensajeErrorUsuario("El entrenador o el deportista no existe.");
                    break;

                case 2290:
                    e.setMensajeErrorUsuario("No puede haber un numero negativo de plazas.");
                    break;

                case 20001:
                    e.setMensajeErrorUsuario("El deportista no puede tener mas de dos entrenamientos el mismo dia.");
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
     * Eliminacion de un registro de la tabla Entrenamiento, por el identificador del Entrenamiento
     *
     * @param idEntrenamiento Identificador del registro a elminiar de la tabla Entrenamiento.
     * @return Devuelve el numero de registros afectados.
     * @throws ExcepcionProyecto Devuelve cualquier error en la conexion de la BD.
     */
    public int eliminarEntrenamiento(Integer idEntrenamiento) throws ExcepcionProyecto
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
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
            e.setSentenciaSQL(dml);
            throw e;
        }
        return resultado;
    }

    /**
     * Modificacion de un registro de la tabla Entrenamiento
     *
     * @param entrenamiento Objeto Entrenamiento con los datos necesarios para la modificacion del registro.
     * Y con el identificador del registro a modificar. idEntrenamiento
     * @throws ExcepcionProyecto Devuelve cualquer errror producido.
     */
    public void modificarEntrenamiento(Entrenamiento entrenamiento) throws ExcepcionProyecto
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
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(llamada);

            switch (ex.getErrorCode())
            { //FIXME revisar trigger de la tabla entrenamiento, salta tabla mutante
                case 2291:
                    e.setMensajeErrorUsuario("El entrenador o el deportista no existe.");
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
    }

    /**
     * Lectura de un registro de la tabla Entrenamiento, buscado por su identificador.
     *
     * @param idEntrenamiento Identificador del registro de la tabla de Entrenamiento.
     * @return Devuelve un objeto de tipo Entrenamiento, con los datos del registro.
     * @throws ExcepcionProyecto Devuelve cualquier error en la conexion BD.
     */
    public Entrenamiento leerEntrenamiento(Integer idEntrenamiento) throws ExcepcionProyecto
    {
        conectar();
        String dql = "SELECT a.*, e.nombre AS nombreEntrenador, e.apellido1 AS apellido1Entrenador, e.apellido2 AS apellido2Entrenador, e.correo AS correoEntrenador, e.id_usuario AS id_usuarioEntrenador"
                + ", e.nombreUsuario AS nombreUsuarioEntrenador, e.telefono AS telefonoEntrenador, e.telefonoEmergencia AS telefonoEmergenciaEntrenador, d.nombre AS nombreDeportista, "
                + "d.apellido1 AS apellido1Deportista, d.apellido2 AS apellido2Deportista, d.correo AS correoDeportista, d.id_usuario AS id_usuarioDeportista, d.nombreUsuario AS nombreUsuarioDeportista, "
                + "d.telefono AS telefonoDeportista, d.telefonoEmergencia AS telefonoEmergenciaDeportista "
                + "FROM entrenamiento a, usuario e, usuario d "
                + "WHERE e.id_usuario = a.id_usuario_entrenador AND a.id_usuario_deportista = d.id_usuario AND a.ID_ENTRENAMIENTO = " + idEntrenamiento;
        
        Entrenamiento entrenamiento = new Entrenamiento();
        try
        {
            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            ResultSet resultado = sentencia.executeQuery(dql);
            if (resultado.next())
            {
                entrenamiento.setFecha(resultado.getDate("fecha"));
                entrenamiento.setIdEntrenamiento(resultado.getInt("id_entrenamiento"));
                entrenamiento.setNombre(resultado.getString("nombre"));
                entrenamiento.setPlazas(resultado.getInt("plazas"));

                Usuario entrenador = new Usuario();
                Usuario deportista = new Usuario();

                entrenador.setApellido1(resultado.getString("apellido1Entrenador"));
                entrenador.setApellido2(resultado.getString("apellido2Entrenador"));
                entrenador.setCorreo(resultado.getString("correoEntrenador"));
                entrenador.setIdUsuario(resultado.getInt("id_usuarioEntrenador"));
                entrenador.setNombre(resultado.getString("nombreEntrenador"));
                entrenador.setNombreUsuario(resultado.getString("nombreUsuarioEntrenador"));
                entrenador.setTelefono(resultado.getString("telefonoEntrenador"));
                entrenador.setTelefonoEmergencia(resultado.getString("telefonoEmergenciaEntrenador"));

                entrenamiento.setIdUsuarioEntrenador(entrenador);

                deportista.setApellido1(resultado.getString("apellido1Deportista"));
                
                deportista.setApellido2(resultado.getString("apellido2Deportista"));
                
                deportista.setCorreo(resultado.getString("correoDeportista"));
                deportista.setIdUsuario(resultado.getInt("id_usuarioDeportista"));
                deportista.setNombre(resultado.getString("nombreDeportista"));
                deportista.setNombreUsuario(resultado.getString("nombreUsuarioDeportista"));
                deportista.setTelefono(resultado.getString("telefonoDeportista"));
                deportista.setTelefonoEmergencia(resultado.getString("telefonoEmergenciaDeportista"));
                
                entrenamiento.setIdUsuarioDeportista(deportista);
                
            }
            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (SQLException ex)
        {
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setSentenciaSQL(dql);
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");

            throw e;
        }
        return entrenamiento;
    }

    /**
     * Lectura de todos los datos de la tabla Entrenamiento.
     *
     * @return Devuelve un arrayList con objetos de tipo Entrenamiento, con los datos de cada registro.
     * @throws ExcepcionProyecto Devuelve cualquier error de conexion BD.
     */
    public ArrayList<Entrenamiento> leerEntrenamientos() throws ExcepcionProyecto
    {
        conectar();
        String dql = "SELECT a.*, e.nombre AS nombreEntrenador, e.apellido1 AS apellido1Entrenador, e.apellido2 AS apellido2Entrenador, e.correo AS correoEntrenador, e.id_usuario AS id_usuarioEntrenador"
                + ", e.nombreusuario AS nombreUsuarioEntrenador, e.telefono AS telefonoEntrenador, e.telefonoemergencia AS telefonoEmergenciaEntrenador, d.nombre AS nombreDeportista, "
                + "d.apellido1 AS apellido1Deportista, d.apellido2 AS apellido2Deportista, d.correo AS correoDeportista, d.id_usuario AS id_usuarioDeportista, d.nombreusuario AS nombreUsuarioDeportista, "
                + "d.telefono AS telefonoDeportista, d.telefonoemergencia AS telefonoEmergenciaDeportista "
                + "FROM entrenamiento a, usuario e, usuario d "
                + "WHERE e.id_usuario = a.id_usuario_entrenador AND a.id_usuario_deportista = d.id_usuario";

        ArrayList<Entrenamiento> entrenamientos = new ArrayList<>();
        try
        {
            Statement sentencia = conexion.createStatement();

            //----- Lanzamiento de una sentencia DQL
            ResultSet resultado = sentencia.executeQuery(dql);

            while (resultado.next())
            {
                Entrenamiento entre = new Entrenamiento();
                entre.setFecha(resultado.getDate("fecha"));
                entre.setIdEntrenamiento(resultado.getInt("id_entrenamiento"));
                entre.setNombre(resultado.getString("nombre"));
                entre.setPlazas(resultado.getInt("plazas"));

                Usuario entrenador = new Usuario();
                Usuario deportista = new Usuario();

                entrenador.setApellido1(resultado.getString("apellido1Entrenador"));
                entrenador.setApellido2(resultado.getString("apellido2Entrenador"));
                entrenador.setCorreo(resultado.getString("correoEntrenador"));
                entrenador.setIdUsuario(resultado.getInt("id_usuarioEntrenador"));
                entrenador.setNombre(resultado.getString("nombreEntrenador"));
                entrenador.setNombreUsuario(resultado.getString("nombreUsuarioEntrenador"));
                entrenador.setTelefono(resultado.getString("telefonoEntrenador"));
                entrenador.setTelefonoEmergencia(resultado.getString("telefonoEmergenciaEntrenador"));

                entre.setIdUsuarioEntrenador(entrenador);

                deportista.setApellido1(resultado.getString("apellido1Deportista"));
                deportista.setApellido2(resultado.getString("apellido2Deportista"));
                deportista.setCorreo(resultado.getString("correoDeportista"));
                deportista.setIdUsuario(resultado.getInt("id_usuarioDeportista"));
                deportista.setNombre(resultado.getString("nombreDeportista"));
                deportista.setNombreUsuario(resultado.getString("nombreUsuarioDeportista"));
                deportista.setTelefono(resultado.getString("telefonoDeportista"));
                deportista.setTelefonoEmergencia(resultado.getString("telefonoEmergenciaDeportista"));

                entre.setIdUsuarioDeportista(deportista);

                entrenamientos.add(entre);
            }

            //----- Cerrar la Conexión a la BD
            sentencia.close();
            conexion.close();

        } catch (SQLException ex)
        {
            ExcepcionProyecto e = new ExcepcionProyecto();
            e.setMensajeErrorAdministrador(ex.getMessage());
            e.setSentenciaSQL(dql);
            e.setCodigoError(ex.getErrorCode());
            e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");

            throw e;
        }
        return entrenamientos;
    }
}