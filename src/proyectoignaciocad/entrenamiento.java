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

    public entrenamiento()
    {
    }

    public entrenamiento(Integer idEntrenamiento, Integer plazas, String nombre, Date fecha, usuario idUsuarioEntrenador, usuario idUsuarioDeportista)
    {
        this.idEntrenamiento = idEntrenamiento;
        this.plazas = plazas;
        this.nombre = nombre;
        this.fecha = fecha;
        this.idUsuarioEntrenador = idUsuarioEntrenador;
        this.idUsuarioDeportista = idUsuarioDeportista;
    }

    public Integer getIdEntrenamiento()
    {
        return idEntrenamiento;
    }

    public void setIdEntrenamiento(Integer idEntrenamiento)
    {
        this.idEntrenamiento = idEntrenamiento;
    }

    public Integer getPlazas()
    {
        return plazas;
    }

    public void setPlazas(Integer plazas)
    {
        this.plazas = plazas;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public usuario getIdUsuarioEntrenador()
    {
        return idUsuarioEntrenador;
    }

    public void setIdUsuarioEntrenador(usuario idUsuarioEntrenador)
    {
        this.idUsuarioEntrenador = idUsuarioEntrenador;
    }

    public usuario getIdUsuarioDeportista()
    {
        return idUsuarioDeportista;
    }

    public void setIdUsuarioDeportista(usuario idUsuarioDeportista)
    {
        this.idUsuarioDeportista = idUsuarioDeportista;
    }
    
    
}
