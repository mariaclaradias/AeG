import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

class Aresta{
  public int vizinho;
  public int vizinhanca;

  Aresta(int vizinho, int vizinhanca){
    this.vizinho = vizinho;
    this.vizinhanca = vizinhanca;
  }

}
//
class Vertice implements Comparable<Vertice>  {
  public int bairro;
  public List<Aresta> vizinhos;

  public Vertice(int bairro){
    this.bairro = bairro;
    vizinhos = new ArrayList<>();
  }

  public void inserirAresta(int bairro, int vizinho){
    Aresta a = new Aresta(bairro, vizinho);
    vizinhos.add(a);
  }

  public void removerAresta(Aresta a){
    vizinhos.remove(a);
  }

  public void imprimir(){
    System.out.print("["+bairro+ "]-> ");
    for(Aresta i : vizinhos){
      System.out.print(i.vizinho+"("+i.vizinhanca+");");
    }
    System.out.println();
  }

  public void removerMaterias(int vizinho){
      vizinhos.remove(vizinho);
  }

  public int getBairro(){
      return this.bairro;
  }

  public List<Aresta> getVizinhos(){
      return this.vizinhos;
  }

  @Override
  public int compareTo(Vertice v){
     return Integer.compare(this.bairro, v.bairro);
  }
}

class Grafo {
  public List<Vertice> v;

  public Grafo(){
    v = new ArrayList<>();
  }

  public void inserirGrafo(int x){
    Vertice i = new Vertice(x);
    if(!pesquisar(i.bairro)){
      v.add(i);
    }
  }

  public void inserirVizinho(int i, int vizinho, int vizinhanca){
    Vertice aux = getVertice(i);
    aux.inserirAresta(vizinho, vizinhanca);

    // se o vertice que está apontado como vizinho já existir
    // se não cria o vertice e insere
    if(pesquisar(vizinho)){
      getVertice(vizinho).inserirAresta(aux.bairro, vizinhanca);
    }else{
      inserirGrafo(vizinho);
      getVertice(vizinho).inserirAresta(aux.bairro, vizinhanca);
    }
  }

  public void imprimir(){
    for(Vertice i : v){
      i.imprimir();
    }
  }//impimir

  public boolean pesquisar(int i){
    boolean resp = false;
    for(Vertice aux : v) {
      if(aux.bairro == i){
        resp = true;
      }
    }
    return resp;
  }

  public Vertice getVertice(int k){
    Vertice novo =  new Vertice(0);
    for(Vertice i : v){
      if(i.bairro == k){
        novo = i;
      }
    }
    return novo;
  }

  public Vertice menorGrau(){

    Vertice aux = v.get(0);
    for(Vertice i : v){
        if(i.vizinhos.size() < aux.vizinhos.size()){
          aux = i;
        }
    }
    return aux;
  }

  public Vertice maiorGrau(){
    Vertice aux = v.get(0);
    for(Vertice i : v){
        if(i.vizinhos.size() > aux.vizinhos.size()){
          aux = i;
        }
    }
    return aux;
  }

  public void removerArestas(){
    List<Aresta> remover = new ArrayList<>();
    for(Vertice i : v) {
      List<Aresta> vizinhos = i.vizinhos;
      for(Aresta j : vizinhos) {
        if(!pesquisar(j.vizinho)){
          remover.add(j);
        }
      }
      for(Aresta j : remover) {
        i.removerAresta(j);
      }
    }
  }

  // acha o conjunto máximo retirando primeiro os vértices de maior grau
  public void conjuntoIM(){
    List<Vertice> i = new ArrayList<>();
    Grafo clone = this;

    while(clone.v.size() > 0){
      Vertice aux = clone.maiorGrau();
      i.add(aux);
      List<Aresta> a = aux.getVizinhos();
      for(Aresta j : a){
        clone.v.remove(clone.getVertice(j.vizinho));
      }
      clone.v.remove(clone.maiorGrau());
      clone.removerArestas();
    }

    System.out.println(i.size());
    Collections.sort(i);

    String resp="";
    for(Vertice x: i) {
      resp += " "+x.bairro+",";
    }
    System.out.println(resp.substring (0, resp.length() - 1));
  }

}//Grafo

class GrafoBurger{

    public static void main(String[] args)throws Exception{
      Scanner s = new Scanner(System.in);
      //grafo(0) ou digrafo(1)
      String line = s.nextLine();
      line = s.nextLine();
      //número total de bairros
      int bairros = Integer.parseInt(line);
      Grafo grafo = new Grafo();
      line = s.nextLine();
      String array[];
      Vertice x;

      while(!line.equals("FIM")){
        array = line.split(",");
        grafo.inserirGrafo(Integer.parseInt(array[0]));
        grafo.inserirVizinho(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
        line = s.nextLine();
      }

      grafo.conjuntoIM();

      //
      // String pendencias[] = new String[Integer.parseInt(line)];
      // for(int k=0; k<pendencias.length; k++) {
      //   pendencias[k]=s.nextLine();
      // }
      // //grafo.imprimir();
      // Grafo novo = grafo.retirarMateriasCursadas(pendencias);
      // novo.kahn(pendencias);
      // novo.imprimir();
    }
}
