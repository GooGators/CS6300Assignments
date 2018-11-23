package edu.gatech.seclass.encode;

import org.apache.commons.cli.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class Main {

/*
Empty Main class for Individual Project 1 in GT CS6300
 */

    public static void main(String[] args) {

        Options options = new Options();
        //Build an option for the 'n' option
        Option nOption = Option.builder("n").hasArg().optionalArg(true).required(false).desc("integer").build();
        //add this option
        options.addOption(nOption);

        options.addOption("c",true, "string");
        options.addOption("d", true, "integer");
        //create an option group with r and l
        OptionGroup group = new OptionGroup();
        group.addOption(new Option("r", true, "integer"));
        group.addOption(new Option("l",true, "integer"));
        options.addOptionGroup(group);

        //create a command line parser
        CommandLineParser parser = new DefaultParser();
        //parse the command line
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        }catch (ParseException e){
            usage();
            return;
        }
        //get the left off arguments
        List<String> list = cmd.getArgList();
        String fileName = null;

        if(list.size() == 0) {
            if(cmd.hasOption("n")){
                fileName = cmd.getOptionValue("n");
            }
            else {
                //If no file name is specified, print the usage
                usage();
                return;
            }
        }
        if(fileName == null)
            //The file is first element in the list
            fileName = list.get(0);
        StringBuilder sb = new StringBuilder();
        FileReader reader;
        try {
            reader = new FileReader(fileName);
            int val;

            while((val = reader.read()) != -1){
                sb.append((char)val);
            }

        }catch (FileNotFoundException e){
            System.err.println("File Not Found");
            return;
        }catch (IOException e){
            usage();
            return;
        }

        String fileText = sb.toString();
        //variable to see if there are any options at all
        boolean hasOptions = false;
        //d option is applied first
        if(cmd.hasOption("d")){
            hasOptions = true;
            int arg = 0;
            try {
                arg = Integer.parseInt(cmd.getOptionValue("d"));
            }catch (NumberFormatException e){
                usage();
                return;
            }
            //If the argument is less than 0, print usage and return
            if(arg < 0){
                usage();
                return;
            }
            fileText = dOperation(arg, fileText.trim());
        }

        //If we have the l or r option
        if(cmd.hasOption("r")){
            hasOptions = true;
            int arg = 0;
            try {
                arg = Integer.parseInt(cmd.getOptionValue("r"));
            }catch (NumberFormatException e){
                usage();
                return;
            }
            if (arg < 1) {
                usage();
                return;
            }
            else {
                StringBuilder lineBuilder = new StringBuilder();
                //String[] lines = fileText.split("[\n|\r]");
                StringBuilder stringBuilder = new StringBuilder();
                for(int i=0; i < fileText.length(); i++){
                    char currentChar = fileText.charAt(i);

                    if(currentChar == '\n' || currentChar == '\r'){
                        stringBuilder.append(rOperation(arg, lineBuilder.toString()));
                        stringBuilder.append(currentChar);
                        lineBuilder = new StringBuilder();
                    }
                    else{
                        lineBuilder.append(currentChar);
                    }
                }
                if(lineBuilder.length() > 0)
                    stringBuilder.append(rOperation(arg, lineBuilder.toString()));

                fileText = stringBuilder.toString();
            }
        }
        else if(cmd.hasOption("l")){
            hasOptions = true;
            int arg = 0;
            try {
                arg = Integer.parseInt(cmd.getOptionValue("l"));
            }catch (NumberFormatException e){
                usage();
                return;
            }
            if (arg < 1) {
                usage();
                return;
            }
            else {
                StringBuilder lineBuilder = new StringBuilder();
                //String[] lines = fileText.split("[\n|\r]");
                StringBuilder stringBuilder = new StringBuilder();
                for(int i=0; i < fileText.length(); i++){
                    char currentChar = fileText.charAt(i);

                    if(currentChar == '\n' || currentChar == '\r'){
                        stringBuilder.append(lOperation(arg, lineBuilder.toString()));
                        stringBuilder.append(currentChar);
                        lineBuilder = new StringBuilder();
                    }
                    else{
                        lineBuilder.append(currentChar);
                    }
                }
                if(lineBuilder.length() > 0)
                    stringBuilder.append(lOperation(arg, lineBuilder.toString()));

                fileText = stringBuilder.toString();
            }
        }


        //If there is a c option, then
        if(cmd.hasOption("c")){
            hasOptions = true;
            String vals[] = cmd.getOptionValues("c");
            String arg = vals[vals.length-1];

            if (!arg.contains("[^A-Za-z]")) {
                usage();
            }

            if (arg.contains("[^A-Za-z0-9")) {
                usage();
            }

            fileText = cOperation(arg, fileText);

        }

        //If we have the 'n' option
        if(cmd.hasOption("n")){
            hasOptions = true;
            //get the corresponding value. If there is no value, set the value to 0
            int value = 0;
            try {
                value = Integer.parseInt(cmd.getOptionValue('n', "0"));
            }catch (NumberFormatException e){
                usage();
                return;
            }
            //now, do the n operation

            if (value > 25 || value < 0) {
                usage();
                return;
            }
            else {
                fileText = nOperation(value, fileText.trim());
            }
        }

        //If there were no options, then by default we have to do 'n' operation
        if(!hasOptions){
            fileText = nOperation(13, fileText);
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
        if(arg > rString.length()){
            arg = arg%rString.length();
        }
        rString = rString.substring(rString.length()-arg) + rString.substring(0, rString.length()-arg);
        return rString;
    }

    public static String lOperation(int arg, String lString) {
        if(arg > lString.length()){
            arg = arg%lString.length();
        }
        lString = lString.substring(arg) + lString.substring(0, arg);
        return lString;
    }


    private static String  nOperation(int arg, String nString) {

        StringBuilder nEncode = new StringBuilder();

        char[] letters = nString.toCharArray();

        for (char letter : letters) {
            if(Character.isLowerCase(letter) && Character.isAlphabetic(letter)) {
                int value = (arg + letter - 'a') % 26;
                value++;
                nEncode.append(String.format("%02d",value));
            }
            if (Character.isUpperCase(letter) && Character.isAlphabetic(letter)) {
                int value = (arg + letter - 'A') % 26 + 27;
                nEncode.append(String.format("%02d", value));
            }
            else if (!Character.isAlphabetic(letter)){
                nEncode.append(letter);
            }
        }
        return nEncode.toString();
    }

    private static String dOperation(int arg, String fileText) {
        if(arg == 0)
            return "";
        //Create a hash map to store the counts of a char
        HashMap<Character, Integer> hashMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < fileText.length(); i++){
            //Get the current char
            char c = fileText.charAt(i);
            //if the current char is present in the hashmap
            if(hashMap.containsKey(c)){
                int count = hashMap.get(c);
                //If the count is less than the given value
                if(count < arg){
                    //increment the count
                    count++;
                    //and add the updated count to the hashmap
                    hashMap.put(c, count);
                    sb.append(c);
                }
            }
            else{
                sb.append(c);
                //put the count corresponding to this character
                hashMap.put(c, 1);
            }
        }
        return sb.toString();
    }

    private static void usage() {
        System.err.println("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>");
    }
}