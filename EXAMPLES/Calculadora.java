import java.util.Scanner;

public class Calculadora {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Boolean state = true;

        while (state) {
            System.out.println("Choose operation: ");
            System.out.println("""
                1. Sum +
                2. Res -
                3. Div /
                4. Mult *

                5. Salir
                """);


            Integer choose = sc.nextInt();

            if (choose == 5) {
                System.out.println("...");
                state = false;
                break;
                }
            

            System.out.print("First number: ");
            Integer first = sc.nextInt();

            System.out.println("""
                    """);
                
            System.out.print("Second number: ");
            Integer second = sc.nextInt();

            System.out.println("""
                    """);

            
            switch (choose) {
                case 1: 
                System.out.println("Resultado: " + (first + second));
                    break;
                case 2: 
                System.out.println("Resultado: " + (first - second));
                    break;
                case 3: 
                System.out.println("Resultado: " + (first / second));
                    break;
                case 4: 
                System.out.println("Resultado: " + (first * second));
                    break;
                case 5: state = false;
                    break;
            
                default:System.out.println("Invalid Input**");
                    break;
            }     
        }
    }
}
