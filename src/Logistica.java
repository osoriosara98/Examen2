import java.util.Scanner;

/* Clase principal donde se despliega el menu para elegir entre las opcioens del CRUD*/
public class Logistica {

    // Scanner para leer la entrada del usuario
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        GestorEnvios gestor = new GestorEnvios(); // objeto que manejará el CRUD
        int opcion;

        //do /while : haga esto.. mientras se cumpla esta condicion
        do {
            System.out.println("\nMenu de envios:");
            System.out.println("1. Agregar envío");
            System.out.println("2. Retirar envío");
            System.out.println("3. Listar envíos");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt(); //guardo la opcion que elige el usuario
            sc.nextLine(); // el buffer que es la memoria temporal para limpiar el menu anterior y poner el nuevo submenu de la opcion que se escogio

            // uso switch cuando tengo mas de dos opciones sino usaria if
            switch (opcion) {
                case 1:
                    gestor.agregarEnvio();
                    break;
                case 2:
                    gestor.retirarEnvio();
                    break;
                case 3:
                    gestor.listarEnvios();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 4);
    }
}
