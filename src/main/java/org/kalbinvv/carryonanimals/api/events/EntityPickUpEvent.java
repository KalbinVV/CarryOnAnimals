package org.kalbinvv.carryonanimals.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityPickUpEvent extends Event{

	private static final HandlerList HANDLERS = new HandlerList();
	
	private final Player player;
	private final Entity entity;
	private boolean cancelled;
	
	public EntityPickUpEvent(Player player, Entity entity) {
		this.player = player;
		this.entity = entity;
		this.cancelled = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public static HandlerList getHandlerList() {
        return HANDLERS;
    }
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	

	
	
}
