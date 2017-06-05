package com.zstax.util.常用类.oo.adapter;

public class Printer {
	
	public void printIntArray(int[] array){
		if (array != null){
			for (int i=0; i<array.length; i++){
				System.out.print(array[i] + " ");
			}
			System.out.println();
		}
	} 
}
