package org.kalbinvv.carryonanimals.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityDropEvent extends Event{

	private static final HandlerList HANDLERS = new HandlerList();
	
	private final Player player;
	private boolean cancelled;
	
	public EntityDropEvent(Player player) {
		this.player = player;
		this.cancelled = false;
	}
	
	public Player getPlayer() {
		return player;
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
