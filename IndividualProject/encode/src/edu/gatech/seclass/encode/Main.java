package edu.gatech.seclass.encode;

import org.apache.commons.cli.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

/*
Empty Main class for Individual Project 1 in GT CS6300
 */

    public static void main(String[] args) throws ParseException{

        Options options = new Options();
        //Build an option for the 'n' option
        Option nOption = Option.builder("n").hasArg().optionalArg(true).required(false).desc("integer").build();
        //add this option
        options.addOption(nOption);

        options.addOption("c",true, "string");
        //create an option group with r and l
        OptionGroup group = new OptionGroup();
        group.addOption(new Option("r", true, "integer"));
        group.addOption(new Option("l",true, "integer"));
        options.addOptionGroup(group);

        //create a command line parser
        CommandLineParser parser = new DefaultParser();
        //parse the command line
        CommandLine cmd = parser.parse(options, args);
        //get the left off arguments
        List<String> list = cmd.getArgList();
        if(list.size() == 0) {
            //If no file name is specified, print the usage
            usage();
            return;
        }
        //The file is first element in the list
        String fileName = list.get(0);
        StringBuilder sb = new StringBuilder();
        Scanner scanner;
        try {
            scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()){
                sb.append(scanner.nextLine());
                sb.append("\n");
            }
        }catch (FileNotFoundException e){
            System.out.println("File does not exist");
            return;
        }

        String fileText = sb.toString();
        //variable to see if there are any options at all
        boolean hasOptions = false;
        //If we have the l or r option
        if(cmd.hasOption("r")){
            hasOptions = true;
            int arg = Integer.parseInt(cmd.getOptionValue("r"));
            if (arg < 1) {
                usage();
            }
            else {
                fileText = rOperation(arg, fileText.trim());
            }
        }
        else if(cmd.hasOption("l")){
            hasOptions = true;
            int arg = Integer.parseInt(cmd.getOptionValue("l"));
            if (arg < 1) {
                usage();
            }
            else {
                fileText = lOperation(arg, fileText.trim());
            }
        }


        //If there is a c option, then
        if(cmd.hasOption("c")){
            hasOptions = true;
            String arg = cmd.getOptionValue("c");
            fileText = cOperation(arg, fileText);

            if (!arg.contains("[^A-Za-z]")) {
                usage();
            }
            if (arg.contains("[^A-Za-z0-9")) {
                usage();
            }
        }

        //If we have the 'n' option
        if(cmd.hasOption("n")){
            hasOptions = true;
            //get the corresponding value. If there is no value, set the value to 13
            int value = Integer.parseInt(cmd.getOptionValue('n', "13"));
            //now, do the n operation

            if (value > 25 || value <= 0) {
                usage();
            }
            else {
                fileText = nOperation(value, fileText.trim());
            }
        }

        //If there were no options, then by default we have to do 'n' operation
        if(!hasOptions){
            fileText = nOperation(13, fileText.trim());
        }

        //Open the same file for writing
        try {
            PrintWriter writer = new PrintWriter(new File(fileName));
            writer.write(fileText);
            writer.close();
            writer.flush();
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }

    }


    private static String cOperation(String arg, String fileText) {
        //string builder to store the modified string
        StringBuilder sb = new StringBuilder(fileText);
        //Create a hash map to map lower and upper and vice versa
        HashMap<Character, Character> map = new HashMap<>();
        //For each character in arg
        for(int i=0; i < arg.length(); i++){
            char c = arg.charAt(i);
            //store the opposite case of char c in the map
            if(Character.isLowerCase(c)){
                map.put(c, Character.toUpperCase(c));
                map.put(Character.toUpperCase(c), c);
            }
            else{
                map.put(c, Character.toLowerCase(c));
                map.put(Character.toLowerCase(c),c);
            }
        }

        //For each character in the file text
        for(int i=0; i < sb.length(); i++){
            char c = sb.charAt(i);
            //If this is in map, then replace
            if(map.containsKey(c)){
                sb.setCharAt(i, map.get(c));
            }
        }

        return sb.toString();
    }


    private static String rOperation(int arg, String rString) {
        rString = rString.substring(rString.length()-arg) + rString.substring(0, rString.length()-arg);
        return rString;
    }

    public static String lOperation(int arg, String lString) {
        lString = lString.substring(arg) + lString.substring(0, arg);
        return lString;
    }


    //not quite working right
    private static String  nOperation(int arg, String nString) {

        StringBuilder nEncode = new StringBuilder();

        nString = nString.toLowerCase();

        char[] letters = nString.toCharArray();


        for (char letter : letters) {
            if(Character.isAlphabetic(letter)) {
                int value = (arg + letter - 'a') % 26;
                value++;
                nEncode.append(String.format("%02d",value));
            }
            else{
                nEncode.append(letter);
            }
        }
        return nEncode.toString();
    }

    private static void usage() {
        System.err.println("Usage: encode [-n [int]] [-r int | -l int] [-c string] <filename>");
    }
}