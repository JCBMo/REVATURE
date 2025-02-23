import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoList {
        Scanner sc = new Scanner(System.in);
        List<String> tasks = new ArrayList<>();
        boolean state = true;
        
    
        public static  void main(String[] args) {
            ToDoList app = new ToDoList();
            app.iniciar();
    }

    public void iniciar(){
        System.out.println("""

                    ______________________

                    Select an action: 
                    1. Add task
                    2. Show tasks
                    3. Edit
    
                    4. Exit 

                    """);
            System.out.print("Select option: ");
            Integer selec = sc.nextInt();

            if(selec == 4){
                System.out.println(tasks);
                state = false;
            }

        while (state) {
            switch (selec) {
                case 1:agregar();
                    break;
                case 2: ver();
                    break;
                case 3: edit();
                    break;
            
                default: System.out.println("Select an option");
                    break;
            }   
        }
    }

    public  void agregar(){
        System.out.print("Choose number of tasks: ");
        Integer numTasks = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i < (numTasks + 1); i++) {
            System.out.print("Enter the task " + i + ":");
            String tarea = sc.nextLine();
            tasks.add(tarea);            
        }
        iniciar();   
    }


    public void ver(){

        while (state) {
            if( tasks.size() == 0){
                System.out.println("""
                    ______________________
    
                    Number of tasks:
                    No tasks***
                    """);
                
                iniciar();
            }else{
                System.out.println("""
                    ______________________
    
                    Tasks: 
                    """);
                for (int i = 0; i < tasks.size(); i++){
                
                    System.out.println((i+1) + ". " + tasks.get(i));
                }
                
                iniciar();
            }
        }

        
        
    }

    public void edit(){         

        while (state) {
            if (tasks.size() == 0){
                System.out.println("No tasks");
                iniciar();
            } else {
                for(int i = 0; i < tasks.size(); i++ ){
                    System.out.println((i+1) + ". " + tasks.get(i));
                }
            } 
            System.out.print("Select task to edit: ");
            Integer editTask = sc.nextInt(); 
            sc.nextLine();

            int index = editTask - 1;

            System.out.print("Enter the new taskS: ");
            String newTask = sc.nextLine();

            tasks.set(index, newTask);   
            iniciar();        
        }
    }
}
