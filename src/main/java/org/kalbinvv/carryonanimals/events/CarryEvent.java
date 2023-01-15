package org.kalbinvv.carryonanimals.events;


import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.kalbinvv.carryonanimals.CarryOn;
import org.kalbinvv.carryonanimals.protections.ProtectionsList;
import org.kalbinvv.carryonanimals.sounds.CarrySound;
import org.kalbinvv.carryonanimals.sounds.SoundsUtils;
import org.kalbinvv.carryonanimals.utils.ChatUtils;
import org.kalbinvv.carryonanimals.utils.ConfigurationUtils;
import org.kalbinvv.carryonanimals.utils.MessageType;
import org.kalbinvv.carryonanimals.utils.ParticlesUtils;

public class CarryEvent implements Listener{

	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		if(!event.getHand().equals(EquipmentSlot.HAND)) {
			return;
		}

		Entity entity = event.getRightClicked();
		Player player = event.getPlayer();

		Configuration configuration = CarryOn.getConfiguration();

		String prefix = configuration.getString("messages.prefix");

		
		if(!ConfigurationUtils.isWorldEnabled(player.getWorld())) {
			String message = configuration.getString("messages.notEnabledWorld");
			
			Bukkit.getScheduler().runTaskLaterAsynchronously(CarryOn.getPlugin(),
					() -> {
						ChatUtils.sendMessage(
								prefix, 
								message, 
								player,
								MessageType.Event);
					}, 2l);
			
			return;
		}
		
		if(!player.isSneaking()) {
			return;
		}
		
		if(!ConfigurationUtils.initAllowedEntitiesTypes(configuration)
				.contains(entity.getType())) {
			String message = configuration.getString("messages.invalidEntity");

			Bukkit.getScheduler().runTaskLaterAsynchronously(CarryOn.getPlugin(),
					() -> {
						ChatUtils.sendMessage(
								prefix, 
								message, 
								player,
								MessageType.Event);
					}, 2l);

			return;
		}

		if(!player.hasPermission("carryonanimals.default")) {
			String message = configuration.getString("messages.permission");

			Bukkit.getScheduler().runTaskLaterAsynchronously(CarryOn.getPlugin(),
					() -> {
						ChatUtils.sendMessage(
								prefix, 
								message, 
								player,
								MessageType.Event);
					}, 2l);
			return;
		}

		ProtectionsList protectionsList = CarryOn.getProtectionsList();

		if(!protectionsList.checkAll(player, entity)) {
			String message = protectionsList.getMessage();

			Bukkit.getScheduler().runTaskLaterAsynchronously(CarryOn.getPlugin(),
					() -> {
						ChatUtils.sendMessage(
								prefix, 
								message, 
								player,
								MessageType.Event);
					}, 2l);

			return;
		}

		CarrySound pickUpSound = SoundsUtils.getPickUpSound();

		if(pickUpSound.isRegistered()) {
			pickUpSound.play(player);
		}

		if(ConfigurationUtils.isParticleRegistered()) {
			ParticlesUtils.spawnParticle(
					ConfigurationUtils.getParticle(), 
					entity.getLocation(),
					ConfigurationUtils.getParticleCount());
		}

		player.addPassenger(entity);
	}

	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();

		if(!player.isSneaking()) {

			boolean hasPassengers = player.eject();

			if(hasPassengers) {
				CarrySound placeSound = SoundsUtils.getPlaceSound();

				if(placeSound.isRegistered()) {
					placeSound.play(player);
				}

				if(ConfigurationUtils.isParticleRegistered()) {
					ParticlesUtils.spawnParticle(
							ConfigurationUtils.getParticle(), 
							player.getLocation(),
							ConfigurationUtils.getParticleCount());
				}
			}

		}
	}

}
