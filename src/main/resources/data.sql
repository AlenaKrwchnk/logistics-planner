
INSERT INTO product_category(id, code, name) VALUES
                                                 (1,'ELECTRONICS','Electronics'),
                                                 (2,'FURNITURE','Furniture'),
                                                 (3,'FOOD','Food & Perishables'),
                                                 (4,'TEXTILES','Textiles'),
                                                 (5,'CHEMICALS','Chemicals')
ON DUPLICATE KEY UPDATE name=VALUES(name);


INSERT INTO product(id, sku, name, category_id, unit_weight_kg, unit_value_usd) VALUES
                                                                                    (1,'E-001','Smartphone Model A',1,0.18,450),
                                                                                    (2,'E-002','Laptop Pro',1,1.8,1200),
                                                                                    (3,'F-001','Office Desk',2,30,250),
                                                                                    (4,'F-002','Wooden Chair',2,7,85),
                                                                                    (5,'FO-001','Frozen Salmon Box',3,25,400),
                                                                                    (6,'TE-001','Cotton T-Shirt Pack',4,10,120),
                                                                                    (7,'CH-001','Industrial Cleaner (IBC)',5,1000,1500)
ON DUPLICATE KEY UPDATE name=VALUES(name), unit_value_usd=VALUES(unit_value_usd), unit_weight_kg=VALUES(unit_weight_kg);


INSERT INTO location(id, code, name, lat, lon, country) VALUES
                                                            (1,'PL-WAW','Warsaw',52.2297,21.0122,'PL'),
                                                            (2,'DE-BER','Berlin',52.5200,13.4050,'DE'),
                                                            (3,'NL-RTM','Rotterdam Port',51.9225,4.4791,'NL'),
                                                            (4,'BE-ANR','Antwerp Port',51.2194,4.4025,'BE'),
                                                            (5,'FR-PAR','Paris',48.8566,2.3522,'FR'),
                                                            (6,'ES-BCN','Barcelona Port',41.3851,2.1734,'ES'),
                                                            (7,'IT-GEN','Genoa Port',44.4056,8.9463,'IT'),
                                                            (8,'UK-LON','London',51.5072,-0.1276,'GB'),
                                                            (9,'PL-GDN','Gdansk Port',54.3520,18.6466,'PL'),
                                                            (10,'DE-MUC','Munich',48.1351,11.5820,'DE'),
                                                            (11,'CZ-PRG','Prague',50.0755,14.4378,'CZ'),
                                                            (12,'NL-AMS','Amsterdam',52.3676,4.9041,'NL'),
                                                            (13,'PT-LIS','Lisbon Port',38.7223,-9.1393,'PT'),
                                                            (14,'SE-GOT','Gothenburg Port',57.7089,11.9746,'SE'),
                                                            (15,'NO-OSL','Oslo',59.9139,10.7522,'NO')
ON DUPLICATE KEY UPDATE name=VALUES(name);


INSERT INTO customs_checkpoint(id, name, country, location_id) VALUES
                                                                   (1,'PL-DE Border','DE',2),
                                                                   (2,'NL-BE Maritime Check','NL',3),
                                                                   (3,'FR-ES Transit','FR',5),
                                                                   (4,'EU North Gate','SE',14)
ON DUPLICATE KEY UPDATE name=VALUES(name);


INSERT INTO eco_fee_rule(id, vehicle_type, price_per_kgco2usd) VALUES
                                                                   (1,'TRUCK',0.025),
                                                                   (2,'RAIL',0.010),
                                                                   (3,'SHIP',0.004)
ON DUPLICATE KEY UPDATE price_per_kgco2usd=VALUES(price_per_kgco2usd);



INSERT INTO tariff_rule(id, category_id, origin_country, dest_country, rate_percent, min_usd, max_usd) VALUES
                                                                                                           (1,1,'PL','NL',5,3,NULL),
                                                                                                           (2,2,'DE','FR',10,15,NULL),
                                                                                                           (3,3,'NL','DE',2,1,50),
                                                                                                           (4,5,'IT','NL',8,10,1000),
                                                                                                           (5,1,'CN','EU',12,50,10000) -- cross-border example
ON DUPLICATE KEY UPDATE rate_percent=VALUES(rate_percent), min_usd=VALUES(min_usd);



INSERT INTO segment(id, origin_id, destination_id, vehicle_type, distance_km, base_cost_per_km_usd, speed_kmh, co2kg_per_km, customs_id, geo_json) VALUES
                                                                                                                                                        -- Poland: Gdansk -> Warsaw (truck)

                                                                                                                                                        (1,9,1,'TRUCK',340,0.8,70,0.9,NULL,'{\"type\":\"LineString\",\"coordinates\":[[18.6466,54.3520],[21.0122,52.2297]]}'),
                                                                                                                                                        (22,1,9,'TRUCK',340,0.8,70,0.9,NULL,'{\"type\":\"LineString\",\"coordinates\":[[21.0122,52.2297],[18.6466,54.3520]]}'),
                                                                                                                                                        -- Warsaw -> Berlin (truck)

                                                                                                                                                        (2,1,2,'TRUCK',570,0.9,70,0.8,1,'{\"type\":\"LineString\",\"coordinates\":[[21.0122,52.2297],[13.4050,52.5200]]}'),
                                                                                                                                                        (23,2,1,'TRUCK',570,0.9,70,0.8,1,'{\"type\":\"LineString\",\"coordinates\":[[13.4050,52.5200],[21.0122,52.2297]]}'),
                                                                                                                                                        -- Berlin -> Amsterdam (rail)

                                                                                                                                                        (3,2,12,'RAIL',650,0.45,80,0.35,NULL,'{\"type\":\"LineString\",\"coordinates\":[[13.4050,52.5200],[4.9041,52.3676]]}'),
                                                                                                                                                        (24,12,2,'RAIL',650,0.45,80,0.35,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.9041,52.3676],[13.4050,52.5200]]}'),
                                                                                                                                                        -- Amsterdam -> Rotterdam (truck short feeder)

                                                                                                                                                        (4,12,3,'TRUCK',35,0.7,60,0.5,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.9041,52.3676],[4.4791,51.9225]]}'),
                                                                                                                                                        (25,3,12,'TRUCK',35,0.7,60,0.5,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.4791,51.9225],[4.9041,52.3676]]}'),
                                                                                                                                                        -- Rotterdam -> Antwerp (ship short coastal)

                                                                                                                                                        (5,3,4,'SHIP',75,0.25,25,0.05,2,'{\"type\":\"LineString\",\"coordinates\":[[4.4791,51.9225],[4.4025,51.2194]]}'),
                                                                                                                                                        (26,4,3,'SHIP',75,0.25,25,0.05,2,'{\"type\":\"LineString\",\"coordinates\":[[4.4025,51.2194],[4.4791,51.9225]]}'),
                                                                                                                                                        -- Antwerp -> Rotterdam (rail)

                                                                                                                                                        (6,4,3,'RAIL',75,0.4,70,0.2,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.4025,51.2194],[4.4791,51.9225]]}'),
                                                                                                                                                        (27,3,4,'RAIL',75,0.4,70,0.2,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.4791,51.9225],[4.4025,51.2194]]}'),
                                                                                                                                                        -- Rotterdam -> Barcelona (ship)

                                                                                                                                                        (7,3,6,'SHIP',1670,0.12,20,0.03,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.4791,51.9225],[2.1734,41.3851]]}'),
                                                                                                                                                        (28,6,3,'SHIP',1670,0.12,20,0.03,NULL,'{\"type\":\"LineString\",\"coordinates\":[[2.1734,41.3851],[4.4791,51.9225]]}'),
                                                                                                                                                        -- Barcelona -> Genoa (ship coastal)

                                                                                                                                                        (8,6,7,'SHIP',850,0.10,22,0.04,NULL,'{\"type\":\"LineString\",\"coordinates\":[[2.1734,41.3851],[8.9463,44.4056]]}'),
                                                                                                                                                        (29,7,6,'SHIP',850,0.10,22,0.04,NULL,'{\"type\":\"LineString\",\"coordinates\":[[8.9463,44.4056],[2.1734,41.3851]]}'),
                                                                                                                                                        -- Genoa -> Rotterdam (rail+truck via transshipment) — rail segment

                                                                                                                                                        (9,7,3,'RAIL',1200,0.5,75,0.25,NULL,'{\"type\":\"LineString\",\"coordinates\":[[8.9463,44.4056],[4.4791,51.9225]]}'),
                                                                                                                                                        (30,3,7,'RAIL',1200,0.5,75,0.25,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.4791,51.9225],[8.9463,44.4056]]}'),
                                                                                                                                                        -- London -> Rotterdam (ferry/ship)

                                                                                                                                                        (10,8,3,'SHIP',300,0.2,24,0.06,NULL,'{\"type\":\"LineString\",\"coordinates\":[[-0.1276,51.5072],[4.4791,51.9225]]}'),
                                                                                                                                                        (31,3,8,'SHIP',300,0.2,24,0.06,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.4791,51.9225],[-0.1276,51.5072]]}'),
                                                                                                                                                        -- Paris -> Barcelona (truck)

                                                                                                                                                        (11,5,6,'TRUCK',1030,0.95,65,0.85,NULL,'{\"type\":\"LineString\",\"coordinates\":[[2.3522,48.8566],[2.1734,41.3851]]}'),
                                                                                                                                                        (32,6,5,'TRUCK',1030,0.95,65,0.85,NULL,'{\"type\":\"LineString\",\"coordinates\":[[2.1734,41.3851],[2.3522,48.8566]]}'),
                                                                                                                                                        -- Munich -> Vienna (truck/rail)

                                                                                                                                                        (12,10,11,'TRUCK',380,0.85,70,0.7,NULL,'{\"type\":\"LineString\",\"coordinates\":[[11.5820,48.1351],[14.4378,50.0755]]}'),
                                                                                                                                                        (33,11,10,'TRUCK',380,0.85,70,0.7,NULL,'{\"type\":\"LineString\",\"coordinates\":[[14.4378,50.0755],[11.5820,48.1351]]}'),
                                                                                                                                                        -- Amsterdam -> London (ship)

                                                                                                                                                        (13,12,8,'SHIP',520,0.22,26,0.06,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.9041,52.3676],[-0.1276,51.5072]]}'),
                                                                                                                                                        (34,8,12,'SHIP',520,0.22,26,0.06,NULL,'{\"type\":\"LineString\",\"coordinates\":[[-0.1276,51.5072],[4.9041,52.3676]]}'),
                                                                                                                                                        -- Lisbon -> Barcelona (ship long)

                                                                                                                                                        (14,13,6,'SHIP',1250,0.09,18,0.05,NULL,'{\"type\":\"LineString\",\"coordinates\":[[-9.1393,38.7223],[2.1734,41.3851]]}'),
                                                                                                                                                        (35,6,13,'SHIP',1250,0.09,18,0.05,NULL,'{\"type\":\"LineString\",\"coordinates\":[[2.1734,41.3851],[-9.1393,38.7223]]}'),
                                                                                                                                                        -- Gothenburg -> Rotterdam (ship)

                                                                                                                                                        (15,14,3,'SHIP',900,0.11,20,0.035,4,'{\"type\":\"LineString\",\"coordinates\":[[11.9746,57.7089],[4.4791,51.9225]]}'),
                                                                                                                                                        (36,3,14,'SHIP',900,0.11,20,0.035,4,'{\"type\":\"LineString\",\"coordinates\":[[4.4791,51.9225],[11.9746,57.7089]]}'),
                                                                                                                                                        -- Oslo -> Gothenburg (truck/rail)

                                                                                                                                                        (16,15,14,'TRUCK',290,1.1,65,0.95,NULL,'{\"type\":\"LineString\",\"coordinates\":[[10.7522,59.9139],[11.9746,57.7089]]}'),
                                                                                                                                                        (37,14,15,'TRUCK',290,1.1,65,0.95,NULL,'{\"type\":\"LineString\",\"coordinates\":[[11.9746,57.7089],[10.7522,59.9139]]}'),
                                                                                                                                                        -- Berlin -> Munich (rail)

                                                                                                                                                        (17,2,10,'RAIL',585,0.48,90,0.28,NULL,'{\"type\":\"LineString\",\"coordinates\":[[13.4050,52.5200],[11.5820,48.1351]]}'),
                                                                                                                                                        (38,10,2,'RAIL',585,0.48,90,0.28,NULL,'{\"type\":\"LineString\",\"coordinates\":[[11.5820,48.1351],[13.4050,52.5200]]}'),
                                                                                                                                                        -- Gdańsk -> Rotterdam (combined: truck->rail->ship simplified as one long truck for demo)

                                                                                                                                                        (18,9,3,'TRUCK',950,0.95,70,0.9,1,'{\"type\":\"LineString\",\"coordinates\":[[18.6466,54.3520],[4.4791,51.9225]]}'),
                                                                                                                                                        (39,3,9,'TRUCK',950,0.95,70,0.9,1,'{\"type\":\"LineString\",\"coordinates\":[[4.4791,51.9225],[18.6466,54.3520]]}'),
                                                                                                                                                        -- Rotterdam -> Lisbon (ship)

                                                                                                                                                        (19,3,13,'SHIP',2300,0.08,16,0.04,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.4791,51.9225],[-9.1393,38.7223]]}'),
                                                                                                                                                        (40,13,3,'SHIP',2300,0.08,16,0.04,NULL,'{\"type\":\"LineString\",\"coordinates\":[[-9.1393,38.7223],[4.4791,51.9225]]}'),
                                                                                                                                                        -- Antwerp -> Genoa (rail)

                                                                                                                                                        (20,4,7,'RAIL',1200,0.5,75,0.3,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.4025,51.2194],[8.9463,44.4056]]}'),
                                                                                                                                                        (41,7,4,'RAIL',1200,0.5,75,0.3,NULL,'{\"type\":\"LineString\",\"coordinates\":[[8.9463,44.4056],[4.4025,51.2194]]}'),
                                                                                                                                                        -- Amsterdam -> Paris (truck)

                                                                                                                                                        (21,12,5,'TRUCK',500,0.9,70,0.8,NULL,'{\"type\":\"LineString\",\"coordinates\":[[4.9041,52.3676],[2.3522,48.8566]]}'),
                                                                                                                                                        (42,5,12,'TRUCK',500,0.9,70,0.8,NULL,'{\"type\":\"LineString\",\"coordinates\":[[2.3522,48.8566],[4.9041,52.3676]]}')
ON DUPLICATE KEY UPDATE distance_km=VALUES(distance_km), base_cost_per_km_usd=VALUES(base_cost_per_km_usd);


INSERT INTO segment(id, origin_id, destination_id, vehicle_type, distance_km, base_cost_per_km_usd, speed_kmh, co2kg_per_km, customs_id, geo_json) VALUES

                                                                                                                                                        (44,2,11,'TRUCK',350,0.85,70,0.8,NULL,'{\"type\":\"LineString\",\"coordinates\":[[13.4050,52.5200],[14.4378,50.0755]]}'),
                                                                                                                                                        (46,11,2,'TRUCK',350,0.85,70,0.8,NULL,'{\"type\":\"LineString\",\"coordinates\":[[14.4378,50.0755],[13.4050,52.5200]]}'),
                                                                                                                                                        (45,11,1,'TRUCK',540,0.9,68,0.85,NULL,'{\"type\":\"LineString\",\"coordinates\":[[14.4378,50.0755],[21.0122,52.2297]]}'),
                                                                                                                                                        (47,1,11,'TRUCK',540,0.9,68,0.85,NULL,'{\"type\":\"LineString\",\"coordinates\":[[21.0122,52.2297],[14.4378,50.0755]]}')




ON DUPLICATE KEY UPDATE distance_km=VALUES(distance_km);



INSERT INTO product_allowed_vehicle_types(product_id, allowed_vehicle_types) VALUES
-- Electronics
(1, 'TRUCK'),
(1, 'RAIL'),
(2, 'TRUCK'),
(2, 'RAIL'),

-- Furniture
(3, 'TRUCK'),
(3, 'RAIL'),
(3, 'SHIP'),
(4, 'TRUCK'),
(4, 'RAIL'),
(4, 'SHIP'),

-- Food & Perishables
(5, 'TRUCK'),
(5, 'SHIP'),
(5, 'RAIL'),

-- Textiles
(6, 'TRUCK'),
(6, 'SHIP'),
(6, 'RAIL'),

-- Chemicals
(7, 'TRUCK'),
(7, 'RAIL'),
(7, 'SHIP');

