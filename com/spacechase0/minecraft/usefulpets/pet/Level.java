package com.spacechase0.minecraft.usefulpets.pet;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Level
{
	public static final int MAX_LEVEL = 20;
	
	public int getLevelExperienceRequirements( int level )
	{
		return level * 2;
	}
	
	public List< ItemStack > getLevelItemRequirements( int level )
	{
		List< ItemStack > list = new ArrayList< ItemStack >();
		
		int coal = level * 4;
		int iron = ( level - 3 ) * 2;
		int redstone = ( level - 7 ) * 2;
		int gold = ( level - 12 ) * 2;
		int diamond = ( level - 16 ) * 3;
		
		list.add( new ItemStack( Item.coal, coal ) );
		if ( level >= 4 )
		{
			list.add( new ItemStack( Item.ingotIron, iron ) );
		}
		if ( level >= 8 )
		{
			list.add( new ItemStack( Block.blockRedstone, redstone ) );
		}
		if ( level >= 13 )
		{
			list.add( new ItemStack( Item.ingotGold, gold ) );
		}
		if ( level >= 17 )
		{
			list.add( new ItemStack( Item.diamond, diamond ) );
		}
		
		return list;
	}
}
