
all: package

include database/db.mk
include testing/test.mk

package: $(DEPLOY_JAR)

tags::
	ctags -R mestat-app

try-server: stamps/initialise-db-stamp
	cd mestat-app && PGHOST=localhost PGPORT=5007 PGDATABASE=mestat \
		lein ring server-headless

repl:
	cd mestat-app && PGHOST=localhost PGPORT=5007 PGDATABASE=mestat \
		lein repl

nrepl: stamps/initialise-db-stamp
	cd mestat-app && lein repl :connect localhost:5014

.PHONY: all package try-server repl nrepl

