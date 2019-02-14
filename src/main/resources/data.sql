insert into COUNTRIES (id, name) values (1, 'Ukraine');
insert into COUNTRIES (id, name) values (2, 'Poland');
insert into COUNTRIES (id, name) values (3, 'Germany');

insert into CITIES (id, name) values (1, 'Kiev');
insert into CITIES (id, name) values (2, 'Odessa');
insert into CITIES (id, name) values (3, 'Lviv');

insert into CITIES (id, name) values (4, 'Warsaw');
insert into CITIES (id, name) values (5, 'Krakow');

insert into CITIES (id, name) values (6, 'Berlin');
insert into CITIES (id, name) values (7, 'Frankfurt');

insert into STREETS (id, name) values (1, 'Grushevskogo');
insert into STREETS (id, name) values (2, 'Khreschatyk');
insert into STREETS (id, name) values (3, 'Taras Shevchenko Avenue');

insert into STREETS (id, name) values (4, 'Pushkinskaya');
insert into STREETS (id, name) values (5, 'Deribasovskaya');
insert into STREETS (id, name) values (6, 'Grecheskaya');

insert into STREETS (id, name) values (7, 'Svobody Avenue');
insert into STREETS (id, name) values (8, 'Mitskevycha Square');
insert into STREETS (id, name) values (9, 'Rynok Square');

insert into STREETS (id, name) values (10, 'aleja Waszyngtona');
insert into STREETS (id, name) values (11, 'Armii Ludowej');

insert into STREETS (id, name) values (12, 'Jozefa Dietla');
insert into STREETS (id, name) values (13, 'Stradomska');

insert into STREETS (id, name) values (14, 'Otto Brown strasse');
insert into STREETS (id, name) values (15, 'Friedrichstrasse');

insert into STREETS (id, name) values (16, 'Kleyerstrasse');
insert into STREETS (id, name) values (17, 'Lanstrasse');

insert into ADDRESSES (country_id, city_id, street_id) values (1, 1, 1);
insert into ADDRESSES (country_id, city_id, street_id) values (1, 1, 2);
insert into ADDRESSES (country_id, city_id, street_id) values (1, 1, 3);

insert into ADDRESSES (country_id, city_id, street_id) values (1, 2, 4);
insert into ADDRESSES (country_id, city_id, street_id) values (1, 2, 5);
insert into ADDRESSES (country_id, city_id, street_id) values (1, 2, 6);

insert into ADDRESSES (country_id, city_id, street_id) values (1, 3, 7);
insert into ADDRESSES (country_id, city_id, street_id) values (1, 3, 8);
insert into ADDRESSES (country_id, city_id, street_id) values (1, 3, 9);

insert into ADDRESSES (country_id, city_id, street_id) values (2, 4, 10);
insert into ADDRESSES (country_id, city_id, street_id) values (2, 4, 11);

insert into ADDRESSES (country_id, city_id, street_id) values (2, 5, 12);
insert into ADDRESSES (country_id, city_id, street_id) values (2, 5, 13);

insert into ADDRESSES (country_id, city_id, street_id) values (3, 6, 14);
insert into ADDRESSES (country_id, city_id, street_id) values (3, 6, 15);

insert into ADDRESSES (country_id, city_id, street_id) values (3, 7, 16);
insert into ADDRESSES (country_id, city_id, street_id) values (3, 7, 17);