INSERT INTO Customer (username, name, address, email, phone_number) VALUES
    ('user1', 'Name1', 'Address1', 'email1@example.com', '0700000001'),
    ('user2', 'Name2', 'Address2', 'email2@example.com', '0700000002'),
    ('user3', 'Name3', 'Address3', 'email3@example.com', '0700000003'),
    ('user4', 'Name4', 'Address4', 'email4@example.com', '0700000004'),
    ('user5', 'Name5', 'Address5', 'email5@example.com', '0700000005');

INSERT INTO Car (price_per_day, fabric, model, registration_number, is_Booked) VALUES
    (500, 'Fabric1', 'Model1', 'REG123', TRUE),
    (600, 'Fabric2', 'Model2', 'REG124', FALSE),
    (550, 'Fabric3', 'Model3', 'REG125', FALSE),
    (650, 'Fabric4', 'Model4', 'REG126', FALSE),
    (700, 'Fabric5', 'Model5', 'REG127', FALSE);


INSERT INTO Booking (customer_ID, car_ID, booking_date, number_of_days, is_active) VALUES
    (1, 1, '2024-04-22', 10, CURRENT_DATE BETWEEN DATE '2024-04-22' AND DATE '2024-04-22' +10),
    (2, 2, '2024-01-01', 2, CURRENT_DATE BETWEEN DATE '2024-01-01' AND DATE '2024-01-01' +2),
    (3, 3, '2024-01-01', 2, CURRENT_DATE BETWEEN DATE '2024-01-01' AND DATE '2024-01-01' +2);
