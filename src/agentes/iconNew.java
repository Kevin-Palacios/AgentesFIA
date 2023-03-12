/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Yomonki
 */
public class iconNew extends ImageIcon {
    private int peso;
    private int i, j;
    
    iconNew(Image imagen, int peso){
        super(imagen);
        this.peso = peso;
    }
    
    iconNew(Image imagen){
        super(imagen);
        
    }
    iconNew(String rutaImagen){
        super(rutaImagen);
        
    }
    
    
    
    
    @Override
    public boolean equals(Object other){
      iconNew otherImage = (iconNew) other;
      if (other == null) return false;
      return this.peso==otherImage.peso;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    
    
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
    
    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
