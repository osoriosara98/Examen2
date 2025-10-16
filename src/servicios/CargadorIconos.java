package servicios;

import javax.swing.ImageIcon;

public class CargadorIconos {

    public static ImageIcon cargarIcono(String path) {
        java.net.URL url = CargadorIconos.class.getResource(path);
        return (url == null) ? null : new ImageIcon(url);
    }
}
