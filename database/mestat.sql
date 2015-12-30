
create table if not exists location (
	id serial primary key,
	coord point not null,
	modtime timestamp not null default now(),
	mergedto integer
);

create index location_coord_key on location using gist (coord);

create table if not exists tag (
	id serial primary key,
	name text not null,
	ns text not null,
	modtime timestamp not null default now(),
	priority integer,
	icon text,
	unique (ns, name)
);

create table if not exists location_tag (
	location integer not null,
	tag integer not null,
	unique (location, tag)
);

create table if not exists webuser (
	id serial primary key,
	username text not null unique,
	identification text,
	authscheme text not null default 'key',
	unique (username)
);

