
PIDF=stamps/test-server.pid
DEPLOY_JAR=mestat-app/target/mestat-app-0.1.0-SNAPSHOT-standalone.jar
SERVER_LOG=stamps/test-server.log

test-server-unsetup:
	-test -s $(PIDF) && kill `cat $(PIDF)` && rm $(PIDF)

test-server-run: $(PIDF)

.PHONY: test-server-run test-server-unsetup

$(DEPLOY_JAR): mestat-app/src
	cd mestat-app && lein ring uberjar

$(PIDF): $(DEPLOY_JAR) test-server-unsetup
	PGHOST=localhost PGPORT=5007 PGDATABASE=mestat \
	       java -jar $< > $(SERVER_LOG) & echo $$! > $@
	until grep 'Started server on port 5005' $(SERVER_LOG); do \
	echo "Waiting for server to come up..."; \
	sleep 1; \
	done

internal-tests: stamps/initialise-db-stamp
	cd mestat-app && PGHOST=localhost PGPORT=5007 PGDATABASE=mestat lein test

external-tests: test-server-run stamps/initialise-db-stamp
	./testing/test-application http://localhost:5005

database-tests: stamps/initialise-db-stamp
	./testing/test-database -h /tmp -p 5007 mestat

test: database-tests internal-tests external-tests

.PHONY: test internal-tests external-tests database-tests

