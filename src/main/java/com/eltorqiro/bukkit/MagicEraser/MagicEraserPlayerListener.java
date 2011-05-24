
package com.eltorqiro.bukkit.MagicEraser;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.BlockFace;

/**
 * Magic Eraser player event listener
 * @author eltorqiro
 */
public class MagicEraserPlayerListener extends PlayerListener {
    private final MagicEraser plugin;

    public MagicEraserPlayerListener(MagicEraser instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
    	if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {
    		return;
    	}
    	
    	Block block = event.getClickedBlock();
    	ItemStack item = event.getItem();
    	Player player = event.getPlayer();

    	if( event.isCancelled() || block == null || block.getType() == Material.AIR || !player.isOp() || item.getType() !=  Material.GOLD_PICKAXE ) {
    		return;
    	}

    	// erase in different patterns depending what block type was hit
    	switch( block.getType() ) {
    		// sponge makes a bounding box disappear
    		case SPONGE:
    			// get boundaries of contiguous box
    			// north = x getting more negative
    			// east = z getting more negative
    			// down = y getting more negative
    			
    			// north most
    			Block tempBlock; Block northBlock = null; Block eastBlock = null; Block downBlock = null;
    			for( tempBlock = block; tempBlock.getType() == Material.SPONGE; tempBlock = tempBlock.getRelative(BlockFace.NORTH) ) {
    				northBlock = tempBlock;
    			}

    			// east most
    			for( tempBlock = block; tempBlock.getType() == Material.SPONGE; tempBlock = tempBlock.getRelative(BlockFace.EAST) ) {
    				eastBlock = tempBlock;
    			}

    			// down most
    			for( tempBlock = block; tempBlock.getType() == Material.SPONGE; tempBlock = tempBlock.getRelative(BlockFace.DOWN) ) {
    				downBlock = tempBlock;
    			}
    			
    			// get replacement material type
    			Material replacementMaterial;
    			if(block.getY() == 127) {
    				replacementMaterial = Material.AIR;
    			}
    			else {
    				replacementMaterial = block.getRelative(BlockFace.UP).getType();
    			}

    			// remove replacement material type block
    			plugin.playerEraseBlock(player, block.getRelative(BlockFace.UP));
    			
    			// remove blocks within boundaries
    			for( int y = block.getY(); y >= downBlock.getY(); y-- ) {
	    			for( int x = block.getX(); x >= northBlock.getX(); x-- ) {
			    		for( int z = block.getZ(); z >= eastBlock.getZ(); z-- ) {
			    				plugin.playerEraseBlock(player, block.getWorld().getBlockAt(x, y, z), replacementMaterial);
			    		}
		    		}
		    	}

	    	break;

	    	// default just break one block
	    	default:
	    		plugin.playerEraseBlock(player, block);
	    	break;
    	}
    }
    
}
