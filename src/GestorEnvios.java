import java.util.ArrayList;
import java.util.Scanner;

/* Clase encargada de manejar el CRUD de los envíos */
public class GestorEnvios {

    //creo un arreglo para  almacenar los envíos
    private ArrayList<Envio> listaEnvios = new ArrayList<>();
    //uso scanner para leer datos por consola
    private Scanner sc = new Scanner(System.in);

    // creo método para agregar un nuevo envío
    public void agregarEnvio() {
        System.out.println("\nAgregar envío:");

        System.out.print("Código: ");
        String codigo = sc.nextLine();

        System.out.print("Cliente: ");
        String cliente = sc.nextLine();

        System.out.print("Peso (kg): ");
        double peso = sc.nextDouble();

        System.out.print("Distancia (km): ");
        double distancia = sc.nextDouble();
        sc.nextLine(); 

        System.out.println("Tipo de envío (1. Terrestre, 2. Aéreo, 3. Marítimo): ");
        int tipo = sc.nextInt();
        sc.nextLine(); // para impedir que salte al siguiente input sin haber terminado de escribir la info

        Envio envio;

        //uso switch para mirar el numero que ingresó el usuario
        switch (tipo) {
            case 1: //case despliega las opciones
                envio = new EnvioTerrestre(codigo, cliente, peso, distancia);
                break; // uso break para detener el switch, ya no espera mas opciones una vez elige uno de los cases
            case 2:
                envio = new EnvioAereo(codigo, cliente, peso, distancia);
                break;
            case 3:
                envio = new EnvioMaritimo(codigo, cliente, peso, distancia);
                break;
            default://agrego default para manejar errores
                System.out.println("Tipo no válido, no se creó el envío.");
                return;
        }

        listaEnvios.add(envio);
        System.out.println("✅ Envío agregado correctamente.");
    }
}
