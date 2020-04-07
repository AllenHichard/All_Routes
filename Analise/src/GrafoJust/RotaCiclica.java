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
 * @author Allen Hichard Marques dos Santos, Alisson Vilas Verde
 */
public class RotaCiclica {

    private final Map<Vertice, LinkedHashSet<Aresta>> mapa = new HashMap();
    private LinkedList<Vertice> visitado;
    private Vertice start;
    private Vertice ultimo;
    private List distancias = new LinkedList();
    private List<List> lista = new LinkedList();
    private double distancia;
    private final Map<List, Double> caminhosPeso = new HashMap();
    private final Map<List, Double> melhoresCaminhos = new HashMap();
    double menor = Double.MAX_VALUE;
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
        LinkedHashSet<Aresta> adjacente = mapa.get(nome1); // C1
        if (adjacente == null) { // 1
            adjacente = new LinkedHashSet(); // C2
            mapa.put(nome1, adjacente); //C3
        }
        Aresta a = new Aresta(nome2, peso); //C4
        adjacente.add(a); //C6
        //Complexidade do método adicionar é apenas uma constante
        // C = (C1 + C2 + C3 + C4 + C5) 
        // O(C + 1) = T1

    }

    /**
     * Liga as duas vértices em virse-versa. Por exemplo A liga B e B liga A.
     *
     * @param nome1
     * @param nome2
     */
    public void adicionarDuplo(Vertice nome1, Vertice nome2, double peso) {
        adicionar(nome1, nome2, peso); // tempo C do adicionar 
        adicionar(nome2, nome1, peso); // tempo C do adicionar
        // A complexidade do adicionar duplo é duas vezes o adicionar
        // O (2*C + 2) = T2
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
        Set adjacente = mapa.get(nome1); // C1
        if (adjacente == null) { //1
            return false; // 1
        }
        return adjacente.contains(nome2); //1
        //O(C1 + 2) = T3;
    }

    /**
     * O método retorna uma lista que contem uma hash.
     *
     * @param last ultimo da atual hash.
     * @return
     */
    public LinkedList<Aresta> nosAdjacente(Vertice last) {
        LinkedHashSet<Aresta> adjacente = mapa.get(last); //C1 + tempo da Hash(provavel O(1))
        if (adjacente == null) { // 1
            return new LinkedList(); // 1
        }
        return new LinkedList(adjacente); //1
        // 0(C1 + 2) + provavel O(1) = T4
    }

    /**
     * Método que permite a busca de todos caminhos possíveis do um ponto a
     * outro. Por ser recursivo ele pega todos os caminhos e vao salvando em uma
     * lista no método printarNaLista, que guarda cada caminho em uma posição.
     *
     * @param ultimo o ponto dois.
     * @return a listas com todos os caminhos.
     */
    public List todosCaminhos(Vertice ultimo) {
        LinkedList<Aresta> nodes = nosAdjacente(visitado.getLast()); // C1 + T4
        //O algoritmo tem comportamento de uma matriz, fazendo combinação entre todos os vertices do grafo
        //Este laço executa A*Quantidade de Recurvisade que pe dado pena quantidade de vertice
        //Para maioria dos Casos O|A*V|
        for (Aresta node : nodes) {  
            if (visitado.contains(node.getDestino())) {
                continue;
            }
            if (node.equals(ultimo)) {
                distancia += node.getPeso();
                visitado.add(node.getDestino());
                distancias.add(distancia);
                printarNaLista(visitado);
                visitado.removeLast();
                distancia -= node.getPeso();
                break;
            }
        }
        for (Aresta node : nodes) {  //O mesmo vale para este laço O|A*V|
            if (visitado.contains(node) || node.equals(ultimo)) {
                continue;
            }
            distancia += node.getPeso();
            visitado.addLast(node.getDestino());
            todosCaminhos(ultimo);
            visitado.removeLast();
            distancia -= node.getPeso();
        }
        return lista;

        // tempo Total 2*A*V
    }

    public void limpar() {
        lista = new LinkedList<>();
    }

    /**
     * O método concatena caminho por caminho salvando em uma lista, para depois
     * ser retornado pelo método primeiraBorda.
     *
     * @param visitado listas de visitados do caminho atual.
     */
    private void printarNaLista(LinkedList<Vertice> visitado) {
        List<Vertice> nova = new LinkedList();
        LinkedList pilha = new LinkedList();
        for (Vertice node : visitado) { // T6 = O(V + 1)
            nova.add(node);
            pilha.addFirst(node);
        }
        pilha.removeFirst(); //C
        pilhaGeral.add(pilha); //C
        lista.add(nova); //C
    }

    /**
     * retorna o ponto inicial.
     *
     * @return
     */
    public Vertice getInicio() {
        return start;
    }

    /**
     * recebe um ponto inicial.
     *
     * @param start inicio
     */
    public void setInicio(Vertice start) {
        visitado = new LinkedList();
        this.start = start;
        visitado.add(start);
    }

    //Não está sendo utilizando
    public void Visualizar(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            List l = (List) it.next();
            Iterator ut = l.iterator();
            while (ut.hasNext()) {
                Vertice a = (Vertice) ut.next();
                System.out.print(a.toString() + " ");
            }
            System.out.println("");
        }
        //System.out.println(caminhosPeso.toString());
    }

    //Não está sendo utilizado
    public void MelhoresCaminho(List list) {
        if (list.size() > 0) {

            System.out.println("O menor valor é: " + menor);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                List l = (List) it.next();
                System.out.println(l.toString());
                System.out.println(caminhosPeso.get(l));

                if (caminhosPeso.get(l) == menor) {
                    melhoresCaminhos.put(l, menor);
                }

            }
            System.out.println("Melhores Caminhos");
            System.out.println(melhoresCaminhos.toString());
        } else {
            System.out.println("Não existe caminho");
        }
    }
    
    
    //Melhor caminho irá mostra ao usuário o caminho de ida e volta de menor peso
    public void MelhorCaminho(List list) {
        if (list != null) { //1 
            System.out.println("O melhor Caminho é: "); //C1
            System.out.println(list.toString()); //C2
            System.out.println("A menor distância é: " + menor); // C3

        } else {
            System.out.println("Não existe caminho");//C4
        }
    }

    public List AnaliseCombinatoria(List list) {
        int count = 0;
        Object v[] = distancias.toArray();
        int i = 0, j = 0;
        List menorCaminho = null;
        Iterator yt = list.iterator();
        while (yt.hasNext()) { 
            // Aproximado A * V pois fica oscilando a depender da forma de entrada dos vertices
            // Resultado do método de todos os Caminhos que é A*V ou seja na lista terá por estimativa A*V caminhos
            //Para maioria dos casos.
            List l = (List) yt.next();
            Iterator ut = pilhaGeral.iterator();
            while (ut.hasNext()) { // A * V igual ao laço anterior, a diferença é que nessas listas os vertices estão
                // em opostos(invertidos)
                LinkedList aux = (LinkedList) ut.next();
                Iterator pi = aux.iterator();
                
                if (l.size() + aux.size() == mapa.size() + 1 && !(l.size() + aux.size() == 3) && (double) v[i] + (double) v[j] < menor) {
                    List caminho = new LinkedList(l);
                    while (pi.hasNext()) { // Esse laço é não será levado em conta, devido a aproximações dos acima
                        // Pois esse laço só executa quando as condições do if forem aceitas
                        // O que chega nesse while seria normalmente V/2
                       
                        Vertice c = (Vertice) pi.next();
                        if (!caminho.contains(c)) {
                            caminho.add(c);
                        } else if (c.equals(start)) {
                            caminho.add(c);
                            menorCaminho = caminho;
                            menor = (double) v[i] + (double) v[j];
                        } else {
                            break;
                        }
                    }
                }
                j++;
            }
            j = 0;
            i++;
            //Para Casos Pequeno, podemos considerar que o algoritmo executa em |A^2 * V^2|
            //Sendo que para casos maiores, o problema tende a aumentar consideravelmente se tornando um
            //Exponencial, o que tornaria este algoritmo inviavel, a depender da aplicação.

        }
       
        return menorCaminho;
    }

    /**
     * retorna o ponto final.
     *
     * @return
     */
    public Vertice getFinal() {
        return ultimo;
    }

    /**
     * recebe um ponto final.
     *
     * @param ultimo final
     */
    public void setFinal(Vertice ultimo) {
        this.ultimo = ultimo;
    }

    public static void main(String[] args) {
        // Resultados obtidos através de testes:
        // Para valores pequenos entre 5,6 vertices A*V a complexidade geral
        // Para valores entre 7 e 11 V^2 * A^2;
        // Quando a entrada no terceiro laço se torna mais frequente o algoritmo é 2^n um exponensial, este
        // fato começa a se tornar frequente para o numerod e vertices maior que 11
        //Para os Testes Foram usados grafos completamentes conectados, pois a quantidade de iterações foi dada
        // em função do pior caso.
        
        
        RotaCiclica grafo = new RotaCiclica();
        Vertice a = new Vertice("A");
        Vertice b = new Vertice("B");
        Vertice c = new Vertice("C");
        Vertice d = new Vertice("D");
        Vertice e = new Vertice("E");
        
        
        grafo.adicionarDuplo(a, b, 10);
        grafo.adicionarDuplo(a, c, 10);
        grafo.adicionarDuplo(b, c, 1);
        grafo.adicionarDuplo(b, d, 10);
        grafo.adicionarDuplo(c, d, 10);
        grafo.adicionarDuplo(a, d, 1);
        
        grafo.setInicio(a); // Origem
        List list = grafo.todosCaminhos(d); // Destino
        grafo.MelhorCaminho(grafo.AnaliseCombinatoria(list));

    }
}
