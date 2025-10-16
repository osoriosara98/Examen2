package configuraciones;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FiltroNumerico extends DocumentFilter {
    private final TipoNumero tipo;

    public enum TipoNumero {
        ENTERO,
        DECIMAL
    }

    public FiltroNumerico(TipoNumero tipo) {
        this.tipo = tipo;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (esValido(fb, offset, string, 0)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (esValido(fb, offset, text, length)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }

    private boolean esValido(FilterBypass fb, int offset, String texto, int length) {
        if (texto == null || texto.isEmpty()) {
            return true;
        }

        try {
            String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
            String textoNuevo = textoActual.substring(0, offset) + texto +
                               textoActual.substring(offset + length);

            if (textoNuevo.isEmpty()) {
                return true;
            }

            if (tipo == TipoNumero.ENTERO) {
                return textoNuevo.matches("^\\d+$");
            } else {
                return textoNuevo.matches("^\\d*[.,]?\\d*$") &&
                       contarOcurrencias(textoNuevo, '.') <= 1 &&
                       contarOcurrencias(textoNuevo, ',') <= 1 &&
                       (contarOcurrencias(textoNuevo, '.') + contarOcurrencias(textoNuevo, ',')) <= 1;
            }
        } catch (BadLocationException e) {
            return false;
        }
    }

    private int contarOcurrencias(String texto, char caracter) {
        return (int) texto.chars().filter(ch -> ch == caracter).count();
    }
}
