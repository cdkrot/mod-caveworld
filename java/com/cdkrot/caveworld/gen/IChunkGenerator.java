package com.cdkrot.caveworld.gen;

import com.cdkrot.caveworld.worldaccess.IBlockControl;
import com.cdkrot.caveworld.worldaccess.IChunkBlockControl;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.chunk.IChunkProvider;

public interface IChunkGenerator extends IGenerator
{
	void generate(IChunkBlockControl w, long chunk_x, long chunk_z);
}
