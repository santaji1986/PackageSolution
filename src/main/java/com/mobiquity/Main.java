package com.mobiquity;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;

public class Main {

	public static void main(String[] args) {
		if (args.length > 0) {
			try {
				System.out.println(Packer.pack(args[0]));
			} catch (APIException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Invalid absolute filepath provided.");
		}
	}

}
