import java.util.Scanner;
class Grafo{
  int[][] matriz;
  int tamanho;

  public Grafo(){
    matriz=null;
  }

  public Grafo(int num){
    this.tamanho = num;
    matriz = new int[tamanho][tamanho];
    for (int i=0; i<tamanho; i++){
      for (int j=0; j<tamanho; j++){
        matriz[i][j] = 0;
      }
    }
  }//matriz

  public void inserirGrafo(int origem, int destino, int peso){
    matriz[origem][destino] = peso;
    matriz[destino][origem] = peso;
  }//arestas

  public void inicializar(int[] d, String[] p, int v0){
    for(int i = 0; i < tamanho; i++){
      p[i] = "";
    }
    p[v0]+=v0;
    for(int i = 0; i < tamanho; i++){
      if(matriz[v0][i]!= 0){
        d[i] = matriz[v0][i];
        p[i] += v0;
        p[i] +=i;
      }else{
        d[i] = Integer.MAX_VALUE;
    }
   }
    d[v0]=0;
  }//inicializar

  public void relaxa(int[] d, String[] p, int u, int v ){
    if(matriz[u][v]!=0){
      if(d[v] > (d[u] + matriz[u][v]) ){
        d[v]= d[u] + matriz[u][v];
        p[v] = p[u]+v;
      }
    }
  }//relaxa

  public boolean existeAberto(boolean[] aberto){
    boolean resp = false;
    for(int i=0; i<tamanho; i++) {
      if(aberto[i]){
        resp = true;
      }
    }
    return resp;
  }//existeAberto

  int menorDistancia(boolean[]aberto, int[]d){
    int resp;
    int i;
    for(i=0; i<tamanho; i++){
      if(aberto[i]) break;
    }
    if(i==tamanho){
      resp=-1;
    }else{
      int menor = i;
      for(i=menor+1; i<tamanho; i++){
        if(aberto[i] && (d[menor]>d[i])){
          menor =i;
        }
      }
      resp=menor;
    }
    return resp;
  }//menorDistancia

  public int[] dijkstra(int v0){
    int d[] = new int[tamanho];
    String p[] = new String[tamanho];
    boolean aberto[] = new boolean[tamanho];

    inicializar(d,p,v0);

    for(int i=0; i<tamanho; i++){
      aberto[i] = true;
    }

    while(existeAberto(aberto)){
      int u = menorDistancia(aberto, d);
      aberto[u]= false;
      for(int i=0; i<tamanho; i++){
        if(matriz[u][i]!= 0){
          relaxa(d,p,u,i);
        }
      }
    }

    for(int i=0; i<tamanho; i++) {
      System.out.println(p[i]);
    }
    return d;


  }

  public void imprimirOrdem(){
    for (int i=0; i<tamanho; i++){
      for (int j=0; j<tamanho; j++){
        if(matriz[i][j]!=0 && j>i){
          System.out.print("{"+i+","+j+","+ matriz[i][j]+"}");
        }
      }
      System.out.println();
    }
  }

  public void imprimirMatriz(){
    for (int i = 0; i < tamanho; i++){
      for(int j = 0; j < tamanho; j++)
        System.out.print(matriz[i][j]);
    System.out.println();
    }
  }

}//Matriz

class tp3{
  public static void main(String[] args)throws Exception{
    Scanner s = new Scanner(System.in);
    //grafo(0) ou digrafo(0)
    String line = s.next();
    //numero de cruzamentos
    line = s.next();
    int tamanho =Integer.parseInt(line);
    Grafo grafo = new Grafo(tamanho);
    //ponto incial
    line = s.next();
    int inicio = Integer.parseInt(line);
    //origem, destino, peso
    line = s.next();
    while(!line.equals("FIM")){
      String array[] = new String[3];
      array = line.split(",");
      grafo.inserirGrafo(Integer.parseInt(array[0]),
                         Integer.parseInt(array[1]),
                         Integer.parseInt(array[2]));
      line = s.next();
    }
    grafo.imprimirOrdem();
    int resp[] = grafo.dijkstra(inicio);
    for(int i=0; i<tamanho; i++) {
      System.out.println(resp[i]);
    }
  //  grafo.mostrarGrafo();





  }//main
}
