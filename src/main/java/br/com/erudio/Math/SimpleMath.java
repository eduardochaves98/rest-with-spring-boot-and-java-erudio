package br.com.erudio.Math;



public class SimpleMath {

    public Double sum(Double numberOne, Double numberTwo){
        return numberOne + numberTwo;
    }

    public Double subtraction(Double numberOne, Double numberTwo){
        return numberOne - numberTwo;
    }

    public Double multiplication(Double numberOne, Double numberTwo) {
        return numberOne * numberTwo;
    }

    public Double division(Double numberOne, Double numberTwo) {
        return numberOne / numberTwo;
    }
    public Double sqrt(Double numberOne){
        return Math.sqrt(numberOne);
    }
    public Double avg(Double numberOne, Double numberTwo) throws Exception{
        return (numberOne + numberTwo)/2;
    }
}
