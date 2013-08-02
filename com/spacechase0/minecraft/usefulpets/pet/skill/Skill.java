package com.spacechase0.minecraft.usefulpets.pet.skill;

import java.util.HashMap;
import java.util.Map;

import com.spacechase0.minecraft.usefulpets.pet.food.FoodType;

public class Skill
{
	public Skill( int theId, String theName )
	{
		id = theId;
		name = theName;
		levelReq = 1;
		
		if ( skills.containsKey( id ) )
		{
			throw new IllegalArgumentException( "Skill " + id + " already exists." );
		}
		skills.put( id, this );
	}
	
	public Skill( int theId, String theName, int theLevelReq, int[] theSkillReqs )
	{
		this( theId, theName );
		levelReq = theLevelReq;
		skillReqs = theSkillReqs;
	}
	
	public final int id;
	public final String name;
	public int levelReq;
	public int[] skillReqs;
	
	public static final Map< Integer, Skill > skills = new HashMap< Integer, Skill >();
	
	public static Skill HUNGER = new FoodSkill( 0, "", FoodType.SPECIES );
	public static Skill HUNGER_OTHER = new FoodSkill( 1, "eatOther", FoodType.OTHER_SPECIES );
	public static Skill HUNGER_PLANTS = new FoodSkill( 2, "eatPlants", FoodType.PLANTS );
	public static Skill HUNGER_PROCESSED = new FoodSkill( 3, "eatProcessed", FoodType.PROCESSED );
	public static Skill HUNGER_GROSS = new FoodSkill( 4, "eatGross", FoodType.GROSS );
}
