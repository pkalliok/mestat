
PIDF=stamps/test-server.pid
DEPLOY_JAR=mestat-app/target/mestat-app-0.1.0-SNAPSHOT-standalone.jar
SERVER_LOG=stamps/test-server.log

test-server-unsetup:
	-test -s $(PIDF) && kill `cat $(PIDF)` && rm $(PIDF)

test-server-run: $(PIDF)

.PHONY: test-server-run test-server-unsetup

$(DEPLOY_JAR):
	cd mestat-app && lein ring uberjar

$(PIDF): $(DEPLOY_JAR) test-server-unsetup
	java -jar $< > $(SERVER_LOG) & echo $$! > $@
	until grep 'Started server on port 5005' $(SERVER_LOG); do \
	echo "Waiting for server to come up..."; \
	sleep 1; \
	done

run-tests: test-server-run
	./testing/run-tests http://localhost:5005/

