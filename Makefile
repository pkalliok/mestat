
all: package

include database/db.mk
include testing/test.mk

package: $(DEPLOY_JAR)

