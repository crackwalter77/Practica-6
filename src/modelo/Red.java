/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author walter
 */
public class Red {
    private Integer idCasaOrigen;
    private Integer idCasaDestino;
    private Double peso;

    public Red() {
    }

    public Red(Integer idCasaOrigen, Integer idCasaDestino, Double peso) {
        this.idCasaOrigen = idCasaOrigen;
        this.idCasaDestino = idCasaDestino;
        this.peso = peso;
    }

    public Integer getIdCasaOrigen() {
        return idCasaOrigen;
    }

    public void setIdCasaOrigen(Integer idCasaOrigen) {
        this.idCasaOrigen = idCasaOrigen;
    }

    public Integer getIdCasaDestino() {
        return idCasaDestino;
    }

    public void setIdCasaDestino(Integer idCasaDestino) {
        this.idCasaDestino = idCasaDestino;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
      return peso.toString();
    }
}
