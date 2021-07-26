INSERT INTO data_types
(id, name, unit)
VALUES (1, 'water', '');

INSERT INTO combinators
(id, name)
VALUES (1, 'true-sum');

INSERT INTO group_types
(id, name)
VALUES (1, 'water-group');

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

INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/water/sensor1', 1);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/water/sensor2', 2);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/water/sensor3', 3);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/water/sensor4', 4);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/water/sensor5', 5);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/water/sensor6', 6);

INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (1, 1, 'water-group-1', 1);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (2, 1, 'water-group-2', 1);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (3, 1, 'water-group-3', 1);

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
VALUES (1, 1, 14);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (1, 1, 15);
