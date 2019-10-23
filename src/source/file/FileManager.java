package source.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
	
	// These objects are for reading and writing to files
	private File file;
	private Scanner reader;
	private PrintStream writer;
	// These are for temporarily storing the input from a file
	ArrayList<Integer> input;
	ArrayList<String> textInput;
	// This array will store the non-data members during input. The actual data is returned by read()
	String[] words; // You can use getWords() to return this if you need it
	// Where to load the file from
	String path;
	
	public FileManager(String path)
	{
		this.path = path;
		words = null;
		textInput = new ArrayList<String>();
		input = new ArrayList<Integer>();
	}
	
	public int[] read()
	{
		try
		{
			file = new File(path);
			reader = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			System.err.println("Can't load path: " + path);
			e.printStackTrace();
			return null;
		}
		
		while (reader.hasNext())
		{
			if (reader.hasNextInt())
			{
				input.add(reader.nextInt());
			}
			else
			{
				textInput.add(reader.next());
			}
		}
		
		if (textInput.isEmpty())
			words = null;
		else
		{
			words = new String[textInput.size()];
			for (int i = 0; i < textInput.size(); i++)
				words[i] = textInput.get(i);
		}
		
		reader.close();
		
		if (input.isEmpty())
			return null;
		else
		{
			int[] array = new int[input.size()];
			for (int i = 0; i < input.size(); i++)
				array[i] = input.get(i);
			return array;
		}
	}
	
	public void write(String[] comment, int[] data)
	{
		try
		{
			file = new File(path);
			writer = new PrintStream(file);
		}
		catch (FileNotFoundException e) {
			System.err.println("FATAL ERROR: Can't write to path: " + path);
			e.printStackTrace();
			return;
		}
		
		for (int i = 0; i < data.length; i++)
		{
			writer.println(comment[i] + " " + data[i]);
		}
		System.out.println("Sucessfully wrote data to: " + path);
		writer.close();
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void setPath(String p)
	{
		this.path = p;
	}
	
	public String[] getWords()
	{
		return words;
	}
}