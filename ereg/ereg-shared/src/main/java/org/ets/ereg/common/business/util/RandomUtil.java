package org.ets.ereg.common.business.util;

public class RandomUtil {

	public static int getRandomIntegerBetween(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

}
