package com.cdkrot.caveworld.gen.cave;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

import com.cdkrot.caveworld.gen.AbstractChunkGenerator;
import com.cdkrot.caveworld.gen.IChunkGenerator;
import com.cdkrot.caveworld.worldaccess.IChunkBlockControl;

public class GeneratorFoodChests extends AbstractChunkGenerator implements IChunkGenerator
{
	private static long seed;
		
	@Override
	public void generate(IChunkBlockControl w, int chunk_x, int chunk_z)
	{
		Random rand = makeChunkRandom(chunk_x, chunk_z);
		int tries = 6 + rand.nextInt(8);
		for (int i=0; i<tries; i++)
		{
			int y = 205 + rand.nextInt(48);
			int x = 1 + rand.nextInt(14);
			int z = 1 + rand.nextInt(14);
			
			if (w.getBlock(x, y, z)!=Blocks.air || w.getBlock(x, y+1, z)!=Blocks.air)
				continue;
			for (int x_=x-1; x_<=x+1; x_++)
				for (int z_=z-1; z_<=z+1; z_++)
					w.setBlock(x_, y, z_, Blocks.log);
			
			w.setBlock(x, y+1, z, Blocks.chest);
			TileEntityChest tile = (TileEntityChest)w.getTileEntity(x, y+1, z);
			
			Item items[] = new Item[]
					{Items.wheat, Items.wheat_seeds, Items.potato, Items.carrot,
					Items.reeds, Items.pumpkin_seeds};
			int nums[] = new int[]
					{rand.nextInt(8), rand.nextInt(4), rand.nextInt(4)==0?1:0, rand.nextInt(4)==0?1:0,
					rand.nextInt(8)==0?(rand.nextInt(4)==0?2:1):0, rand.nextInt(8)==0?(rand.nextInt(8)==0?2:1):0};
			int inv_pos=0;
			for (int j=0; j<items.length; j++)
			{
				if (nums[j]==0) continue;
				tile.setInventorySlotContents(inv_pos, new ItemStack(items[j], nums[j]));
				inv_pos++;
			}
		}
	}

}
