package com.giornosmp.rainingblocks;

import com.giornosmp.rainingblocks.commands.BlockDrop;
import com.giornosmp.rainingblocks.commands.BlockDropTabComplete;
import com.giornosmp.rainingblocks.commands.TestCommand;
import com.giornosmp.rainingblocks.gamestates.BlockStage;
import com.giornosmp.rainingblocks.gamestates.State;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class RainingBlocks extends JavaPlugin {

    private State gameStates;
    private BlockStage blockStage;

    public State getGameState() {
        return gameStates;
    }
    public void setGameState(State gameState) {
        this.gameStates = gameState;
    }

    public BlockStage getblockStage() {
        return blockStage;
    }
    public void setblockStage(BlockStage blockStage) {
        this.blockStage = blockStage;
    }

    @Override
    public void onEnable() {
        this.saveConfig();
        setGameState(State.OFF);
        setblockStage(BlockStage.STAGE1);

        Objects.requireNonNull(this.getCommand("rainingblocks")).setExecutor(new BlockDrop(this));
        this.getCommand("deez").setExecutor(new TestCommand());
        Objects.requireNonNull(this.getCommand("rainingblocks")).setTabCompleter(new BlockDropTabComplete());
    }

    public void saveConfig(){
        this.saveDefaultConfig();
    }
}
