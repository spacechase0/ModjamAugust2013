package com.spacechase0.minecraft.usefulpets.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData( INetworkManager manager, Packet250CustomPayload packet, Player player )
	{
		if ( packet.channel.equals( "SC0_UP|CS" ) )
		{
			ChangeSkillPacket cs = ChangeSkillPacket.fromData( packet.data );
			ChangeSkillPacket.process( ( EntityPlayer ) player, cs );
		}
	}
}
