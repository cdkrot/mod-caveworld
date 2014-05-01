package com.cdkrot.caveworld.gen.cave;

import java.util.Random;

import com.cdkrot.caveworld.gen.IChunkGenerator;
import com.cdkrot.caveworld.worldaccess.IBlockControl;
import com.cdkrot.caveworld.worldaccess.IChunkBlockControl;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Generates few grass (or mycelium) spots.
 * @author cdkrot
 *
 */
public class GeneratorAnimalShelter implements IChunkGenerator
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
		int tries = rand.nextInt(4); //now of tries to make.
		for (int i=0; i<tries; i++)
		{
			int y = 225 + rand.nextInt(25);
			int x0 = 2  + rand.nextInt(4);
			int z0 = 2  + rand.nextInt(4);
			
			while (w.getBlock(x0, y, z0)==Blocks.air)
				y--;
			
			int x2 = x0 + rand.nextInt(4) + 4;
			int z2 = z0 + rand.nextInt(4) + 4;
			for (int x1=x0; x1<=x2; x1++)
				for (int z1=z0; z1<=z2; z1++)
				{
					for (int dy=1; dy>=-1; dy--)
					if (w.getBlock(x1, y+dy+1, z1)==Blocks.air &&
					( (w.getBlock(x1, y+dy, z1)==Blocks.stone) || w.getBlock(x1, y+dy, z1)==Blocks.dirt))
					{
						Block grass_block = (w.getBiomeAt(x0, z0)==BiomeGenBase.mushroomIsland.biomeID)?Blocks.mycelium:Blocks.grass;
						w.setBlock(x1, y+dy, z1, grass_block);
						if (seed%2 + rand.nextInt(16)==1)
							w.setBlock(x1, y+dy+1, z1, Blocks.sapling);
					
						int y_max = y+dy-6+rand.nextInt(4);
						for (int y1=y+dy-1; (y1>=y_max)&&(w.getBlock(x1, y1, z1)==Blocks.air); y1--)
							w.setBlock(x1, y1, z1, Blocks.dirt);
					}
				}
		}
	}

}
