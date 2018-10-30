import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;


class Aresta{
  public String origem;
  public String destino;
  public int dependencia;

  public Aresta(String origem, String destino, int dependencia){
    this.origem = origem;
    this.destino = destino;
    this.dependencia = dependencia;
  }

  public String getOrigem(){
    return origem;
  }

  public void setDestino(String destino){
    this.destino = destino;
  }

  @Override
  public String toString(){
    return "O:" + origem + ", " + " D: " + destino + ", Dep:" + dependencia;
  }
}

//Materia, Lista de Materias, Nivel
class Vertice implements Comparable<Vertice>{
  public String materia;
  public List<String> pendencias;
  public int nivel;

  public Vertice(String materia){
    this.materia = materia;
    pendencias = new ArrayList<>();
    this.nivel = 0;
  }


  public void inserirVertice(String materia){
    pendencias.add(materia);
  }

  @Override
  public String toString(){
    String str = "[" + materia +"]-> ";
    for(String s : pendencias)
      str += s + ";";
    str += "\n";
    return str;
  }

  public boolean hasPendencia(String vertice){
    return pendencias.contains(vertice);
  }

  public void removerMaterias(String materia){
      pendencias.remove(materia);
  }

  public String getMateria(){
      return this.materia;
  }

  public List<String> getPendencias(){
      return pendencias;
  }

  @Override
  public int compareTo (Vertice v){
      return Integer.compare(this.nivel, v.nivel);
  }
}

class Grafo {
  public List<Vertice> v;
  public List<Aresta> p;

  public List<Aresta> getPendencias(){
    return p;
  }

  public Grafo(){
    v = new ArrayList<>();
    p = new ArrayList<>();
  }

  public void inserirGrafo(Vertice vertice){
    v.add(vertice);
  }

  @Override
  public String toString (){
    String str = "";
    for (Vertice vertice : v){
      str += vertice + "\n";
    }
    return str;
  }

  public void arestas(String[] s){
    p = new ArrayList<>();
    Aresta a;
    for(Vertice i : v){
      List<String> pendencias = i.getPendencias();
      for(String j : pendencias){
        a = new Aresta(i.getMateria(), "0", 0);
        for(String k: s){
          if(j.equals(k)) {
            a.setDestino(j);
            p.add(a);
          }
        }
      }
    }
  }

  public Grafo retirarMateriasCursadas(String[] s){
    Grafo novo = new Grafo();
    for(Vertice i : v){
      for(String j : s){
        if(i.materia.equals(j)){
          Vertice n = new Vertice(j);
          novo.inserirGrafo(i);
        }
      }
    }
    novo.limparGrafo(s);
    novo.arestas(s);
    return novo;
  }

  public boolean verticeIndependente(Vertice v){
    boolean resp=false;
      for(Aresta i : p){
        // se Vertice e origem, ele tem dependencia com outra materia
        if(i.origem.equals(v.materia)){
          resp = true;
          i.dependencia = 2;
        }
      }
    return resp;
  }

  public Vertice removerArco(String s){
      for(int i = 0; i < p.size(); i++){
        Aresta e = p.get(i);
        if(e.destino.equals(s)){
          p.remove(i);
          return getVertice(e.origem);
        }
      }
      return null;
  }

  public Vertice getVertice(String k){
    Vertice novo =  new Vertice("");
    for(Vertice i : v){
      if(i.materia.equals(k)){
        novo = i;
      }
    }
    return novo;
  }

  public boolean hasTest(String[] s, String tmp){
    for(String k : s){
      if(k.equals(tmp))
        return true;
    }
    return false;
  }

  public void limparGrafo(String[] s){
    for( int i = 0; i < v.size(); i++){
      Vertice k = v.get(i);
      for (int j = 0; j < k.pendencias.size(); j++){
        if(!hasTest(s,k.pendencias.get(j))){
          k.pendencias.remove(j);
        }
      }
    }
  }



  public void kahn(String [] pendente)throws Exception{
    List<Vertice> s = new ArrayList<>();
    List<Vertice> l = new ArrayList<>();
    List<Vertice> removidos = new ArrayList<>();


    for(Vertice i : v){
      if(!verticeIndependente(i)) {
        s.add(i);
        i.nivel =1;
      }
    }

    while (!s.isEmpty()){
      Vertice v = s.remove(0);
      if(!l.contains(v)){
        l.add(v);
      }

      String destinos[] = new String[p.size()];
      for(int i=0; i<p.size(); i++) {
        Aresta a = p.get(i);
        destinos[i]= a.destino;
      }


      Vertice w;
      for(int i=0; i< p.size(); i++){
          if(v.materia.equals(destinos[i])){
            w = removerArco(destinos[i]);
            removidos.add(v);
            if(!verticeIndependente(w)){
              s.add(w);
              w.nivel = v.nivel + 1;
            }
          }
      }
      for(Vertice r : removidos){
          v.removerMaterias(r.materia);
      }
    }
    for(Aresta i : p){
      System.out.println(i);
    }
    if(p.isEmpty()){
        Collections.sort(l);
        int b = l.get(0).nivel;
        for (Vertice v : l){
            if (v.nivel > b){
                b = v.nivel;
            }
        }
        System.out.println(b);
        for(Vertice v : l) System.out.println(v.materia);
    }else{
        System.out.println("Ciclo");
    }
  }//destinoskahn

}//Grafo

class MateriasPendentes{

    public static void main(String[] args)throws Exception{
      Scanner s = new Scanner(System.in);
      String line = s.nextLine();
      //leitura da qtd de matérias
      int materias = Integer.parseInt(line);
      Grafo grafo = new Grafo();
      line = s.nextLine();
      String array[];
      Vertice x;


      for(int i=0; i<materias; i++){
        array = line.split(";");
        x = new Vertice(array[0]);
        grafo.inserirGrafo(x);
        for(int j=1; j<array.length; j++){
          x.inserirVertice(array[j]);
        }
        line = s.nextLine();
      }

      String pendencias[] = new String[Integer.parseInt(line)];
      for(int k=0; k<pendencias.length; k++) {
        pendencias[k]=s.nextLine();
      }
      Grafo novo = grafo.retirarMateriasCursadas(pendencias);
      novo.kahn(pendencias);
    }
}
