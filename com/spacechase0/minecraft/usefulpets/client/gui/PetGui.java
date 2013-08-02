package com.spacechase0.minecraft.usefulpets.client.gui;

import com.spacechase0.minecraft.usefulpets.entity.PetEntity;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

// Based off of GuiAchievements
public class PetGui extends GuiScreen
{
	public PetGui( EntityPlayer thePlayer, PetEntity thePet )
	{
		super();
		
		player = thePlayer;
		pet = thePet;
	}
	
	private EntityPlayer player;
	private PetEntity pet;
}
