package core;

import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;
import org.rosuda.JRI.REXP;


public class Rcomm {
   static Rengine rengine; // initialized in constructor or autowired

   public static void main(String[] args) {
      rengine.eval(String.format("greeting <- '%s'", "Hello R World"));
      REXP result = rengine.eval("greeting");
      System.out.println("Greeting from R: "+result.asString());
   }
}