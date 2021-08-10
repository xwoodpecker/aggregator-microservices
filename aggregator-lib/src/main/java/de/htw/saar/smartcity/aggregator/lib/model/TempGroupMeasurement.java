package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * The type Temp group measurement - used for computing aggregates
 */
public class TempGroupMeasurement implements Serializable {

    @JsonIgnore
    private CombinatorModel combinatorModel;

    private Integer maximumSize;

    //private HashMap<Long, Measurement> producerIdMeasurementMap = new HashMap<>();

    @JsonProperty("store")
    private GroupMeasurementStore groupMeasurementStore = new GroupMeasurementStore();


    /**
     * Instantiates a new Temp group measurement.
     */
    public TempGroupMeasurement() {
    }


    /**
     * Instantiates a new Temp group measurement.
     *
     * @param maximumSize the maximum size
     * @param groupId     the group id
     */
    public TempGroupMeasurement(Integer maximumSize, Long groupId) {
        this.maximumSize = maximumSize;
        this.groupMeasurementStore.setGroupId(groupId);

    }

    /**
     * Gets aggregate name.
     *
     * @return the aggregate name
     */
    @JsonIgnore
    public String getAggregateName() {
        return combinatorModel.getName();
    }

    /**
     * Gets group combinator.
     *
     * @return the group combinator
     */
    @JsonIgnore
    public CombinatorModel getGroupCombinator() {
        return combinatorModel;
    }

    /**
     * Sets group combinator.
     *
     * @param abstractCombinatorModel the abstract combinator model
     */
    public void setGroupCombinator(CombinatorModel abstractCombinatorModel) {
        this.combinatorModel = abstractCombinatorModel;
    }

    /**
     * Gets maximum size.
     *
     * @return the maximum size
     */
    public Integer getMaximumSize() {
        return maximumSize;
    }

    /**
     * Sets maximum size.
     *
     * @param maximumSize the maximum size
     */
    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }


    /**
     * Gets group measurement store.
     *
     * @return the group measurement store
     */
    public GroupMeasurementStore getGroupMeasurementStore() {
        return groupMeasurementStore;
    }

    /**
     * Sets group measurement store.
     *
     * @param groupMeasurementStore the group measurement store
     */
    public void setGroupMeasurementStore(GroupMeasurementStore groupMeasurementStore) {
        this.groupMeasurementStore = groupMeasurementStore;
    }

    /**
     * Ready boolean.
     *
     * @return the boolean
     */
    @JsonIgnore
    public boolean ready() {
        return maximumSize == groupMeasurementStore.getProducerIdMeasurementMap().values().size();
    }

    /**
     * Combine measurement.
     *
     * @return the measurement
     * @throws MeasurementException the measurement exception
     */
    @JsonIgnore
    public Measurement combine() throws MeasurementException {
        Measurement m = new Measurement();
        m.setTime(LocalDateTime.now());
        m.setValue(combinatorModel.getFunction().apply(groupMeasurementStore));
        return m;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TempGroupMeasurement{");
        sb.append(", maximumSize=").append(maximumSize);
        sb.append(", groupMeasurementStore=").append(groupMeasurementStore);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Put group measurement store measurement.
     *
     * @param producerId  the producer id
     * @param measurement the measurement
     */
    public void putGroupMeasurementStoreMeasurement(Long producerId, Measurement measurement) {
        groupMeasurementStore.getProducerIdMeasurementMap().put(producerId, measurement);
    }
}
