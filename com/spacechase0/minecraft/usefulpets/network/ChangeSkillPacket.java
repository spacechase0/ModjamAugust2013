package com.spacechase0.minecraft.usefulpets.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.UUID;

import com.spacechase0.minecraft.usefulpets.entity.PetEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;

public class ChangeSkillPacket
{
	public ChangeSkillPacket( int theEntity, int theAction, int theSkill )
	{
		entity = theEntity;
		action = theAction;
		skill = theSkill;
	}
	
	public Packet250CustomPayload getData()
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream( bytes );
		
		try
		{
			stream.writeInt( entity );
			stream.writeInt( action );
			stream.writeInt( skill );
		}
		catch ( Exception exception )
		{
			exception.printStackTrace();
		}
		
		return new Packet250CustomPayload( "SC0_UP|CS", bytes.toByteArray() );
	}
	
	public static ChangeSkillPacket fromData( byte[] data )
	{
		ByteArrayInputStream bytes = new ByteArrayInputStream( data );
		DataInputStream stream = new DataInputStream( bytes );
		
		try
		{
			int entity = stream.readInt();
			int action = stream.readInt();
			int id = stream.readInt();
			
			return new ChangeSkillPacket( entity, action, id );
		}
		catch ( Exception exception )
		{
			exception.printStackTrace();
		}
		
		return null;
	}
	
	public static void process( EntityPlayer player, ChangeSkillPacket packet )
	{
		Entity entity = player.worldObj.getEntityByID( packet.entity );
		if ( entity == null || !( entity instanceof PetEntity ) )
		{
			return;
		}
		PetEntity pet = ( PetEntity ) entity;
		
		if ( !pet.getOwnerName().equals( player.username ) )
		{
			return;
		}
		
		switch ( packet.action )
		{
			case ACTION_ADD: pet.addSkill( packet.skill ); break;
			case ACTION_REMOVE: pet.removeSkill( packet.skill ); break;
			case ACTION_RESET: pet.resetSkills(); break;
		}
	}
	
	public int entity;
	public int action;
	public int skill;

	public static final int ACTION_ADD = 0;
	public static final int ACTION_REMOVE = 1;
	public static final int ACTION_RESET = 2;
}
