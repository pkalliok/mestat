
insert into webuser(username) values ('system'), ('testuser');
insert into location(coord) values (point '(60.16952, 24.93545)');
insert into tag(name, ns) values ('city', 'testuser'), ('capital', 'testuser');
insert into location_tag(location, tag) values (1, 1), (1, 2);

