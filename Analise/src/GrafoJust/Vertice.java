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
public class Vertice {
    private String nome;
    private double peso;
    
    public Vertice(String nome){
        this.nome = nome;   
    }
    
    public void setPeso(double peso){
        this.peso = peso;
    }
    
     public double getPeso(){
        return peso;
    }
    
    public String getNome(){
        return nome;
    }
    
    @Override
    public boolean equals(Object e){
        Vertice cidade = (Vertice) e;
        return cidade.getNome().equals(nome);
    }
    
    @Override
    public String toString(){
        return nome;
    }
}
