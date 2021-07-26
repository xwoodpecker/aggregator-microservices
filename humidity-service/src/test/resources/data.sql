INSERT INTO data_types
(id, name, unit)
VALUES (1, 'temperature', '');
INSERT INTO data_types
(id, name, unit)
VALUES (2, 'humidity', '');
INSERT INTO data_types
(id, name, unit)
VALUES (3, 'dewpoint', '');
INSERT INTO data_types
(id, name, unit)
VALUES (4, 'heatflux', '');
INSERT INTO data_types
(id, name, unit)
VALUES (5, 'shutter', '');

INSERT INTO combinators
(id, name)
VALUES (1, 'double-avg');
INSERT INTO combinators
(id, name)
VALUES (2, 'double-min');
INSERT INTO combinators
(id, name)
VALUES (3, 'double-max');
INSERT INTO combinators
(id, name)
VALUES (4, 'dewpoint-combinator');
INSERT INTO combinators
(id, name)
VALUES (5, 'heatflux-combinator');
INSERT INTO combinators
(id, name)
VALUES (6, 'shutter-combinator');

INSERT INTO group_types
(id, name)
VALUES (1, 'temperature-group');
INSERT INTO group_types
(id, name)
VALUES (2, 'dewpoint-group');
INSERT INTO group_types
(id, name)
VALUES (3, 'heatflux-group');

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
VALUES ('S', 7, '', true, 2, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 8, '', true, 2, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 9, '', true, 2, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 10, '', true, 2, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 11, '', true, 2, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('S', 12, '', true, 2, '', NULL);
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
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 22, '', true, 3, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 23, '', true, 3, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 24, '', true, 3, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 25, '', true, 4, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 26, '', true, 4, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 27, '', true, 4, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 28, '', true, 5, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 29, '', true, 5, '', NULL);
INSERT INTO producers
(dtype, id, object_store_path, export_as_metric, data_type_id, information, location_id)
VALUES ('A', 30, '', true, 5, '', NULL);

INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/temperature/sensor1', 1);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/temperature/sensor2', 2);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/temperature/sensor3', 3);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/temperature/sensor4', 4);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/temperature/sensor5', 5);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/temperature/sensor6', 6);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor1', 7);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor2', 8);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor3', 9);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor4', 10);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor5', 11);
INSERT INTO sensors
(name, id)
VALUES ('data/aggregator/humidity/sensor6', 12);

INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (1, 1, 'temperature-group-1', 1);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (2, 1, 'temperature-group-2', 1);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (3, 1, 'temperature-group-3', 1);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (4, 1, 'dewpoint-group-1', 2);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (5, 1, 'dewpoint-group-2', 2);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (6, 1, 'dewpoint-group-3', 2);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (7, 1, 'heatflux-group-1', 3);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (8, 1, 'heatflux-group-2', 3);
INSERT INTO `groups`
(id, active, name, group_type_id)
VALUES (9, 1, 'heatflux-group-3', 3);

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

INSERT INTO producer_group
(producer_id, group_id)
VALUES (1, 4);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (7, 4);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (2, 5);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (8, 5);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (3, 6);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (9, 6);

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

INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (4, 4, 22);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (4, 5, 23);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (4, 6, 24);

INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (5, 7, 25);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (6, 7, 26);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (5, 8, 27);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (6, 8, 28);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (5, 9, 29);
INSERT INTO aggregators
(combinator_id, group_id, id)
VALUES (6, 9, 30);

INSERT INTO producer_group
(producer_id, group_id)
VALUES (22, 7);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (23, 8);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (24, 9);

INSERT INTO producer_group
(producer_id, group_id)
VALUES (1, 7);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (2, 8);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (3, 9);

INSERT INTO producer_group
(producer_id, group_id)
VALUES (4, 7);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (5, 8);
INSERT INTO producer_group
(producer_id, group_id)
VALUES (6, 9);

INSERT INTO tags
(id, name)
VALUES (1, 'outside-temperature');
INSERT INTO tags
(id, name)
VALUES (2, 'inside-temperature');

INSERT INTO producer_tag
(producer_id, tag_id)
VALUES (1, 2);
INSERT INTO producer_tag
(producer_id, tag_id)
VALUES (2, 2);
INSERT INTO producer_tag
(producer_id, tag_id)
VALUES (3, 2);
INSERT INTO producer_tag
(producer_id, tag_id)
VALUES (4, 1);
INSERT INTO producer_tag
(producer_id, tag_id)
VALUES (5, 1);
INSERT INTO producer_tag
(producer_id, tag_id)
VALUES (6, 1);

INSERT INTO formula_items
(id, name)
VALUES (1, 'u-value');

INSERT INTO formula_item_values
(id, value, formula_item_id)
VALUES (1, '0.4', 1);
INSERT INTO formula_item_values
(id, value, formula_item_id)
VALUES (2, '0.8', 1);
INSERT INTO formula_item_values
(id, value, formula_item_id)
VALUES (3, '1.3', 1);
INSERT INTO formula_item_values
(id, value, formula_item_id)
VALUES (4, '1.7', 1);
INSERT INTO formula_item_values
(id, value, formula_item_id)
VALUES (5, '2.2', 1);
INSERT INTO formula_item_values
(id, value, formula_item_id)
VALUES (6, '3.0', 1);
INSERT INTO formula_item_values
(id, value, formula_item_id)
VALUES (7, '5.0', 1);

INSERT INTO group_formula_item_value
(group_id, formula_item_value_id)
VALUES (7, 7);
INSERT INTO group_formula_item_value
(group_id, formula_item_value_id)
VALUES (8, 2);
INSERT INTO group_formula_item_value
(group_id, formula_item_value_id)
VALUES (9, 4);

