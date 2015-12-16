
-- name: db-points-near
-- Return points in order of proximity to :point, along with their tag(s).
SELECT loc.id, loc.coord, tag.name, tag.ns
FROM location loc, location_tag l, tag
WHERE loc.id = l.location
  AND l.tag = tag.id
  AND loc.mergedto IS NULL
ORDER BY loc.coord <-> (:point)::point
LIMIT (:limit)::integer
OFFSET (:page)::integer * (:limit)::integer;

