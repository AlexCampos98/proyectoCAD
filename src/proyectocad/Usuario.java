package proyectocad;

/**
 * Clase Pojo con los datos de un usuario.
 * @author Alejandro Campos Maestre
 */
public class Usuario
{

    Integer idUsuario;
    String correo, nombre, apellido1, apellido2, telefono, telefonoEmergencia, nombreUsuario;

    /**
     * Constructor vacio de la clase
     */
    public Usuario()
    {
    }

    /**
     * Constructor con solo el identificador del objeto usuario
     * @param idUsuario identificador del objeto usuario
     */
    public Usuario(Integer idUsuario)
    {
        this.idUsuario = idUsuario;
    }
    
    /**
     * Constructor completo de la clase
     * @param idUsuario Identificador del registro de la tabla usuario
     * @param correo email del registro de la tabla usuario
     * @param nombre nombre del usuario de la tabla usuario
     * @param apellido1 apellido1 del usuario de la tabla usuario
     * @param apellido2 apellido2 del usuario de la tabla usuario
     * @param telefono telefono del usuario de la tabla usuario
     * @param telefonoEmergencia telefono de emergencia del usuario de la tabla usuario
     * @param nombreUsuario NickName del usuario de la tabla usuario
     */
    public Usuario(Integer idUsuario, String correo, String nombre, String apellido1, String apellido2, String telefono, String telefonoEmergencia, String nombreUsuario)
    {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;
        this.telefonoEmergencia = telefonoEmergencia;
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtencion del atributo idUsuario
     * @return Devuelve un Integer
     */
    public Integer getIdUsuario()
    {
        return idUsuario;
    }

    /**
     * Asignacion del atributo idUsuario
     * @param idUsuario Tipo Integer con el identificador del registro de Usuario
     */
    public void setIdUsuario(Integer idUsuario)
    {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtencion del atributo correo
     * @return Devuelve un String con el correo
     */
    public String getCorreo()
    {
        return correo;
    }

    /**
     * Asignacion del atributo correo
     * @param correo Tipo String con el correo del Usuario
     */
    public void setCorreo(String correo)
    {
        this.correo = correo;
    }

    /**
     * Obtencion del atributo nombre
     * @return Devuelve un String con el nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * Asignacion del atributo nombre
     * @param nombre Tipo String con el nombre del Usuario
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * Obtencion del atributo apellido1
     * @return Devuelve un String con el apellido1
     */
    public String getApellido1()
    {
        return apellido1;
    }

    /**
     * Asignacion del atributo apellido1
     * @param apellido1 Tipo String con el apellido1 del Usuario
     */
    public void setApellido1(String apellido1)
    {
        this.apellido1 = apellido1;
    }

    /**
     * Obtencion del atributo apellido2
     * @return Devuelve un String con el apellido2
     */
    public String getApellido2()
    {
        return apellido2;
    }

    /**
     * Asignacion del atributo apellido2
     * @param apellido2 Tipo String con el apellido2 del Usuario
     */
    public void setApellido2(String apellido2)
    {
        this.apellido2 = apellido2;
    }

    /**
     * Obtencion del atributo telefono
     * @return Devuelve un String con el telefono
     */
    public String getTelefono()
    {
        return telefono;
    }

    /**
     * Asignacion del atributo telefono
     * @param telefono Tipo String con el telefono del Usuario
     */
    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    /**
     * Obtencion del atributo telefonoEmergencia
     * @return Devuelve un String con el telefonoEmergencia
     */
    public String getTelefonoEmergencia()
    {
        return telefonoEmergencia;
    }

    /**
     * Asignacion del atributo telefonoEmergencia
     * @param telefonoEmergencia Tipo String con el telefonoEmergencia del Usuario
     */
    public void setTelefonoEmergencia(String telefonoEmergencia)
    {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    /**
     * Obtencion del atributo nombreUsuario
     * @return Devuelve un String con el nombreUsuario
     */
    public String getNombreUsuario()
    {
        return nombreUsuario;
    }

    /**
     * Asignacion del atributo nombreUsuario
     * @param nombreUsuario Tipo String con el nombreUsuario del Usuario
     */
    public void setNombreUsuario(String nombreUsuario)
    {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Metodo usado para obtener todos los datos contenidos en la clase.
     * @return Devuelve un String con la informacion contenida todos los atributos.
     */
    @Override
    public String toString()
    {
        return "usuario{" + "idUsuario=" + idUsuario + ", correo=" + correo + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", telefono=" + telefono + ", telefonoEmergencia=" + telefonoEmergencia + ", nombreUsuario=" + nombreUsuario + '}';
    }

}
