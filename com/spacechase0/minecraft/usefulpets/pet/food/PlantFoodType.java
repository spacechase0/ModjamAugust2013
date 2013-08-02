package com.spacechase0.minecraft.usefulpets.pet.food;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PlantFoodType extends SpecificFoodType
{
	public PlantFoodType()
	{
		super();
		
		foods.add( new ItemStack( Item.appleRed ) );
		foods.add( new ItemStack( Item.appleGold ) );
		foods.add( new ItemStack( Item.potato ) );
		foods.add( new ItemStack( Item.bakedPotato ) );
		foods.add( new ItemStack( Item.poisonousPotato ) );
		foods.add( new ItemStack( Item.carrot ) );
		foods.add( new ItemStack( Item.goldenCarrot ) );
		foods.add( new ItemStack( Item.melon ) );
	}
}
