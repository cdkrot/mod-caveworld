package com.cdkrot.caveworld.gen.cave;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import com.cdkrot.caveworld.gen.AbstractChunkGenerator;
import com.cdkrot.caveworld.gen.IChunkGenerator;
import com.cdkrot.caveworld.worldaccess.IBlockControl;
import com.cdkrot.caveworld.worldaccess.IChunkBlockControl;

/**
 * Cave-world generation -- post generation
 * 
 * @author cdkrot
 *
 */
public class GeneratorCavePostProduction extends AbstractChunkGenerator
{
	@Override
	public void generate(IChunkBlockControl w, int chunk_x, int chunk_z)
	{
		int[] HeightMap=w.perform_height_mapping();
		for (int x=0; x<16; x++)
			for (int z=0; z<16; z++)
			{
				if (HeightMap[(x<<4)+z]>=200)
				{
					Block b = w.getBlock(x, HeightMap[(x<<4)+z], z);
					if (b==Blocks.stone || b==Blocks.dirt)
						w.setBlock(x, HeightMap[(x<<4)+z], z, Blocks.grass);
					
				}
			}
	}
	
}
