package com.spacechase0.minecraft.usefulpets.pet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.spacechase0.minecraft.usefulpets.pet.skill.Skill;

import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum PetType
{
	CAT( "cat", EntityOcelot.class, "hitt", new int[] { Skill.HUNGER.id, Skill.REPELLANT.id } ),
	DOG( "dog", EntityWolf.class, "hurt", new int[] { Skill.HUNGER.id, Skill.COMBAT.id } );
	
	PetType( String theName, Class toConvertFrom, String theHurtSound, int[] theDefaultSkills )
	{
		name = theName;
		convertFrom = toConvertFrom;
		hurtSound = theHurtSound;
		for ( int id : theDefaultSkills )
		{
			defaultSkills.add( id );
		}
	}
	
	public String getLivingSound()
	{
		if ( this.equals( CAT ) )
		{
			return ( ( rand.nextInt( 4 ) == 0 ) ? "mob.cat.purreow" : "mob.cat.meow" );
		}
		else if ( this.equals( DOG ) )
		{
			return "mob.wolf.bark";
		}
		
		return null;
	}
	
	public String getHurtSound()
	{
		return "mob." + name + "." + hurtSound;
	}
	
	public final String name;
	public final Class convertFrom;
	public final float sizeX = 0.6f;
	public final float sizeY = 0.8f;
	public final List< ItemStack > defaultFoodChoices = new ArrayList< ItemStack >();
	public final List< Integer > defaultSkills = new ArrayList< Integer >();
	
	private final String hurtSound;
	
	public static final Map< String, PetType > types = new HashMap< String, PetType >();
	private static final Random rand = new Random();
	
	public static PetType forName( String name )
	{
		return types.get( name );
	}
	
	static
	{
		CAT.defaultFoodChoices.add( new ItemStack( Item.fishRaw ) );
		CAT.defaultFoodChoices.add( new ItemStack( Item.fishCooked ) );
		CAT.defaultFoodChoices.add( new ItemStack( Item.chickenRaw ) );
		CAT.defaultFoodChoices.add( new ItemStack( Item.chickenCooked ) );

		DOG.defaultFoodChoices.add( new ItemStack( Item.porkRaw ) );
		DOG.defaultFoodChoices.add( new ItemStack( Item.porkCooked ) );
		DOG.defaultFoodChoices.add( new ItemStack( Item.beefRaw ) );
		DOG.defaultFoodChoices.add( new ItemStack( Item.beefCooked ) );
		
		types.put( CAT.name, CAT );
		types.put( DOG.name, DOG );
	}
}
