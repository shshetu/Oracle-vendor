package com.shetu;

import java.lang.reflect.Array;
import java.util.Scanner;

public class AppleAndOrange {
            /*Fields
s: integer, starting point of Sam's house location. = 7
t: integer, ending location of Sam's house location. = 11
a: integer, location of the Apple tree. = 5
b: integer, location of the Orange tree. = 15
m: number of apples = 3
n: number of oranges = 2
apples: integer array, distances at which each apple falls from the tree.
oranges: integer array, distances at which each orange falls from the tree.

//distances : for m = -2 2 1 ,  for n = 5 -6
        * */

    public static void main(String[] args) {
        //declare s,t,a,b,m,n,apple[],oranges[]
        //take input as follows: s=7, t=11,a= 5, b= 15, m = 3, n = 2
        Scanner input = new Scanner(System.in);
        int s = input.nextInt();
        int t = input.nextInt();
        int a = input.nextInt();
        int b = input.nextInt();
        int m = input.nextInt();
        int n = input.nextInt();
        int[] apple = new int[m];
        int[] orange = new int[n];
        //count apples and oranges
        int countApple = 0;
        int countOrange = 0;



        //assign the values in apples array: -2 2 1
        for (int i = 0;i<m;i++){
            apple[i] = input.nextInt();
        //logic:  if(a-2)>6 && (a-2)<12 {countApple++;}
            if((a+apple[i])>=s && (a+apple[i])<=t){
                countApple++;
            }

        }

        //assing the values in oranges array: 5 -6
        for (int j = 0;j<n;j++){
            orange[j] = input.nextInt();

        //logic: if(b+5)>6 && (b-6)<12 {countOrange++;}
            if((b+orange[j])>=s && (b+orange[j])<=t){
                countOrange++;
            }
        }

        //print : countApple and countOrange
        System.out.println(countApple);
        System.out.println(countOrange);
    }
}
