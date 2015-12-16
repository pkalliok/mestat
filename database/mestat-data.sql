
insert into webuser(username) values ('system'), ('anonymous'), ('testuser');

insert into location(coord) values
 (point '(24.93545,60.16952)'),
 (point '(25.46816,65.01236)'),
 (point '(31.5,61.0)');

insert into tag(name, ns) values
 ('deleted', 'system'),
 ('city', 'testuser'),
 ('capital', 'testuser');

insert into location_tag(location, tag) values (1, 2), (1, 3), (2, 2);

