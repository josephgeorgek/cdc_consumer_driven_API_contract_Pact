package contracts
 
import org.springframework.cloud.contract.spec.Contract
 
Contract.make {
    description 'Should return one person in a list'
 
    request {
        method GET()
        url '/api/persons'
    }
    response {
        status OK()
        body '''\
            [
                {
                    "name": "Joseph",
                   
              }
            ]
        '''
        headers {
            contentType('application/json')
        }
    }
}