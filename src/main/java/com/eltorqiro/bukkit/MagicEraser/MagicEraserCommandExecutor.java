package com.eltorqiro.bukkit.MagicEraser;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;

public class MagicEraserCommandExecutor implements CommandExecutor {

	private final MagicEraser plugin;
	
	public MagicEraserCommandExecutor(MagicEraser instance) {
		this.plugin = instance;
	}
	

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    	// only usable in-game
    	if( !(sender instanceof Player) ) {
    		return false;
    	}
    	
    	Player player = (Player)sender;

    	// only ops can use command
    	if( !player.isOp() ) {
    		player.sendMessage( command.getName() + " only usable by OPs" );
    		return true;
    	}
    	
    	// simulate what the client considers a targeted block
    	Block targetBlock = player.getTargetBlock( null, 4 );

    	// no args passed? show help message
    	if( args.length == 0 ) {
    		player.sendMessage( command.getName() + " : \nCopy\nPaste" );
    		return true;
    	}
    	
    	String subCommand = args[1];
    	
    	// copy command
    	if( subCommand.equalsIgnoreCase("copy") ) {
    		
    	}
    	
    	// paste command
    	else if( subCommand.equalsIgnoreCase("paste") ) {
    		
    		
    	}
    	
    	return true;
    }
}
