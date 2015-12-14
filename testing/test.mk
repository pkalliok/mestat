
PIDF=stamps/test-server.pid
DEPLOY_JAR=mestat-app/target/mestat-app-0.1.0-SNAPSHOT-standalone.jar

test-server-unsetup:
	-test -s $(PIDF) && kill `cat $(PIDF)` && rm $(PIDF)

test-server-run: $(PIDF)

.PHONY: test-server-run test-server-unsetup

$(DEPLOY_JAR):
	cd mestat-app && lein ring uberjar

$(PIDF): $(DEPLOY_JAR) test-server-unsetup
	java -jar $< & echo $$! > $@
	@echo "Waiting for server to come up..."
	sleep 6

run-tests: test-server-run
	./testing/run-tests http://localhost:5005/

