/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoignaciocad;

/**
 *
 * @author Alejandro Campos Maestre
 */
public class usuario
{

    Integer idUsuario;
    String correo, nombre, apellido1, apellido2, telefono, telefonoEmergencia, nombreUsuario;

    public usuario(Integer idUsuario, String correo, String nombre, String apellido1, String apellido2, String telefono, String telefonoEmergencia, String nombreUsuario)
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

    public usuario()
    {
    }

    public Integer getIdUsuario()
    {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario)
    {
        this.idUsuario = idUsuario;
    }

    public String getCorreo()
    {
        return correo;
    }

    public void setCorreo(String correo)
    {
        this.correo = correo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellido1()
    {
        return apellido1;
    }

    public void setApellido1(String apellido1)
    {
        this.apellido1 = apellido1;
    }

    public String getApellido2()
    {
        return apellido2;
    }

    public void setApellido2(String apellido2)
    {
        this.apellido2 = apellido2;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    public String getTelefonoEmergencia()
    {
        return telefonoEmergencia;
    }

    public void setTelefonoEmergencia(String telefonoEmergencia)
    {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    public String getNombreUsuario()
    {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario)
    {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public String toString()
    {
        return "usuario{" + "idUsuario=" + idUsuario + ", correo=" + correo + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", telefono=" + telefono + ", telefonoEmergencia=" + telefonoEmergencia + ", nombreUsuario=" + nombreUsuario + '}';
    }

}
