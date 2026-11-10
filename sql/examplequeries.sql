SELECT * FROM apparatus;
SELECT * FROM room;

SELECT id, name
FROM room
ORDER BY name;

SELECT id, extension_number, room_id, operator_name
FROM apparatus;

SELECT id, extension_number, room_id, operator_name
FROM apparatus
WHERE id = 1;

SELECT id, extension_number, room_id, operator_name
FROM apparatus
WHERE room_id = 1;

SELECT id, extension_number, room_id, operator_name
FROM apparatus
WHERE extension_number = 100;

SELECT * FROM apparatus WHERE room_id = 1;
