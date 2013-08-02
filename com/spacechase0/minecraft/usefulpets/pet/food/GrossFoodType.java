package com.spacechase0.minecraft.usefulpets.pet.food;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GrossFoodType extends SpecificFoodType
{
	public GrossFoodType()
	{
		super();
		
		foods.add( new ItemStack( Item.spiderEye ) );
		foods.add( new ItemStack( Item.rottenFlesh ) );
	}
}
