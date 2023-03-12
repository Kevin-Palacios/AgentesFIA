/*0000  
0001
0010
0011
0100
0101
0110
0111=====
1000==========
1001
1010
1011
1100 ===========
1101=========
1110========
1111==========
 */
 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 *
 * @author macario
 */
public class Agente extends Thread {

    String nombre;
    int i;
    int j;
    int[][] mapa;
    iconNew icon;
    int[][] matrix;
    JLabel tablero[][];
    int traeCoso;
    int encontro;
    JLabel casillaAnterior;
    Random aleatorio = new Random(System.currentTimeMillis());
    Coordenadas coordAnteriores;
    boolean repintar;
    //Random direccion = new Random(System.currentTimeMillis());

    public Agente(String nombre, iconNew icon, int[][] matrix, JLabel tablero[][]) {
        this.nombre = nombre;
        this.icon = icon;
        this.matrix = matrix;
        this.tablero = tablero;

        this.i = aleatorio.nextInt(matrix.length);
        this.j = aleatorio.nextInt(matrix.length);
        tablero[i][j].setIcon(icon);
        this.mapa=new int[matrix.length][matrix.length];
        this.repintar=false;
        this.encontro=0;
        
    }

    public void run() {
        crearMapa();
        System.out.println("Robot: "+nombre+" Inicio");
        for (int k = 0; k < mapa.length; k++) {
            for (int l = 0; l < mapa.length; l++) {
                System.out.print(mapa[k][l]+" \t");
            }
            System.out.println();
        }
        System.out.println("Robot: "+nombre+" Fin");
        mover resultadoMoverse = new mover();
        iconNew nave;
        ArrayList navesx = new ArrayList<Integer>();
        ArrayList navesy = new ArrayList<Integer>();

        int pos = 0;
        navesx.clear();
        navesy.clear();
        for (int k = 0; k < matrix.length; k++) {
            for (int l = 0; l < matrix.length; l++) {
                nave = (iconNew) tablero[k][l].getIcon();
                if (tablero[k][l].getIcon() != null) {
                    if (nave.getPeso() == 2) {
                        System.out.println("Nave encontrada, x:" + k + " y: " + l);
                        navesx.add(k);
                        navesy.add(l);
                    }
                }

            }
        }

        int dirRow = 1;
        int dirCol = 1;      //F+,F-,C+,C-  
        int[] posObstaculos = {0, 0, 0, 0};
        

        boolean condicion = true;
        
        traeCoso = 0;
        while (condicion) {

            //boolean bandera = true;
            posObstaculos[0] = 0;
            posObstaculos[1] = 0;
            posObstaculos[2] = 0;
            posObstaculos[3] = 0;
            boolean bandera_cambia = true;
            
            iconNew muestra;
            int direccion = (int) (Math.random() * 2);
            resultadoMoverse.setBandera(bandera_cambia);
            resultadoMoverse.setDireccionAgente(direccion);
            resultadoMoverse.setDireccionCol(dirCol);
            resultadoMoverse.setDireccionRow(dirRow);
            resultadoMoverse.setRomper(false);
            //verificar si hay obstaculos a su alrededor antes de tomar una direccion

            if (i < matrix.length - 1) {
                if (tablero[i + 1][j].getIcon() != null) {
                    muestra = (iconNew) tablero[i + 1][j].getIcon();
                    if (muestra.getPeso() == 1) {
                        posObstaculos[0] = 1;
                    } 
                }

            } else {
                posObstaculos[0] = 1;
            }
            if (i > 0) {
                if (tablero[i - 1][j].getIcon() != null) {
                    muestra = (iconNew) tablero[i - 1][j].getIcon();
                    if (muestra.getPeso() == 1) {
                        posObstaculos[1] = 1;
                    }
                }

            } else {
                posObstaculos[1] = 1;
            }
            if (j < matrix.length - 1) {
                if (tablero[i][j + 1].getIcon() != null) {
                    muestra = (iconNew) tablero[i][j + 1].getIcon();
                    if (muestra.getPeso() == 1) {
                        posObstaculos[2] = 1;

                    }
                }

            } else {
                posObstaculos[2] = 1;
            }
            if (j > 0) {
                if (tablero[i][j - 1].getIcon() != null) {
                    muestra = (iconNew) tablero[i][j - 1].getIcon();
                    if (muestra.getPeso() == 1) {
                        posObstaculos[3] = 1;
                    }
                }

            } else {
                posObstaculos[3] = 1;
            }
             //F+,F-,C+,C-
            resultadoMoverse = moverse(posObstaculos, dirCol, dirRow, direccion, bandera_cambia);
            if (resultadoMoverse.isRomper()) {
                break;
            }
            direccion=resultadoMoverse.getDireccionAgente();
            dirCol=resultadoMoverse.getDireccionCol();
            dirRow=resultadoMoverse.getDireccionRow();
            bandera_cambia=resultadoMoverse.isBandera();
            casillaAnterior = tablero[i][j];
            coordAnteriores = new Coordenadas(i, j);
            if (bandera_cambia) {
                int numeroRow = (int) (Math.random() * 2);
                if (numeroRow == 1) {
                    dirRow = 1;
                } else {
                    dirRow = -1;
                }

                if (i > matrix.length - 2 && dirRow == 1) {
                    dirRow = -1;
                }
                if (i < 1 && dirRow == -1) {
                    dirRow = 1;
                }

                if (numeroRow == 1) {
                    dirCol = 1;
                } else {
                    dirCol = -1;
                }

                if (j > matrix.length - 2 && dirCol == 1) {
                    dirCol = -1;
                }
                if (j < 1 && dirCol == -1) {
                    dirCol = 1;
                }
            }
            //Recoje muestras

            if (traeCoso == 0) {

                if (i < matrix.length - 1) {
                    if (tablero[i + 1][j].getIcon() != null) {
                        muestra = (iconNew) tablero[i + 1][j].getIcon();
                        if (muestra.getPeso() == 3) {
                            dirRow = 1;
                            direccion = 1;
                            traeCoso = 1;
                            encontro=1;
                        }
                    }
                }

                if (i > 0) {
                    if (tablero[i - 1][j].getIcon() != null) {
                        muestra = (iconNew) tablero[i - 1][j].getIcon();
                        if (muestra.getPeso() == 3) {
                            dirRow = -1;
                            direccion = 1;
                            traeCoso = 1;
                            encontro=1;
                        }
                    }
                }
                if (j < matrix.length - 1) {
                    if (tablero[i][j + 1].getIcon() != null) {
                        muestra = (iconNew) tablero[i][j + 1].getIcon();
                        if (muestra.getPeso() == 3) {
                            dirCol = 1;
                            direccion = 0;
                            traeCoso = 1;
                            encontro=1;
                        }
                    }
                }

                if (j > 0) {
                    if (tablero[i][j - 1].getIcon() != null) {
                        muestra = (iconNew) tablero[i][j - 1].getIcon();
                        if (muestra.getPeso() == 3) {
                            dirCol = -1;
                            direccion = 0;
                            traeCoso = 1;
                            encontro=1;
                        }
                    }
                }
                
                //cuando direccion ==1 vamos a ir arriba o abajo, el signo de dirRow va a decir si arriba(-) o abajo(+)
                //cuando direccion ==0 vamos a ir izquierda o derecha, el signo de dirRow va a decir si izquierda(-) o derecha(+)
                if (direccion == 1) {
                    i = i + dirRow;
                    //System.out.println(nombre + " DirRow: " + dirRow);
                } else {
                    j = j + dirCol;
                    //System.out.println(nombre + " DirCol: " + dirCol);
                }

                actualizarPosicion(navesx, navesy);

                try {

                    sleep(321 + aleatorio.nextInt(100));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
            }else{
                System.out.println("*******************************Trae coso");
                regresarNave(navesx, navesy);
                traeCoso=0;
            }

            /*
            if (!hayMuestra() && traeCoso==0) {
                condicion=false;
            }
            */

        }
        
        System.out.println("No hay mas muestras en el tablero");

    }

    /*
    public static iconNew getIconNew(){
        URL urlImage = ClassLoader.getSystemResource("org/sunspotworld/heatsensors/icon/" + name + ".png");
        
    }
     */
    public synchronized void actualizarPosicion(ArrayList<Integer> navesx, ArrayList<Integer> navesy) {
        iconNew motherIcon;
        iconNew sampleIcon;
        casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
        motherIcon = new iconNew("imagenes/mother.png");
        motherIcon = new iconNew(motherIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH), 2);
        sampleIcon = new iconNew("imagenes/sample.png");
        sampleIcon = new iconNew(sampleIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH), 3);
        for (int k = 0; k < navesx.size(); k++) {
            tablero[navesx.get(k)][navesy.get(k)].setIcon(motherIcon);
        }
        
        if (repintar && traeCoso==1 && encontro==0) {
            tablero[coordAnteriores.getX()][coordAnteriores.getY()].setIcon(sampleIcon);
            repintar=false;
        }
        if (tablero[i][j].getIcon()!=null) {
            if (((iconNew)tablero[i][j].getIcon()).getPeso()==3 && traeCoso==1 && encontro==0) {
                coordAnteriores = new Coordenadas(i,j);
                repintar=true;
            }
        }
        if (traeCoso==1) {
            icon = new iconNew("imagenes/"+nombre+"_1.png");
            icon = new iconNew(icon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH), 0);
            tablero[i][j].setIcon(icon);
        }else{
            icon = new iconNew("imagenes/"+nombre+"_0.png");
            icon = new iconNew(icon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH), 0);
            tablero[i][j].setIcon(icon);
        }
        
        //System.out.println(nombre + " in -> Row: " + i + " Col:" + j);
        encontro=0;
    }

    public synchronized void regresarNave(ArrayList<Integer> navesx, ArrayList<Integer> navesy){
        if (mapa[i][j]==0) {
            System.out.println("Imposible regresar");
        }else{
            while(mapa[i][j]!=1){
                if (i<mapa.length-1) {
                    if (mapa[i+1][j]==mapa[i][j]-1 || mapa[i+1][j]==1) {
                        casillaAnterior = tablero[i][j];

                        i = i + 1;
                        //System.out.println(nombre + " DirRow: " + dirRow);
                        //System.out.println(nombre + " DirCol: " + dirCol);


                        actualizarPosicion(navesx, navesy);
                        if (mapa[i][j]==1) {
                            traeCoso=0;
                        }
                        

                        try {

                            sleep(321 + aleatorio.nextInt(100));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                if (i>0) {
                    if (mapa[i-1][j]==mapa[i][j]-1 || mapa[i-1][j]==1) {
                        casillaAnterior = tablero[i][j];
                        i = i - 1;
                        //System.out.println(nombre + " DirRow: " + dirRow);
                        //System.out.println(nombre + " DirCol: " + dirCol);


                        actualizarPosicion(navesx, navesy);
                        if (mapa[i][j]==1) {
                            traeCoso=0;
                        }
                        try {

                            sleep(321 + aleatorio.nextInt(100));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }
                if (j<mapa.length-1) {
                    if (mapa[i][j+1]==mapa[i][j]-1 || mapa[i][j+1]==1) {
                        casillaAnterior = tablero[i][j];
                        j = j + 1;
                        //System.out.println(nombre + " DirRow: " + dirRow);
                        //System.out.println(nombre + " DirCol: " + dirCol);


                        actualizarPosicion(navesx, navesy);
                        if (mapa[i][j]==1) {
                            traeCoso=0;
                        }
                        try {

                            sleep(321 + aleatorio.nextInt(100));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }
                if (j>0) {
                    if (mapa[i][j-1]==mapa[i][j]-1 || mapa[i][j-1]==1) {
                        casillaAnterior = tablero[i][j];
                        j = j - 1;
                        //System.out.println(nombre + " DirRow: " + dirRow);
                        //System.out.println(nombre + " DirCol: " + dirCol);


                        actualizarPosicion(navesx, navesy);
                        if (mapa[i][j]==1) {
                            traeCoso=0;
                        }
                        try {

                            sleep(321 + aleatorio.nextInt(100));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            }
        }
    }
    
    public synchronized int buscarNave(ArrayList<Integer> navesx, ArrayList<Integer> navesy) {
        int index_menor = 0;
        int distancia = 0;
        int distanciaMenor = 100000;
        for (int k = 0; k < navesx.size(); k++) {

            distancia = Math.abs(navesx.get(k) - i) + Math.abs(navesy.get(k) - j);
            if (distancia < distanciaMenor) {
                distanciaMenor = distancia;
                index_menor = k;
            }
        }

        return index_menor;
    }
   
    public synchronized void crearMapa(){
        Queue<Coordenadas> colaNaves = new LinkedList<>();
        for (int k = 0; k < mapa.length; k++) {
            for (int l = 0; l < mapa.length; l++) {
                if (tablero[k][l].getIcon() != null) {
                    if (((iconNew)tablero[k][l].getIcon()).getPeso()==2) {
                        colaNaves.add(new Coordenadas(k, l));
                        mapa[k][l]=1;
                    }else if(((iconNew)tablero[k][l].getIcon()).getPeso()==1){
                        mapa[k][l]=100000;
                    }
                }
            }
        }
        
        Coordenadas coordenadasExtraidas;
        while(!colaNaves.isEmpty()){
            
            coordenadasExtraidas = colaNaves.remove();
            llenarMapaAbajo(coordenadasExtraidas.getX(), coordenadasExtraidas.getY(), coordenadasExtraidas.getX(), coordenadasExtraidas.getY());
            llenarMapaArriba(coordenadasExtraidas.getX(), coordenadasExtraidas.getY(), coordenadasExtraidas.getX(), coordenadasExtraidas.getY());
            //llenarMapaDer(coordenadasExtraidas.getX(), coordenadasExtraidas.getY(), coordenadasExtraidas.getX(), coordenadasExtraidas.getY());
            //llenarMapaIzq(coordenadasExtraidas.getX(), coordenadasExtraidas.getY(), coordenadasExtraidas.getX(), coordenadasExtraidas.getY());

            for (int k = 0; k < mapa.length; k++) {
                for (int l = 0; l < mapa.length; l++) {
                    if (mapa[k][l]<1000 && mapa[k][l]!=0) {
                        try {
                            if (mapa[k+1][l]==0 || mapa[k-1][l]==0 || mapa[k][l+1]==0 || mapa[k][l-1]==0) {
                                System.out.println("Esta cosa paso owo K: " + k+"l: "+l);
                                llenarMapaAbajo(k, l, k, l);
                                llenarMapaArriba(k, l, k, l);
                                System.out.println("Esta cosa paso owo K: " + k+"l: "+l);
                            }
                        } catch (Exception e) {
                        }

                    }
                }

            }
            
        }
    }
    
    public synchronized void verificarCasillasMapa(int cx, int cy){
        if (cx<mapa.length-1) {
                    if (mapa[cx+1][cy]==0 || (mapa[cx+1][cy]>mapa[cx][cy] && mapa[cx+1][cy]<1000)) {
                        mapa[cx+1][cy]=mapa[cx][cy]+1;
                    }
                }
                
                if (cx>0) {
                    if (mapa[cx-1][cy]==0 || (mapa[cx-1][cy]>mapa[cx][cy] && mapa[cx-1][cy]<1000)) {
                        mapa[cx-1][cy]=mapa[cx][cy]+1;
                        //llenarMapa(cx-1, cy, corigx, corigy);
                    }
                }
                if (cy<mapa.length-1) {
                    if (mapa[cx][cy+1]==0 || (mapa[cx][cy+1]>mapa[cx][cy] && mapa[cx][cy+1]<1000)) {
                        mapa[cx][cy+1]=mapa[cx][cy]+1;
                        //llenarMapa(cx, cy+1, corigx, corigy);
                    }
                }
                if (cy>0) {
                    if (mapa[cx][cy-1]==0 || (mapa[cx][cy-1]>mapa[cx][cy] && mapa[cx][cy-1]<1000)) {
                        mapa[cx][cy-1]=mapa[cx][cy]+1;
                        //llenarMapa(cx, cy-1, corigx, corigy);
                    }
                }
    }
    public synchronized void llenarMapaAbajo(int cx, int cy, int corigx, int corigy){
        try {
            if (cx>=0 && cx<mapa.length && cy>=0 && cy<mapa.length) {
                
                verificarCasillasMapa(cx, cy);
                
                //llenarMapaArriba(cx-1, cy, corigx, corigy);
                if (cy<mapa.length-1) {
                    if (mapa[cx][cy+1]<1000) {
                        llenarMapaDer(cx, cy+1, corigx, corigy);
                    }
                }
                
                if (cy>0) {
                    if (mapa[cx][cy-1]<1000) {
                        llenarMapaIzq(cx, cy-1, corigx, corigy);
                    }
                }
                
                if (cx>0) {
                    if (mapa[cx-1][cy]<1000) {
                        llenarMapaArriba(cx-1, cy, corigx, corigy);
                    }
                }
                
                if (cx<mapa.length-1) {
                    if (mapa[cx+1][cy]<1000) {
                        llenarMapaAbajo(cx+1, cy, corigx, corigy);
                    }
                }
                
            }
        } catch (Exception e) {
            System.out.println("Se rompe en abajo cx="+cx+" y cy="+cy);
        }
    }
    
    public synchronized void llenarMapaArriba(int cx, int cy, int corigx, int corigy){
        try {
            if (cx>=0 && cx<mapa.length && cy>=0 && cy<mapa.length) {
                
                verificarCasillasMapa(cx, cy);
                
                if (cy<mapa.length-1) {
                    if (mapa[cx][cy+1]<1000) {
                        llenarMapaDer(cx, cy+1, corigx, corigy);
                    }
                }
                
                if (cy>0) {
                    if (mapa[cx][cy-1]<1000) {
                        llenarMapaIzq(cx, cy-1, corigx, corigy);
                    }
                }
                
                if (cx>0) {
                    if (mapa[cx-1][cy]<1000) {
                        llenarMapaArriba(cx-1, cy, corigx, corigy);
                    }
                }
                
            }
        } catch (Exception e) {
            System.out.println("Se rompe en arriba cx="+cx+" y cy="+cy);
        }
    }
    
    public synchronized void llenarMapaDer(int cx, int cy, int corigx, int corigy){
        try {
            if (cx>=0 && cx<mapa.length && cy>=0 && cy<mapa.length) {
                verificarCasillasMapa(cx, cy);
                
                if (cy<mapa.length-1) {
                    if (mapa[cx][cy+1]<1000) {
                        llenarMapaDer(cx, cy+1, corigx, corigy);
                    }
                }
                
            }
        } catch (Exception e) {
            System.out.println("Se rompe en der cx="+cx+" y cy="+cy);
        }
    }
    
    public synchronized void llenarMapaIzq(int cx, int cy, int corigx, int corigy){
        try {
            if (cx>=0 && cx<mapa.length && cy>=0 && cy<mapa.length) {
                
                verificarCasillasMapa(cx, cy);
                
                if (cy>0) {
                    if (mapa[cx][cy-1]<1000) {
                        llenarMapaIzq(cx, cy-1, corigx, corigy);
                    }
                }
                
            }
        } catch (Exception e) {
            System.out.println("Se rompe en izq cx="+cx+" y cy="+cy);
        }
    }
    
    public synchronized mover moverse(int[] posObstaculos, int dirCol, int dirRow, int direccion, boolean bandera_cambia){
        mover resultado = new mover();
        resultado.setRomper(false);
        resultado.setBandera(bandera_cambia);
        resultado.setDireccionAgente(direccion);
        resultado.setDireccionCol(dirCol);
        resultado.setDireccionRow(dirRow);
        if (posObstaculos[0] == 1 && posObstaculos[1] == 1 && posObstaculos[2] == 1 && posObstaculos[3] == 1) {
            resultado.setRomper(true);
            return resultado;

        } else if (posObstaculos[0] == 1 && posObstaculos[1] == 1 && posObstaculos[2] == 1 && posObstaculos[3] == 0) {
            dirCol = -1;
            direccion = 0;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 1 && posObstaculos[1] == 1 && posObstaculos[2] == 0 && posObstaculos[3] == 1) {
            dirCol = 1;
            direccion = 0;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 1 && posObstaculos[1] == 1 && posObstaculos[2] == 0 && posObstaculos[3] == 0) {
            direccion = 0;
        } else if (posObstaculos[0] == 1 && posObstaculos[1] == 0 && posObstaculos[2] == 1 && posObstaculos[3] == 1) {
            dirRow = -1;
            direccion = 1;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 1 && posObstaculos[1] == 0 && posObstaculos[2] == 1 && posObstaculos[3] == 0) {
            dirRow = -1;
            dirCol = -1;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 1 && posObstaculos[1] == 0 && posObstaculos[2] == 0 && posObstaculos[3] == 1) {
            dirRow = -1;
            dirCol = 1;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 1 && posObstaculos[1] == 0 && posObstaculos[2] == 0 && posObstaculos[3] == 0) {
            dirRow = -1;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 0 && posObstaculos[1] == 1 && posObstaculos[2] == 1 && posObstaculos[3] == 1) {
            dirRow = 1;
            direccion = 1;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 0 && posObstaculos[1] == 1 && posObstaculos[2] == 1 && posObstaculos[3] == 0) {
            dirRow = 1;
            dirCol = -1;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 0 && posObstaculos[1] == 1 && posObstaculos[2] == 0 && posObstaculos[3] == 1) {
            dirRow = 1;
            dirCol = 1;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 0 && posObstaculos[1] == 1 && posObstaculos[2] == 0 && posObstaculos[3] == 0) {
            dirRow = 1;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 0 && posObstaculos[1] == 0 && posObstaculos[2] == 1 && posObstaculos[3] == 1) {
            direccion = 1;
        } else if (posObstaculos[0] == 0 && posObstaculos[1] == 0 && posObstaculos[2] == 1 && posObstaculos[3] == 0) {
            dirCol = -1;
            bandera_cambia = false;
        } else if (posObstaculos[0] == 0 && posObstaculos[1] == 0 && posObstaculos[2] == 0 && posObstaculos[3] == 1) {
            dirCol = 1;
            bandera_cambia = false;
        }
        resultado.setBandera(bandera_cambia);
        resultado.setDireccionAgente(direccion);
        resultado.setDireccionCol(dirCol);
        resultado.setDireccionRow(dirRow);
        return resultado;
    }

    /*
    public synchronized boolean hayMuestra(){
        for (int k = 0; k < matrix.length; k++) {
            for (int l = 0; l < matrix.length; l++) {
                if (tablero[i][j].getIcon()!=null) {
                    if (((iconNew)tablero[i][j].getIcon()).getPeso()==3) {
                        
                        return true;
                    }
                }
                
            }
        }
        return false;
    }
    */
}