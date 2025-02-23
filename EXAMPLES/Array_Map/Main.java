package ArraySetMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    Scanner sc = new Scanner(System.in);
    List<ArrayL> num = new ArrayList<>();
    boolean state = true;

    public static void main(String[] args) {
        Main app = new Main();
        app.selctOption();
    }

    public void selctOption(){
        System.out.println("""
            ______________________

            Select an option: 
            1. ArrayList
            2. HashMap
    
            3. Exit 

            """);
        System.out.print("Select option: ");
        Integer selec = sc.nextInt();

        while (state) {
            switch (selec) {
                case 1: ArrayListt();                    
                    break;
                case 2: hashMap();
                    break;
                case 3: System.out.println("Exit");
                state = false;                    
                    break;
                default: System.out.println("Invalid option");
                    break;
            }
        }
        
    }

    public void ArrayListt(){
        num. add(new ArrayL("One"));
        num. add(new ArrayL("Two"));
        num. add(new ArrayL("Three"));
        for (int i = 0; i < num.size(); i++){
                
            System.out.println((i+1) + ". " + num.get(i));
        }
        selctOption();
    }

    public void hashMap(){
        MapC newMap = new MapC();
        HashMap<String, String> map = newMap.getHashMap();
        map.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
        state = false;
        selctOption();
    }
}
