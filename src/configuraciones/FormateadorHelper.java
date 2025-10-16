package configuraciones;

import java.text.DecimalFormat;

public class FormateadorHelper {
    private static final DecimalFormat dfNum   = new DecimalFormat("#,##0.0");
    private static final DecimalFormat dfMoney = new DecimalFormat("#,##0");

    public static String formatearNumero(double numero) {
        return dfNum.format(numero);
    }

    public static String formatearMoneda(double monto) {
        return dfMoney.format(Math.round(monto));
    }
}
