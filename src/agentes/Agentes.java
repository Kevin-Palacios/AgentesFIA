/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package agentes;

/**
 *
 * @author macario
 */
public class Agentes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic herez
        int [] holawas = new int[12];
        for (int l = 0; l < holawas.length; l++) {
            try {
                if (l==0) {
                    System.out.println(holawas[l+22]);
                }else{
                    System.out.println(holawas[l]);
                }
                
            } catch (Exception e) {
                System.out.println("Error owo");
            }
            
        }
        Escenario e = new Escenario();
        e.setVisible(true);
    }
    
}
