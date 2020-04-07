/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoJust;

/**
 *
 * @author allen
 */
public class Aresta {
    private Vertice destino;
    private double peso;
    
    public Aresta(Vertice destino, double peso){
        this.destino = destino;
        this.peso = peso;
    }
    
    public Vertice getDestino(){
        return destino;
    }
    public double getPeso(){
        return peso;
    }
    
    @Override
    public boolean equals(Object e){
        
            return destino.equals(e);
        
    }
    
    @Override
    public String toString(){
        return destino.getNome();
    }
}
