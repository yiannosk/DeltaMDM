package test;

import java.io.PrintStream;

import org.rosuda.JRI.RConsoleOutputStream;
import org.rosuda.JRI.Rengine;

import core.TextConsole2;

public class rtest2 {
    public static void main(String[] args) {
        System.out.println("Press <Enter> to continue (time to attach the debugger if necessary)");
        try { System.in.read(); } catch(Exception e) {};
        System.out.println("Creating Rengine (with arguments)");
		Rengine re=new Rengine(args, true, new TextConsole2());
        System.out.println("Rengine created, waiting for R");
        if (!re.waitForR()) {
            System.out.println("Cannot load R");
            return;
        }
		System.out.println("re-routing stdout/err into R console");
		System.setOut(new PrintStream(new RConsoleOutputStream(re, 0)));
		System.setErr(new PrintStream(new RConsoleOutputStream(re, 1)));
		
		System.out.println("Letting go; use main loop from now on");
    }
}
