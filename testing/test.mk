
PIDF=stamps/test-server.pid

test-server-unsetup:
	-test -s $(PIDF) && kill `cat $(PIDF)` && rm $(PIDF)

test-server-run: $(PIDF)

.PHONY: test-server-run test-server-unsetup

$(PIDF): test-server-unsetup
	echo "TODO: start service here" & echo $$! > $@

run-tests: test-server-run
	./testing/run-tests http://localhost:5000/

