package net.nperkins.stablemaster.listeners;

import net.nperkins.stablemaster.StableMaster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkListener implements Listener{

    final StableMaster plugin;

    public ChunkListener(StableMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void chunkUnloadEvent(ChunkUnloadEvent event) {

        if (StableMaster.horseChunk.contains(event.getChunk())) event.setCancelled(true);

    }

}