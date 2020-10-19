# cdc_consumer_driven_API_contract_Pact

This folder contains everything to set up a pact broker (with postgres) 

Prerequisites:
- docker-compose

Steps:
- ``docker-compose up``

You should now be able to access the Pact Broker on <http://localhost>.


cd consumer 
    mvn verify pact:publish
    
cd provider
    mvn clean package
