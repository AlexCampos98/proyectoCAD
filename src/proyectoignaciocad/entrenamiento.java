/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoignaciocad;

import java.util.Date;

/**
 *
 * @author Alejandro Campos Maestre
 */
public class entrenamiento
{
    Integer idEntrenamiento, plazas;
    String nombre;
    Date fecha;
    usuario idUsuarioEntrenador, idUsuarioDeportista;

    /**
     * Constructor vacio de la clase
     */
    public entrenamiento()
    {
    }

    /**
     * Constructor completo de la clase
     * @param idEntrenamiento Identificador del registro entrenamiento (id_entrenamiento)
     * @param plazas Numero de plazas del registro entrenamiento
     * @param nombre Nombre del entrenamiento del registro
     * @param fecha fecha del entrenamiento del registro
     * @param idUsuarioEntrenador Objeto usuario entrenador
     * @param idUsuarioDeportista Objeto usuario deportista
     */
    public entrenamiento(Integer idEntrenamiento, Integer plazas, String nombre, Date fecha, usuario idUsuarioEntrenador, usuario idUsuarioDeportista)
    {
        this.idEntrenamiento = idEntrenamiento;
        this.plazas = plazas;
        this.nombre = nombre;
        this.fecha = fecha;
        this.idUsuarioEntrenador = idUsuarioEntrenador;
        this.idUsuarioDeportista = idUsuarioDeportista;
    }

    /**
     * Obtencion del idEntrenamiento de la clase
     * @return Tipo Integer con la informacion de idEntrenamiento
     */
    public Integer getIdEntrenamiento()
    {
        return idEntrenamiento;
    }

    /**
     * Asignacion del atributo idEntrenamiento
     * @param idEntrenamiento Tipo Integer con el identificador del entrenamiento
     */
    public void setIdEntrenamiento(Integer idEntrenamiento)
    {
        this.idEntrenamiento = idEntrenamiento;
    }

    /**
     * Obtencion del plazas de la clase
     * @return Tipo Integer con la informacion de plazas
     */
    public Integer getPlazas()
    {
        return plazas;
    }

    /**
     * Asignacion del atributo plazas
     * @param plazas Tipo Integer con el numero de plazas
     */
    public void setPlazas(Integer plazas)
    {
        this.plazas = plazas;
    }

    /**
     * Obtencion del nombre de la clase
     * @return Tipo Integer con la informacion de nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * Asignacion del atributo nombre
     * @param nombre Tipo String con la informacion de nombre
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * Obtencion del fecha de la clase
     * @return Tipo Date con la informacion de fecha
     */
    public Date getFecha()
    {
        return fecha;
    }

    /**
     * Asignacion del atributo fecha
     * @param fecha Tipo Date con la informacion de fecha
     */
    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    /**
     * Obtencion del objeto usuario con la informacion del entrenador
     * @return Tipo usuario con la informacion del usuario entrenador
     */
    public usuario getIdUsuarioEntrenador()
    {
        return idUsuarioEntrenador;
    }

    /**
     * Asignacion del objeto usuario que sera el entrenador
     * @param idUsuarioEntrenador Tipo usuario con el objeto usuario que sera el entrenador
     */
    public void setIdUsuarioEntrenador(usuario idUsuarioEntrenador)
    {
        this.idUsuarioEntrenador = idUsuarioEntrenador;
    }

    /**
     * Obtencion del objeto usuario con la informacion del deportista
     * @return Tipo usuario con la informacion del usuario deportista
     */
    public usuario getIdUsuarioDeportista()
    {
        return idUsuarioDeportista;
    }

    /**
     * Asignacion del objeto usuario que sera el deportista
     * @param idUsuarioDeportista Tipo usuario con el objeto usuario que sera el deportista
     */
    public void setIdUsuarioDeportista(usuario idUsuarioDeportista)
    {
        this.idUsuarioDeportista = idUsuarioDeportista;
    }

    /**
     * Metodo usado para obtener todos los datos contenidos en la clase.
     * @return Devuelve un String con la informacion contenida de todos los atributos.
     */
    @Override
    public String toString()
    {
        return "entrenamiento{" + "idEntrenamiento=" + idEntrenamiento + ", plazas=" + plazas + ", nombre=" + nombre + ", fecha=" + fecha + ", idUsuarioEntrenador=" + idUsuarioEntrenador + ", idUsuarioDeportista=" + idUsuarioDeportista + '}';
    }
}
