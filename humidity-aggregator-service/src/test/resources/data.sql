INSERT INTO data_types
(id, name, unit)
VALUES (1, 'humidity', '');

INSERT INTO combinators
(id, name)
VALUES (1, 'double-avg');
INSERT INTO combinators
(id, name)
VALUES (2, 'double-min');
INSERT INTO combinators
(id, name)
VALUES (3, 'double-max');

INSERT INTO group_types
(id, name)
VALUES (1, 'humidity-group');

INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 1, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 2, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 3, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 4, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 5, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 6, '', true, 1, '', NULL);

INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 13, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 14, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 15, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 16, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 17, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 18, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 19, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 20, '', true, 1, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 21, '', true, 1, '', NULL);

INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor1', 1);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor2', 2);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor3', 3);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor4', 4);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor5', 5);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor6', 6);

INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (1, 1, 'humidity-group-1', 1);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (2, 1, 'humidity-group-2', 1);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (3, 1, 'humidity-group-3', 1);

INSERT INTO producer_group
(producer_id, group_id)
VALUES (1, 1);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (2, 1);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (3, 2);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (4, 2);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (5, 3);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (6, 3);


INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (1, 1, 13);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (2, 1, 14);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (3, 1, 15);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (1, 2, 16);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (2, 2, 17);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (3, 2, 18);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (1, 3, 19);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (2, 3, 20);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (3, 3, 21);
