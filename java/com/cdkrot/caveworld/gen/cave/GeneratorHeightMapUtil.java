package com.cdkrot.caveworld.gen.cave;

import com.cdkrot.caveworld.gen.AbstractChunkGenerator;
import com.cdkrot.caveworld.worldaccess.IChunkBlockControl;

/**
 * Utility class, generates height maps for our needs.
 * Right now only 16*16.
 * @author cds
 *
 */
public class GeneratorHeightMapUtil
{
	public int chunk_x;
	public int chunk_z;
	public int height_base;
	public int height_variation;
	public long seed;
	public void initData(int chunk_x, int chunk_z, int height_base, int height_variation, long seed)
	{
		this.chunk_x=chunk_x;
		this.chunk_z=chunk_z;
		this.height_base=height_base;
		this.height_variation=height_variation;
		this.seed=seed;
	}
	/**
	 * Generates randomized heightmap
	 * @return
	 */
	public int[] generate()
	{
		int buf[] = new int[17*17];
		generate(buf);
		return buf;
	}
	/**
	 * Generates randomized heightmap
	 * Reuse input array of size 17*17
	 * @param buf
	 */
	public void generate(int buf[])
	{
		for (int d=0; d<=16; d++)
		{
			buf[0*17+d]=AbstractChunkGenerator.makeCoordRandom(16*chunk_x, 16*chunk_z+d, seed)
					.nextInt(2*height_variation+1)-height_variation;
			buf[d*17+0]=AbstractChunkGenerator.makeCoordRandom(16*chunk_x+d, 16*chunk_z, seed)
					.nextInt(2*height_variation+1)-height_variation;
		}
		generate_do(buf, 0, 16, 0, 16, height_variation/2);
	}
	public void generate_do(int[] buf, int x0, int x1, int z0, int z1, int modif_variation)
	{
		int height_x0_z0 = buf[x0*17+z0];
		int height_x0_z1 = buf[x0*17+z1];
		int height_x1_z0 = buf[x1*17+z0];
		int height_x1_z1 = height_base +
				AbstractChunkGenerator.makeCoordRandom(16*chunk_x+x1, 16*chunk_z+z1, seed)
				.nextInt(2*modif_variation+1)-modif_variation;
		if (x0+1!=x1)
		{
			generate_do(buf, x0, (x0+x1)/2, z0, (z0+z1)/2, modif_variation/2);
			generate_do(buf, x0, (x0+x1)/2, (z0+z1)/2, z1, modif_variation/2);
			generate_do(buf, (x0+x1)/2, x1, z0, (z0+z1)/2, modif_variation/2);
			generate_do(buf, (x0+x1)/2, x1, (z0+z1)/2, z1, modif_variation/2);
		}
		return;
	}
}
