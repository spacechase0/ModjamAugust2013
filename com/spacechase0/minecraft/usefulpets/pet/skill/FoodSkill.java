package com.spacechase0.minecraft.usefulpets.pet.skill;

import com.spacechase0.minecraft.usefulpets.pet.food.FoodType;

public class FoodSkill extends Skill
{
	public FoodSkill( int theId, String theName, FoodType theType )
	{
		super( theId, ( theName.equals( "" ) ? "hunger" : ( "hunger." + theName ) ), getPosX( theId, theName ), getPosY( theId, theName ) );
		type = theType;
		
		if ( name.equals( "" ) )
		{
			mainId = id;
		}
		else
		{
			skillReqs = new int[] { mainId };
		}
	}
	
	public FoodSkill( int theId, String theName, int theLevelReq, FoodType theType )
	{
		this( theId, theName, theType );
		levelReq = theLevelReq;
	}
	
	private static float getPosX( int id, String name )
	{
		if ( name.equals( "" ) )
		{
			return 2;
		}
		
		int diff = id - mainId;
		
		return 2 - 0.5f + ( diff % 2 );
	}
	
	private static float getPosY( int id, String name )
	{
		if ( name.equals( "" ) )
		{
			return 0;
		}
		
		int diff = id - mainId;
		
		return 1.5f + ( diff / 2 );
	}
	
	public final FoodType type;
	private static int mainId;
}
