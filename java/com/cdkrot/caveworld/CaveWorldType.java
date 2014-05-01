package com.cdkrot.caveworld;

import com.cdkrot.caveworld.gen.cave.GenLayerBiomeCave;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class CaveWorldType extends WorldType
{

	public CaveWorldType(){
		super("cdkrot-caveworld");
	}

	@Override
	public WorldChunkManager getChunkManager(World world)
	{
		return new CaveWorldChunkManager(world);
	}

	public int getMinimumSpawnHeight(World w) {
		return 225;
	}

	public IChunkProvider getChunkGenerator(World world, String generatorOptions)
	{
		ChunkProviderCave Provider = new ChunkProviderCave(world, world.getSeed());
		return Provider;
	}
	public double getHorizon(World world) {
		return 0.0D;
	}

	public boolean hasVoidParticles(boolean flag) {
		return false;
	}

	public double voidFadeMagnitude() {
		return 1.0D;
	}

	@Override
	public float getCloudHeight() {
		return 0.0F;
	}
    public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer)
    {
    	return new GenLayerBiomeCave(worldSeed, 1L);
    }
    public int getSpawnFuzz()
    {
        return 0;
    }

}
