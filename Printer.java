import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Printer{
	public static void main(String [] args){
		File file = new File("Maze1.txt");
		try{
			print(file);
		}catch{System.out.println("FILE NOT FOUND");}
	}
	
