package de.htw.saar.smartcity.aggregator.core.receiver;

import com.rabbitmq.client.*;
import de.htw.saar.smartcity.aggregator.core.builder.GroupMeasurementBuilder;
import de.htw.saar.smartcity.aggregator.core.properties.CoreApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.receiver.ReceiverConnection;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupMember;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.service.GroupService;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class GroupsReceiver extends ReceiverConnection {


    private final GroupMeasurementBuilder groupMeasurementBuilder;

    private final GroupService groupService;

    private Channel channel;

    private ConcurrentMap<Long, String> groupIdConsumerTagMap = new ConcurrentHashMap<>();


    public GroupsReceiver(CoreApplicationProperties coreApplicationProperties, GroupMeasurementBuilder groupMeasurementBuilder, GroupService groupService) {

        super(coreApplicationProperties);
        this.groupMeasurementBuilder = groupMeasurementBuilder;
        this.groupService = groupService;


        try {
            channel = connection.createChannel();

            //consumeMonitor();


            List<Group> groups = groupService.findAllActiveGroups();

            for(Group group : groups) {

                consume(group);

                bindQueues(group.getAllSensorsRecursive(), group.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // we could monitor queue deletions (automatically configured when going idle)
    // and set groups inactive in case of their sensors not sending measurements
    private void consumeMonitor() {
        try {
            //bind monitor queue
            String q = channel.queueDeclare().getQueue();
            channel.queueBind(q, "amq.rabbitmq.event", "queue.*");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    String event = envelope.getRoutingKey();
                    Map<String, Object> headers = properties.getHeaders();
                    String name = headers.get("name").toString();
                    String vhost = headers.get("vhost").toString();

                    if (!event.equals("queue.created")) {
                        //queue.deleted ?
                    }
                }
            };

            channel.basicConsume(q, true, consumer);
        }catch(IOException ex) {

        }
    }


    public void memberAddedToGroup(GroupMember member, Group group) {

        bindQueues(member.getAllSensorsRecursive(), group.getName());
    }


    public void memberRemovedFromGroup(GroupMember member, Group group) {

        unbindQueues(member.getAllSensorsRecursive(), group.getName());
    }




    public void groupActivated(Group group) {
        if(group.isActive()) {
            consume(group);
        }
    }

    private void consume(Group group) {
        try {
            channel.queueDeclare(group.getName(), true, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                String routingKey = delivery.getEnvelope().getRoutingKey();
                String topic = routingKey.replaceAll("\\.", "/");
                SensorMeasurement sensorMeasurement = new SensorMeasurement(topic, message);
                //get group necessary ?
                Optional<Group> curr = groupService.findGroupById(group.getId());
                if (curr.isPresent())
                    this.groupMeasurementBuilder.addSensorMeasurementForGroup(curr.get(), sensorMeasurement);
            };
            String tag = channel.basicConsume(group.getName(), true, deliverCallback, consumerTag -> {
            });
            groupIdConsumerTagMap.put(group.getId(), tag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void bindQueues(List<Sensor> sensors, String groupName) {
        sensors.forEach(s -> {
            String routingKey = s.getName().replaceAll("/", ".");

            try {
                channel.queueBind(groupName, "amq.topic", routingKey);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    private void unbindQueues(List<Sensor> sensors, String groupName) {
        sensors.forEach(s -> {
        String routingKey = s.getName().replaceAll("/", ".");

        try {
            channel.queueUnbind(groupName, "amq.topic", routingKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    }

    public void groupDeactivated(Long groupId) {
        try {
            channel.basicCancel(groupIdConsumerTagMap.get(groupId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}