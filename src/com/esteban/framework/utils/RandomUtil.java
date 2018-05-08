package com.esteban.framework.utils;

import java.util.Random;

public class RandomUtil {

	public static String randomNumber() {
		StringBuffer strbufguess = new StringBuffer();
		String strguess = new String();
		int[] nums = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rannum = new Random();
		int count;
		int i = 0, temp_i = 0;
		for (int j = 10; j > 4; j--) {
			i = 0;
			temp_i = 0;
			count = rannum.nextInt(j);
			while (i <= count) {
				if (nums[temp_i] == -1)
					temp_i++;
				else {
					i++;
					temp_i++;
				}
			}
			strbufguess.append(Integer.toString(nums[temp_i - 1]));
			nums[temp_i - 1] = -1;
		}
		strguess = strbufguess.toString();
		rannum = null;
		strbufguess = null;
		nums = null;
		return strguess;
	}

	public static int getRandom(int probability) {
		Random rand = new Random();
		return rand.nextInt(probability - 1) + 1;
	}

}
