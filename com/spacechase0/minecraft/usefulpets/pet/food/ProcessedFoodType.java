package com.spacechase0.minecraft.usefulpets.pet.food;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ProcessedFoodType extends SpecificFoodType
{
	public ProcessedFoodType()
	{
		super();
		
		foods.add( new ItemStack( Item.cookie ) );
		foods.add( new ItemStack( Item.pumpkinPie ) );
		foods.add( new ItemStack( Item.bread ) );
		foods.add( new ItemStack( Item.bowlSoup ) );
	}
}
