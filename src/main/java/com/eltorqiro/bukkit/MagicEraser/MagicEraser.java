
package com.eltorqiro.bukkit.MagicEraser;

import org.bukkit.Material;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Magic Eraser plugin
 *
 * @author eltorqiro
 */
public class MagicEraser extends JavaPlugin {
    private final MagicEraserPlayerListener playerListener = new MagicEraserPlayerListener(this);

    public void onDisable() {
    	// show plugin disable message in console
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!" );

    }

    public void onEnable() {
    	// register command handlers
    	getCommand("tqme").setExecutor( new MagicEraserCommandExecutor(this) );
    	
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
        
        // show plugin enabled message in console
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    public void playerEraseBlock(Player player, Block block, Material replacementMaterial) {
		// do not delete base layer (y = 1)
    	if( block != null && block.getType() != replacementMaterial && block.getY() > 1) {
			block.setType(replacementMaterial);


			// log if bedrock is the removed type
			if( block.getType() == Material.BEDROCK ) {
				System.out.println( "Bedrock deleted by " + player.getDisplayName() + " at X:" + block.getX() + ", Y:" + block.getY() + ", Z:" + block.getZ() );
			}
		}
    }
    
    public void playerEraseBlock(Player player, Block block) {
    	playerEraseBlock(player, block, Material.AIR);
    }
}
