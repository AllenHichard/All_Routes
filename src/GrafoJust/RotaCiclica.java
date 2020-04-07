package GrafoJust;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * A classe AuxGrafo foi implementada principalmente para mostrar todos os
 * caminhos possíveis de uma vértice a outra através de suas ligações.
 *
 * @author Allen Hichard Marques dos Santos
 */
public class RotaCiclica {

    private final Map<Vertice, LinkedHashSet<Aresta>> mapa = new HashMap();
    private LinkedList<Vertice> visitado;
    private Vertice start;
    private Vertice ultimo;
    private List distancias = new LinkedList();
    private List<List> lista = new LinkedList();
    private List<Vertice> list = new LinkedList<>();
    private double distancia;
    private final Map<List, Double> caminhosPeso = new HashMap();
    private final Map<List, Double> melhoresCaminhos = new HashMap();
    static double menor = Double.MAX_VALUE;
    private List pilhaGeral = new Stack();

    /**
     * O método adicionar, insere em uma tabela hash os dois nomes que nesse
     * caso é considerado duas vertices. Só um detalhe a classe tem um
     * comportamento igual a um grafo, mas com finalidade apenas de mostrar
     * todos os caminhos possíveis.
     *
     * @param nome1 primeira vertice
     * @param nome2 segunda vertice
     */
    public void adicionar(Vertice nome1, Vertice nome2, double peso) {
        LinkedHashSet<Aresta> adjacente = mapa.get(nome1);
        if (adjacente == null) {
            adjacente = new LinkedHashSet();
            mapa.put(nome1, adjacente);
        }
        Aresta a = new Aresta(nome2, peso);
        adjacente.add(a);

    }

    public void setInicio(Vertice start) {
        visitado = new LinkedList();
        this.start = start;
        visitado.add(start);
    }

    /**
     * Liga as duas vértices em virse-versa. Por exemplo A liga B e B liga A.
     *
     * @param nome1
     * @param nome2
     */
    public void adicionarDuplo(Vertice nome1, Vertice nome2, double peso) {
        adicionar(nome1, nome2, peso);
        adicionar(nome2, nome1, peso);

    }

    /**
     * Não permite dois lugares com o mesmo nome, método não utilizado no
     * problema já que pode haver duas cidades com o mesmo nome."Talves possa
     * não ficou nada restrito quando a isso"
     *
     * @param nome1
     * @param nome2
     * @return
     */
    public boolean estaConectado(Vertice nome1, Vertice nome2) {
        Set adjacente = mapa.get(nome1);
        if (adjacente == null) {
            return false;
        }
        return adjacente.contains(nome2);
    }

    /**
     * O método retorna uma lista que contem uma hash.
     *
     * @param last ultimo da atual hash.
     * @return
     */
    public LinkedList<Aresta> nosAdjacente(Vertice last) {
        LinkedHashSet<Aresta> adjacente = mapa.get(last);
        if (adjacente == null) {
            return new LinkedList();
        }
        return new LinkedList(adjacente);
    }

    /**
     * Método que permite a busca de todos caminhos possíveis do um ponto a
     * outro. Por ser recursivo ele pega todos os caminhos e vao salvando em uma
     * lista no método printarNaLista, que guarda cada caminho em uma posição.
     *
     * @param ultimo o ponto dois.
     * @return a listas com todos os caminhos.
     */
    public List todosCaminhos() {
        LinkedList<Aresta> nodes = nosAdjacente(visitado.getLast());
        for (Aresta node : nodes) {
            if (visitado.contains(node.getDestino()) && !node.getDestino().equals(start)) {
                continue;
            }
            if (node.equals(start) && visitado.size() == mapa.size() && mapa.size() > 2) {
                distancia += node.getPeso();
                visitado.add(node.getDestino());
                distancias.add(distancia);
                printarNaLista(visitado, distancia);
                visitado.removeLast();
                distancia -= node.getPeso();
                break;
            }
        }
        for (Aresta node : nodes) {
            if (visitado.contains(node) || node.equals(start)) {
                continue;
            }
            distancia += node.getPeso();
            visitado.addLast(node.getDestino());
            todosCaminhos();
            visitado.removeLast();
            distancia -= node.getPeso();
        }
        return list;
    }

    /**
     * O método concatena caminho por caminho salvando em uma lista, para depois
     * ser retornado pelo método primeiraBorda.
     *
     * @param visitado listas de visitados do caminho atual.
     */
    private void printarNaLista(LinkedList<Vertice> visitado, double distancia) {

        if (distancia < menor) {
            menor = distancia;
            list = new LinkedList<>();
            for (Vertice node : visitado) {
                list.add(node);
            }
        }
    }

    public static void main(String[] args) {
        RotaCiclica grafo = new RotaCiclica();
        Vertice a = new Vertice("A");
        Vertice b = new Vertice("B");
        Vertice c = new Vertice("C");
        Vertice d = new Vertice("D");
        Vertice e = new Vertice("E");
        grafo.adicionarDuplo(a, b, 10);
        grafo.adicionarDuplo(a, c, 8);
        grafo.adicionarDuplo(c, d, 10);
        grafo.adicionarDuplo(b, d, 10);
        grafo.adicionarDuplo(a, d, 1);
        grafo.adicionarDuplo(c, b, 1);
        grafo.setInicio(a);
        List list = grafo.todosCaminhos();
        if (list.size() > 0) {
            System.out.println(list.toString());
            System.out.println("menor Caminho " + menor);
        } else {
            System.out.println("Não existe Caminho");
        }

    }
}
