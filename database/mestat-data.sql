
insert into webuser(username, authscheme) values
 ('system', 'none'),
 ('anonymous', 'any'),
 ('testuser', 'key');

insert into location(coord) values
 (point '(24.93545,60.16952)'),
 (point '(25.46816,65.01236)'),
 (point '(31.5,61.0)');

insert into tag(name, ns, priority) values
 ('deleted', 'system', 10),
 ('city', 'testuser', 50),
 ('capital', 'testuser', 60),
 ('lake', 'testuser', 50);

insert into location_tag(location, tag) values (1, 2), (1, 3), (2, 2), (3, 4);

