
create table if not exists location (
	id serial primary key,
	coord point not null,
	modtime timestamp not null default now(),
	mergedto integer
);

create index location_coord_key on location using gist (coord);

