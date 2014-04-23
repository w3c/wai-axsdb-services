wai-axsdb-services
=========

Accessibility Support Database Backend

This is the server side of the Accessibility Support Database offering CRUD REST Services to the front end.

Project page: http://www.w3.org/WAI/ACT/

Install
==========

mvn -Dmaven.test.skip=true install

cp build.default.properties ../build.properties

edit build.properties

ant upload

Login to your server and install dir:


ant deploy-all
server restart
Apache proxy config if required and restart 

TODO:
test files folder
log folder
config files