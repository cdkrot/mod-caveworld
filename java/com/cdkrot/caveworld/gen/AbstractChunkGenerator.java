package com.cdkrot.caveworld.gen;

import java.util.Random;

public abstract class AbstractChunkGenerator implements IChunkGenerator
{
	protected long seed;
	
	public 	void initSEED(long s)
	{
		seed=s;
	}
	
	public void initRG(Random r){;}
	
	public Random makeChunkRandom(long chunk_x, long chunk_z)
	{
		return new Random(1299709*chunk_x+1020379*chunk_z+seed);
	}
}
