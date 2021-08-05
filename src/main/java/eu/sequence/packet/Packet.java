package eu.sequence.packet;

import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.NMSPacket;
import io.github.retrooper.packetevents.packetwrappers.play.in.abilities.WrappedPacketInAbilities;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Packet {

    private final byte id;
    private final NMSPacket nmsPacket;
    private final boolean receiving;

    public boolean isSending() {
        return !receiving;
    }

    public boolean isReceiving() {
        return receiving;
    }

    public boolean isFlying() {
        return isReceiving() &&
                PacketType.Play.Client.Util.isInstanceOfFlying(id);
    }

    public boolean isUseEntity() {
        return isReceiving() && id == PacketType.Play.Client.USE_ENTITY;
    }

    public boolean isExplosion() {
        return isSending() && id == PacketType.Play.Server.EXPLOSION;
    }

    public boolean isRotation() {
        return isReceiving()
                && (id == PacketType.Play.Client.LOOK
                || id == PacketType.Play.Client.POSITION_LOOK);
    }

    public boolean isPosition() {
        return isReceiving()
                && (id == PacketType.Play.Client.POSITION
                || id == PacketType.Play.Client.POSITION_LOOK);
    }

    public boolean isArmAnimation() {
        return isReceiving() && id == PacketType.Play.Client.ARM_ANIMATION;
    }

    public boolean isAbilities() {
        return isReceiving() && id == PacketType.Play.Client.ABILITIES;
    }

    public boolean isBlockPlace() {
        return isReceiving() && id == PacketType.Play.Client.BLOCK_PLACE;
    }

    public boolean isBlockDig() {
        return isReceiving() && id == PacketType.Play.Client.BLOCK_DIG;
    }

    public boolean isWindowClick() {
        return isReceiving() && id == PacketType.Play.Client.WINDOW_CLICK;
    }

    public boolean isEntityAction() {
        return isReceiving() && id == PacketType.Play.Client.ENTITY_ACTION;
    }

    public boolean isPosLook() {
        return isReceiving() && id == PacketType.Play.Client.POSITION_LOOK;
    }

    public boolean isCloseWindow() {
        return isReceiving() && id == PacketType.Play.Client.CLOSE_WINDOW;
    }

    public boolean isKeepAlive() {
        return isReceiving() && id == PacketType.Play.Client.KEEP_ALIVE;
    }

    public boolean isSteerVehicle() {
        return isReceiving() && id == PacketType.Play.Client.STEER_VEHICLE;
    }

    public boolean isHeldItemSlot() {
        return isReceiving() && id == PacketType.Play.Client.HELD_ITEM_SLOT;
    }

    public boolean isClientCommand() {
        return isReceiving() && id == PacketType.Play.Client.CLIENT_COMMAND;
    }

    public boolean isCustomPayload() {
        return isReceiving() && id == PacketType.Play.Client.CUSTOM_PAYLOAD;
    }

    public boolean isIncomingTransaction() {
        return isReceiving() && id == PacketType.Play.Client.TRANSACTION;
    }

    public boolean isSendingTransaction() {
        return isSending() && id == PacketType.Play.Server.TRANSACTION;
    }

    public boolean isAcceptingTeleport() {
        return isReceiving() && id == PacketType.Play.Client.TELEPORT_ACCEPT;
    }

    public boolean isTeleport() {
        return isSending() && id == PacketType.Play.Server.ENTITY_TELEPORT;
    }

    public boolean isOutPosition() {
        return isSending() && id == PacketType.Play.Server.POSITION;
    }

    public boolean isVelocity() {
        return isSending() && id == PacketType.Play.Server.ENTITY_VELOCITY;
    }

    public boolean isRelEntityMove() {
        return isSending() && id == PacketType.Play.Server.REL_ENTITY_MOVE;
    }

    //TODO : move that somewhere
    public boolean spoofingAbilities() {
        if (isAbilities()) {
            WrappedPacketInAbilities wrappedPacketInAbilities = new WrappedPacketInAbilities(nmsPacket);
            return wrappedPacketInAbilities.isFlying() && !wrappedPacketInAbilities.isFlightAllowed().get();
        }
        return false;

    }
}
