import java.util.ArrayList;
import java.util.Scanner;

/* Clase encargada de manejar el CRUD de los envíos */
public class GestorEnvios {

    // creo un arreglo para almacenar los envíos
    private ArrayList<Envio> listaEnvios = new ArrayList<>();
    // uso scanner para leer datos por consola
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
        sc.nextLine(); // para impedir que salte al siguiente input sin haber terminado de escribir la
                       // info

        Envio envio;

        // uso switch para mirar el numero que ingresó el usuario
        switch (tipo) {
            case 1: // case despliega las opciones
                envio = new EnvioTerrestre(codigo, cliente, peso, distancia);
                break; // uso break para detener el switch, ya no espera mas opciones una vez elige uno
                       // de los cases
            case 2:
                envio = new EnvioAereo(codigo, cliente, peso, distancia);
                break;
            case 3:
                envio = new EnvioMaritimo(codigo, cliente, peso, distancia);
                break;
            default:// agrego default para manejar errores
                System.out.println("Tipo no válido, no se creó el envío.");
                return;
        }

        listaEnvios.add(envio);
        System.out.println("✅ Envío agregado correctamente.");
    }



    // método para listar todos los envíos
    public void listarEnvios() {
        System.out.println("\nLista de envíos:");
        if (listaEnvios.isEmpty()) {
            System.out.println("No hay envíos registrados.");
            return;
        }
        for (Envio envio : listaEnvios) {
            System.out.printf("Código: %s, Cliente: %s, Peso: %.2f kg, Distancia: %.2f km, Tarifa: $%.2f\n",
                    envio.codigo, envio.cliente, envio.peso, envio.distancia, envio.calcularTarifa());
        }
    }

    // método para retirar un envío por su código
    public void retirarEnvio() {
        System.out.print("\nIngrese el código del envío a retirar: ");
        String codigo = sc.nextLine();

        for (int i = 0; i < listaEnvios.size(); i++) {
            if (listaEnvios.get(i).codigo.equals(codigo)) {
                listaEnvios.remove(i);
                System.out.println("✅ Envío retirado correctamente.");
                return;
            }
        }
        System.out.println("❌ Envío no encontrado.");
    }
}
