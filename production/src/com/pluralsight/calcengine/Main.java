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
            //loops through the array of @opCodes
            for(int i = 0; i < opCodes.length; i++){
                //initializes @results array by going to @execute() for each index in the @opCodes
                results[i] = execute(opCodes[i], leftval[i], rightVal[i]);
            }
            //loops through the @results array and prints out the values
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
        //prompt the user
        System.out.println("Enter an operation and two numbers");
        //initialize scanner
        Scanner scan = new Scanner(System.in);
        //get the user data
        String userInput = scan.nextLine();
        //split the input based off whitespace's
        String[] parts = userInput.split(" ");
        //close the scanner to not create memory leak;
        scan.close();
        //go to @performOperations();
        performOperation(parts);
    }
    //will execute the operation(add,sub,..) and check if the
    // user wants to edit time operation
    private static void performOperation(String[] parts) {
        //go to @opCodeFromString();
        char opCode = opCodeFromString(parts[0]);
        //checks if the first character is 'w'
        if(opCode == 'w'){
            // go to @handleWhen();
            handleWhen(parts);
        }else{
            //go to @valueFromWord(); to return a double
            double leftval = valueFromWord(parts[1]);
            //go to @valueFromWord(); to return a double
            double rightVal = valueFromWord(parts[2]);
            //go to @execute(); to return a double
            double result = execute(opCode, leftval, rightVal);
            //go to @displayResult to show the results of the input
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

    //will get the values from the user in the command line
    private static void handleCommandLine(String[] args) {
        //initliaze character by getting the first character
        char opCode = args[0].charAt(0);
        //initialize values by converting the string to double
        double leftval = Double.parseDouble(args[1]);
        double rightVal = Double.parseDouble(args[2]);
        //initialize the value by going to @execute() and returning a double;
        double result = execute(opCode, leftval, rightVal);
        //printing out the results
        System.out.println(result);
    }
    //checks the inputs from the user and does the math operation based off of opCode
    public static double execute(char opCode, double leftval, double rightVal){
        //initialize result
        double result;
        //checks what character opCode is
        switch(opCode){
            //does addition operation
            case 'a':
                result = leftval + rightVal;
                break;
            //does subtraction operation
            case 's':
                result = leftval - rightVal;
                break;
            //does divsion operation
            case 'd':
                result = rightVal != 0 ? result = leftval / rightVal : 0.0d;
                break;
            //does multiplication operation
            case 'm':
                result = leftval * rightVal;
                break;
            //if the operation is not part of the switch-case then it will give an error message
            default:
                System.out.println("Invalid opCode: " + opCode);
                result = 0.0;
                break;
        }

        return result;
    }
    //converts string input to a character
    public static char opCodeFromString(String operatiomName){
        char opCode = operatiomName.charAt(0);
        return opCode;
    }

    //gets the string input and converts it to a number
    public static double valueFromWord(String word){
        //initialize string array and create values from 1 - 9 with strings
        String[] numberWords = {
            "zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine"
        };
        //initialize double and set it to a number
        double value = -1d;
        //loop through @numberWords
        for (int i = 0; i < numberWords.length; i++) {
            //check if @word is the same as the value's from @numberWords
            if(word.equals(numberWords[i])){
                //if the value is the same then set that index to @value and break out of the for-loop
                value = i;
                break;
            }
        }
        //check if the value is the same as it initialized
        if(value == -1d){
            //convert @word to a double and set it to @value
           value = Double.parseDouble(word);
        }
        
        return value;
    }
}
