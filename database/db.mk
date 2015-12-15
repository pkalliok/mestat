
export PATH := /usr/lib/postgresql/9.4/bin:$(PATH)
PIDF=stamps/postgres.pid
DB_DEFS=database/drop-mestat.sql database/mestat.sql database/mestat-data.sql

database/pg-data:
	initdb $@

#ensure-postgres-unsetup:
#	-test -s $(PIDF) && kill `cat $(PIDF)` && rm $(PIDF)
#.PHONY: ensure-postgres-unsetup

$(PIDF): database/pg-data
	postgres -k /tmp -p 5007 -D $< > stamps/postgres.log & echo $$! > $@
	sleep 2

stamps/create-db-stamp: $(PIDF)
	createdb -h /tmp -p 5007 mestat
	touch $@

database/backup.sql: $(DB_DEFS)
	pg_dump --data-only -h /tmp -p 5007 mestat > $@

stamps/update-db-stamp: $(DB_DEFS) database/backup.sql stamps/create-db-stamp
	cat database/drop-mestat.sql database/mestat.sql | \
		psql -h /tmp -p 5007 mestat
	touch $@

stamps/initialise-db-stamp: database/mestat-data.sql stamps/update-db-stamp
	psql -h /tmp -p 5007 mestat < $<
	touch $@

stamps/migrate-db-stamp: database/backup.sql stamps/update-db-stamp
	psql -h /tmp -p 5007 mestat < $<
	touch $@

