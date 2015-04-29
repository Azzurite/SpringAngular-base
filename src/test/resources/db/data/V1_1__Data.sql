INSERT INTO customer (unique_name) VALUES ('TEST_CUSTOMER');
INSERT INTO property (id, customer_unique_name, name, value) VALUES (1, 'TEST_CUSTOMER', 'Vorname', 'Test');

SELECT
	pg_catalog.setval(pg_get_serial_sequence('property', 'id'), MAX(id))
FROM
	property;
