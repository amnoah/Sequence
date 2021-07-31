package eu.sequence.check;

import eu.sequence.event.PacketEvent;
import lombok.Getter;
import eu.sequence.data.PlayerData;
import org.bukkit.Bukkit;

public abstract class Check {
    protected final PlayerData playerData;
    @Getter protected final String name;
    @Getter protected final boolean experimental;

    public Check(final PlayerData playerData) {
        this.playerData = playerData;

        /* get the annotation and extract the check info from it */
        if (this.getClass().isAnnotationPresent(CheckInfo.class)) {
            final CheckInfo checkInfo = this.getClass().getAnnotation(CheckInfo.class);
            this.name = checkInfo.name();
            this.experimental = checkInfo.experimental();
        } else {
            Bukkit.getConsoleSender().sendMessage("No CheckInfo annotation found in class " + this.getClass().getSimpleName());
            this.name = "Error";
            this.experimental = false;
        }
    }

    protected void flag() {

    }

    public void handle(PacketEvent e) {

    }
}
