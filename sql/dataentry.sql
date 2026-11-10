INSERT INTO room (id, name) VALUES
    (NEXTVAL('room_SEQ'), 'Главный зал'),
    (NEXTVAL('room_SEQ'), 'Офис'),
    (NEXTVAL('room_SEQ'), 'Переговорная');

INSERT INTO apparatus (id, extension_number, room_id, operator_name) VALUES
    (NEXTVAL('apparatus_SEQ'), 100, 1,  'Вася'),
    (NEXTVAL('apparatus_SEQ'), 101, 1, 'Коля'),
    (NEXTVAL('apparatus_SEQ'), 102, 1, 'Петя'),
    (NEXTVAL('apparatus_SEQ'), 202, 2, 'Юля'),
    (NEXTVAL('apparatus_SEQ'), 203, 2, 'Маша');
