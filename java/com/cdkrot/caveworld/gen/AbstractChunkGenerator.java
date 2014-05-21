package com.cdkrot.caveworld.gen;

import java.util.Random;

public abstract class AbstractChunkGenerator implements IChunkGenerator
{
	protected long seed;
	
	public void initSEED(long s)
	{
		seed=s;
	}
	
	public void initRG(Random r){;}
	
	/**
	 * Makes a random generator for chunk. Using seed
	 * @param chunk_x -- the x
	 * @param chunk_z -- the z
	 * @return new random
	 */
	public Random makeChunkRandom(long chunk_x, long chunk_z)
	{
		return makeCoordRandom(chunk_x, chunk_z, seed);
	}
	public static Random makeCoordRandom(long x, long z, long seed)
	{
		return new Random(1299709*x+1020379*z+seed);
	}
}
