package com.cdkrot.caveworld.gen.cave;


import com.cdkrot.caveworld.ModCaveWorld;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerIsland;

public class GenLayerBiomeCave extends GenLayer
{
	public final GenLayerIsland core;
	
	public GenLayerBiomeCave(long par1, long K)
	{
		super(par1);
		this.core = new GenLayerIsland(1L);
	}

	@Override
	public int[] getInts( int p1, int p2, int p3, int p4)
	{
		int b[] = core.getInts(p1, p2, p3, p4);
		for (int i=0; i<b.length; i++)
			b[i]= ((b[i]==0)?ModCaveWorld.instance.BiomeGenCaveClassic.biomeID:ModCaveWorld.instance.BiomeGenCaveMushroom.biomeID);
		return b;
	}

}
