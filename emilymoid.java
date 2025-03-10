Asteroid.java:
-------------
import java.lang.Math;

import java.util.Scanner;

public class Asteroid {
    private double a;
    private double b;
    private double c;
    private double A;
    private double B;
    private double increment;
    private double xStartLine;
    private double xEndLine;
    private String neoName;
    Scanner scanner = new Scanner(System.in);

    // constructor for Asteroid
    public Asteroid(String theneoName) {
        neoName = theneoName;
    }

    public void twoTrajectories() {
        System.out.println("\nEnter the a, b, and c values of the " + neoName + "'s quadratic trajectory:");
        System.out.print("a: ");
        a = scanner.nextDouble();
        System.out.print("b: ");
        b = scanner.nextDouble();
        System.out.print("c: ");
        c = scanner.nextDouble();    
       

        System.out.println("\nEnter the semi-major (A) and semi-minor (B) axes' values for Earth's orbit:");
        System.out.print("A: ");
        A = scanner.nextDouble();
        System.out.print("B: ");
        B = scanner.nextDouble();
    }
   
    public void startEndIncrement() {
        System.out.println("\nEnter the x-value the MOID determination should start with:");
        xStartLine = scanner.nextDouble();
       
        System.out.println("\nEnter the x-value the MOID determination should end with:");
        xEndLine = scanner.nextDouble();
       
        System.out.print("\nEnter the incrementation value: ");
        increment = scanner.nextDouble();
        
        
    }
   
    public void getMoid()
    {
        // first calculate currentDistance so you have a value that previousDistance can compare to
        double centerCircleX = 0;
        double centerCircleY = 0;
        // previousDistance set to very large value so it will be larger than currentDistance and therefore the while loop will can start running
        double previousDistance = 10000000;
        // double xStartLine = thexStartLine;
        double yStartLine = c + b*xStartLine+a*Math.pow(xStartLine,2);
        double currentDistance = Math.sqrt(Math.pow(centerCircleY-yStartLine,2) + Math.pow(centerCircleX-xStartLine,2));
        // finds the x coordinate on orbit of earth that intersects 
        // with a line from 0,0 to the point on the asteroid orbit that returns the shortest distance
        double pointOnCircleX = -Math.sqrt((Math.pow(A,2)*Math.pow(B,2))/(Math.pow(B,2) + Math.pow(A,2)*Math.pow(-yStartLine/xStartLine, 2)));
        double pointOnCircleY = Math.sqrt(1.0- Math.pow(pointOnCircleX/A,2.0))*B;
    
        // actual shortest distance between a point on the circle and one on the line
        double shortestDistance = Math.sqrt(Math.pow(pointOnCircleY-yStartLine,2) + Math.pow(pointOnCircleX-xStartLine, 2));
        
        // loops break when the distance between the center of the ellipse and the ellipse begins to increase
        while (currentDistance < previousDistance)
        {
            System.out.println(shortestDistance);
            previousDistance = currentDistance;
            xStartLine -= increment;
            yStartLine = c + b*xStartLine+a*Math.pow(xStartLine,2);
            currentDistance = Math.sqrt(Math.pow(centerCircleY-yStartLine,2) + Math.pow(centerCircleX-xStartLine,2));
            pointOnCircleX = -Math.sqrt((Math.pow(A,2)*Math.pow(B,2))/(Math.pow(B,2) + Math.pow(A,2)*Math.pow(-yStartLine/xStartLine, 2)));
            pointOnCircleY = Math.sqrt(1.0- Math.pow(pointOnCircleX/A,2.0))*B;
            shortestDistance = Math.sqrt(Math.pow(pointOnCircleY-yStartLine,2) + Math.pow(pointOnCircleX-xStartLine, 2));
        }
       
       
        // print out shortest distance statement
        System.out.println("The worst case MOID between Earth and " + neoName + " is " + shortestDistance + " million km");
    
    }
}

AsteroidApp.java:
----------------
import java.util.Scanner;

public class AsteroidApp {
    public static void main(String[] args)
    {
        // for testing purposes:
        // a: -0.00098
        // b = 0.168
        // c = 153
        // A = 149.6
        // B = 149.58
        // Starting x value = -29.5
        // Ending x value = -35
        // incrementation value = 0.01
        
        
        Scanner scanner = new Scanner(System.in);
       
       // asking for user inputs
        System.out.println("Welcome to the NEO and MOID determination program.");
        System.out.println("/////////////////////////////////////////////////\n");
        System.out.print("Enter the name of the NEO: ");
        String neoName = scanner.nextLine();
       
        // create an Asteroid object and calculate MOID
        Asteroid neo = new Asteroid(neoName);
        neo.twoTrajectories();
        neo.startEndIncrement();
        System.out.println("\nAll possible MOID values are as follows:");
        neo.getMoid();
    }
}

