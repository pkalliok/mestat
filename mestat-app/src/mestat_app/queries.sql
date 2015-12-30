
-- name: db-points-near
-- Return points in order of proximity to :point, along with their tag(s).
SELECT loc.id, loc.coord, tag.name, tag.ns
FROM (SELECT id, coord, modtime
	FROM location
	WHERE mergedto IS NULL
	ORDER BY coord <-> (:point)::point
	LIMIT (:limit)::integer
	OFFSET (:page)::integer * :limit) AS loc, location_tag l, tag
WHERE loc.id = l.location
  AND l.tag = tag.id
  AND ((:mindate)::date IS NULL OR loc.modtime > :mindate)
  AND ((:maxdate)::date IS NULL OR loc.modtime < :maxdate)
  AND ((:maxdist)::float IS NULL OR (loc.coord <-> :point) < :maxdist)
  AND ((:tagpat)::text IS NULL OR tag.name LIKE :tagpat)
  AND ((:username)::text IS NULL OR tag.ns = :username)
ORDER BY loc.coord <-> :point, tag.priority, tag.name;

