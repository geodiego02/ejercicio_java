/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Diego
 */
public class Trabajador {
    private String rut,nombre,direccion,sexo, sueldoBase,sueldoLiquido;
    private int ciudad,cargo;
    
    public Trabajador(){
        rut="";
        nombre="";
        direccion="";
        sexo="";
        sueldoBase="";
        sueldoLiquido="";
        ciudad=0;
        cargo=0;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(String sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public int getCiudad() {
        return ciudad;
    }

    public void setCiudad(int ciudad) {
        this.ciudad = ciudad;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }
    
    public String getSueldoLiquido(){
        getSueldoBase();
        float sl=(Float.parseFloat(sueldoBase))-(Float.parseFloat(sueldoBase))*0.2f;
        sueldoLiquido=String.valueOf(sl);
        return sueldoLiquido;
    }
}
