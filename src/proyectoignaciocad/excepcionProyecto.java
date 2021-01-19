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
public class excepcionProyecto extends Exception
{
    String mensajeErrorUsuario, mensajeErrorAdministrador, sentenciaSQL;
    Integer codigoError;

    /**
     * Constructor vacio de la clase
     */
    public excepcionProyecto()
    {
    }

    /**
     * Constructor completo de la clase
     * @param mensajeErrorUsuario Mensaje a enviar al usuario
     * @param mensajeErrorAdministrador Mensaje a enviar al administrador de la aplicacion
     * @param sentenciaSQL Sentencia SQL que inicio el error
     * @param codigoError  Codigo de error que devuelve la BD.
     */
    public excepcionProyecto(String mensajeErrorUsuario, String mensajeErrorAdministrador, String sentenciaSQL, Integer codigoError)
    {
        this.mensajeErrorUsuario = mensajeErrorUsuario;
        this.mensajeErrorAdministrador = mensajeErrorAdministrador;
        this.sentenciaSQL = sentenciaSQL;
        this.codigoError = codigoError;
    }

    public String getMensajeErrorUsuario()
    {
        return mensajeErrorUsuario;
    }

    public void setMensajeErrorUsuario(String mensajeErrorUsuario)
    {
        this.mensajeErrorUsuario = mensajeErrorUsuario;
    }

    public String getMensajeErrorAdministrador()
    {
        return mensajeErrorAdministrador;
    }

    public void setMensajeErrorAdministrador(String mensajeErrorAdministrador)
    {
        this.mensajeErrorAdministrador = mensajeErrorAdministrador;
    }

    public String getSentenciaSQL()
    {
        return sentenciaSQL;
    }

    public void setSentenciaSQL(String sentenciaSQL)
    {
        this.sentenciaSQL = sentenciaSQL;
    }

    public Integer getCodigoError()
    {
        return codigoError;
    }

    public void setCodigoError(Integer codigoError)
    {
        this.codigoError = codigoError;
    }

    @Override
    public String toString()
    {
        return "excepcionUsuario{" + "mensajeErrorUsuario=" + mensajeErrorUsuario + ", mensajeErrorAdministrador=" + mensajeErrorAdministrador + ", sentenciaSQL=" + sentenciaSQL + ", codigoError=" + codigoError + '}';
    }
    
    
}
