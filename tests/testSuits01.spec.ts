import { test, expect } from '@playwright/test';

test.describe('Test suite backend V1', () => {
  
  test('Test case 01 - Get all customers', async ({ request }) => {
    const getPostsResponse = await request.get('http://localhost:9090/api/v1/customers');
    expect(getPostsResponse.ok()).toBeTruthy(); 
    expect(getPostsResponse.status()).toBe(200);
  });


  
  test('Test case 02 - Update Customer', async ({ request }) => {
      const updateData = {
           "id": 1,
           "username": "user1",
           "name": "Name1",
           "address": "Fake Address",
           "email": "fakeemail1@example.com",
      };
  
      const putPostsResponse = await request.put('http://localhost:9090/api/v1/updatecustomer', {
          data: updateData
      });
  
      expect(putPostsResponse.ok()).toBeTruthy(); 
      expect(putPostsResponse.status()).toBe(200);
  });
  
  
  test('Test case 03 - Add customers', async ({ request }) => {
    
    const addCustomerResponse = await request.post('http://localhost:9090/api/v1/addcustomer', {
        data: {
            "username": "user15",
            "name": "Name15",
            "address": "Fakegatan 15",
            "email": "fakeepost15@example.com",
            "phoneNumber": "0715151515"
        }
    });

    expect(addCustomerResponse.ok()).toBeTruthy();
    expect(addCustomerResponse.status()).toBe(201);
});





  test('Test case 04 - Get all cars', async ({ request }) => {
    const getPostsResponse = await request.get('http://localhost:9090/api/v1/allcars');
    expect(getPostsResponse.ok()).toBeTruthy(); 
    expect(getPostsResponse.status()).toBe(200);
});




test('Test case 05 - Add cars', async ({ request }) => {
  const addCarResponse = await request.post('http://localhost:9090/api/v1/addcar', {
      data: {
          "pricePerDay": 1000,
          "fabric": "Fabric15",
          "model": "Model10",
          "registrationNumber": "REG160" 
      }
  });

  // Validate the response
  expect(addCarResponse.ok()).toBeTruthy(); 
  expect(addCarResponse.status()).toBe(201); 
});



   


  test('Test case 06 - Update Car', async ({ request }) => {
    const updateData = {
        "id": 1,
        "pricePerDay": 500.0,
        "fabric": "Fabric1",
        "model": "Model1",
        "registrationNumber": "REG128",
        "isBooked": true
    };

    const putPostsResponse = await request.put('http://localhost:9090/api/v1/updatecar', {
        data: updateData
    });

    expect(putPostsResponse.ok()).toBeTruthy(); 
    expect(putPostsResponse.status()).toBe(200);
});





  test('Test case 07 - Get all orders', async ({ request }) => {
    const getPostsResponse = await request.get('http://localhost:9090/api/v1/orders');
    expect(getPostsResponse.ok()).toBeTruthy(); 
    expect(getPostsResponse.status()).toBe(200);
  });



  test('Test case 08 - Create orders', async ({ request }) => {
    const createPostsResponse = await request.post('http://localhost:9090/api/v1/ordercar', {
      data: {
        "userId": 2,
        "carId": 3,
        "date": "2024-09-30T09:25:48.231Z",
        "numberOfDays": 0
      }
    });
  
    expect(createPostsResponse.ok()).toBeTruthy();
  });




  test('Test case 09 - My Order', async ({ request }) => {
    const ordereData = {
        "id": 1
    }

     const addOrderResponse = await request.post('http://localhost:9090/api/v1/myorders', {
      data: ordereData 
  });
  
    expect(addOrderResponse.ok()).toBeTruthy();
    expect(addOrderResponse.status()).toBe(200);
  });




  test('Test case 10 - delete customer by ID', async ({ request }) => {
    // Get list of all customers
    const getPostsResponse = await request.get('http://localhost:9090/api/v1/customers');
    expect(getPostsResponse.ok()).toBeTruthy();
  
     const allCustomers = await getPostsResponse.json();
     
     expect(allCustomers.length).toBeGreaterThan(2);

     const lastButOneCustomerID = allCustomers[allCustomers.length - 3].id;
  
     const deleteCustomerResponse = await request.delete(`http://localhost:9090/api/v1/deletecustomer/${lastButOneCustomerID}`);
     expect(deleteCustomerResponse.status()).toBe(404);
  
     const getDeletedCustomerResponse = await request.get(`http://localhost:9090/api/v1/deletecustomer/${lastButOneCustomerID}`);
     expect(getDeletedCustomerResponse.status()).toBe(404); 
  });

});


test('Test case 11 - delete a car by ID', async ({ request }) => {
  // Get all cars
  const getPostsResponse = await request.get('http://localhost:9090/api/v1/allcars');
  expect(getPostsResponse.ok()).toBeTruthy();

   const allCustomers = await getPostsResponse.json();

   const lastButOneCustomerID = allCustomers[allCustomers.length - 2].id;
   const deleteCustomerResponse = await request.delete(`http://localhost:9090/api/v1/deletecar/${lastButOneCustomerID}`);
   expect(deleteCustomerResponse.status()).toBe(404);
 
   const getDeletedCarResponse = await request.get(`http://localhost:9090/api/v1/getcar/${lastButOneCustomerID}`);
   expect(getDeletedCarResponse.status()).toBe(404);

});