package com.cdkrot.caveworld;

import net.minecraft.init.Blocks;
import net.minecraft.world.WorldProviderSurface;

public class CaveWorldProvider extends WorldProviderSurface
{
	@Override
    public boolean canCoordinateBeSpawn(int par1, int par2)
    {
    	if (this.worldObj.getWorldInfo().getTerrainType()==ModCaveWorld.instance.caveWorldType)
    		return canCoordinateBeSpawn_do(par1, par2);
    	else return super.canCoordinateBeSpawn(par1, par2);
    }
	
	public boolean canCoordinateBeSpawn_do(int x, int z)
	{
		for (int y=255; y>220; y--)
			if (worldObj.getBlock(x, y, z)==Blocks.air && worldObj.getBlock(x, y-1, z)==Blocks.air)
				return true;
		return false;
	}
}
