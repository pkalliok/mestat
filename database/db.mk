
export PATH := /usr/lib/postgresql/9.4/bin:$(PATH)
PIDF=stamps/postgres.pid

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

stamps/initialise-db-stamp: database/drop-mestat.sql database/mestat.sql stamps/create-db-stamp
	cat $^ | psql -h /tmp -p 5007 mestat
	touch $@

