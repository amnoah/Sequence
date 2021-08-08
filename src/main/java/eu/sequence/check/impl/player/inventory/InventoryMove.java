package eu.sequence.check.impl.player.inventory;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.packet.Packet;

@CheckInfo(name = "Inventory",subName = "Move")
public class InventoryMove extends Check {

    public InventoryMove(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if(packet.isFlying()) {

            final boolean invOpen = playerData.getPlayer().getOpenInventory().getPlayer() == playerData.getPlayer();

            final boolean impossibleActions = playerData.getActionProcessor().isSneaking() || playerData.getActionProcessor().isSprinting();

            if(impossibleActions && invOpen) {
                flag("isSneaking=" + playerData.getActionProcessor().isSneaking()  + " isSprinting=" +  playerData.getActionProcessor().isSprinting());
            }
        }

    }
}
