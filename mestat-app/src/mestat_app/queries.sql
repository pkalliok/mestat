
-- name: db-points-near
-- Return points in order of proximity to :coord, along with their tag(s).
SELECT loc.id, loc.coord, tag.name, tag.ns
FROM (SELECT id, coord, modtime
	FROM location
	WHERE mergedto IS NULL
	ORDER BY coord <-> (:coord)::point
	LIMIT (:limit)::integer
	OFFSET (:page)::integer * :limit) AS loc, location_tag l, tag
WHERE loc.id = l.location
  AND l.tag = tag.id
  AND ((:mindate)::date IS NULL OR loc.modtime > :mindate)
  AND ((:maxdate)::date IS NULL OR loc.modtime < :maxdate)
  AND ((:maxdist)::float IS NULL OR (loc.coord <-> :coord) < :maxdist)
  AND ((:tagpat)::text IS NULL OR tag.name LIKE :tagpat)
  AND ((:username)::text IS NULL OR tag.ns = :username)
ORDER BY loc.coord <-> :coord, tag.priority, tag.name;

-- name: db-location-id
-- Return the id of the location with coordinates :coord.
SELECT id FROM location WHERE coord ~= (:coord)::point;

-- name: db-insert-location!
-- Create a new location with coordinates :coord.
INSERT INTO location(coord)
VALUES ((:coord)::point);

-- name: db-tag-id
-- Return the id of the tag with namespace :ns and name :name.
SELECT id FROM tag WHERE ns = (:ns)::text and name = (:name)::text;

-- name: db-insert-tag!
INSERT INTO tag(ns, name)
VALUES ((:ns)::text, (:name)::text);

-- name: db-tag-bound?
-- Return non-empty result if there is tag with id :tag for location
-- with id :loc.
SELECT 1 FROM location_tag
WHERE location = (:loc)::integer AND tag = (:tag)::integer;

-- name: db-bind-tag!
-- Add tag with id :tag for location with id :loc.
INSERT INTO location_tag(location, tag)
VALUES ((:loc)::integer, (:tag)::integer);

