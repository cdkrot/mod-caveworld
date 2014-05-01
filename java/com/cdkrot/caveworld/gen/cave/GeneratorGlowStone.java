package com.cdkrot.caveworld.gen.cave;

import java.util.Random;

import com.cdkrot.caveworld.gen.IChunkGenerator;
import com.cdkrot.caveworld.worldaccess.ChunkBlockControl;
import com.cdkrot.caveworld.worldaccess.IBlockControl;
import com.cdkrot.caveworld.worldaccess.IChunkBlockControl;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.EnumSkyBlock;

public class GeneratorGlowStone implements IChunkGenerator
{
	protected long seed;
		
	@Override
	public void initRG(Random r) {;}//don't use World.rand

	@Override
	public void initSEED(long s) {seed=s;}
	
	@Override
	public void generate(IChunkBlockControl w, long chunk_x, long chunk_z)
	{
		Random rand = new Random(1299709*chunk_x+1020379*chunk_z+seed);
		int tries = 8 + rand.nextInt(16);
		for (int i=0; i<tries; i++)
		{
			int y = 200 + rand.nextInt(54);
			int x = 1 + rand.nextInt(14);
			int z = 1 + rand.nextInt(14);
			
			w.setBlock(x, y, z, Blocks.glowstone);
			w.updateLightAt(x, y, z);
			for (int dx=-1; dx<=1; dx++)
				for (int dy=-1; dy<=1; dy++)
					for (int dz=-1; dz<=1; dz++)
					{
						Block b = w.getBlock(x+dx, y+dy, z+dz);
						if (b==Blocks.stone || b==Blocks.dirt)
						{
							w.setBlock(x+dx, y+dy, z+dz, Blocks.glowstone);
							w.updateLightAt(x+dx, y+dy, z+dz);
						}
					}
		}
	}

}
