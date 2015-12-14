
PIDF=stamps/test-server.pid

test-server-unsetup:
	-test -s $(PIDF) && kill `cat $(PIDF)` && rm $(PIDF)

test-server-run: $(PIDF)

.PHONY: test-server-run test-server-unsetup

$(PIDF): test-server-unsetup
	cd mestat-app && lein ring server-headless & echo $$! > $@
	@echo "Waiting for server to come up..."
	sleep 6

run-tests: test-server-run
	./testing/run-tests http://localhost:5005/

