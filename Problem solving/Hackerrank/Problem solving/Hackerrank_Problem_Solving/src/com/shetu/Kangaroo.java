package com.shetu;

//declare and intiallze the fields: x1,v1,x2,v2,count

import java.util.Scanner;

public class Kangaroo {

    public static void main(String[] args) {
        //declare the fields: x1, v1, x2, v2, count
        Scanner input = new Scanner(System.in);
        int x1 = input.nextInt();
        int v1 = input.nextInt();
        int x2 = input.nextInt();
        int v2 = input.nextInt();
        int kangroo1 = x1+v1;
        int kangroo2 = x2+v2;
        //logic:
        if(v2>v1 && x2>x1){
            System.out.println("NO");
        }else{
            int i = 0;
            while (i>=0){
                if (kangroo1 == kangroo2){
                    System.out.println("YES");
                    break;
                }
                kangroo1+=v1;
                kangroo2+=v2;
                i++;
                System.out.println("Loop run: "+i+" times ");
            }
        }


    }
}
