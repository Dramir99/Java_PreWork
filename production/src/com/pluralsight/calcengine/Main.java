package com.pluralsight.calcengine;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        
        //initialize default values
        double[] leftval = {100.0d, 25.0d, 225.0d,11.0};
        double[] rightVal = {50.0, 92.0,17.0,3.0};
        
        // d is for division, a is for addition, s is for subtraction, m is for multiplication
        char[] opCodes = {'d','a','s','m'};

        //will hold the result of the values
        double[] results = new double[opCodes.length];

        //checks if there is a valuen given when running it in the command line
        //if no values are given then it will run using default values
        if(args.length == 0){
            for(int i = 0; i < opCodes.length; i++){
                results[i] = execute(opCodes[i], leftval[i], rightVal[i]);
            }
            
            for(double currRes : results){
                System.out.println(currRes);
            }
        }
        // if it has one value typed as "interactive" then will allow the user to provide the values themselves
        else if(args.length == 1 && args[0].equals("interactive")){
            executeInteractiverly();
        }
        //if the user has only 3 values then it will do the calcuation using the provided inputs
        else if(args.length == 3){
            handleCommandLine(args);
        }
        //if the user inputs something that is not part of a calculation then it will provide an error
        else{
            System.out.println("please provide an operation code and 2 numeric values");
        }
        
        
    }
    // will ask the user to provide an operation(add, sub,..) and two numbers
    public static void executeInteractiverly(){
        System.out.println("Enter an operation and two numbers");
        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();
        String[] parts = userInput.split(" ");
        scan.close();
        performOperation(parts);
    }

    private static void performOperation(String[] parts) {
        char opCode = opCodeFromString(parts[0]);
        if(opCode == 'w'){
            handleWhen(parts);
        }else{
            double leftval = valueFromWord(parts[1]);
            double rightVal = valueFromWord(parts[2]);
            double result = execute(opCode, leftval, rightVal);

            displayResult(opCode, leftval, rightVal, result);
        }

        
    }

    private static void handleWhen(String[] parts) {
        LocalDate startDate = LocalDate.parse(parts[1]);
        long daysToAdd = (long) valueFromWord(parts[2]);
        LocalDate newDate = startDate.plusDays(daysToAdd);

        String output = String.format("%s plus %d days is %s", startDate, daysToAdd, newDate);
        System.out.println(output);
    }


    private static void displayResult(char opCode, double leftval, double rightVal, double result) {
        char symbol = symbolFromOpCode(opCode);

        String output = String.format("%.3f %c %.3f = %.3f", leftval, symbol, rightVal, result);

        System.out.println(output);
    }

    private static char symbolFromOpCode(char opCode){
        char[] opCodes = {'a', 's', 'm', 'd'};
        char[] symbols = {'+', '-', '*', '/'};
        char symbol = ' ';

        for(int i = 0; i < opCodes.length; i++){
            if(opCode == opCodes[i]){
                symbol = symbols[i];
                break;
            }
        }

        return symbol;
    }

    private static void handleCommandLine(String[] args) {
        char opCode = args[0].charAt(0);
        double leftval = Double.parseDouble(args[1]);
        double rightVal = Double.parseDouble(args[2]);

        double result = execute(opCode, leftval, rightVal);
        System.out.println(result);
    }

    public static double execute(char opCode, double leftval, double rightVal){
        double result;

        switch(opCode){
            case 'a':
                result = leftval + rightVal;
                break;
            case 's':
                result = leftval - rightVal;
                break;
            case 'd':
                result = rightVal != 0 ? result = leftval / rightVal : 0.0d;
                break;
            case 'm':
                result = leftval * rightVal;
                break;
            default:
                System.out.println("Invalid opCode: " + opCode);
                result = 0.0;
                break;
        }

        return result;
    }

    public static char opCodeFromString(String operatiomName){
        char opCode = operatiomName.charAt(0);
        return opCode;
    }

    public static double valueFromWord(String word){
        String[] numberWords = {
            "zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine"
        };
        double value = -1d;

        for (int i = 0; i < numberWords.length; i++) {
            if(word.equals(numberWords[i])){
                value = i;
                break;
            }
        }
        if(value == -1d){
           value = Double.parseDouble(word);
        }
        return value;
    }
}
