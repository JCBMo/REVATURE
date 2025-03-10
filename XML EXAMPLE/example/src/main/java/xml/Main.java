package xml;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        ApplicationContext contextXML = new ClassPathXmlApplicationContext("Beans.xml");
        HelloWorld obj = (HelloWorld) contextXML.getBean("helloW");
        obj.getMessage();

        Animal animal1 = (Animal) contextXML.getBean("animal1");
        System.out.println(animal1);

        Animal animal2 = (Animal) contextXML.getBean("animal2");
        System.out.println(animal2);
        
        Owner owner = (Owner) contextXML.getBean("owner");
        System.out.println(owner);

    }
}