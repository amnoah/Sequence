package eu.sequence.data.processors;

import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.packet.Packet;
import eu.sequence.utilities.EvictingList;
import eu.sequence.utilities.MathUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class ClickingProcessor extends Processor {

    private final PlayerData data;

    private int ticks;
    private long lastArmAnimationPacketSent;
    private double kurtosis,stdDeviation,variance,deviationSquared,speed;
    private EvictingList samples = new EvictingList<Integer>(120);
    private boolean diggingSent;

    @Override
    public void handle(Packet packet) {
        if(packet.isArmAnimation()) {

            this.lastArmAnimationPacketSent = System.currentTimeMillis();

            samples.add(ticks);

            this.kurtosis = MathUtils.getKurtosis(samples);
            this.variance = MathUtils.getVariance(samples);
            this.stdDeviation = MathUtils.getStandardDeviation(samples);
            this.deviationSquared = MathUtils.deviationSquared(samples);

            this.speed = this.ticks * 50.0D;
            this.ticks = 0;


        }else if(packet.isFlying()) {

            this.diggingSent = false;
            this.ticks++;

        }else if(packet.isBlockDig()) {

            this.diggingSent = true;
        }
    }
}
