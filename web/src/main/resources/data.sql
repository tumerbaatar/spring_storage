INSERT INTO storage (id, name, slug) VALUES (501, 'Reset', 'reset');
INSERT INTO storage (id, name, slug) VALUES (502, 'Garant', 'garant');

INSERT INTO part (id, part_number, permanent_hash, name, description, manufacturer, manufacturer_part_number, price, creation_timestamp, update_timestamp, storage_id)
VALUES (
  1001,
  'pn23093022309',
  'testPart1',
  'SSD drive',
  'some description',
  'MSI',
  'MSI MPN',
  100.01,
  '2018-02-28 16:15:22.291001',
  '2018-02-28 16:16:22.291002',
  501
);

INSERT INTO part (id, part_number, permanent_hash, name, description, manufacturer, manufacturer_part_number, price, creation_timestamp, update_timestamp, storage_id)
VALUES (
  1002,
  'pn39223093022',
  'testPart2',
  'Sony Blu-ray DVD drive',
  'Very rare part',
  'Lenovo',
  'Lenovo MPN',
  900.01,
  '2018-02-28 16:17:22.291003',
  '2018-02-28 16:19:22.291004',
  501
);

INSERT INTO part (id, part_number, permanent_hash, name, description, manufacturer, manufacturer_part_number, price, creation_timestamp, update_timestamp, storage_id)
VALUES (
  1003,
  'pn 777',
  'testPart3',
  'Клавиатура',
  'Average part',
  'Asus',
  'Asus MPN',
  900.01,
  '2018-02-28 16:20:22.291005',
  '2018-02-28 16:21:22.291006',
  502
);

INSERT INTO box (id, name, permanent_hash, single_part_box, creation_timestamp, update_timestamp, storage_id) VALUES (
  2001, 'testBox-a', 'testBox1', FALSE,
  '2018-02-28 16:22:22.291006',
  '2018-02-28 16:23:22.291007',
  501
);

INSERT INTO box (id, name, permanent_hash, single_part_box, creation_timestamp, update_timestamp, storage_id) VALUES (
  2002, 'testBox-b', 'testBox2', FALSE,
  '2018-02-28 16:24:22.291008',
  '2018-02-28 16:25:22.291009',
  501
);

INSERT INTO box (id, name, permanent_hash, single_part_box, creation_timestamp, update_timestamp, storage_id) VALUES (
  2003, 'testBox-c', 'testBox3', FALSE,
  '2018-02-28 16:26:22.291010',
  '2018-02-28 16:27:22.291011',
  501
);

INSERT INTO box (id, name, permanent_hash, single_part_box, creation_timestamp, update_timestamp, storage_id) VALUES (
  2004, 'garantTestBox', 'garantTestBox', FALSE,
  '2018-02-28 16:26:22.291010',
  '2018-02-28 16:27:22.291011',
  502
);

-- set images to dummy parts
INSERT INTO part_images_paths (part_id, images) VALUES (
  1001,
  '/media/placeholders/parts/part_image_placeholder.png'
);

INSERT INTO part_images_paths (part_id, images) VALUES (
  1002,
  '/media/placeholders/parts/part_image_placeholder.png'
);

INSERT INTO part_images_paths (part_id, images) VALUES (
  1003,
  '/media/placeholders/parts/part_image_placeholder.png'
);
-- end set images to dummy parts
