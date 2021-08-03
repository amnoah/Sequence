/*
 * This program, Ash, was designed to be a plugin for Spigot to catch cheaters.
 * Copyright (C) 2021 ExDiggers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package eu.sequence.data.tracker;

import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.event.impl.PacketPlaySendEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.transaction.WrappedPacketInTransaction;
import io.github.retrooper.packetevents.packetwrappers.play.out.entity.WrappedPacketOutEntity;
import io.github.retrooper.packetevents.packetwrappers.play.out.entitydestroy.WrappedPacketOutEntityDestroy;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityteleport.WrappedPacketOutEntityTeleport;
import io.github.retrooper.packetevents.packetwrappers.play.out.namedentityspawn.WrappedPacketOutNamedEntitySpawn;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;
import io.github.retrooper.packetevents.utils.vector.Vector3d;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@RequiredArgsConstructor
public class EntityTracker extends Processor {

    // DO NOT TOUCH

    private final PlayerData data;

    private final Map<Integer, TrackedEntity> trackedEntities = new ConcurrentHashMap<>();

    private final Map<Short, TrackedEntity.Update> updates = new ConcurrentHashMap<>();

    private double deltaBuffer;

    public void onPacketPlaySend(PacketPlaySendEvent event) {
        byte packetId = event.getPacketId();


        if (packetId == PacketType.Play.Server.REL_ENTITY_MOVE
                || packetId == PacketType.Play.Server.REL_ENTITY_MOVE_LOOK) {
            WrappedPacketOutEntity.WrappedPacketOutRelEntityMove relEntityMove = new WrappedPacketOutEntity.WrappedPacketOutRelEntityMove(event.getNMSPacket());

            TrackedEntity.Update update = new TrackedEntity.Update(
                    relEntityMove.getDeltaX(),
                    relEntityMove.getDeltaY(),
                    relEntityMove.getDeltaY(),
                    relEntityMove.getEntityId(),
                    (short) ThreadLocalRandom.current().nextInt(32767),
                    TrackedEntity.UpdateType.REL_MOVE
            );

            updates.put(update.getId(), update);

            WrappedPacketOutTransaction transaction = new WrappedPacketOutTransaction(
                    0, update.getId(), false
            );

            data.sendPacket(transaction, false);

            event.setPostTask(() ->
                    data.sendPacket(transaction, true)
            );
        } else if (packetId == PacketType.Play.Server.ENTITY_TELEPORT) {
            WrappedPacketOutEntityTeleport entityTeleport = new WrappedPacketOutEntityTeleport(event.getNMSPacket());

            Vector3d pos = entityTeleport.getPosition();

            TrackedEntity.Update update = new TrackedEntity.Update(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    entityTeleport.getEntityId(),
                    (short) ThreadLocalRandom.current().nextInt(32767),
                    TrackedEntity.UpdateType.SPAWN
            );

            updates.put(update.getId(), update);

            WrappedPacketOutTransaction transaction = new WrappedPacketOutTransaction(
                    0, update.getId(), false
            );

            data.sendPacket(transaction, false);

            event.setPostTask(() ->
                    data.sendPacket(transaction, true)
            );
        } else if (packetId == PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
            WrappedPacketOutNamedEntitySpawn namedEntitySpawn = new WrappedPacketOutNamedEntitySpawn(event.getNMSPacket());

            boolean player = namedEntitySpawn.getEntity() instanceof Player;

            Bukkit.broadcastMessage("spawn player=" + player);

            if (player) {
                Vector3d pos = namedEntitySpawn.getPosition();

                TrackedEntity.Update update = new TrackedEntity.Update(
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        namedEntitySpawn.getEntityId(),
                        (short) ThreadLocalRandom.current().nextInt(32767),
                        TrackedEntity.UpdateType.SPAWN
                );

                updates.put(update.getId(), update);

                WrappedPacketOutTransaction transaction = new WrappedPacketOutTransaction(
                        0, update.getId(), false
                );

                data.sendPacket(transaction, false);

                event.setPostTask(() ->
                        data.sendPacket(transaction, true)
                );
            }
        } else if (packetId == PacketType.Play.Server.ENTITY_DESTROY) {
            WrappedPacketOutEntityDestroy entityDestroy = new WrappedPacketOutEntityDestroy(event.getNMSPacket());

            boolean player = entityDestroy.getEntity() instanceof Player;

            Bukkit.broadcastMessage("destory player=" + player);

            if (player) {
                TrackedEntity.Update update = new TrackedEntity.Update(
                        -1,
                        -1,
                        -1,
                        entityDestroy.getEntityId(),
                        (short) ThreadLocalRandom.current().nextInt(32767),
                        TrackedEntity.UpdateType.REMOVE
                );

                updates.put(update.getId(), update);

                WrappedPacketOutTransaction transaction = new WrappedPacketOutTransaction(
                        0, update.getId(), false
                );

                data.sendPacket(transaction, false);

                event.setPostTask(() ->
                        data.sendPacket(transaction, true)
                );
            }
        }
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isIncomingTransaction()) {
            WrappedPacketInTransaction wrapper = new WrappedPacketInTransaction(packet.getNmsPacket());

            short id = wrapper.getActionNumber();

            if (updates.containsKey(id)) {
                TrackedEntity.Update update = updates.get(id);

                if (!update.isResponded()) {
                    update.setRespondedTick(data.getTick());
                    update.setResponded(true);

                    updates.replace(id, update);

                    switch (update.getType()) {
                        case SPAWN:
                            if (trackedEntities.containsKey(update.getEntityId())) {
                                TrackedEntity trackedEntity = trackedEntities.get(update.getEntityId());

                                trackedEntity.handleMove(update.getX(), update.getY(), update.getZ(), false);
                            } else {
                                TrackedEntity trackedEntity = new TrackedEntity();

                                trackedEntity.handleMove(update.getX(), update.getY(), update.getZ(), false);

                                trackedEntities.put(update.getEntityId(), trackedEntity);
                            }
                            break;
                        case REL_MOVE:
                            if (trackedEntities.containsKey(update.getEntityId())) {
                                TrackedEntity trackedEntity = trackedEntities.get(update.getEntityId());

                                trackedEntity.handleMove(update.getX(), update.getY(), update.getZ(), true);
                            }
                            break;
                        case REMOVE:
                            trackedEntities.remove(update.getEntityId());
                    }
                } else {
                    updates.remove(id);

                    int delta = data.getTick() - update.getRespondedTick();

                    if (delta > 1) {
                        ++deltaBuffer;
                    } else {
                        deltaBuffer = Math.max(0, deltaBuffer - 0.05);
                    }
                }
            }
        } else if (packet.isFlying()) {
            trackedEntities.values().forEach(TrackedEntity::onTick);
        }
    }
}
