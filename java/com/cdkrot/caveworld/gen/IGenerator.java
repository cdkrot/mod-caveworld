package com.cdkrot.caveworld.gen;

import java.util.Random;

public interface IGenerator
{
	/**
	 * Initializes random generator with a World Random.
	 * @param r
	 */
	void initRG(Random r);
	/**
	 * Initializes seed field with world seed.
	 * @param s
	 */
	void initSEED(long s);
}
